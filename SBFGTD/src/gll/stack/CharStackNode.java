package gll.stack;

import gll.result.AbstractNode;
import gll.result.CharNode;

public final class CharStackNode extends AbstractStackNode implements IMatchableStackNode{
	private final char[][] ranges;
	private final char[] characters;
	
	private final String production;
	
	private AbstractNode result;
	
	public CharStackNode(int id, int dot, String production, char[][] ranges, char[] characters){
		super(id, dot);
		
		this.production = production;

		this.ranges = ranges;
		this.characters = characters;
	}
	
	private CharStackNode(CharStackNode original){
		super(original);
		
		ranges = original.ranges;
		characters = original.characters;
		
		production = original.production;
	}
	
	public String getIdentifier(){
		throw new UnsupportedOperationException();
	}
	
	public String getName(){
		throw new UnsupportedOperationException();
	}
	
	public String getMethodName(){
		throw new UnsupportedOperationException();
	}
	
	public boolean match(char[] input){
		char next = input[startLocation];
		for(int i = ranges.length - 1; i >= 0; --i){
			char[] range = ranges[i];
			if(next >= range[0] && next <= range[1]){
				result = new CharNode(production, next);
				return true;
			}
		}
		
		for(int i = characters.length - 1; i >= 0; --i){
			if(next == characters[i]){
				result = new CharNode(production, next);
				return true;
			}
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
	
	public AbstractNode getResult(){
		return result;
	}
	
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append(production);
		sb.append(getId());
		sb.append('(');
		sb.append(startLocation);
		sb.append(',');
		sb.append(startLocation + getLength());
		sb.append(')');
		
		return sb.toString();
	}
}
