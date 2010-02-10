package gll.stack;

public class NonTerminalParseStackNode extends ParseStackNode{
	private final String nonTerminal;
	
	public NonTerminalParseStackNode(String nonTerminal){
		super();
		
		this.nonTerminal = nonTerminal;
	}
	
	public String getName(){
		return nonTerminal;
	}
	
	public String getMethodName(){
		return getName();
	}
	
	public byte[] getTerminalData(){
		return null;
	}
	
	public String getNonTerminalName(){
		return nonTerminal;
	}
}
