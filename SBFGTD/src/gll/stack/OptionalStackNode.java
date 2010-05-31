package gll.stack;

import gll.result.ContainerNode;
import gll.result.INode;

public final class OptionalStackNode extends AbstractStackNode implements IListStackNode{
	private final AbstractStackNode optional;
	
	private final String nodeName;
	
	private boolean marked;
	
	private final INode result;
	
	public OptionalStackNode(int id, AbstractStackNode optional, String nodeName){
		super(id);
		
		this.optional = optional;
		
		this.nodeName = nodeName;
		
		this.result = null;
	}
	
	private OptionalStackNode(OptionalStackNode optionalParseStackNode){
		super(optionalParseStackNode);
		
		optional = optionalParseStackNode.optional;
		
		nodeName = optionalParseStackNode.nodeName;
		
		result = new ContainerNode(nodeName);
	}
	
	public void mark(){
		marked = true;
	}
	
	public boolean isMarked(){
		return marked;
	}
	
	public int getLength(){
		throw new UnsupportedOperationException();
	}
	
	public boolean reduce(char[] input){
		throw new UnsupportedOperationException();
	}
	
	public AbstractStackNode getCleanCopy(){
		return new OptionalStackNode(this);
	}
	
	public AbstractStackNode getCleanCopyWithPrefix(){
		OptionalStackNode opsn = new OptionalStackNode(this);
		opsn.prefixes = prefixes;
		opsn.prefixStartLocations = prefixStartLocations;
		return opsn;
	}
	
	public AbstractStackNode[] getChildren(){
		AbstractStackNode copy = optional.getCleanCopy();
		AbstractStackNode epsn = new EpsilonStackNode(DEFAULT_LIST_EPSILON_ID);
		copy.addEdge(this);
		epsn.addEdge(this);
		
		copy.setStartLocation(startLocation);
		epsn.setStartLocation(startLocation);
		
		return new AbstractStackNode[]{copy, epsn};
	}
	
	public String getMethodName(){
		throw new UnsupportedOperationException();
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
