package gll.result;

import gll.result.struct.Link;
import gll.util.IndexedStack;

public class EpsilonNode extends AbstractNode{
	private final static String EPSILON_STRING = "";
	
	public EpsilonNode(){
		super();
	}
	
	public void addAlternative(Link children){
		throw new UnsupportedOperationException();
	}
	
	protected boolean isNullable(){
		return false;
	}
	
	protected boolean isSeparator(){
		return false;
	}
	
	protected String print(IndexedStack<AbstractNode> stack, int depth, CycleMark cycleMark){
		return EPSILON_STRING;
	}
	
	public String toString(){
		return EPSILON_STRING;
	}
}
