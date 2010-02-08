package gll;

import gll.nodes.INode;
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
	
	protected final ParseStackFrame root;
	protected Set<ParseStack> stacks;
	
	// Updated temporary stuff.
	protected ParseStack stackBeingWorkedOn;
	
	public SGLL(String start, byte[] input){
		super();
		
		this.input = input;
		
		root = new ParseStackFrame(0, new NonTerminalParseStackNode(start));
			
		stacks = new HashSet<ParseStack>();
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
		ParseStackFrame current = new ParseStackFrame(prev.frameNumber + 1, symbolsToExpect);
		for(int i = symbolsToExpect.length - 1; i >= 0; i--){
			symbolsToExpect[i].addEdge(prev);
		}
		parseStack.currentTop = current;
	}
	
	public void reduceTerminal(){
		ParseStackFrame frame = stackBeingWorkedOn.currentTop;
		ParseStackNode terminalNode = frame.getCurrentNode();
		byte[] terminalData = terminalNode.getTerminalData();
		
		if(!terminalMatchesAtPosition(terminalData)){
			System.out.println("Failed to reduce terminal:\t"+new String(terminalData)+"\tAt stack: "+stackBeingWorkedOn.hashCode()); // Temp
			// This stack dies of, up to the last split point.
			stacks.remove(stackBeingWorkedOn);
			return;
		}
		
		// TODO Construct the result.
		
		// Try to reduce the non terminals on top of the stack (if possible).
		stackBeingWorkedOn.location += terminalData.length;
	}
	
	private void tryNonTerminalReduction(ParseStack stack, ParseStackFrame frame){
		if(frame.frameNumber == 0){ // Root reached.
			if(stack.location == input.length){
				// Temp
				System.out.println("Reduce:\t"+frame+"\tAt stack: "+stackBeingWorkedOn.hashCode()); // Temp
				
				// TODO Construct the result.
			}else{
				// Temp
				System.out.println("Failed to reduce:\t"+frame+"\tAt stack: "+stackBeingWorkedOn.hashCode()); // Temp
			}
			
			stacks.remove(stack);
			
			return; // The end.
		}
		
		// Temp
		System.out.println("Reduce:\t"+frame+"\tAt stack: "+stackBeingWorkedOn.hashCode()); // Temp

		// TODO Construct the result.
		
		// Update the stack.
		int byteToMoveTo = stack.location;
		List<ParseStackFrame> prevFrames = frame.getCurrentNode().getEdges(); // TODO Centralize edge data
		for(int j = prevFrames.size() - 1; j >= 1; j--){
			ParseStack newStack = new ParseStack(prevFrames.get(j), byteToMoveTo);
			stacks.add(newStack);
		}
		
		stack.currentTop = prevFrames.get(0);
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
			int index = 0;
			List<ParseStack> lowestStacks = new ArrayList<ParseStack>();
			
			// Clone
			Iterator<ParseStack> stacksIterator = stacks.iterator();
			while(stacksIterator.hasNext()){
				ParseStack stack = stacksIterator.next();
				ParseStackFrame pf = stack.currentTop;
				if(pf.frameNumber > index){
					index = pf.frameNumber;
					lowestStacks = new ArrayList<ParseStack>();
					lowestStacks.add(stack);
				}else if(pf.frameNumber == index){
					lowestStacks.add(stack);
				}
			}
			
			// Parse
			for(int i = lowestStacks.size() - 1; i >= 0; i--){
				ParseStack stack = lowestStacks.get(i);
				ParseStackFrame frame = stack.currentTop;
				
				if(frame.isComplete()){
					tryNonTerminalReduction(stack, frame);
				}else{
					stackBeingWorkedOn = stack;
					frame.nextSymbol();
					callMethod(frame.getCurrentNode().getMethodName());
				}
			}
		}while(stacks.size() > 0);
		
		// Temp
		return null;
	}
}
