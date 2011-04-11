package gtd.stack;

import gtd.result.AbstractNode;
import gtd.result.CharNode;

public final class CharStackNode extends AbstractStackNode implements IMatchableStackNode{
	private final char character;
	
	private final String production;
	
	private AbstractNode result;
	
	public CharStackNode(int id, int dot, String production, char character){
		super(id, dot);
		
		this.production = production;
		
		this.character = character;
	}
	
	private CharStackNode(CharStackNode original){
		super(original);
		
		character = original.character;
		
		production = original.production;
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
	
	public boolean match(char[] input){
		char next = input[startLocation];
		if(next == character){
			result = new CharNode(production, next);
			return true;
		}
		return false;
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
