package gll;

import gll.nodes.Alternative;
import gll.nodes.INode;
import gll.nodes.NonTerminalNode;
import gll.stack.NonTerminalParseStackNode;
import gll.stack.ParseStackFrame;
import gll.stack.ParseStackNode;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class SGLL implements IGLL{
	private final byte[] input;
	
	private final Set<ParseStackFrame> todoList;
	
	// Updatable
	private ParseStackFrame stackFrameBeingWorkedOn;
	private List<ParseStackFrame> lastIterationTodoList;
	private List<ParseStackFrame> lastTerminalReducedStacks;
	private List<ParseStackFrame> possiblyMergeableStacks;
	private List<ParseStackFrame> expandedStacks;
	
	private List<ParseStackFrame> lastExpects;
	
	private final List<INode> parseResults;
	
	public SGLL(String start, byte[] input){
		super();
		
		this.input = input;
		
		todoList = new HashSet<ParseStackFrame>();
		
		lastIterationTodoList = new ArrayList<ParseStackFrame>();
		lastTerminalReducedStacks = new ArrayList<ParseStackFrame>();
		possiblyMergeableStacks = new ArrayList<ParseStackFrame>();
		expandedStacks = new ArrayList<ParseStackFrame>();
		
		lastExpects = new ArrayList<ParseStackFrame>();
		
		parseResults = new ArrayList<INode>();
		
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
	
	private void tryExpand(ParseStackFrame frame){// TODO Implement
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
			for(int j = possiblyMergeableStacks.size() - 1; j >= 0; j--){
				ParseStackFrame possiblyAnAlternative = possiblyMergeableStacks.get(j);
				if(possiblyAnAlternative.isMergable(expectFrame)){
					possiblyAnAlternative.mergeWith(expectFrame);
					continue OUTER;
				}
			}
			
			if(expectFrame.getNextNode().isNonTerminal()){ // Only check if we have a non terminal on the left side.
				if(stackFrameBeingWorkedOn.isMergable(expectFrame)){ // Found left recursion
					stackFrameBeingWorkedOn.addEdge(stackFrameBeingWorkedOn);
					continue;
				}
			}
			
			// TODO Check for transitive self references (more).
			
			// TODO Check for opportunities to introduce 'cycles' on parallel stacks (more).
			
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
		
		INode[] results = frame.getResults();
		
		Set<ParseStackFrame> edges = frame.getEdges();
		Iterator<ParseStackFrame> edgesIterator = edges.iterator();
		while(edgesIterator.hasNext()){
			ParseStackFrame prevFrame = edgesIterator.next();
			ParseStackNode node = prevFrame.getNextNode();
			prevFrame = updateFrame(prevFrame, new NonTerminalNode(node.getNonTerminalName(), results));
			
			lastIterationTodoList.add(prevFrame);
		}
	}
	
	private void reduceNonTerminal(ParseStackFrame frame){
		Set<ParseStackFrame> edges = frame.getEdges();
		if(edges.size() == 0){
			if(frame.getLevel() != input.length){
				return; // Parse failed.
			}
			
			INode[] results = frame.getResults();
			for(int i = results.length - 1; i >= 0; i--){
				parseResults.add(results[i]);
			}
			return; // Root reached.
		}
		
		INode[] results = frame.getResults();
		
		Iterator<ParseStackFrame> edgesIterator = edges.iterator();
		OUTER : while(edgesIterator.hasNext()){
			ParseStackFrame prevFrame = edgesIterator.next();
			ParseStackNode node = prevFrame.getNextNode();
			prevFrame = updateFrame(prevFrame, new NonTerminalNode(node.getNonTerminalName(), results));
			
			for(int i = lastIterationTodoList.size() - 1; i >= 0; i--){
				ParseStackFrame possiblyAnAlternative = lastIterationTodoList.get(i);
				if(possiblyAnAlternative.isMergable(prevFrame)){
					possiblyAnAlternative.mergeWith(prevFrame);
					continue OUTER;
				}
			}
			for(int i = lastTerminalReducedStacks.size() - 1; i >= 0; i--){
				ParseStackFrame possiblyAnAlternative = lastTerminalReducedStacks.get(i);
				if(possiblyAnAlternative.isMergable(prevFrame)){
					possiblyAnAlternative.mergeWith(prevFrame);
					continue OUTER;
				}
			}
			
			lastIterationTodoList.add(prevFrame);
		}
	}
	
	private void expand(List<ParseStackFrame> stacksToExpand){
		expandedStacks = new ArrayList<ParseStackFrame>();
		
		List<ParseStackFrame> copyOfStacksToExpand = stacksToExpand;
		do{
			int lowestStackFrameNumber = Integer.MAX_VALUE;
			for(int i = copyOfStacksToExpand.size() - 1; i >= 0; i--){
				ParseStackFrame frame = copyOfStacksToExpand.get(i);
				int frameNumber = frame.getFrameNumber();
				if(frameNumber < lowestStackFrameNumber){
					stacksToExpand = new ArrayList<ParseStackFrame>();
					stacksToExpand.add(frame);
					lowestStackFrameNumber = frameNumber;
				}else if(frameNumber == lowestStackFrameNumber){
					stacksToExpand.add(frame);
				}
			}
			
			lastIterationTodoList = new ArrayList<ParseStackFrame>();
			for(int i = stacksToExpand.size() - 1; i >= 0; i--){
				ParseStackFrame frame = stacksToExpand.get(i);
				copyOfStacksToExpand.remove(frame); // TODO Optimize.
				
				tryExpand(frame);
				lastExpects = new ArrayList<ParseStackFrame>(); // Clear.
			}
			stackFrameBeingWorkedOn = null; // Clear.
			copyOfStacksToExpand.addAll(lastIterationTodoList);
		}while(copyOfStacksToExpand.size() > 0);
		
		possiblyMergeableStacks = new ArrayList<ParseStackFrame>();
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
			lastTerminalReducedStacks = lastIterationTodoList;
			List<ParseStackFrame> copyOfLastIteration = lastIterationTodoList;
			do{
				List<ParseStackFrame> stacksToReduce = new ArrayList<ParseStackFrame>();
				int highestStackFrameNumber = -1;
				for(int i = copyOfLastIteration.size() - 1; i >= 0; i--){
					ParseStackFrame frame = copyOfLastIteration.get(i);
					int frameNumber = frame.getFrameNumber();
					if(frameNumber > highestStackFrameNumber){
						stacksToReduce = new ArrayList<ParseStackFrame>();
						stacksToReduce.add(frame);
						highestStackFrameNumber = frameNumber;
					}else if(frameNumber == highestStackFrameNumber){
						stacksToReduce.add(frame);
					}
				}
				
				lastIterationTodoList = new ArrayList<ParseStackFrame>();
				for(int i = stacksToReduce.size() - 1; i >= 0; i--){
					ParseStackFrame frame = stacksToReduce.get(i);
					copyOfLastIteration.remove(frame); // TODO Optimize.
					
					if(frame.isComplete()){
						reduceNonTerminal(frame);
					}else{
						stacksToExpand.add(frame);
					}
				}
				copyOfLastIteration.addAll(lastIterationTodoList);
			}while(copyOfLastIteration.size() > 0);
			
			lastTerminalReducedStacks = new ArrayList<ParseStackFrame>(); // Clear.
			
			// Expand stacks.
			expand(stacksToExpand);
			
			// Update the todo list.
			for(int i = expandedStacks.size() - 1; i >= 0; i--){
				todoList.add(expandedStacks.get(i));
			}
		}while(todoList.size() > 0);
		
		// Return the result(s).
		int nrOfParseResults = parseResults.size();
		
		if(nrOfParseResults == 0) throw new RuntimeException("Parse Error");
		if(nrOfParseResults == 1) return new NonTerminalNode("parsetree", new INode[]{parseResults.get(0)});
		
		INode[] results = new INode[nrOfParseResults];
		for(int i = nrOfParseResults - 1; i >= 0; i--){
			results[i] = parseResults.get(i);
		}
		
		return new NonTerminalNode("parsetree", new INode[]{new Alternative(results)});
	}
}
