package gll.stack;

import gll.IGLL;
import gll.result.ContainerNode;
import gll.result.INode;
import gll.result.struct.Link;
import gll.util.ArrayList;
import gll.util.LinearIntegerKeyedMap;

public final class OptionalStackNode extends AbstractStackNode implements IListStackNode{
	private final AbstractStackNode optional;
	
	private final String nodeName;
	
	private ContainerNode result;
	
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
	
	private OptionalStackNode(OptionalStackNode original, LinearIntegerKeyedMap<ArrayList<Link>> prefixes){
		super(original, prefixes);
		
		optional = original.optional;
		
		nodeName = original.nodeName;
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
		return new OptionalStackNode(this, prefixesMap);
	}
	
	public void setResultStore(ContainerNode resultStore){
		result = resultStore;
	}
	
	public ContainerNode getResultStore(){
		return result;
	}
	
	public AbstractStackNode[] getChildren(){
		AbstractStackNode copy = optional.getCleanCopy();
		AbstractStackNode epsn = new EpsilonStackNode(IGLL.DEFAULT_LIST_EPSILON_ID);
		copy.addEdge(this);
		epsn.addEdge(this);
		
		copy.setStartLocation(startLocation);
		epsn.setStartLocation(startLocation);
		
		return new AbstractStackNode[]{copy, epsn};
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
