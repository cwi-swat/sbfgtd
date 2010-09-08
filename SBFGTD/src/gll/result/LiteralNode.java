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
	
	protected boolean isNullable(){
		return false;
	}
	
	protected boolean isSeparator(){
		return false;
	}
	
	protected String print(IndexedStack<AbstractNode> stack, int depth, CycleMark cycleMark){
		return new String(content);
	}
	
	public String toString(){
		return new String(content);
	}
}
