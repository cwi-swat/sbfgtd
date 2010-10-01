package gll.stack;

import gll.result.AbstractContainerNode;
import gll.result.AbstractNode;
import gll.result.struct.Link;
import gll.util.ArrayList;

public final class SeparatedListStackNode extends AbstractStackNode implements IListStackNode{
	private final static EpsilonStackNode EMPTY = new EpsilonStackNode(DEFAULT_LIST_EPSILON_ID, 0);
	
	private final String nodeName;

	private final AbstractStackNode child;
	private final AbstractStackNode[] separators;
	private final boolean isPlusList;
	
	private AbstractContainerNode result;
	
	public SeparatedListStackNode(int id, int dot, AbstractStackNode child, AbstractStackNode[] separators, String nodeName, boolean isPlusList){
		super(id, dot);
		
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
		return new SeparatedListStackNode(this);
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
		listNode.initEdges();
		listNode.addEdgeWithPrefix(this, null, startLocation);
		
		int numberOfSeparators = separators.length;
		AbstractStackNode[] prod = new AbstractStackNode[numberOfSeparators + 2];
		
		listNode.setNext(prod);
		prod[0] = listNode; // Start
		for(int i = numberOfSeparators - 1; i >= 0; --i){
			AbstractStackNode separator = separators[i];
			separator.setNext(prod);
			separator.markAsSeparator();
			prod[i + 1] = separator;
		}
		prod[numberOfSeparators + 1] = listNode; // End
		
		if(isPlusList){
			return new AbstractStackNode[]{listNode};
		}
		
		AbstractStackNode empty = EMPTY.getCleanCopy();
		empty.markAsEndNode();
		empty.setStartLocation(startLocation);
		empty.initEdges();
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
