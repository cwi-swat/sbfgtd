package gll.stack;

import gll.result.Alternative;
import gll.result.INode;
import gll.util.ArrayList;

public final class NonTerminalParseStackNode extends ParseStackNode{
	private final String nonTerminal;
	
	private int endLocation;
	
	private final ArrayList<INode> results;
	
	public NonTerminalParseStackNode(String nonTerminal, int id){
		super(id);
		
		this.nonTerminal = nonTerminal;
		
		results = null;
		
		endLocation = -1;
	}
	
	private NonTerminalParseStackNode(NonTerminalParseStackNode nonTerminalParseStackNode){
		super(nonTerminalParseStackNode.getId());

		this.nonTerminal = nonTerminalParseStackNode.nonTerminal;
		
		this.nexts = nonTerminalParseStackNode.nexts;
		this.edges = nonTerminalParseStackNode.edges;
		
		results = new ArrayList<INode>();
		
		endLocation = -1;
	}
	
	public boolean isTerminal(){
		return false;
	}
	
	public boolean isNonTerminal(){
		return true;
	}
	
	public String getMethodName(){
		return nonTerminal;
	}
	
	public byte[] getTerminalData(){
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
		ntpsn.prefixLengths = prefixLengths;
		return ntpsn;
	}
	
	public int getLength(){
		return (endLocation - startLocation);
	}
	
	public void setEndLocation(int endLocation){
		this.endLocation = endLocation;
	}
	
	public boolean endLocationIsSet(){
		return (endLocation != -1);
	}
	
	public int getEndLocation(){
		return endLocation;
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
		sb.append(endLocation);
		sb.append(')');
		
		return sb.toString();
	}
}
