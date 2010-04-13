package gll.stack;

import gll.IGLL;
import gll.result.Alternative;
import gll.result.INode;
import gll.util.ArrayList;

public class SeparatedListStackNode extends StackNode{
	private final String nodeName;

	private final StackNode child;
	private final StackNode[] separators;
	private final boolean isPlusList;
	
	private boolean marked;
	
	private final ArrayList<INode> results;
	
	public SeparatedListStackNode(int id, StackNode child, StackNode[] separators, String nodeName, boolean isPlusList){
		super(id);
		
		this.nodeName = nodeName;
		
		this.child = child;
		this.separators = separators;
		this.isPlusList = isPlusList;
		
		this.results = null;
	}
	
	public SeparatedListStackNode(int id, StackNode child, StackNode[] separators, String nodeName, boolean isPlusList, ArrayList<INode> results){
		super(id);
		
		this.nodeName = nodeName;
		
		this.child = child;
		this.separators = separators;
		this.isPlusList = isPlusList;
		
		this.results = results;
	}
	
	public SeparatedListStackNode(SeparatedListStackNode separatedListStackNode){
		super(separatedListStackNode);
		
		nodeName = separatedListStackNode.nodeName;

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
		return nodeName;
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
		StackNode psn = child.getCleanCopy();
		StackNode cpsn = child.getCleanCopy();
		SeparatedListStackNode slpsn = new SeparatedListStackNode((id | IGLL.LIST_LIST_FLAG), child, separators, nodeName, true, new ArrayList<INode>(1));
		
		StackNode from = slpsn;
		for(int i = 0; i < separators.length; i++){
			StackNode to = separators[i];
			from.addNext(to);
			from = to;
		}
		from.addNext(psn);
		psn.addEdge(slpsn);
		psn.addEdge(this);
		
		cpsn.addEdge(slpsn);
		cpsn.addEdge(this);
		
		psn.setStartLocation(-1); // Reset.
		slpsn.setStartLocation(startLocation);
		cpsn.setStartLocation(startLocation);
		
		if(isPlusList){
			return new StackNode[]{cpsn};
		}
		
		EpsilonStackNode epsn = new EpsilonStackNode(DEFAULT_LIST_EPSILON_ID);
		epsn.addEdge(this);
		
		return new StackNode[]{cpsn, epsn};
	}
	
	public void addResult(INode result){
		results.add(result);
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
