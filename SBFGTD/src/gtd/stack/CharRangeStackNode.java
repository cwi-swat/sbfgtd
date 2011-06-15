package gtd.stack;

import gtd.result.AbstractNode;
import gtd.result.CharNode;

public final class CharRangeStackNode extends AbstractStackNode implements IMatchableStackNode{
	private final char[][] ranges;
	
	private final String production;
	
	private final AbstractNode result;
	
	public CharRangeStackNode(int id, int dot, String production, char[][] ranges){
		super(id, dot);
		
		this.production = production;

		this.ranges = ranges;
		
		result = null;
	}
	
	private CharRangeStackNode(CharRangeStackNode original){
		super(original);
		
		ranges = original.ranges;
		
		production = original.production;
		
		result = null;
	}
	
	private CharRangeStackNode(CharRangeStackNode original, AbstractNode result){
		super(original);
		
		this.ranges = original.ranges;
		
		this.production = original.production;
		
		this.result = result;
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
	
	public AbstractNode match(char[] input, int location){
		char next = input[location];
		for(int i = ranges.length - 1; i >= 0; --i){
			char[] range = ranges[i];
			if(next >= range[0] && next <= range[1]){
				return new CharNode(production, next);
			}
		}
		return null;
	}
	
	public AbstractStackNode getCleanCopy(){
		return new CharRangeStackNode(this);
	}
	
	public AbstractStackNode getCleanCopyWithResult(AbstractNode result){
		return new CharRangeStackNode(this, result);
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
