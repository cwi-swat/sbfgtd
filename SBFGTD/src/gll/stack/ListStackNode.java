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
	
	private ContainerNode result;
	
	public ListStackNode(int id, AbstractStackNode child, String nodeName, boolean isPlusList){
		super(id);
		
		this.nodeName = nodeName;
		
		this.child = child;
		this.isPlusList = isPlusList;
	}
	
	private ListStackNode(ListStackNode original, int newId){
		super(newId);
		
		nodeName = original.nodeName;

		child = original.child;
		isPlusList = true;
	}
	
	private ListStackNode(ListStackNode original){
		super(original);
		
		nodeName = original.nodeName;

		child = original.child;
		isPlusList = original.isPlusList;
	}
	
	private ListStackNode(ListStackNode original, ArrayList<INode[]> prefixes, IntegerList prefixStartLocations){
		super(original, prefixes, prefixStartLocations);
		
		nodeName = original.nodeName;

		child = original.child;
		isPlusList = original.isPlusList;
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
	
	public void initializeResultStore(){
		result = new ContainerNode(nodeName);
	}
	
	public int getLength(){
		throw new UnsupportedOperationException();
	}
	
	public AbstractStackNode[] getChildren(){
		AbstractStackNode psn = child.getCleanCopy();
		AbstractStackNode lpsn = new ListStackNode(this, id | IGLL.LIST_LIST_FLAG);
		
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
