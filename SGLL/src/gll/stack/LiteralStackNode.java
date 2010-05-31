package gll.stack;

import gll.result.INode;
import gll.result.LiteralNode;

public final class LiteralStackNode extends AbstractStackNode implements IReducableStackNode{
	private final char[] literal;
	
	private final LiteralNode result;
	
	public LiteralStackNode(int id, char[] literal){
		super(id);

		this.literal = literal;
		result = new LiteralNode(literal);
	}
	
	private LiteralStackNode(LiteralStackNode literalParseStackNode){
		super(literalParseStackNode);

		literal = literalParseStackNode.literal;
		
		result = literalParseStackNode.result;
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
		LiteralStackNode lpsn = new LiteralStackNode(this);
		lpsn.prefixes = prefixes;
		lpsn.prefixStartLocations = prefixStartLocations;
		return lpsn;
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
