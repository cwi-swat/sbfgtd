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
		super(terminalParseStackNode.id);

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
	
	public boolean isList(){
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
	
	public ParseStackNode getCleanCopyWithPrefix(){
		TerminalParseStackNode tpsn = new TerminalParseStackNode(this);
		tpsn.prefixes = prefixes;
		tpsn.prefixStartLocations = prefixStartLocations;
		return tpsn;
	}
	
	public int getLength(){
		return terminal.length;
	}
	
	public void setEndLocation(int endLocation){
		throw new UnsupportedOperationException();
	}
	
	public boolean endLocationIsSet(){
		throw new UnsupportedOperationException();
	}
	
	public int getEndLocation(){
		return (startLocation + getLength());
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
		sb.append(',');
		sb.append(startLocation + getLength());
		sb.append(')');
		
		return sb.toString();
	}
}
