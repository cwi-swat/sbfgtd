package gtd.stack;


public final class ListStackNode extends AbstractExpandableStackNode{
	private final static EpsilonStackNode EMPTY = new EpsilonStackNode(DEFAULT_LIST_EPSILON_ID, 0);
	
	private final String nodeName;

	private final AbstractStackNode[] children;
	private final AbstractStackNode emptyChild;
	
	public ListStackNode(int id, int dot, AbstractStackNode child, String nodeName, boolean isPlusList){
		super(id, dot);
		
		this.nodeName = nodeName;
		
		this.children = generateChildren(child);
		this.emptyChild = isPlusList ? null : generateEmptyChild();
	}
	
	private ListStackNode(ListStackNode original, int startLocation){
		super(original, startLocation);
		
		nodeName = original.nodeName;

		children = original.children;
		emptyChild = original.emptyChild;
	}
	
	private AbstractStackNode[] generateChildren(AbstractStackNode child){
		AbstractStackNode listNode = child.getCleanCopy(DEFAULT_START_LOCATION);
		listNode.markAsEndNode();
		listNode.setProduction(new AbstractStackNode[]{listNode, listNode});
		
		return new AbstractStackNode[]{listNode};
	}
	
	private AbstractStackNode generateEmptyChild(){
		AbstractStackNode empty = EMPTY.getCleanCopy(DEFAULT_START_LOCATION);
		empty.setProduction(new AbstractStackNode[]{empty});
		empty.markAsEndNode();
		
		return empty;
	}
	
	public String getIdentifier(){
		return nodeName+id; // Add the id to make it unique.
	}
	
	public String getName(){
		return nodeName;
	}
	
	public AbstractStackNode getCleanCopy(int startLocation){
		return new ListStackNode(this, startLocation);
	}
	
	public AbstractStackNode[] getChildren(){
		return children;
	}
	
	public boolean canBeEmpty(){
		return (emptyChild != null);
	}
	
	public AbstractStackNode getEmptyChild(){
		return emptyChild;
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
