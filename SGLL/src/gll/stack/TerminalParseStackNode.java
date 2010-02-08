package gll.stack;

public class TerminalParseStackNode extends ParseStackNode{
	public final byte[] terminal;
	
	public TerminalParseStackNode(byte[] terminal){
		super();

		this.terminal = terminal;
	}
	
	public String getMethodName(){
		return new String(terminal);
	}
	
	public byte[] getTerminalData(){
		return terminal;
	}
	
	public String getNonTerminalName(){
		return null;
	}
}
