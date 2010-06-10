package gll.result;

import gll.result.struct.Link;
import gll.util.IndexedStack;

import java.io.IOException;
import java.io.Writer;

public class CharNode implements INode{
	private final String production;
	private final char character;
	
	public CharNode(String production, char character){
		super();
		
		this.production = production;
		this.character = character;
	}
	
	public void addAlternative(Link children){
		throw new UnsupportedOperationException();
	}
	
	public boolean isEpsilon(){
		return false;
	}
	
	public void print(Writer out, IndexedStack<INode> stack, int depth) throws IOException{
		out.write(production);
		out.write('(');
		out.write(character);
		out.write(')');
	}
	
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append(production);
		sb.append('(');
		sb.append(character);
		sb.append(')');
		return sb.toString();
	}
}
