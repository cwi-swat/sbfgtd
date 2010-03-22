package gll.stack;

import gll.result.Alternative;
import gll.result.INode;

import java.util.ArrayList;
import java.util.List;

public final class NonTerminalParseStackNode extends ParseStackNode{
	private final String nonTerminal;
	
	private final List<INode> results;
	
	public NonTerminalParseStackNode(String nonTerminal, int id){
		super(id);
		
		this.nonTerminal = nonTerminal;
		results = null;
	}
	
	private NonTerminalParseStackNode(NonTerminalParseStackNode nonTerminalParseStackNode){
		super(nonTerminalParseStackNode.getId());

		this.nonTerminal = nonTerminalParseStackNode.nonTerminal;
		
		this.nexts = nonTerminalParseStackNode.nexts;
		this.edges = nonTerminalParseStackNode.edges;
		
		results = new ArrayList<INode>();
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
	
	public int getLength(){
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
		sb.append(')');
		
		return sb.toString();
	}
}
