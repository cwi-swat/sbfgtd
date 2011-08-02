package gtd.stack;

import gtd.result.AbstractNode;
import gtd.result.EpsilonNode;

public final class EpsilonStackNode extends AbstractStackNode implements IMatchableStackNode{
	private final static EpsilonNode result = new EpsilonNode();
	
	public EpsilonStackNode(int id, int dot){
		super(id, dot);
	}
	
	private EpsilonStackNode(EpsilonStackNode original, int startLocation){
		super(original, startLocation);
	}
	
	public boolean isEmptyLeafNode(){
		return true;
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
		return result;
	}
	
	public AbstractStackNode getCleanCopy(int startLocation){
		return new EpsilonStackNode(this, startLocation);
	}
	
	public AbstractStackNode getCleanCopyWithResult(int startLocation, AbstractNode result){
		return new EpsilonStackNode(this, startLocation);
	}
	
	public int getLength(){
		return 0;
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
		sb.append('(');
		sb.append(startLocation);
		sb.append(')');
		
		return sb.toString();
	}
}
