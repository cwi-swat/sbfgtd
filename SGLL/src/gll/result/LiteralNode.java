package gll.result;

import gll.util.Stack;

import java.io.IOException;
import java.io.Writer;

public class LiteralNode implements INode{
	private final char[] content;
	
	public LiteralNode(char[] content){
		super();
		
		this.content = content;
	}
	
	public void addAlternative(INode[] children){
		throw new UnsupportedOperationException();
	}
	
	public boolean isEpsilon(){
		return false;
	}
	
	public void print(Writer out, Stack<INode> stack) throws IOException{
		out.write(content);
	}
	
	public String toString(){
		return new String(content);
	}
}
