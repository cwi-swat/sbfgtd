package gll.stack;

import gll.IGLL;
import gll.result.Alternative;
import gll.result.INode;
import gll.util.ArrayList;

public class NonTerminalListParseStackNode extends ParseStackNode{
	private final String production;

	private final String child;
	private final boolean isPlusList;
	
	private boolean marked;
	
	private ArrayList<INode> results;
	
	public NonTerminalListParseStackNode(int id, String child, String production, boolean isPlusList){
		super(id);
		
		this.production = production;
		
		this.child = child;
		this.isPlusList = isPlusList;
	}
	
	private NonTerminalListParseStackNode(NonTerminalListParseStackNode nonTerminalListParseStackNode){
		super(nonTerminalListParseStackNode);
		
		production = nonTerminalListParseStackNode.production;

		child = nonTerminalListParseStackNode.child;
		isPlusList = nonTerminalListParseStackNode.isPlusList;
		
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
	
	public ParseStackNode getCleanCopy(){
		return new NonTerminalListParseStackNode(this);
	}
	
	public ParseStackNode getCleanCopyWithPrefix(){
		NonTerminalListParseStackNode ntpsn = new NonTerminalListParseStackNode(this);
		ntpsn.prefixes = prefixes;
		ntpsn.prefixStartLocations = prefixStartLocations;
		return ntpsn;
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
		NonTerminalParseStackNode ntpsn = new NonTerminalParseStackNode(child, (id | IGLL.LIST_CHILD_FLAG));
		NonTerminalParseStackNode ntcpsn = new NonTerminalParseStackNode(child, (id | IGLL.LIST_CHILD_FLAG), new ArrayList<INode>());
		NonTerminalListParseStackNode ntlpsn = new NonTerminalListParseStackNode((id | IGLL.LIST_CHILD_FLAG), child, production, true);
		
		ntpsn.addEdge(this);
		ntlpsn.addNext(ntpsn);
		ntpsn.addEdge(ntlpsn);
		ntcpsn.addEdge(ntlpsn);

		ntlpsn.setStartLocation(startLocation);
		ntcpsn.setStartLocation(startLocation);
		
		if(isPlusList){
			return new ParseStackNode[]{ntcpsn};
		}
		
		EpsilonParseStackNode epsn = new EpsilonParseStackNode(DEFAULT_LIST_EPSILON_ID);
		epsn.addEdge(this);
		
		return new ParseStackNode[]{ntcpsn, epsn};
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
