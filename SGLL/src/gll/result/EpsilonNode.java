package gll.result;

import gll.result.struct.Link;
import gll.util.IndexedStack;

public class EpsilonNode implements INode{
	private final static String EPSILON_STRING = "";
	
	public EpsilonNode(){
		super();
	}
	
	public void addAlternative(Link children){
		throw new UnsupportedOperationException();
	}
	
	public String toString(){
		return EPSILON_STRING;
	}
	
	public String toString(IndexedStack<INode> stack, int depth){
		return EPSILON_STRING;
	}
}
