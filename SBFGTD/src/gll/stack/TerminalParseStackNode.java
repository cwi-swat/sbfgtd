package gll.stack;

import gll.nodes.INode;
import gll.nodes.TerminalNode;

public class TerminalParseStackNode extends ParseStackNode{
	private final byte[] terminal;
	private final INode result;
	
	public TerminalParseStackNode(byte[] terminal){
		super();

		this.terminal = terminal;
		this.result = new TerminalNode(terminal);
	}
	
	public String getName(){
		return new String(terminal);
	}
	
	public String getMethodName(){
		return getName();
	}
	
	public byte[] getTerminalData(){
		return terminal;
	}
	
	public String getNonTerminalName(){
		return null;
	}
	
	public void addResult(INode result){
		throw new UnsupportedOperationException();
	}
	
	public INode getResult(){
		return result;
	}
}
