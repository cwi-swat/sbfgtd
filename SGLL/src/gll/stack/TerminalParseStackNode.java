package gll.stack;

public class TerminalParseStackNode extends ParseStackNode{
	public final byte[] terminal;
	
	public TerminalParseStackNode(byte[] terminal){
		super();

		this.terminal = terminal;
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
}
