package gll.stack;

import gll.IGLL;
import gll.result.ContainerNode;
import gll.result.AbstractNode;
import gll.result.struct.Link;
import gll.util.ArrayList;
import gll.util.LinearIntegerKeyedMap;

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
	
	private ListStackNode(ListStackNode original){
		super(original);
		
		nodeName = original.nodeName;

		child = original.child;
		isPlusList = original.isPlusList;
	}
	
	private ListStackNode(ListStackNode original, LinearIntegerKeyedMap<ArrayList<Link>> prefixes){
		super(original, prefixes);
		
		nodeName = original.nodeName;

		child = original.child;
		isPlusList = original.isPlusList;
	}
	
	public String getName(){
		return nodeName;
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
		return new ListStackNode(this, prefixesMap);
	}
	
	public void setResultStore(ContainerNode resultStore){
		result = resultStore;
	}
	
	public ContainerNode getResultStore(){
		return result;
	}
	
	public int getLength(){
		throw new UnsupportedOperationException();
	}
	
	public AbstractStackNode[] getChildren(){
		AbstractStackNode listNode = child.getCleanCopy();
		
		listNode.addNext(listNode);
		listNode.addEdge(this);
		listNode.setStartLocation(startLocation);
		listNode.addPrefix(null, startLocation); // Dirty hack that works.
		
		if(isPlusList){
			return new AbstractStackNode[]{listNode};
		}
		
		EpsilonStackNode empty = new EpsilonStackNode(IGLL.DEFAULT_LIST_EPSILON_ID);

		empty.setStartLocation(startLocation);
		empty.addEdge(this);
		
		return new AbstractStackNode[]{listNode, empty};
	}
	
	public AbstractNode getResult(){
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
