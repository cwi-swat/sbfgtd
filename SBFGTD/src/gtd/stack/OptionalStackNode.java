package gtd.stack;

import gtd.result.AbstractNode;

public final class OptionalStackNode extends AbstractStackNode implements IListStackNode{
	private final static EpsilonStackNode EMPTY = new EpsilonStackNode(DEFAULT_LIST_EPSILON_ID, 0);
	
	private final AbstractStackNode[] children;
	
	private final String nodeName;
	
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
	
	private AbstractStackNode[] generateChildren(AbstractStackNode optional){
		AbstractStackNode child = optional.getCleanCopy();
		child.setProduction(new AbstractStackNode[]{child});
		child.markAsEndNode();
		
		AbstractStackNode empty = EMPTY.getCleanCopy();
		empty.setProduction(new AbstractStackNode[]{empty});
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
	
	public AbstractStackNode getCleanCopy(){
		return new OptionalStackNode(this);
	}
	
	public AbstractStackNode[] getChildren(){
		return children;
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
		sb.append(',');
		sb.append('?');
		sb.append(')');
		
		return sb.toString();
	}
}
