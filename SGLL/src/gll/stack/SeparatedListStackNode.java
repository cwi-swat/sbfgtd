package gll.stack;

import gll.IGLL;
import gll.result.ContainerNode;
import gll.result.INode;

public final class SeparatedListStackNode extends AbstractStackNode implements IListStackNode{
	private final String nodeName;

	private final AbstractStackNode child;
	private final AbstractStackNode[] separators;
	private final boolean isPlusList;
	
	private boolean marked;
	
	private final INode result;
	
	public SeparatedListStackNode(int id, AbstractStackNode child, AbstractStackNode[] separators, String nodeName, boolean isPlusList){
		super(id);
		
		this.nodeName = nodeName;
		
		this.child = child;
		this.separators = separators;
		this.isPlusList = isPlusList;
		
		this.result = null;
	}
	
	public SeparatedListStackNode(int id, AbstractStackNode child, AbstractStackNode[] separators, String nodeName, boolean isPlusList, INode result){
		super(id);
		
		this.nodeName = nodeName;
		
		this.child = child;
		this.separators = separators;
		this.isPlusList = isPlusList;
		
		this.result = result;
	}
	
	public SeparatedListStackNode(SeparatedListStackNode separatedListStackNode){
		super(separatedListStackNode);
		
		nodeName = separatedListStackNode.nodeName;

		child = separatedListStackNode.child;
		separators = separatedListStackNode.separators;
		isPlusList = separatedListStackNode.isPlusList;
		
		result = new ContainerNode(nodeName);
	}
	
	public String getMethodName(){
		throw new UnsupportedOperationException();
	}
	
	public boolean reduce(char[] input){
		throw new UnsupportedOperationException();
	}
	
	public AbstractStackNode getCleanCopy(){
		return new SeparatedListStackNode(this);
	}
	
	public AbstractStackNode getCleanCopyWithPrefix(){
		SeparatedListStackNode slpsn = new SeparatedListStackNode(this);
		slpsn.prefixes = prefixes;
		slpsn.prefixStartLocations = prefixStartLocations;
		return slpsn;
	}
	
	public int getLength(){
		throw new UnsupportedOperationException();
	}
	
	public void mark(){
		marked = true;
	}
	
	public boolean isMarked(){
		return marked;
	}
	
	public AbstractStackNode[] getChildren(){
		AbstractStackNode psn = child.getCleanCopy();
		SeparatedListStackNode slpsn = new SeparatedListStackNode((id | IGLL.LIST_LIST_FLAG), child, separators, nodeName, true, new ContainerNode(nodeName));
		
		AbstractStackNode from = psn;
		for(int i = 0; i < separators.length; i++){
			AbstractStackNode to = separators[i].getCleanCopy();
			from.addNext(to);
			from = to;
		}
		from.addNext(slpsn);
		
		psn.addEdge(this);
		slpsn.addEdge(this);
		
		psn.setStartLocation(startLocation);
		
		if(isPlusList){
			return new AbstractStackNode[]{psn};
		}
		
		EpsilonStackNode epsn = new EpsilonStackNode(DEFAULT_LIST_EPSILON_ID);
		epsn.addEdge(this);
		epsn.setStartLocation(startLocation);
		
		return new AbstractStackNode[]{psn, epsn};
	}
	
	public void addResult(INode[] children){
		result.addAlternative(children);
	}
	
	public INode getResult(){
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
