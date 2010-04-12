package gll.stack;

import gll.result.Alternative;
import gll.result.INode;
import gll.util.ArrayList;

// TODO NOTE: Under construction.

public class SeparatedListStackNode extends StackNode{
	private final String production;

	private final StackNode child;
	private final StackNode[] separators;
	private final boolean isPlusList;
	
	private boolean marked;
	
	private final ArrayList<INode> results;
	
	public SeparatedListStackNode(int id, StackNode child, StackNode[] separators, String production, boolean isPlusList){
		super(id);
		
		this.production = production;
		
		this.child = child;
		this.separators = separators;
		this.isPlusList = isPlusList;
		
		this.results = null;
	}
	
	public SeparatedListStackNode(int id, StackNode child, StackNode[] separators, String production, boolean isPlusList, ArrayList<INode> results){
		super(id);
		
		this.production = production;
		
		this.child = child;
		this.separators = separators;
		this.isPlusList = isPlusList;
		
		this.results = results;
	}
	
	public SeparatedListStackNode(SeparatedListStackNode separatedListStackNode){
		super(separatedListStackNode);
		
		production = separatedListStackNode.production;

		child = separatedListStackNode.child;
		separators = separatedListStackNode.separators;
		isPlusList = separatedListStackNode.isPlusList;
		
		results = new ArrayList<INode>(1);
	}
	
	public boolean isReducable(){
		return false;
	}
	
	public boolean isList(){
		return true;
	}
	
	public String getMethodName(){
		throw new UnsupportedOperationException();
	}
	
	public boolean reduce(char[] input){
		throw new UnsupportedOperationException();
	}
	
	public String getNodeName(){
		return production;
	}
	
	public StackNode getCleanCopy(){
		return new SeparatedListStackNode(this);
	}
	
	public StackNode getCleanCopyWithPrefix(){
		SeparatedListStackNode slpsn = new SeparatedListStackNode(this);
		slpsn.prefixes = prefixes;
		slpsn.prefixStartLocations = prefixStartLocations;
		return slpsn;
	}
	
	public int getLength(){
		throw new UnsupportedOperationException();
	}
	
	public void mark(){
		marked = true;
	}
	
	public boolean isMarked(){
		return marked;
	}
	
	public StackNode[] getChildren(){
		// TODO Implement
		return null;
	}
	
	public void addResult(INode result){
		results.add(result);
	}
	
	public INode getResult(){
		return new Alternative(results);
	}

	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append(production);
		sb.append(getId());
		sb.append('(');
		sb.append(startLocation);
		sb.append(',');
		sb.append('?');
		sb.append(')');
		
		return sb.toString();
	}
}
