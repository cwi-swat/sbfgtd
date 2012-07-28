package gtd.stack;


public final class SeparatedListStackNode extends AbstractExpandableStackNode{
	private final static EpsilonStackNode EMPTY = new EpsilonStackNode(DEFAULT_LIST_EPSILON_ID, 0);
	
	private final String nodeName;

	private final AbstractStackNode[] children;
	private final AbstractStackNode emptyChild;
	
	public SeparatedListStackNode(int id, int dot, AbstractStackNode child, AbstractStackNode[] separators, String nodeName, boolean isPlusList){
		super(id, dot);
		
		this.nodeName = nodeName;
		
		this.children = generateChildren(child, separators);
		this.emptyChild = isPlusList ? null : generateEmptyChild();
	}
	
	private SeparatedListStackNode(SeparatedListStackNode original, int startLocation){
		super(original, startLocation);
		
		nodeName = original.nodeName;

		children = original.children;
		emptyChild = original.emptyChild;
	}
	
	private AbstractStackNode[] generateChildren(AbstractStackNode child,  AbstractStackNode[] separators){
		AbstractStackNode listNode = child.getCleanCopy(DEFAULT_START_LOCATION);
		listNode.markAsEndNode();
		
		int numberOfSeparators = separators.length;
		AbstractStackNode[] prod = new AbstractStackNode[numberOfSeparators + 2];
		
		listNode.setProduction(prod);
		prod[0] = listNode; // Start
		for(int i = numberOfSeparators - 1; i >= 0; --i){
			AbstractStackNode separator = separators[i];
			separator.setProduction(prod);
			separator.markAsSeparator();
			prod[i + 1] = separator;
		}
		prod[numberOfSeparators + 1] = listNode; // End
		
		return new AbstractStackNode[]{listNode};
	}
	
	private AbstractStackNode generateEmptyChild(){
		AbstractStackNode empty = EMPTY.getCleanCopy(DEFAULT_START_LOCATION);
		empty.setProduction(new AbstractStackNode[]{empty});
		empty.markAsEndNode();
		
		return empty;
	}
	
	public String getName(){
		return nodeName;
	}
	
	public AbstractStackNode getCleanCopy(int startLocation){
		return new SeparatedListStackNode(this, startLocation);
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
