package gll.stack;

import gll.result.EpsilonNode;
import gll.result.INode;

public final class EpsilonStackNode extends StackNode{
	private final static EpsilonNode result = new EpsilonNode();
	
	public EpsilonStackNode(int id){
		super(id);
	}
	
	private EpsilonStackNode(EpsilonStackNode epsilonParseStackNode){
		super(epsilonParseStackNode);
	}
	
	public boolean isReducable(){
		return true;
	}
	
	public boolean isList(){
		return false;
	}
	
	public String getMethodName(){
		throw new UnsupportedOperationException();
	}
	
	public boolean reduce(char[] input){
		return true;
	}
	
	public StackNode getCleanCopy(){
		return new EpsilonStackNode(this);
	}
	
	public StackNode getCleanCopyWithPrefix(){
		EpsilonStackNode epsn = new EpsilonStackNode(this);
		epsn.prefixes = prefixes;
		epsn.prefixStartLocations = prefixStartLocations;
		return epsn;
	}
	
	public int getLength(){
		return 0;
	}
	
	public void mark(){
		throw new UnsupportedOperationException();
	}
	
	public boolean isMarked(){
		throw new UnsupportedOperationException();
	}
	
	public StackNode[] getChildren(){
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
		sb.append('(');
		sb.append(startLocation);
		sb.append(')');
		
		return sb.toString();
	}
}
