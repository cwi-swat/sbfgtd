package gll.stack;

import gll.result.INode;

public class OptionalStackNode extends StackNode{
	private final StackNode optional;
	
	private final String production;
	
	private boolean marked;
	
	private INode result;
	
	public OptionalStackNode(int id, StackNode optional, String production){
		super(id);
		
		this.optional = optional;
		
		this.production = production;
	}
	
	private OptionalStackNode(OptionalStackNode optionalParseStackNode){
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
	
	public StackNode getCleanCopy(){
		return new OptionalStackNode(this);
	}
	
	public StackNode getCleanCopyWithPrefix(){
		OptionalStackNode opsn = new OptionalStackNode(this);
		opsn.prefixes = prefixes;
		opsn.prefixStartLocations = prefixStartLocations;
		return opsn;
	}
	
	public StackNode[] getChildren(){
		StackNode copy = optional.getCleanCopy();
		StackNode epsn = new EpsilonStackNode(DEFAULT_LIST_EPSILON_ID);
		copy.addEdge(this);
		epsn.addEdge(this);

		StackNode[] children = new StackNode[2];
		children[0] = copy;
		children[1] = epsn;
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
