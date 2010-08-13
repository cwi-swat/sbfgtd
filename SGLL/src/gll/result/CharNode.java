package gll.result;

import gll.result.struct.Link;
import gll.util.IndexedStack;

public class CharNode extends AbstractNode{
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
	
	public String print(IndexedStack<AbstractNode> stack, int depth, CycleMark cycleMark){
		StringBuilder sb = new StringBuilder();
		sb.append(production);
		sb.append('(');
		sb.append(character);
		sb.append(')');
		return sb.toString();
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
