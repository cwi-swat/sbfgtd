package gll.result;

import gll.result.struct.Link;
import gll.util.IndexedStack;

import java.io.IOException;
import java.io.Writer;

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
	
	public void print(Writer out, IndexedStack<INode> stack, int depth) throws IOException{
		out.write(content);
	}
	
	public String toString(){
		return new String(content);
	}
}
