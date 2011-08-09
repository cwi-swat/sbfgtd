package gtd.stack;

import gtd.result.AbstractNode;
import gtd.result.LiteralNode;

public final class LiteralStackNode extends AbstractMatchableStackNode{
	private final char[] literal;
	
	private final LiteralNode result;
	
	public LiteralStackNode(int id, int dot, char[] literal){
		super(id, dot);

		this.literal = literal;
		result = new LiteralNode(literal);
	}
	
	private LiteralStackNode(LiteralStackNode original, int startLocation){
		super(original, startLocation);

		literal = original.literal;
		
		result = original.result;
	}
	
	public boolean isEmptyLeafNode(){
		return false;
	}
	
	public AbstractNode match(char[] input, int location){
		for(int i = literal.length - 1; i >= 0; --i){
			if(literal[i] != input[location + i]) return null; // Did not match.
		}
		return result;
	}
	
	public AbstractStackNode getCleanCopy(int startLocation){
		return new LiteralStackNode(this, startLocation);
	}
	
	public AbstractStackNode getCleanCopyWithResult(int startLocation, AbstractNode result){
		return new LiteralStackNode(this, startLocation);
	}
	
	public int getLength(){
		return literal.length;
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
		sb.append(')');
		
		return sb.toString();
	}
}
