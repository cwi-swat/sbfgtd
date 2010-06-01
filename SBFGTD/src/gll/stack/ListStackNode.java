package gll.stack;

import gll.IGLL;
import gll.result.ContainerNode;
import gll.result.INode;
import gll.util.ArrayList;
import gll.util.IntegerList;

public final class ListStackNode extends AbstractStackNode implements IListStackNode{
	private final String nodeName;

	private final AbstractStackNode child;
	private final boolean isPlusList;
	
	private final INode result;
	
	public ListStackNode(int id, AbstractStackNode child, String nodeName, boolean isPlusList){
		super(id);
		
		this.nodeName = nodeName;
		
		this.child = child;
		this.isPlusList = isPlusList;
		
		this.result = null;
	}
	
	private ListStackNode(int id, AbstractStackNode child, String nodeName, boolean isPlusList, INode result){
		super(id);
		
		this.nodeName = nodeName;
		
		this.child = child;
		this.isPlusList = isPlusList;
		
		this.result = result;
	}
	
	private ListStackNode(ListStackNode original){
		super(original);
		
		nodeName = original.nodeName;

		child = original.child;
		isPlusList = original.isPlusList;
		
		result = new ContainerNode(nodeName);
	}
	
	private ListStackNode(ListStackNode original, ArrayList<INode[]> prefixes, IntegerList prefixStartLocations){
		super(original, prefixes, prefixStartLocations);
		
		nodeName = original.nodeName;

		child = original.child;
		isPlusList = original.isPlusList;
		
		result = new ContainerNode(nodeName);
	}
	
	public String getMethodName(){
		throw new UnsupportedOperationException();
	}
	
	public boolean reduce(char[] input){
		throw new UnsupportedOperationException();
	}
	
	public boolean isClean(){
		return (result == null);
	}
	
	public AbstractStackNode getCleanCopy(){
		return new ListStackNode(this);
	}
	
	public AbstractStackNode getCleanCopyWithPrefix(){
		return new ListStackNode(this, prefixes, prefixStartLocations);
	}
	
	public int getLength(){
		throw new UnsupportedOperationException();
	}
	
	public AbstractStackNode[] getChildren(){
		AbstractStackNode psn = child.getCleanCopy();
		ListStackNode lpsn = new ListStackNode((id | IGLL.LIST_LIST_FLAG), child, nodeName, true, new ContainerNode(nodeName));
		
		psn.addNext(lpsn);
		lpsn.addEdge(this);
		psn.addEdge(this);
		
		psn.setStartLocation(startLocation);
		
		if(isPlusList){
			return new AbstractStackNode[]{psn};
		}
		
		EpsilonStackNode epsn = new EpsilonStackNode(DEFAULT_LIST_EPSILON_ID);
		epsn.addEdge(this);
		epsn.setStartLocation(startLocation);
		
		return new AbstractStackNode[]{psn, epsn};
	}
	
	public void addResult(INode[] children){
		result.addAlternative(children);
	}
	
	public INode getResult(){
		return result;
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
