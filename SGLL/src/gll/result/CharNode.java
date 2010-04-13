package gll.result;

public class CharNode implements INode{
	private final String production;
	private final char character;
	
	public CharNode(String production, char character){
		super();
		
		this.production = production;
		this.character = character;
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
