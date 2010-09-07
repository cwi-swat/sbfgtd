package gll.result;

import gll.result.struct.Link;
import gll.util.IndexedStack;

public class LiteralNode extends AbstractNode{
	private final char[] content;
	
	public LiteralNode(char[] content){
		super();
		
		this.content = content;
	}
	
	public void addAlternative(Link children){
		throw new UnsupportedOperationException();
	}
	
	public boolean isNullable(){
		return false;
	}
	
	public String print(IndexedStack<AbstractNode> stack, int depth, CycleMark cycleMark){
		return new String(content);
	}
	
	public String toString(){
		return new String(content);
	}
}
