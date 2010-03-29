package gll.stack;

import gll.result.Alternative;
import gll.result.INode;
import gll.util.ArrayList;

// TODO Add list code.
public class NonTerminalListParseStackNode extends ParseStackNode{
	private final String methodName;
	
	private final char[][] ranges;
	private final char[] characters;
	
	private boolean firstRequired;
	
	private int endLocation;
	
	private final ArrayList<INode> results;
	
	public NonTerminalListParseStackNode(int id, char[][] ranges, char[] characters, boolean isPlusList){
		super(id);
		
		this.ranges = ranges;
		this.characters = characters;
		
		firstRequired = isPlusList;
		
		methodName = String.valueOf(id);
		
		endLocation = -1;
		
		results = null;
	}
	
	public NonTerminalListParseStackNode(NonTerminalListParseStackNode nonTerminalListParseStackNode){
		super(nonTerminalListParseStackNode.id);
		
		ranges = nonTerminalListParseStackNode.ranges;
		characters = nonTerminalListParseStackNode.characters;
		
		firstRequired = nonTerminalListParseStackNode.firstRequired;
		
		methodName = nonTerminalListParseStackNode.methodName;
		
		endLocation = -1;
		
		results = new ArrayList<INode>();
	}
	
	public boolean isNonTerminal(){
		return false;
	}
	
	public boolean isTerminal(){
		return false;
	}
	
	public boolean isList(){
		return true;
	}
	
	public String getMethodName(){
		return methodName;
	}
	
	public byte[] getTerminalData(){
		throw new UnsupportedOperationException();
	}
	
	public String getNonTerminalName(){
		throw new UnsupportedOperationException();
	}
	
	public ParseStackNode getCleanCopy(){
		return new NonTerminalListParseStackNode(this);
	}
	
	public ParseStackNode getCleanCopyWithPrefix(){
		NonTerminalListParseStackNode ntpsn = new NonTerminalListParseStackNode(this);
		ntpsn.prefixes = prefixes;
		ntpsn.prefixStartLocations = prefixStartLocations;
		return ntpsn;
	}
	
	public int getLength(){
		return (endLocation - startLocation);
	}
	
	public void setEndLocation(int endLocation){
		this.endLocation = endLocation;
	}
	
	public boolean endLocationIsSet(){
		return endLocation != -1;
	}
	
	public int getEndLocation(){
		return endLocation;
	}
	
	public void addResult(INode result){
		results.add(result);
	}
	
	public INode getResult(){
		return new Alternative(results); // TODO Check if this is ok.
	}
}
