package gll.stack;

import gll.result.INode;
import gll.result.TerminalNode;

public final class TerminalParseStackNode extends ParseStackNode{
	private final char[] terminal;
	
	private final TerminalNode result;
	
	public TerminalParseStackNode(char[] terminal, int id){
		super(id);

		this.terminal = terminal;
		result = new TerminalNode(terminal);
	}
	
	private TerminalParseStackNode(TerminalParseStackNode terminalParseStackNode){
		super(terminalParseStackNode.id);

		terminal = terminalParseStackNode.terminal;
		
		nexts = terminalParseStackNode.nexts;
		edges = terminalParseStackNode.edges;
		
		result = terminalParseStackNode.result;
	}
	
	public boolean isReducable(){
		return true;
	}
	
	public boolean isList(){
		return false;
	}
	
	public String getMethodName(){
		throw new UnsupportedOperationException();
	}
	
	public boolean reduce(char[] input, int location){
		for(int i = terminal.length - 1; i >= 0; i--){
			if(terminal[i] != input[startLocation + i]) return false; // Did not match.
		}
		return true;
	}
	
	public String getNodeName(){
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
	
	public void mark(){
		throw new UnsupportedOperationException();
	}
	
	public boolean isMarked(){
		throw new UnsupportedOperationException();
	}
	
	public ParseStackNode[] getListChildren(){
		throw new UnsupportedOperationException();
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
