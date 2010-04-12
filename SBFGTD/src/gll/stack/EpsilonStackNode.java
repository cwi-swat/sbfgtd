package gll.stack;

import gll.result.INode;
import gll.result.TextNode;

public class EpsilonStackNode extends StackNode{
	private final TextNode result;
	
	public EpsilonStackNode(int id){
		super(id);
		
		result = new TextNode(new char[]{});
	}
	
	private EpsilonStackNode(EpsilonStackNode terminalParseStackNode){
		super(terminalParseStackNode);
		
		result = terminalParseStackNode.result;
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
	
	public String getNodeName(){
		throw new UnsupportedOperationException();
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
	
	public void addResult(INode result){
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
