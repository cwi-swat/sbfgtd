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
	
	public boolean isNullable(){
		return true;
	}
	
	public String print(IndexedStack<AbstractNode> stack, int depth, CycleMark cycleMark){
		return EPSILON_STRING;
	}
	
	public String toString(){
		return EPSILON_STRING;
	}
}
