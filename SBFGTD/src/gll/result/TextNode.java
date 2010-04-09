package gll.result;

public class TextNode implements INode{
	private final char[] content;
	
	public TextNode(char[] content){
		super();
		
		this.content = content;
	}
	
	public int items(){
		throw new UnsupportedOperationException();
	}
	
	public INode[] getItems(){
		throw new UnsupportedOperationException();
	}
	
	public String toString(){
		return new String(content);
	}
}
