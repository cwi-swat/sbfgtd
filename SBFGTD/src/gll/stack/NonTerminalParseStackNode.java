package gll.stack;

import gll.result.Alternative;
import gll.result.INode;
import gll.util.ArrayList;

public final class NonTerminalParseStackNode extends ParseStackNode{
	private final String nonTerminal;
	
	private boolean visited;
	
	private final ArrayList<INode> results;
	
	public NonTerminalParseStackNode(String nonTerminal, int id){
		super(id);
		
		this.nonTerminal = nonTerminal;
		
		visited = false;
		
		results = null;
	}
	
	private NonTerminalParseStackNode(NonTerminalParseStackNode nonTerminalParseStackNode){
		super(nonTerminalParseStackNode.id);

		this.nonTerminal = nonTerminalParseStackNode.nonTerminal;
		
		this.nexts = nonTerminalParseStackNode.nexts;
		this.edges = nonTerminalParseStackNode.edges;
		
		visited = false;
		
		results = new ArrayList<INode>();
	}
	
	public boolean isTerminal(){
		return false;
	}
	
	public boolean isNonTerminal(){
		return true;
	}
	
	public boolean isList(){
		return false;
	}
	
	public String getMethodName(){
		return nonTerminal;
	}
	
	public char[] getTerminalData(){
		throw new UnsupportedOperationException();
	}
	
	public String getNonTerminalName(){
		return nonTerminal;
	}
	
	public ParseStackNode getCleanCopy(){
		return new NonTerminalParseStackNode(this);
	}
	
	public ParseStackNode getCleanCopyWithPrefix(){
		NonTerminalParseStackNode ntpsn = new NonTerminalParseStackNode(this);
		ntpsn.prefixes = prefixes;
		ntpsn.prefixStartLocations = prefixStartLocations;
		return ntpsn;
	}
	
	public int getLength(){
		throw new UnsupportedOperationException();
	}
	
	public void mark(){
		visited = true;
	}
	
	public boolean isMarked(){
		return visited;
	}
	
	public ParseStackNode getNextListChild(char[] input, int position){
		throw new UnsupportedOperationException();
	}
	
	public void addResult(INode result){
		results.add(result);
	}
	
	public INode getResult(){
		return new Alternative(results);
	}

	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append(nonTerminal);
		sb.append(getId());
		sb.append('(');
		sb.append(startLocation);
		sb.append(',');
		sb.append('?');
		sb.append(')');
		
		return sb.toString();
	}
}
