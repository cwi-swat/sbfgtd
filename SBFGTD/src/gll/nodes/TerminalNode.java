package gll.nodes;

public class TerminalNode implements INode{
	public final String content;
	
	public TerminalNode(String content){
		super();
		
		this.content = content;
	}
	
	public String toString(){
		return content;
	}
}
