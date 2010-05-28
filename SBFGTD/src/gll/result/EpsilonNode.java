package gll.result;

import gll.util.Stack;

import java.io.IOException;
import java.io.Writer;

public class EpsilonNode implements INode{
	private final static String EPSILON_STRING = "";
	
	public EpsilonNode(){
		super();
	}
	
	public void addAlternative(INode[] children){
		throw new UnsupportedOperationException();
	}
	
	public boolean isEpsilon(){
		return true;
	}
	
	public void print(Writer out, Stack<INode> stack) throws IOException{
		out.write(EPSILON_STRING);
	}
	
	public String toString(){
		return EPSILON_STRING;
	}
}
