package gll.stack;

import gll.IGLL;
import gll.result.Alternative;
import gll.result.ContainerNode;
import gll.result.INode;
import gll.util.ArrayList;

public class ListStackNode extends StackNode{
	private final String nodeName;

	private final StackNode child;
	private final boolean isPlusList;
	
	private boolean marked;
	
	private final ArrayList<INode> results;
	
	public ListStackNode(int id, StackNode child, String nodeName, boolean isPlusList){
		super(id);
		
		this.nodeName = nodeName;
		
		this.child = child;
		this.isPlusList = isPlusList;
		
		this.results = null;
	}
	
	public ListStackNode(int id, StackNode child, String nodeName, boolean isPlusList, ArrayList<INode> results){
		super(id);
		
		this.nodeName = nodeName;
		
		this.child = child;
		this.isPlusList = isPlusList;
		
		this.results = results;
	}
	
	private ListStackNode(ListStackNode listParseStackNode){
		super(listParseStackNode);
		
		nodeName = listParseStackNode.nodeName;

		child = listParseStackNode.child;
		isPlusList = listParseStackNode.isPlusList;
		
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
	
	public StackNode getCleanCopy(){
		return new ListStackNode(this);
	}
	
	public StackNode getCleanCopyWithPrefix(){
		ListStackNode lpsn = new ListStackNode(this);
		lpsn.prefixes = prefixes;
		lpsn.prefixStartLocations = prefixStartLocations;
		return lpsn;
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
		StackNode psn = child.getCleanCopy();
		StackNode cpsn = child.getCleanCopy();
		ListStackNode lpsn = new ListStackNode((id | IGLL.LIST_LIST_FLAG), child, nodeName, true, new ArrayList<INode>(1));
		
		lpsn.addNext(psn);
		psn.addEdge(lpsn);
		psn.addEdge(this);
		
		cpsn.addEdge(lpsn);
		cpsn.addEdge(this);
		
		psn.setStartLocation(-1);
		lpsn.setStartLocation(startLocation);
		cpsn.setStartLocation(startLocation);
		
		if(isPlusList){
			return new StackNode[]{cpsn};
		}
		
		EpsilonStackNode epsn = new EpsilonStackNode(DEFAULT_LIST_EPSILON_ID);
		epsn.addEdge(this);
		
		return new StackNode[]{cpsn, epsn};
	}
	
	public void addResult(INode[] children){
		results.add(new ContainerNode(nodeName, children));
	}
	
	public INode getResult(){
		return new Alternative(results);
	}

	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append(nodeName);
		sb.append(getId());
		sb.append('(');
		sb.append(startLocation);
		sb.append(',');
		sb.append('?');
		sb.append(')');
		
		return sb.toString();
	}
}
