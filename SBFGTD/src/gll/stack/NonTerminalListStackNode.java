package gll.stack;

import gll.IGLL;
import gll.result.Alternative;
import gll.result.INode;
import gll.util.ArrayList;

public class NonTerminalListStackNode extends StackNode{
	private final String production;

	private final String child;
	private final boolean isPlusList;
	
	private boolean marked;
	
	private final ArrayList<INode> results;
	
	public NonTerminalListStackNode(int id, String child, String production, boolean isPlusList){
		super(id);
		
		this.production = production;
		
		this.child = child;
		this.isPlusList = isPlusList;
		
		this.results = null;
	}
	
	public NonTerminalListStackNode(int id, String child, String production, boolean isPlusList, ArrayList<INode> results){
		super(id);
		
		this.production = production;
		
		this.child = child;
		this.isPlusList = isPlusList;
		
		this.results = results;
	}
	
	private NonTerminalListStackNode(NonTerminalListStackNode nonTerminalListParseStackNode){
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
	
	public StackNode getCleanCopy(){
		return new NonTerminalListStackNode(this);
	}
	
	public StackNode getCleanCopyWithPrefix(){
		NonTerminalListStackNode ntpsn = new NonTerminalListStackNode(this);
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
	
	public StackNode[] getChildren(){
		NonTerminalStackNode ntpsn = new NonTerminalStackNode(child, (id | IGLL.LIST_NEXT_FLAG), new ArrayList<INode>(1));
		NonTerminalStackNode ntcpsn = new NonTerminalStackNode(child, (id | IGLL.LIST_CHILD_FLAG), new ArrayList<INode>(1));
		NonTerminalListStackNode ntlpsn = new NonTerminalListStackNode((id | IGLL.LIST_LIST_FLAG), child, production, true, new ArrayList<INode>(1));
		
		ntpsn.addEdge(this);
		ntlpsn.addNext(ntpsn);
		ntpsn.addEdge(ntlpsn);
		ntcpsn.addEdge(ntlpsn);
		ntcpsn.addEdge(this);

		ntlpsn.setStartLocation(startLocation);
		ntcpsn.setStartLocation(startLocation);
		
		if(isPlusList){
			return new StackNode[]{ntcpsn};
		}
		
		EpsilonStackNode epsn = new EpsilonStackNode(DEFAULT_LIST_EPSILON_ID);
		epsn.addEdge(this);
		
		return new StackNode[]{ntcpsn, epsn};
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
