package gtd.stack;

import gtd.result.AbstractNode;
import gtd.result.CharNode;

public final class CharStackNode extends AbstractStackNode implements IMatchableStackNode{
	private final char character;
	
	private final String production;
	
	private final AbstractNode result;
	
	public CharStackNode(int id, int dot, char character){
		super(id, dot);
		
		this.production = "["+character+"]";
		
		this.character = character;
		
		result = new CharNode(production, character);
	}
	
	private CharStackNode(CharStackNode original){
		super(original);
		
		character = original.character;
		
		production = original.production;
		
		result = original.result;
	}
	
	public String getIdentifier(){
		throw new UnsupportedOperationException();
	}
	
	public String getName(){
		throw new UnsupportedOperationException();
	}
	
	public boolean isEmptyLeafNode(){
		return false;
	}
	
	public String getMethodName(){
		throw new UnsupportedOperationException();
	}
	
	public AbstractNode match(char[] input, int location){
		if(input[location] == character){
			return result;
		}
		return null;
	}
	
	public AbstractStackNode getCleanCopy(){
		return new CharStackNode(this);
	}
	
	public int getLength(){
		return 1;
	}
	
	public AbstractStackNode[] getChildren(){
		throw new UnsupportedOperationException();
	}
	
	public boolean canBeEmpty(){
		throw new UnsupportedOperationException();
	}
	
	public AbstractStackNode getEmptyChild(){
		throw new UnsupportedOperationException();
	}
	
	public AbstractNode getResult(){
		throw new UnsupportedOperationException();
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
