package gll.result;

public class EpsilonNode implements INode{
	private final static String EPSILON_STRING = "";
	
	public EpsilonNode(){
		super();
	}
	
	public boolean isEpsilon(){
		return true;
	}
	
	public String toString(){
		return EPSILON_STRING;
	}
}
