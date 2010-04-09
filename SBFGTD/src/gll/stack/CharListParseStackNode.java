package gll.stack;

import gll.IGLL;
import gll.result.INode;

public class CharListParseStackNode extends ParseStackNode{
	private final String production;
	
	private final char[][] ranges;
	private final char[] characters;
	
	private final String child;
	private final boolean isPlusList;
	
	private boolean marked;
	
	private INode result;
	
	public CharListParseStackNode(int id, char[][] ranges, char[] characters, String child, String production, boolean isPlusList){
		super(id);
		
		this.ranges = ranges;
		this.characters = characters;
		
		this.production = production;
		
		this.child = child;
		this.isPlusList = isPlusList;
	}
	
	private CharListParseStackNode(CharListParseStackNode terminalListParseStackNode){
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
	
	public ParseStackNode getCleanCopy(){
		return new CharListParseStackNode(this);
	}
	
	public ParseStackNode getCleanCopyWithPrefix(){
		CharListParseStackNode tpsn = new CharListParseStackNode(this);
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
	
	public ParseStackNode[] getChildren(){
		CharParseStackNode cpsn = new CharParseStackNode(ranges, characters, (id | IGLL.LIST_CHILD_FLAG), production);
		CharParseStackNode ccpsn = new CharParseStackNode(ranges, characters, (id | IGLL.LIST_CHILD_FLAG), production);
		CharListParseStackNode clpsn = new CharListParseStackNode((id | IGLL.LIST_CHILD_FLAG), ranges, characters, child, production, true);

		cpsn.addEdge(this);
		clpsn.addNext(cpsn);
		cpsn.addEdge(clpsn);
		ccpsn.addEdge(clpsn);

		clpsn.setStartLocation(startLocation);
		ccpsn.setStartLocation(startLocation);
		
		if(isPlusList){
			return new ParseStackNode[]{ccpsn};
		}
		
		EpsilonParseStackNode epsn = new EpsilonParseStackNode(DEFAULT_LIST_EPSILON_ID);
		epsn.addEdge(this);
		
		return new ParseStackNode[]{ccpsn, epsn};
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
