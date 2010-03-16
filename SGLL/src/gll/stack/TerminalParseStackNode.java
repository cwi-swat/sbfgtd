package gll.stack;

public final class TerminalParseStackNode extends ParseStackNode{
	private final byte[] terminal;
	
	public TerminalParseStackNode(byte[] terminal, int id){
		super(id);

		this.terminal = terminal;
	}
	
	private TerminalParseStackNode(TerminalParseStackNode terminalParseStackNode){
		super(terminalParseStackNode.getId());

		this.terminal = terminalParseStackNode.terminal;
		
		this.nexts = terminalParseStackNode.nexts;
		this.edges = terminalParseStackNode.edges;
	}
	
	public boolean isTerminal(){
		return true;
	}
	
	public boolean isNonTerminal(){
		return false;
	}
	
	public String getMethodName(){
		throw new UnsupportedOperationException();
	}
	
	public byte[] getTerminalData(){
		return terminal;
	}
	
	public String getNonTerminalName(){
		throw new UnsupportedOperationException();
	}
	
	public ParseStackNode getCleanCopy(){
		return new TerminalParseStackNode(this);
	}
	
	public int getLength(){
		return terminal.length;
	}
	
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append(new String(terminal));
		sb.append(getId());
		sb.append('(');
		sb.append(startLocation);
		sb.append(')');
		
		return sb.toString();
	}
}
