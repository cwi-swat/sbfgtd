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
	protected final byte[] input;
	
	protected Set<ParseStack> stacks;
	
	private final List<INode> results;
	
	// Updated temporary stuff.
	protected ParseStack stackBeingWorkedOn;
	
	public SGLL(String start, byte[] input){
		super();
		
		this.input = input;
		
		ParseStackFrame root = new ParseStackFrame(0, new NonTerminalParseStackNode(start));
			
		stacks = new HashSet<ParseStack>();
		stacks.add(new ParseStack(root));
		
		results = new ArrayList<INode>();
	}
	
	public boolean terminalMatchesAtPosition(byte[] terminalData){
		int location = stackBeingWorkedOn.location;
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
		ParseStackFrame prev = parseStack.currentTop;
		ParseStackFrame current = new ParseStackFrame(prev.frameNumber + 1, symbolsToExpect);
		current.addEdge(prev);
		parseStack.currentTop = current;
	}
	
	public void reduceTerminal(){
		ParseStackFrame frame = stackBeingWorkedOn.currentTop;
		ParseStackNode terminalNode = frame.getCurrentNode();
		byte[] terminalData = terminalNode.getTerminalData();
		
		if(!terminalMatchesAtPosition(terminalData)){
			System.out.println("Failed to reduce(1):\t"+new String(terminalData)+"\tAt stack: "+stackBeingWorkedOn.hashCode()); // Temp
			// This stack dies of, up to the last split point.
			stacks.remove(stackBeingWorkedOn);
			return;
		}
		
		System.out.println("Matched terminal:\t"+new String(terminalData)+"\tAt stack: "+stackBeingWorkedOn.hashCode());
		
		// Construct the result.
		frame = new ParseStackFrame(frame);
		frame.addResult(new TerminalNode(terminalData));
		stackBeingWorkedOn.currentTop = frame;
		
		// Try to reduce the non terminals on top of the stack (if possible).
		stackBeingWorkedOn.location += terminalData.length;
	}
	
	private void reduceFrame(ParseStack stack, ParseStackFrame frame){
		if(frame.frameNumber == 0){ // Root reached.
			stacks.remove(stack); // Remove the stack from the todo-list.
			
			if(stack.location != input.length){
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
		int byteToMoveTo = stack.location;
		List<ParseStackFrame> prevFrames = frame.getEdges(); // TODO Centralize edge data
		for(int j = prevFrames.size() - 1; j >= 1; j--){
			ParseStackFrame prevFrame = prevFrames.get(j);
			ParseStackNode node = prevFrame.getCurrentNode();
			prevFrame = new ParseStackFrame(prevFrame);
			prevFrame.addResult(new NonTerminalNode(node.getName(), results)); // Always a non-terminal
			ParseStack newStack = new ParseStack(prevFrame, byteToMoveTo);
			stacks.add(newStack);
		}
		
		ParseStackFrame prevFrame = prevFrames.get(0);
		ParseStackNode node = prevFrame.getCurrentNode();
		prevFrame = new ParseStackFrame(prevFrame);
		prevFrame.addResult(new NonTerminalNode(node.getName(), results)); // Always a non-terminal
		stack.currentTop = prevFrame;
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
				int location = stack.location;
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
				ParseStackFrame frame = stack.currentTop;
				
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
