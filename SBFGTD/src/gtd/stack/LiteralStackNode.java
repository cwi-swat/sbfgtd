package gtd.stack;

import gtd.result.AbstractNode;
import gtd.result.LiteralNode;

public final class LiteralStackNode extends AbstractStackNode implements IMatchableStackNode{
	private final char[] literal;
	
	private final LiteralNode result;
	
	public LiteralStackNode(int id, int dot, char[] literal){
		super(id, dot);

		this.literal = literal;
		result = new LiteralNode(literal);
	}
	
	private LiteralStackNode(LiteralStackNode original){
		super(original);

		literal = original.literal;
		
		result = original.result;
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
		for(int i = literal.length - 1; i >= 0; --i){
			if(literal[i] != input[location + i]) return null; // Did not match.
		}
		return result;
	}
	
	public AbstractStackNode getCleanCopy(){
		return new LiteralStackNode(this);
	}
	
	public int getLength(){
		return literal.length;
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
		sb.append(new String(literal));
		sb.append(getId());
		sb.append('(');
		sb.append(startLocation);
		sb.append(')');
		
		return sb.toString();
	}
}
