package gll.stack;

import gll.IGLL;
import gll.result.ContainerNode;
import gll.result.INode;

public final class ListStackNode extends AbstractStackNode implements IListStackNode{
	private final String nodeName;

	private final AbstractStackNode child;
	private final boolean isPlusList;
	
	private boolean marked;
	
	private final INode result;
	
	public ListStackNode(int id, AbstractStackNode child, String nodeName, boolean isPlusList){
		super(id);
		
		this.nodeName = nodeName;
		
		this.child = child;
		this.isPlusList = isPlusList;
		
		this.result = null;
	}
	
	public ListStackNode(int id, AbstractStackNode child, String nodeName, boolean isPlusList, INode result){
		super(id);
		
		this.nodeName = nodeName;
		
		this.child = child;
		this.isPlusList = isPlusList;
		
		this.result = result;
	}
	
	private ListStackNode(ListStackNode listParseStackNode){
		super(listParseStackNode);
		
		nodeName = listParseStackNode.nodeName;

		child = listParseStackNode.child;
		isPlusList = listParseStackNode.isPlusList;
		
		result = new ContainerNode(nodeName);
	}
	
	public String getMethodName(){
		throw new UnsupportedOperationException();
	}
	
	public boolean reduce(char[] input){
		throw new UnsupportedOperationException();
	}
	
	public AbstractStackNode getCleanCopy(){
		return new ListStackNode(this);
	}
	
	public AbstractStackNode getCleanCopyWithPrefix(){
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
