package gll;

import gll.nodes.INode;
import gll.nodes.NonTerminalNode;
import gll.nodes.TerminalNode;
import gll.stack.NonTerminalParseStackNode;
import gll.stack.ParseStack;
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
	
	private Set<ParseStack> stacks;
	
	private final List<INode> results;
	
	// Updated temporary stuff.
	private ParseStack stackBeingWorkedOn;
	
	public SGLL(String start, byte[] input){
		super();
		
		this.input = input;
		
		ParseStackFrame root = createParseStackFrame(new NonTerminalParseStackNode(start));
			
		stacks = new HashSet<ParseStack>();
		stacks.add(new ParseStack(root));
		
		results = new ArrayList<INode>();
	}
	
	private boolean terminalMatchesAtPosition(byte[] terminalData){
		int location = stackBeingWorkedOn.getCurrentLocation();
		int size = terminalData.length;
		
		if(location + size > input.length) return false;
		
		for(int i = size - 1; i >= 0; i--){
			if(terminalData[i] != input[location + i]){
				return false;
			}
		}
		return true;
	}
	
	public void expectAlternative(ParseStackNode... symbolsToExpect){
		ParseStack parseStack = new ParseStack(stackBeingWorkedOn);
		stacks.add(parseStack);
		stacks.remove(stackBeingWorkedOn); // TODO Think of a better way to do this.
		updateStack(parseStack, symbolsToExpect);
	}
	
	public void expect(ParseStackNode... symbolsToExpect){
		updateStack(stackBeingWorkedOn, symbolsToExpect);
	}
	
	// TODO Add sharing.
	private void updateStack(ParseStack parseStack, ParseStackNode... symbolsToExpect){
		ParseStackFrame current = createParseStackFrame(symbolsToExpect);
		current.addEdge(parseStack.getTop());
		parseStack.setTop(current);
	}
	
	public void reduceTerminal(){
		ParseStackFrame frame = stackBeingWorkedOn.getTop();
		ParseStackNode terminalNode = frame.getCurrentNode();
		byte[] terminalData = terminalNode.getTerminalData();
		
		if(!terminalMatchesAtPosition(terminalData)){
			System.out.println("Failed to reduce(1):\t"+new String(terminalData)+"\tAt stack: "+stackBeingWorkedOn.hashCode()); // Temp
			// This stack dies of, up to the last split point.
			stacks.remove(stackBeingWorkedOn);
			return;
		}
		
		//System.out.println("Matched terminal:\t"+new String(terminalData)+"\tAt stack: "+stackBeingWorkedOn.hashCode());
		
		// Construct the result.
		frame.addResult(new TerminalNode(terminalData));
		stackBeingWorkedOn.setTop(frame);
		
		// Try to reduce the non terminals on top of the stack (if possible).
		stackBeingWorkedOn.moveLocation(terminalData.length);
	}
	
	private void reduceFrame(ParseStack stack, ParseStackFrame frame){
		List<ParseStackFrame> prevFrames = frame.getEdges();
		int numberOfPrevFrames = prevFrames.size();
		
		if(numberOfPrevFrames == 0){ // Root reached.
			stacks.remove(stack); // Remove the stack from the todo-list.
			
			if(stack.getCurrentLocation() != input.length){
				// Temp
				System.out.println("Failed to reduce(2):\t"+frame+"\tAt stack: "+stack.hashCode()); // Temp
				
				return;
			}
			
			// Temp
			System.out.println("Reduce:\t"+frame+"\tAt stack: "+stack.hashCode()); // Temp
			
			results.add(frame.getResults()[0]); // Temp
			
			return;
		}
		
		// Temp
		System.out.println("Reduce:\t"+frame+"\tAt stack: "+stack.hashCode()); // Temp

		// TODO Construct the result.
		INode[] results = frame.getResults();
		
		// Update the stack.
		int byteToMoveTo = stack.getCurrentLocation();
		for(int j = numberOfPrevFrames - 1; j >= 1; j--){
			ParseStackFrame prevFrame = prevFrames.get(j);
			ParseStackNode node = prevFrame.getCurrentNode();
			prevFrame = createParseStackFrame(prevFrame, node.getName(), results);
			ParseStack newStack = new ParseStack(prevFrame, byteToMoveTo);
			stacks.add(newStack);
		}
		
		ParseStackFrame prevFrame = prevFrames.get(0);
		ParseStackNode node = prevFrame.getCurrentNode();
		prevFrame = createParseStackFrame(prevFrame, node.getName(), results);
		stack.setTop(prevFrame);
	}
	
	private ParseStackFrame createParseStackFrame(ParseStackNode... symbolsToExpect){
		// TODO Add sharing.
		return new ParseStackFrame(symbolsToExpect);
	}
	
	private ParseStackFrame createParseStackFrame(ParseStackFrame frameToClone, String nodeName, INode[] results){
		// TODO Add sharing.
		ParseStackFrame clonedStackFrame = new ParseStackFrame(frameToClone);
		clonedStackFrame.addResult(new NonTerminalNode(nodeName, results)); // Always a non-terminal
		
		return clonedStackFrame;
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
	
	public INode parse(){
		do{
			int leastProgressedLocation = Integer.MAX_VALUE;
			List<ParseStack> leastProgressedStacks = new ArrayList<ParseStack>();
			
			// Clone
			Iterator<ParseStack> stacksIterator = stacks.iterator();
			while(stacksIterator.hasNext()){
				ParseStack stack = stacksIterator.next();
				int location = stack.getCurrentLocation();
				if(location < leastProgressedLocation){
					leastProgressedLocation = location;
					leastProgressedStacks = new ArrayList<ParseStack>();
					leastProgressedStacks.add(stack);
				}else if(location == leastProgressedLocation){
					leastProgressedStacks.add(stack);
				}
			}
			
			// Parse
			for(int i = leastProgressedStacks.size() - 1; i >= 0; i--){
				ParseStack stack = leastProgressedStacks.get(i);
				ParseStackFrame frame = stack.getTop();
				
				stackBeingWorkedOn = stack;
				
				if(frame.isComplete()){
					reduceFrame(stack, frame);
				}else{
					frame.nextSymbol();
					callMethod(frame.getCurrentNode().getMethodName());
				}
			}
		}while(stacks.size() > 0);
		
		// Temp
		// Construct the result
		int nrOfResults = results.size();
		INode[] allResults = new INode[nrOfResults];
		for(int i = nrOfResults - 1; i >= 0; i--){
			allResults[i] = results.get(i);
		}
		
		return new NonTerminalNode("parsetree", allResults);
	}
}
