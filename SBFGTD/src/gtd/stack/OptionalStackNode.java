package gtd.stack;


public final class OptionalStackNode extends AbstractExpandableStackNode{
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
	
	private OptionalStackNode(OptionalStackNode original, int startLocation){
		super(original, startLocation);
		
		nodeName = original.nodeName;
		
		children = original.children;
		emptyChild = original.emptyChild;
	}
	
	private AbstractStackNode[] generateChildren(AbstractStackNode optional){
		AbstractStackNode child = optional.getCleanCopy(DEFAULT_START_LOCATION);
		child.setProduction(new AbstractStackNode[]{child});
		child.markAsEndNode();
		
		return new AbstractStackNode[]{child};
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
		return new OptionalStackNode(this, startLocation);
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
