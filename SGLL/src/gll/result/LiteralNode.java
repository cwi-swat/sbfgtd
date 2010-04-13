package gll.result;

public class LiteralNode implements INode{
	private final char[] content;
	
	public LiteralNode(char[] content){
		super();
		
		this.content = content;
	}
	
	public boolean isEpsilon(){
		return false;
	}
	
	public String toString(){
		return new String(content);
	}
}
