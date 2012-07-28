package gtd.stack;

import gtd.result.AbstractNode;
import gtd.result.CharNode;

public final class CharStackNode extends AbstractMatchableStackNode{
	private final char character;
	
	private final String production;
	
	private final AbstractNode result;
	
	public CharStackNode(int id, int dot, char character){
		super(id, dot);
		
		this.production = "["+character+"]";
		
		this.character = character;
		
		result = new CharNode(production, character);
	}
	
	private CharStackNode(CharStackNode original, int startLocation){
		super(original, startLocation);
		
		character = original.character;
		
		production = original.production;
		
		result = original.result;
	}
	
	public boolean isEmptyLeafNode(){
		return false;
	}
	
	public AbstractNode match(char[] input, int location){
		if(input[location] == character){
			return result;
		}
		return null;
	}
	
	public AbstractStackNode getCleanCopy(int startLocation){
		return new CharStackNode(this, startLocation);
	}
	
	public AbstractStackNode getCleanCopyWithResult(int startLocation, AbstractNode result){
		return new CharStackNode(this, startLocation);
	}
	
	public int getLength(){
		return 1;
	}
	
	public AbstractNode getResult(){
		return result;
	}
	
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append(production);
		sb.append(getId());
		sb.append('(');
		sb.append(startLocation);
		sb.append(')');
		
		return sb.toString();
	}
}
