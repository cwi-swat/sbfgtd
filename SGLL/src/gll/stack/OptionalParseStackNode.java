package gll.stack;

import gll.result.INode;

public class OptionalParseStackNode extends ParseStackNode{
	private final ParseStackNode optional;
	
	private final String production;
	
	private boolean marked;
	
	private INode result;
	
	public OptionalParseStackNode(int id, ParseStackNode optional, String production){
		super(id);
		
		this.optional = optional;
		
		this.production = production;
	}
	
	private OptionalParseStackNode(OptionalParseStackNode optionalParseStackNode){
		super(optionalParseStackNode);
		
		optional = optionalParseStackNode.optional;
		
		production = optionalParseStackNode.production;
	}
	
	public boolean isReducable(){
		return false;
	}
	
	public boolean isList(){
		return true;
	}
	
	public void mark(){
		marked = true;
	}
	
	public boolean isMarked(){
		return marked;
	}
	
	public int getLength(){
		throw new UnsupportedOperationException();
	}
	
	public boolean reduce(char[] input){
		throw new UnsupportedOperationException();
	}
	
	public ParseStackNode getCleanCopy(){
		return new OptionalParseStackNode(this);
	}
	
	public ParseStackNode getCleanCopyWithPrefix(){
		OptionalParseStackNode opsn = new OptionalParseStackNode(this);
		opsn.prefixes = prefixes;
		opsn.prefixStartLocations = prefixStartLocations;
		return opsn;
	}
	
	public ParseStackNode[] getChildren(){
		ParseStackNode[] children = new ParseStackNode[2];
		ParseStackNode copy = optional.getCleanCopy();
		copy.addEdge(this);
		children[0] = copy;
		children[1] = new EpsilonParseStackNode(DEFAULT_LIST_EPSILON_ID);
		return children;
	}
	
	public String getMethodName(){
		throw new UnsupportedOperationException();
	}
	
	public String getNodeName(){
		return production;
	}
	
	public void addResult(INode result){
		this.result = result;
	}
	
	public INode getResult(){
		return result;
	}
}
