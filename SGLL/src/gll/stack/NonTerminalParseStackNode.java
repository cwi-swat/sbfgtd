package gll.stack;

public class NonTerminalParseStackNode extends ParseStackNode{
	public final String nonTerminal;
	
	public NonTerminalParseStackNode(String nonTerminal){
		super();
		
		this.nonTerminal = nonTerminal;
	}
	
	public String getMethodName(){
		return nonTerminal;
	}
	
	public byte[] getTerminalData(){
		return null;
	}
	
	public String getNonTerminalName(){
		return nonTerminal;
	}
}
