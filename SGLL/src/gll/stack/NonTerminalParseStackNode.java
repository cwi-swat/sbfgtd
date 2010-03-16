package gll.stack;

public final class NonTerminalParseStackNode extends ParseStackNode{
	private final String nonTerminal;
	
	public NonTerminalParseStackNode(String nonTerminal, int id){
		super(id);
		
		this.nonTerminal = nonTerminal;
	}
	
	private NonTerminalParseStackNode(NonTerminalParseStackNode nonTerminalParseStackNode){
		super(nonTerminalParseStackNode.getId());

		this.nonTerminal = nonTerminalParseStackNode.nonTerminal;
		
		this.nexts = nonTerminalParseStackNode.nexts;
		this.edges = nonTerminalParseStackNode.edges;
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
