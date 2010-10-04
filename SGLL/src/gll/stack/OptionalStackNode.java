package gll.stack;

import gll.result.AbstractContainerNode;
import gll.result.AbstractNode;
import gll.result.struct.Link;
import gll.util.ArrayList;

public final class OptionalStackNode extends AbstractStackNode implements IListStackNode{
	private final static EpsilonStackNode EMPTY = new EpsilonStackNode(DEFAULT_LIST_EPSILON_ID, 0);
	
	private final AbstractStackNode[] children;
	
	private final String nodeName;
	
	private AbstractContainerNode result;
	
	public OptionalStackNode(int id, int dot, AbstractStackNode optional, String nodeName){
		super(id, dot);
		
		this.children = generateChildren(optional);
		
		this.nodeName = nodeName;
	}
	
	private OptionalStackNode(OptionalStackNode original){
		super(original);
		
		children = original.children;
		
		nodeName = original.nodeName;
	}
	
	private OptionalStackNode(OptionalStackNode original, ArrayList<Link>[] prefixes){
		super(original, prefixes);
		
		children = original.children;
		
		nodeName = original.nodeName;
	}
	
	private AbstractStackNode[] generateChildren(AbstractStackNode optional){
		AbstractStackNode child = optional.getCleanCopy();
		child.markAsEndNode();
		
		AbstractStackNode empty = EMPTY.getCleanCopy();
		empty.markAsEndNode();
		
		return new AbstractStackNode[]{child, empty};
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
		return children;
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
