package gll;

import gll.nodes.Alternative;
import gll.nodes.INode;
import gll.nodes.NonTerminalNode;
import gll.stack.NonTerminalParseStackNode;
import gll.stack.ParseStackFrame;
import gll.stack.ParseStackNode;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SGLL implements IGLL{
	private final byte[] input;
	
	private final List<ParseStackFrame> todoList;
	
	// Updatable
	private ParseStackFrame stackFrameBeingWorkedOn;
	private List<ParseStackFrame> lastIterationTodoList;
	private List<ParseStackFrame> possiblyMergeableStacks;
	private List<ParseStackFrame> expandedStacks;
	
	private List<ParseStackFrame> lastExpects;
	
	private ParseStackFrame rootFrame;
	
	public SGLL(String start, byte[] input){
		super();
		
		this.input = input;
		
		todoList = new ArrayList<ParseStackFrame>();
		
		lastIterationTodoList = new ArrayList<ParseStackFrame>();
		possiblyMergeableStacks = new ArrayList<ParseStackFrame>();
		expandedStacks = new ArrayList<ParseStackFrame>();
		
		lastExpects = new ArrayList<ParseStackFrame>();
		
		rootFrame = null;
		
		// Initialize
		ParseStackNode root = new NonTerminalParseStackNode(start);
		ParseStackFrame rootFrame = new ParseStackFrame(root);
		lastIterationTodoList.add(rootFrame);
	}
	
	public void expect(ParseStackNode... symbolsToExpect){
		ParseStackFrame newFrame = new ParseStackFrame(stackFrameBeingWorkedOn, symbolsToExpect);
		newFrame.setLevel(stackFrameBeingWorkedOn.getLevel());
		lastExpects.add(newFrame);
	}
	
	private void callMethod(String methodName){
		try{
			Method method = this.getClass().getMethod(methodName);
			method.invoke(this);
		}catch(Exception ex){
			// Not going to happen.
			ex.printStackTrace(); // Temp
		}
	}
	
	private boolean containsNonProductiveSelfRecursion(ParseStackFrame frame){
		if(!frame.isProductive()){
			List<ParseStackFrame> edges = frame.getEdges();
			Iterator<ParseStackFrame> edgesIterator = edges.iterator();
			while(edgesIterator.hasNext()){
				ParseStackFrame edge = edgesIterator.next();
				
				List<ParseStackFrame> framesToCheck = new ArrayList<ParseStackFrame>();
				List<ParseStackFrame> framesChecked = new ArrayList<ParseStackFrame>();
				
				framesToCheck.add(edge);
				
				while(framesToCheck.size() > 0){
					ParseStackFrame frameToCheck = framesToCheck.remove(0);
					if(framesChecked.contains(frameToCheck)) continue;
					
					framesChecked.add(frameToCheck);
					
					if(frameToCheck.isProductive()) break;
					if(frameToCheck.hasSameFirstNonTerminal(frame)){
						// Encountered non productive self loop frame.
						return true;
					}
					
					edges = frameToCheck.getEdges();
					edgesIterator = edges.iterator();
					while(edgesIterator.hasNext()){
						framesToCheck.add(edgesIterator.next());
					}
				}
			}
		}
		
		return false;
	}
	
	private void tryExpand(ParseStackFrame frame){
		ParseStackNode node = frame.getNextNode();
		if(node.isTerminal()){
			expandedStacks.add(frame);
			return; // Can't unfold any further.
		}
		
		stackFrameBeingWorkedOn = frame;
		callMethod(node.getMethodName());
		
		// Merge stack if possible.
		OUTER : for(int i = lastExpects.size() - 1; i >= 0; i--){
			ParseStackFrame expectFrame = lastExpects.get(i);
			
			Iterator<ParseStackFrame> possiblyMergeableStacksIterator = possiblyMergeableStacks.iterator();
			while(possiblyMergeableStacksIterator.hasNext()){ // Share (also handles left recursion).
				ParseStackFrame possiblyAnAlternative = possiblyMergeableStacksIterator.next();
				if(possiblyAnAlternative.isMergable(expectFrame)){
					if(possiblyAnAlternative.isProductive() || possiblyAnAlternative != stackFrameBeingWorkedOn){ // Remove non-productive self loops.
						possiblyAnAlternative.mergeWith(expectFrame);
						if(containsNonProductiveSelfRecursion(expectFrame)){// Mark 'useless' cycles.
							possiblyAnAlternative.markSelfRecursive();
						}
					}
					
					continue OUTER;
				}
			}
			
			possiblyMergeableStacks.add(expectFrame);
			lastIterationTodoList.add(expectFrame);
		}
	}
	
	private ParseStackFrame updateFrame(ParseStackFrame parseStackFrame, INode result){
		ParseStackFrame clone = new ParseStackFrame(parseStackFrame);
		clone.moveToNextNode();
		ParseStackNode currentNode = clone.getCurrentNode();
		currentNode.addResult(result);
		clone.moveLevel(currentNode.getLength());
		return clone;
	}
	
	private boolean tryMerge(ParseStackFrame frame){
		Iterator<ParseStackFrame> possiblyMergeableStacksIterator = possiblyMergeableStacks.iterator();
		while(possiblyMergeableStacksIterator.hasNext()){
			ParseStackFrame possiblyAnAlternative = possiblyMergeableStacksIterator.next();
			if(possiblyAnAlternative.isMergable(frame)){
				if(possiblyAnAlternative.isMarkedSelfRecursive()){
					System.out.println("Possibly detected (useless) self recursion."); // Temp.
				}
				
				possiblyAnAlternative.mergeWith(frame);
				return true;
			}
		}
		return false;
	}
	
	private void reduceTerminal(ParseStackFrame frame){
		frame.moveToNextNode();
		ParseStackNode terminal = frame.getCurrentNode();
		byte[] data = terminal.getTerminalData();
		
		int location = frame.getLevel();
		if(location + data.length > input.length){
			return; // Can't reduce.
		}
		
		for(int i = data.length - 1; i >= 0; i--){
			if(data[i] != input[location + i]) return; // Didn't match
		}
		
		// Don't 'complete' the frame if it isn't done.
		if(!frame.isComplete()){
			frame.moveLevel(terminal.getLength());
			lastIterationTodoList.add(frame);
			return;
		}
		
		List<INode> results = frame.getResults();
		
		List<ParseStackFrame> edges = frame.getEdges();
		Iterator<ParseStackFrame> edgesIterator = edges.iterator();
		while(edgesIterator.hasNext()){
			ParseStackFrame prevFrame = edgesIterator.next();
			ParseStackNode node = prevFrame.getNextNode();
			prevFrame = updateFrame(prevFrame, new NonTerminalNode(node.getNonTerminalName(), results));
			
			if(tryMerge(prevFrame)) continue;

			possiblyMergeableStacks.add(prevFrame);
			lastIterationTodoList.add(prevFrame);
		}
	}
	
	private void reduceNonTerminal(ParseStackFrame frame){
		List<ParseStackFrame> edges = frame.getEdges();
		if(edges.size() == 0){
			if(frame.getLevel() != input.length){
				return; // Parse failed.
			}
			
			if(rootFrame != null) throw new RuntimeException("Something went wrong.");
			
			rootFrame = frame; // Only done once.
			
			return; // Root reached.
		}
		
		List<INode> results = frame.getResults();
		
		Iterator<ParseStackFrame> edgesIterator = edges.iterator();
		while(edgesIterator.hasNext()){
			ParseStackFrame prevFrame = edgesIterator.next();
			ParseStackNode node = prevFrame.getNextNode();
			prevFrame = updateFrame(prevFrame, new NonTerminalNode(node.getNonTerminalName(), results));
			
			if(tryMerge(prevFrame)) continue;
			
			possiblyMergeableStacks.add(prevFrame);
			lastIterationTodoList.add(prevFrame);
		}
	}
	
	private void expand(List<ParseStackFrame> stacksToExpand){
		expandedStacks = new ArrayList<ParseStackFrame>();
		
		do{
			lastIterationTodoList = new ArrayList<ParseStackFrame>(); // Clear.
			Iterator<ParseStackFrame> stacksToExpandIterator = stacksToExpand.iterator();
			while(stacksToExpandIterator.hasNext()){
				ParseStackFrame frame = stacksToExpandIterator.next();
				stacksToExpandIterator.remove();
				
				tryExpand(frame);
				lastExpects = new ArrayList<ParseStackFrame>(); // Clear.
			}
			stackFrameBeingWorkedOn = null; // Clear.
			stacksToExpand.addAll(lastIterationTodoList);
		}while(stacksToExpand.size() > 0);
		
		possiblyMergeableStacks = new ArrayList<ParseStackFrame>(); // Clear.
	}
	
	public INode parse(){
		expand(lastIterationTodoList);
		
		for(int i = expandedStacks.size() - 1; i >= 0; i--){
			todoList.add(expandedStacks.get(i));
		}
		
		do{
			// Initialize.
			lastIterationTodoList = new ArrayList<ParseStackFrame>();
			lastExpects = new ArrayList<ParseStackFrame>();
			
			// Get least progressed stacks from the todo list.
			List<ParseStackFrame> leastProgressedStacks = null;
			int closestNextLevel = Integer.MAX_VALUE;
			Iterator<ParseStackFrame> todoListIterator = todoList.iterator();
			while(todoListIterator.hasNext()){
				ParseStackFrame frame = todoListIterator.next();
				int nextLevel = frame.getNextLevel();
				if(nextLevel < closestNextLevel){
					leastProgressedStacks = new ArrayList<ParseStackFrame>();
					leastProgressedStacks.add(frame);
					closestNextLevel = nextLevel;
				}else if(nextLevel == closestNextLevel){
					leastProgressedStacks.add(frame);
				}
			}
			
			// Do terminal reductions where possible.
			for(int i = leastProgressedStacks.size() - 1; i >= 0; i--){
				ParseStackFrame frame = leastProgressedStacks.get(i);
				todoList.remove(frame);
				
				reduceTerminal(frame);
			}
			
			// Do non-terminal reductions where possible.
			List<ParseStackFrame> stacksToExpand = new ArrayList<ParseStackFrame>();
			List<ParseStackFrame> stacksToReduce = new ArrayList<ParseStackFrame>();
			stacksToReduce.addAll(lastIterationTodoList);
			do{
				lastIterationTodoList = new ArrayList<ParseStackFrame>(); // Clear.
				for(int i = stacksToReduce.size() - 1; i >= 0; i--){
					ParseStackFrame frame = stacksToReduce.get(i);
					
					if(frame.isComplete()){
						reduceNonTerminal(frame);
					}else{
						stacksToExpand.add(frame);
					}
				}
				
				stacksToReduce = new ArrayList<ParseStackFrame>();
				stacksToReduce.addAll(lastIterationTodoList);
			}while(stacksToReduce.size() > 0);
			
			possiblyMergeableStacks = new ArrayList<ParseStackFrame>(); // Clear.
			
			// Expand stacks.
			expand(stacksToExpand);
			
			// Update the todo list.
			for(int i = expandedStacks.size() - 1; i >= 0; i--){
				todoList.add(expandedStacks.get(i));
			}
		}while(todoList.size() > 0);
		
		// Return the result(s).
		if(rootFrame == null) throw new RuntimeException("Parse Error.");
		
		List<INode> parseResults = rootFrame.getResults();
		if(parseResults.size() == 1) return new NonTerminalNode("parsetree", parseResults.get(0));
		
		return new NonTerminalNode("parsetree", new Alternative(parseResults));
	}
}
