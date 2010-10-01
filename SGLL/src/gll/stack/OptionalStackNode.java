package gll.stack;

import gll.result.AbstractContainerNode;
import gll.result.AbstractNode;
import gll.result.struct.Link;
import gll.util.ArrayList;

public final class OptionalStackNode extends AbstractStackNode implements IListStackNode{
	private final static EpsilonStackNode EMPTY = new EpsilonStackNode(DEFAULT_LIST_EPSILON_ID, 0);
	
	private final AbstractStackNode optional;
	
	private final String nodeName;
	
	private AbstractContainerNode result;
	
	public OptionalStackNode(int id, int dot, AbstractStackNode optional, String nodeName){
		super(id, dot);
		
		this.optional = optional;
		
		this.nodeName = nodeName;
	}
	
	private OptionalStackNode(OptionalStackNode original){
		super(original);
		
		optional = original.optional;
		
		nodeName = original.nodeName;
	}
	
	private OptionalStackNode(OptionalStackNode original, ArrayList<Link>[] prefixes){
		super(original, prefixes);
		
		optional = original.optional;
		
		nodeName = original.nodeName;
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
	
	public int getLength(){
		throw new UnsupportedOperationException();
	}
	
	public boolean match(char[] input){
		throw new UnsupportedOperationException();
	}
	
	public boolean isClean(){
		return (result == null);
	}
	
	public AbstractStackNode getCleanCopy(){
		return new OptionalStackNode(this);
	}
	
	public AbstractStackNode getCleanCopyWithoutPrefixes(){
		return new OptionalStackNode(this);
	}

	public AbstractStackNode getCleanCopyWithPrefix(){
		return new OptionalStackNode(this, prefixesMap);
	}
	
	public void setResultStore(AbstractContainerNode resultStore){
		result = resultStore;
	}
	
	public AbstractContainerNode getResultStore(){
		return result;
	}
	
	public AbstractStackNode[] getChildren(){
		AbstractStackNode child = optional.getCleanCopy();
		child.markAsEndNode();
		child.setStartLocation(startLocation);
		child.initEdges();
		child.addEdge(this);
		
		AbstractStackNode empty = EMPTY.getCleanCopy();
		empty.markAsEndNode();
		empty.setStartLocation(startLocation);
		empty.initEdges();
		empty.addEdge(this);
		
		return new AbstractStackNode[]{child, empty};
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
