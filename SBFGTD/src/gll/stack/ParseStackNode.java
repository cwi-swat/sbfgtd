package gll.stack;

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
}
