package gll.nodes;

public class TerminalNode implements INode{
	private final byte[] content;
	
	public TerminalNode(byte[] content){
		super();
		
		this.content = content;
	}
	
	public int getLength(){
		return content.length;
	}
	
	public String toString(){
		return new String(content);
	}
}
