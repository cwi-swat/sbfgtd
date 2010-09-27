package gll.stack;

import gll.IGLL;
import gll.result.AbstractNode;
import gll.result.AbstractContainerNode;
import gll.result.struct.Link;
import gll.util.ArrayList;

public final class SeparatedListStackNode extends AbstractStackNode implements IListStackNode{
	private final String nodeName;

	private final AbstractStackNode child;
	private final AbstractStackNode[] separators;
	private final boolean isPlusList;
	
	private AbstractContainerNode result;
	
	public SeparatedListStackNode(int id, AbstractStackNode child, AbstractStackNode[] separators, String nodeName, boolean isPlusList){
		super(id);
		
		this.nodeName = nodeName;
		
		this.child = child;
		this.separators = separators;
		this.isPlusList = isPlusList;
	}
	
	private SeparatedListStackNode(SeparatedListStackNode original){
		super(original);
		
		nodeName = original.nodeName;

		child = original.child;
		separators = original.separators;
		isPlusList = original.isPlusList;
	}
	
	private SeparatedListStackNode(SeparatedListStackNode original, ArrayList<Link>[] prefixes){
		super(original, prefixes);
		
		nodeName = original.nodeName;

		child = original.child;
		separators = original.separators;
		isPlusList = original.isPlusList;
	}
	
	public String getIdentifier(){
		return nodeName+id; // Add the id to make it unique.
	}
	
	public String getName(){
		return nodeName;
	}
	
	public String getMethodName(){
		throw new UnsupportedOperationException();
	}
	
	public boolean match(char[] input){
		throw new UnsupportedOperationException();
	}
	
	public boolean isClean(){
		return (result == null);
	}
	
	public AbstractStackNode getCleanCopy(){
		return new SeparatedListStackNode(id, child, separators, nodeName, isPlusList);
	}
	
	public AbstractStackNode getCleanCopyWithoutPrefixes(){
		return new SeparatedListStackNode(this);
	}

	public AbstractStackNode getCleanCopyWithPrefix(){
		return new SeparatedListStackNode(this, prefixesMap);
	}
	
	public void setResultStore(AbstractContainerNode resultStore){
		result = resultStore;
	}
	
	public AbstractContainerNode getResultStore(){
		return result;
	}
	
	public int getLength(){
		throw new UnsupportedOperationException();
	}
	
	public AbstractStackNode[] getChildren(){
		AbstractStackNode listNode = child.getCleanCopy();
		listNode.markAsEndNode();
		listNode.setStartLocation(startLocation);
		listNode.addEdgeWithPrefix(this, null, startLocation);
		
		AbstractStackNode from = listNode;
		AbstractStackNode to = separators[0].getCleanCopy();
		to.markAsSeparator();
		from.setNext(to);
		from = to;
		for(int i = 1; i < separators.length; ++i){
			to = separators[i].getCleanCopy();
			to.markAsSeparator();
			from.setNext(to);
			from = to;
		}
		from.setNext(listNode);
		
		if(isPlusList){
			return new AbstractStackNode[]{listNode};
		}
		
		EpsilonStackNode empty = new EpsilonStackNode(IGLL.DEFAULT_LIST_EPSILON_ID);
		empty.markAsEndNode();
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
