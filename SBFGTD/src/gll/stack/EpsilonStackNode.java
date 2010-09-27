package gll.stack;

import gll.result.AbstractNode;
import gll.result.AbstractContainerNode;
import gll.result.EpsilonNode;
import gll.result.struct.Link;
import gll.util.ArrayList;

public final class EpsilonStackNode extends AbstractStackNode implements IMatchableStackNode{
	private final static EpsilonNode result = new EpsilonNode();
	
	private boolean isReduced;
	
	public EpsilonStackNode(int id){
		super(id);
	}
	
	private EpsilonStackNode(EpsilonStackNode original){
		super(original);
	}
	
	private EpsilonStackNode(EpsilonStackNode original, ArrayList<Link>[] prefixes){
		super(original, prefixes);
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
		isReduced = true;
		return true;
	}
	
	public boolean isClean(){
		return !isReduced;
	}
	
	public AbstractStackNode getCleanCopy(){
		return new EpsilonStackNode(id);
	}
	
	public AbstractStackNode getCleanCopyWithoutPrefixes(){
		return new EpsilonStackNode(this);
	}

	public AbstractStackNode getCleanCopyWithPrefix(){
		return new EpsilonStackNode(this, prefixesMap);
	}
	
	public void setResultStore(AbstractContainerNode resultStore){
		throw new UnsupportedOperationException();
	}
	
	public AbstractContainerNode getResultStore(){
		throw new UnsupportedOperationException();
	}
	
	public int getLength(){
		return 0;
	}
	
	public AbstractStackNode[] getChildren(){
		throw new UnsupportedOperationException();
	}
	
	public AbstractNode getResult(){
		return result;
	}
	
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append('(');
		sb.append(startLocation);
		sb.append(')');
		
		return sb.toString();
	}
}
