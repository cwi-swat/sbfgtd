package gll.result;

public class TerminalNode implements INode{
	private final byte[] content;
	
	public TerminalNode(byte[] content){
		super();
		
		this.content = content;
	}
	
	public String toString(){
		return new String(content);
	}
}
