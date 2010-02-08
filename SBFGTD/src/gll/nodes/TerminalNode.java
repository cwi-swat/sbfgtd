package gll.nodes;

public class TerminalNode implements INode{
	public final byte[] content;
	
	public TerminalNode(byte[] content){
		super();
		
		this.content = content;
	}
	
	public String toString(){
		return new String(content);
	}
}
