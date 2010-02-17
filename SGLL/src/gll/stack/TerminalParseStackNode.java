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
	
	public INode[] getResults(){
		throw new UnsupportedOperationException();
	}
	
	public INode getResult(){
		return result;
	}
	
	public int getLength(){
		return terminal.length;
	}
	
	public boolean isMergable(ParseStackNode other){
		if(!(other instanceof TerminalParseStackNode)) return false;
		
		TerminalParseStackNode otherNode = (TerminalParseStackNode) other;
		
		byte[] otherTerminal = otherNode.terminal;
		int terminalLength = terminal.length;
		if(terminalLength == otherTerminal.length){
			for(int i = terminalLength - 1; i >= 0; i--){
				if(terminal[i] != otherTerminal[i]) return false;
			}
		}
		
		return true;
	}
}
