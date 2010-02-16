package gll;

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
	private List<ParseStackFrame> lastIteration;
	
	private List<ParseStackFrame> lastExpects;
	
	private final ParseStackNode root;
	private final ParseStackFrame rootFrame;
	
	public SGLL(String start, byte[] input){
		super();
		
		this.input = input;
		
		todoList = new HashSet<ParseStackFrame>();
		
		lastIteration = new ArrayList<ParseStackFrame>();
		
		lastExpects = new ArrayList<ParseStackFrame>();
		
		// Initialize
		root = new NonTerminalParseStackNode(start);
		rootFrame = new ParseStackFrame(root);
		
		todoList.add(rootFrame);
		
		tryExpand(rootFrame);
		
		for(int i = lastIteration.size() - 1; i >= 0; i--){
			todoList.add(lastIteration.get(i));
		}
	}
	
	public void expect(ParseStackNode... symbolsToExpect){
		ParseStackFrame newFrame = new ParseStackFrame(symbolsToExpect);
		newFrame.addEdge(stackFrameBeingWorkedOn);
		lastExpects.add(newFrame);
	}
	
	private ParseStackFrame updateFrame(ParseStackFrame parseStackFrame, INode result){
		ParseStackFrame clone = new ParseStackFrame(parseStackFrame);
		ParseStackNode currentNode = clone.getCurrentNode();
		currentNode.addResult(result);
		clone.moveLevel(currentNode.getLength());
		return clone;
	}
	
	private ParseStackFrame mergeFrames(ParseStackFrame psf1, ParseStackFrame psf2){
		return psf1.mergeWith(psf2);
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
	
	private void reduceTerminal(ParseStackFrame frame){
		ParseStackNode terminal = frame.getCurrentNode();
		byte[] data = terminal.getTerminalData();
		
		int location = frame.getLevel();
		if(location + data.length > input.length){
			return; // Can't reduce.
		}
		
		for(int i = data.length - 1; i >= 0; i--){
			if(data[i] != input[location + i]) return; // Didn't match
		}
		
		Set<ParseStackFrame> edges = frame.getEdges();
		Iterator<ParseStackFrame> edgesIterator = edges.iterator();
		while(edgesIterator.hasNext()){
			ParseStackFrame prevFrame = edgesIterator.next();
			lastIteration.add(prevFrame);
		}
	}
	
	private void reduceNonTerminals(ParseStackFrame frame){// TODO Implement
		ParseStackNode node = frame.getCurrentNode();
		Set<ParseStackFrame> edges = frame.getEdges();
		if(edges.size() == 0){
			return; // Root reached.
		}
	}
	
	private void tryExpand(ParseStackFrame frame){// TODO Implement
		// TODO Merge stacks where possible
	}
	
	public INode parse(){// TODO Implement
		do{
			// Initialize.
			lastIteration = new ArrayList<ParseStackFrame>();
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
				}else if(nextLevel == closestNextLevel){
					leastProgressedStacks.add(frame);
				}
			}
			
			// Do terminal reductions where possible.
			for(int i = leastProgressedStacks.size() - 1; i >= 0; i--){
				ParseStackFrame frame = leastProgressedStacks.get(i);
				todoList.remove(frame);
				
				frame.moveToNextNode();
				reduceTerminal(frame);
			}
			
			// Do non-terminal reductions where possible.
			List<ParseStackFrame> stacksToExpand = new ArrayList<ParseStackFrame>();
			do{
				List<ParseStackFrame> copyOfLastIteration = lastIteration;
				lastIteration = new ArrayList<ParseStackFrame>();
				for(int i = copyOfLastIteration.size() - 1; i >= 0; i--){
					ParseStackFrame frame = copyOfLastIteration.get(i);
					if(frame.isComplete()){
						reduceNonTerminals(frame);
					}else{
						stacksToExpand.add(frame);
					}
				}
			}while(lastIteration.size() > 0);
			
			// Expand stacks. TODO Keep looping till fully expanded
			List<ParseStackFrame> copyOfStacksToExpand = stacksToExpand;
			for(int i = copyOfStacksToExpand.size() - 1; i >= 0; i--){
				ParseStackFrame frame = copyOfStacksToExpand.get(i);
				
				tryExpand(frame);
			}
			
			// Update the todo list.
			for(int i = lastIteration.size() - 1; i >= 0; i--){
				todoList.add(lastIteration.get(i));
			}
		}while(todoList.size() > 0);
		
		return new NonTerminalNode(root.getName(), rootFrame.getResults());// Temp
	}
}
