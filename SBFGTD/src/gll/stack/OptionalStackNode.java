package gll.stack;

import gll.result.ContainerNode;
import gll.result.INode;
import gll.util.ArrayList;
import gll.util.IntegerList;

public final class OptionalStackNode extends AbstractStackNode implements IListStackNode{
	private final AbstractStackNode optional;
	
	private final String nodeName;
	
	private INode result;
	
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
	
	private OptionalStackNode(OptionalStackNode original, ArrayList<INode[]> prefixes, IntegerList prefixStartLocations){
		super(original, prefixes, prefixStartLocations);
		
		optional = original.optional;
		
		nodeName = original.nodeName;
	}
	
	public int getLength(){
		throw new UnsupportedOperationException();
	}
	
	public boolean reduce(char[] input){
		throw new UnsupportedOperationException();
	}
	
	public boolean isClean(){
		return (result == null);
	}
	
	public AbstractStackNode getCleanCopy(){
		return new OptionalStackNode(this);
	}
	
	public AbstractStackNode getCleanCopyWithPrefix(){
		return new OptionalStackNode(this, prefixes, prefixStartLocations);
	}
	
	public void initializeResultStore(){
		result = new ContainerNode(nodeName);
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
