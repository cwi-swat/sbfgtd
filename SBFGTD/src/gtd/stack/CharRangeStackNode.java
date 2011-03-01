package gtd.stack;

import gtd.result.AbstractNode;
import gtd.result.CharNode;

public final class CharRangeStackNode extends AbstractStackNode implements IMatchableStackNode{
	private final char[][] ranges;
	
	private final String production;
	
	private AbstractNode result;
	
	public CharRangeStackNode(int id, int dot, String production, char[][] ranges){
		super(id, dot);
		
		this.production = production;

		this.ranges = ranges;
	}
	
	private CharRangeStackNode(CharRangeStackNode original){
		super(original);
		
		ranges = original.ranges;
		
		production = original.production;
	}
	
	public boolean isEmptyLeafNode(){
		return false;
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
		return false;
	}
	
	public AbstractStackNode getCleanCopy(){
		return new CharRangeStackNode(this);
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
		sb.append(')');
		
		return sb.toString();
	}
}
