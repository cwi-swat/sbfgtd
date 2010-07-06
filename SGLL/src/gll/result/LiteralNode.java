package gll.result;

import gll.result.struct.Link;
import gll.util.IndexedStack;

public class LiteralNode implements INode{
	private final char[] content;
	
	public LiteralNode(char[] content){
		super();
		
		this.content = content;
	}
	
	public void addAlternative(Link children){
		throw new UnsupportedOperationException();
	}
	
	public boolean isEpsilon(){
		return false;
	}
	
	public String toString(){
		return new String(content);
	}
	
	public String toString(IndexedStack<INode> stack, int depth){
		return new String(content);
	}
}
