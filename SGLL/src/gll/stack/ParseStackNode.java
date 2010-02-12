package gll.stack;

import gll.nodes.INode;

public abstract class ParseStackNode{
	
	public ParseStackNode(){
		super();
	}
	
	public boolean isTerminal(){
		return (this instanceof TerminalParseStackNode);
	}
	
	public boolean isNonTerminal(){
		return (this instanceof NonTerminalParseStackNode);
	}
	
	public abstract String getName();
	
	public abstract String getMethodName();
	
	public abstract byte[] getTerminalData();
	
	public abstract String getNonTerminalName();
	
	public abstract void addResult(INode result);
	
	public abstract INode getResult();
}
