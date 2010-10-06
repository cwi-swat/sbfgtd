package gll.stack;

import gll.result.AbstractNode;
import gll.result.LiteralNode;

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
		for(int i = literal.length - 1; i >= 0; --i){
			if(literal[i] != input[startLocation + i]) return false; // Did not match.
		}
		return true;
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
	
	public AbstractNode getResult(){
		return result;
	}
	
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append(new String(literal));
		sb.append(getId());
		sb.append('(');
		sb.append(startLocation);
		sb.append(',');
		sb.append(startLocation + getLength());
		sb.append(')');
		
		return sb.toString();
	}
}
