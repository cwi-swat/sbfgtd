package gtd.stack;

import gtd.result.AbstractNode;

public final class OptionalStackNode extends AbstractStackNode implements IExpandableStackNode{
	private final static EpsilonStackNode EMPTY = new EpsilonStackNode(DEFAULT_LIST_EPSILON_ID, 0);
	
	private final String nodeName;
	
	private final AbstractStackNode[] children;
	private final AbstractStackNode emptyChild;
	
	public OptionalStackNode(int id, int dot, AbstractStackNode optional, String nodeName){
		super(id, dot);
		
		this.nodeName = nodeName;
		
		this.children = generateChildren(optional);
		this.emptyChild = generateEmptyChild();
	}
	
	private OptionalStackNode(OptionalStackNode original){
		super(original);
		
		nodeName = original.nodeName;
		
		children = original.children;
		emptyChild = original.emptyChild;
	}
	
	private AbstractStackNode[] generateChildren(AbstractStackNode optional){
		AbstractStackNode child = optional.getCleanCopy();
		child.setProduction(new AbstractStackNode[]{child});
		child.markAsEndNode();
		
		return new AbstractStackNode[]{child};
	}
	
	private AbstractStackNode generateEmptyChild(){
		AbstractStackNode empty = EMPTY.getCleanCopy();
		empty.setProduction(new AbstractStackNode[]{empty});
		empty.markAsEndNode();
		
		return empty;
	}
	
	public boolean isEmptyLeafNode(){
		return false;
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
	
	public AbstractNode match(char[] input, int location){
		throw new UnsupportedOperationException();
	}
	
	public AbstractStackNode getCleanCopy(){
		return new OptionalStackNode(this);
	}
	
	public AbstractStackNode[] getChildren(){
		return children;
	}
	
	public boolean canBeEmpty(){
		return true;
	}
	
	public AbstractStackNode getEmptyChild(){
		return emptyChild;
	}
	
	public AbstractNode getResult(){
		throw new UnsupportedOperationException();
	}

	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append(nodeName);
		sb.append(getId());
		sb.append('(');
		sb.append(startLocation);
		sb.append(')');
		
		return sb.toString();
	}
}
