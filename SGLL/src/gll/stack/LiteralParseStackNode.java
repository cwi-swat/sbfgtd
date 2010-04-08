package gll.stack;

import gll.result.INode;
import gll.result.TextNode;

public final class LiteralParseStackNode extends ParseStackNode{
	private final char[] literal;
	
	private final TextNode result;
	
	public LiteralParseStackNode(char[] literal, int id){
		super(id);

		this.literal = literal;
		result = new TextNode(literal);
	}
	
	private LiteralParseStackNode(LiteralParseStackNode literalParseStackNode){
		super(literalParseStackNode);

		literal = literalParseStackNode.literal;
		
		result = literalParseStackNode.result;
	}
	
	public boolean isReducable(){
		return true;
	}
	
	public boolean isList(){
		return false;
	}
	
	public boolean isListNode(){
		return false;
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
	
	public String getNodeName(){
		throw new UnsupportedOperationException();
	}
	
	public ParseStackNode getCleanCopy(){
		return new LiteralParseStackNode(this);
	}
	
	public ParseStackNode getCleanCopyWithPrefix(){
		LiteralParseStackNode lpsn = new LiteralParseStackNode(this);
		lpsn.prefixes = prefixes;
		lpsn.prefixStartLocations = prefixStartLocations;
		return lpsn;
	}
	
	public int getLength(){
		return literal.length;
	}
	
	public void mark(){
		throw new UnsupportedOperationException();
	}
	
	public boolean isMarked(){
		throw new UnsupportedOperationException();
	}
	
	public ParseStackNode[] getChildren(){
		throw new UnsupportedOperationException();
	}
	
	public void addResult(INode result){
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
