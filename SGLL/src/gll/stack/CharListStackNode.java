package gll.stack;

import gll.IGLL;
import gll.result.INode;

public class CharListStackNode extends StackNode{
	private final String production;
	
	private final char[][] ranges;
	private final char[] characters;
	
	private final String child;
	private final boolean isPlusList;
	
	private boolean marked;
	
	private INode result;
	
	public CharListStackNode(int id, char[][] ranges, char[] characters, String child, String production, boolean isPlusList){
		super(id);
		
		this.ranges = ranges;
		this.characters = characters;
		
		this.production = production;
		
		this.child = child;
		this.isPlusList = isPlusList;
	}
	
	private CharListStackNode(CharListStackNode terminalListParseStackNode){
		super(terminalListParseStackNode);
		
		ranges = terminalListParseStackNode.ranges;
		characters = terminalListParseStackNode.characters;
		
		child = terminalListParseStackNode.child;
		isPlusList = terminalListParseStackNode.isPlusList;
		
		production = terminalListParseStackNode.production;
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
		return new CharListStackNode(this);
	}
	
	public StackNode getCleanCopyWithPrefix(){
		CharListStackNode tpsn = new CharListStackNode(this);
		tpsn.prefixes = prefixes;
		tpsn.prefixStartLocations = prefixStartLocations;
		return tpsn;
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
		CharStackNode cpsn = new CharStackNode(ranges, characters, (id | IGLL.LIST_NEXT_FLAG), child);
		CharStackNode ccpsn = new CharStackNode(ranges, characters, (id | IGLL.LIST_CHILD_FLAG), child);
		CharListStackNode clpsn = new CharListStackNode((id | IGLL.LIST_LIST_FLAG), ranges, characters, child, production, true);

		cpsn.addEdge(this);
		clpsn.addNext(cpsn);
		cpsn.addEdge(clpsn);
		ccpsn.addEdge(clpsn);
		ccpsn.addEdge(this);

		clpsn.setStartLocation(startLocation);
		ccpsn.setStartLocation(startLocation);
		
		if(isPlusList){
			return new StackNode[]{ccpsn};
		}
		
		EpsilonStackNode epsn = new EpsilonStackNode(DEFAULT_LIST_EPSILON_ID);
		epsn.addEdge(this);
		
		return new StackNode[]{ccpsn, epsn};
	}
	
	public void addResult(INode result){
		this.result = result;
	}
	
	public INode getResult(){
		return result;
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
