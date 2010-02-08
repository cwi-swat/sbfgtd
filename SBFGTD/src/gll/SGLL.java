package gll;

import gll.nodes.INode;
import gll.stack.NonTerminalParseStackNode;
import gll.stack.ParseStack;
import gll.stack.ParseStackFrame;
import gll.stack.ParseStackNode;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class SGLL implements IGLL{
	protected final byte[] input;
	
	protected final ParseStackFrame root;
	protected List<ParseStack> stacks;
	
	// Updated temporary stuff.
	protected ParseStack stackBeingWorkedOn;
	
	public SGLL(String start, byte[] input){
		super();
		
		this.input = input;
		
		root = new ParseStackFrame(new NonTerminalParseStackNode(start));
			
		stacks = new ArrayList<ParseStack>();
		stacks.add(new ParseStack(root));
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
		updateStack(parseStack, symbolsToExpect);
	}
	
	public void expect(ParseStackNode... symbolsToExpect){
		updateStack(stackBeingWorkedOn, symbolsToExpect);
	}
	
	// TODO Add sharing.
	private void updateStack(ParseStack parseStack, ParseStackNode... symbolsToExpect){
		ParseStackFrame prev = parseStack.currentTop;
		ParseStackFrame current = new ParseStackFrame(symbolsToExpect);
		for(int i = symbolsToExpect.length - 1; i >= 0; i--){
			symbolsToExpect[i].addEdge(prev);
		}
		parseStack.currentTop = current;
		
		stacks.add(parseStack);
	}
	
	public void reduceTerminal(){
		ParseStackFrame frame = stackBeingWorkedOn.currentTop;
		ParseStackNode terminalNode = frame.getCurrentNode();
		byte[] terminalData = terminalNode.getTerminalData();
		
		if(!terminalMatchesAtPosition(terminalData)){
			System.out.println("Failed to reduce terminal:\t"+new String(terminalData)+"\tAt stack: "+stackBeingWorkedOn.hashCode()); // Temp
			// This stack dies of, up to the last split point.
			return;
		}
		
		// Temp
		System.out.println("Reduce terminal:\t"+new String(terminalData)+"\tAt stack: "+stackBeingWorkedOn.hashCode()); // Temp

		// TODO Construct the result.
		
		// Update the stack.
		int byteToMoveTo = stackBeingWorkedOn.location + terminalData.length;
		List<ParseStackFrame> prevFrames = terminalNode.edges;
		for(int i = prevFrames.size() - 1; i >= 1; i--){
			ParseStack stack = new ParseStack(prevFrames.get(i), byteToMoveTo);
			stacks.add(stack);
			tryNonTerminalReduction(stack);
		}
		
		stackBeingWorkedOn.currentTop = prevFrames.get(0);
		stackBeingWorkedOn.location = byteToMoveTo;
		stacks.add(stackBeingWorkedOn);
		tryNonTerminalReduction(stackBeingWorkedOn);
		
		// Try to reduce the non terminals on top of the stack (if possible).
		
	}
	
	private boolean tryNonTerminalReduction(ParseStack stack){
		ParseStackFrame frame = stack.currentTop;
		ParseStackNode node = frame.getCurrentNode();
		if(node.isNonTerminal()){
			List<ParseStackFrame> prevFrames = node.edges;
			int nrOfPrevFrames = prevFrames.size();
			
			if(nrOfPrevFrames == 0){ // Root reached.
				if(stack.location == input.length){
					// Temp
					System.out.println("Reduce non-terminal:\t"+node.getNonTerminalName()+"\tAt stack: "+stackBeingWorkedOn.hashCode()); // Temp
					
					// TODO Construct the result.
				}else{
					// Temp
					System.out.println("Failed to reduce non-terminal:\t"+node.getNonTerminalName()+"\tAt stack: "+stackBeingWorkedOn.hashCode()); // Temp
				}
				
				return false;
			}
			
			// Temp
			System.out.println("Reduce non-terminal:\t"+node.getNonTerminalName()+"\tAt stack: "+stackBeingWorkedOn.hashCode()); // Temp

			// TODO Construct the result.
			
			// Update the stack.
			int byteToMoveTo = stack.location;
			for(int i = nrOfPrevFrames - 1; i >= 1; i--){
				ParseStack newStack = new ParseStack(prevFrames.get(i), byteToMoveTo);
				if(!tryNonTerminalReduction(newStack)){
					stacks.add(newStack);
				}
			}
			
			stack.currentTop = prevFrames.get(0);
			if(!tryNonTerminalReduction(stack)){
				stacks.add(stack);
			}
			return true;
		}
		return false;
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
		List<ParseStack> currentStacks = stacks;
		stacks = new ArrayList<ParseStack>();
		
		do{
			for(int i = currentStacks.size() - 1; i >= 0; i--){
				ParseStack stack = currentStacks.get(i);
				ParseStackFrame frame = stack.currentTop;
				
				stackBeingWorkedOn = stack;
				callMethod(frame.getCurrentNode().getMethodName());
			}
			
			currentStacks = stacks;
			stacks = new ArrayList<ParseStack>();
		}while(currentStacks.size() > 0);
		
		// Temp
		return null;
	}
}
