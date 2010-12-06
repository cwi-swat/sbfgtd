package gtd.result;

import gtd.result.struct.Link;
import gtd.util.IndexedStack;

public class LiteralNode extends AbstractNode{
	private final char[] content;
	
	public LiteralNode(char[] content){
		super();
		
		this.content = content;
	}
	
	public void addAlternative(Link children){
		throw new UnsupportedOperationException();
	}
	
	public boolean isEmpty(){
		return false;
	}
	
	public boolean isSeparator(){
		return false;
	}
	
	protected String print(IndexedStack<AbstractNode> stack, int depth, CycleMark cycleMark){
		return new String(content);
	}
	
	public String toString(){
		return new String(content);
	}
}
