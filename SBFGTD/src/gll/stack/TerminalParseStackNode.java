package gll.stack;

import gll.result.INode;
import gll.result.TerminalNode;

public final class TerminalParseStackNode extends ParseStackNode{
	private final byte[] terminal;
	
	private final TerminalNode result;
	
	public TerminalParseStackNode(byte[] terminal, int id){
		super(id);

		this.terminal = terminal;
		result = new TerminalNode(terminal);
	}
	
	private TerminalParseStackNode(TerminalParseStackNode terminalParseStackNode){
		super(terminalParseStackNode.getId());

		this.terminal = terminalParseStackNode.terminal;
		
		this.nexts = terminalParseStackNode.nexts;
		this.edges = terminalParseStackNode.edges;
		
		this.result = terminalParseStackNode.result;
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
	
	public void addResult(INode result){
		throw new UnsupportedOperationException();
	}
	
	public INode getResult(){
		return result;
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
