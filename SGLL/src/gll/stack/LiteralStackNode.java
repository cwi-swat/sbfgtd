package gll.stack;

import gll.result.AbstractNode;
import gll.result.ContainerNode;
import gll.result.LiteralNode;
import gll.result.struct.Link;
import gll.util.ArrayList;

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
	
	private LiteralStackNode(LiteralStackNode original, ArrayList<Link>[] prefixes){
		super(original, prefixes);

		literal = original.literal;
		
		result = original.result;
	}
	
	public String getName(){
		throw new UnsupportedOperationException();
	}
	
	public String getMethodName(){
		throw new UnsupportedOperationException();
	}
	
	public boolean reduce(char[] input){
		for(int i = literal.length - 1; i >= 0; --i){
			if(literal[i] != input[startLocation + i]) return false; // Did not match.
		}
		return true;
	}
	
	public boolean isClean(){
		return true;
	}
	
	public AbstractStackNode getCleanCopy(){
		return new LiteralStackNode(id, literal);
	}
	
	public AbstractStackNode getCleanCopyWithMark(){
		return new LiteralStackNode(this);
	}

	public AbstractStackNode getCleanCopyWithPrefix(){
		return new LiteralStackNode(this, prefixesMap);
	}
	
	public void setResultStore(ContainerNode resultStore){
		throw new UnsupportedOperationException();
	}
	
	public ContainerNode getResultStore(){
		throw new UnsupportedOperationException();
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
