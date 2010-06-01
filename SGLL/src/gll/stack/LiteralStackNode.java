package gll.stack;

import gll.result.INode;
import gll.result.LiteralNode;
import gll.util.ArrayList;
import gll.util.IntegerList;

public final class LiteralStackNode extends AbstractStackNode implements IReducableStackNode{
	private final char[] literal;
	
	private final LiteralNode result;
	
	public LiteralStackNode(int id, char[] literal){
		super(id);

		this.literal = literal;
		result = new LiteralNode(literal);
	}
	
	private LiteralStackNode(LiteralStackNode original){
		super(original);

		literal = original.literal;
		
		result = original.result;
	}
	
	private LiteralStackNode(LiteralStackNode original, ArrayList<INode[]> prefixes, IntegerList prefixStartLocations){
		super(original, prefixes, prefixStartLocations);

		literal = original.literal;
		
		result = original.result;
	}
	
	public String getMethodName(){
		throw new UnsupportedOperationException();
	}
	
	public boolean reduce(char[] input){
		for(int i = literal.length - 1; i >= 0; i--){
			if(literal[i] != input[startLocation + i]) return false; // Did not match.
		}
		return true;
	}
	
	public boolean isClean(){
		return true;
	}
	
	public AbstractStackNode getCleanCopy(){
		return new LiteralStackNode(this);
	}
	
	public AbstractStackNode getCleanCopyWithPrefix(){
		return new LiteralStackNode(this, prefixes, prefixStartLocations);
	}
	
	public void initializeResultStore(){
		// Do nothing.
	}
	
	public int getLength(){
		return literal.length;
	}
	
	public AbstractStackNode[] getChildren(){
		throw new UnsupportedOperationException();
	}
	
	public void addResult(INode[] children){
		throw new UnsupportedOperationException();
	}
	
	public INode getResult(){
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
