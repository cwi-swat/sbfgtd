package gll.stack;

import gll.result.INode;
import gll.result.TextNode;

public class EpsilonParseStackNode extends ParseStackNode{
	private final TextNode result;
	
	public EpsilonParseStackNode(int id){
		super(id);
		
		result = new TextNode(new char[]{});
	}
	
	private EpsilonParseStackNode(EpsilonParseStackNode terminalParseStackNode){
		super(terminalParseStackNode.id);
		
		nexts = terminalParseStackNode.nexts;
		edges = terminalParseStackNode.edges;
		
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
	
	public ParseStackNode getCleanCopy(){
		return new EpsilonParseStackNode(this);
	}
	
	public ParseStackNode getCleanCopyWithPrefix(){
		EpsilonParseStackNode epsn = new EpsilonParseStackNode(this);
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
	
	public ParseStackNode[] getListChildren(){
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
