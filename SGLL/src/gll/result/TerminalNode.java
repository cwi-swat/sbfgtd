package gll.result;

public class TerminalNode implements INode{
	private final char[] content;
	
	public TerminalNode(char[] content){
		super();
		
		this.content = content;
	}
	
	public String toString(){
		return new String(content);
	}
}
