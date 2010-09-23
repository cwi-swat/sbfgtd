package gll.stack;

import gll.IGLL;
import gll.result.AbstractNode;
import gll.result.AbstractContainerNode;
import gll.result.struct.Link;
import gll.util.ArrayList;

public final class OptionalStackNode extends AbstractStackNode implements IListStackNode{
	private final AbstractStackNode optional;
	
	private final String nodeName;
	
	private AbstractContainerNode result;
	
	public OptionalStackNode(int id, AbstractStackNode optional, String nodeName){
		super(id);
		
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
		return new OptionalStackNode(id, optional, nodeName);
	}
	
	public AbstractStackNode getCleanCopyWithMark(){
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
		child.addEdge(this);
		
		AbstractStackNode empty = new EpsilonStackNode(IGLL.DEFAULT_LIST_EPSILON_ID);
		empty.markAsEndNode();
		empty.setStartLocation(startLocation);
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
