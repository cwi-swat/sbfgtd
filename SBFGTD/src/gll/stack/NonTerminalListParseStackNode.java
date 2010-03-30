package gll.stack;

import gll.result.INode;

// TODO Add list code.
public class NonTerminalListParseStackNode extends ParseStackNode{
	private final String listChild;
	
	private final String methodName;
	
	private boolean firstRequired;
	
	private boolean marked;
	
	private INode result;
	
	public NonTerminalListParseStackNode(int id, String listChild, boolean isPlusList){
		super(id);
		
		this.listChild = listChild;
		
		firstRequired = isPlusList;
		
		methodName = String.valueOf(id);
		
		marked = false;
		
		result = null;
	}
	
	public NonTerminalListParseStackNode(NonTerminalListParseStackNode nonTerminalListParseStackNode){
		super(nonTerminalListParseStackNode.id);
		
		listChild = nonTerminalListParseStackNode.listChild;
		
		firstRequired = nonTerminalListParseStackNode.firstRequired;
		
		methodName = nonTerminalListParseStackNode.methodName;
		
		marked = false;
		
		result = null;
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
	
	public char[] getTerminalData(){
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
		throw new UnsupportedOperationException();
	}
	
	public void mark(){
		marked = true;
	}
	
	public boolean isMarked(){
		return marked;
	}
	
	public ParseStackNode getNextListChild(char[] input, int position){
		
		
		// TODO Implement.
		return null; // Temp.
	}
	
	public void addResult(INode result){
		this.result = result;
	}
	
	public INode getResult(){
		return result;
	}
}
