package gll.stack;

import gll.result.ContainerNode;
import gll.result.INode;
import gll.result.struct.Link;
import gll.util.ArrayList;
import gll.util.LinearIntegerKeyedMap;

public final class NonTerminalStackNode extends AbstractStackNode{
	private final String nonTerminal;
	
	private ContainerNode result;
	
	public NonTerminalStackNode(int id, String nonTerminal){
		super(id);
		
		this.nonTerminal = nonTerminal;
	}
	
	private NonTerminalStackNode(NonTerminalStackNode original){
		super(original);

		nonTerminal = original.nonTerminal;
	}
	
	private NonTerminalStackNode(NonTerminalStackNode original, LinearIntegerKeyedMap<ArrayList<Link>> prefixes){
		super(original, prefixes);

		nonTerminal = original.nonTerminal;
	}
	
	public String getName(){
		return nonTerminal;
	}
	
	public String getMethodName(){
		return nonTerminal;
	}
	
	public boolean reduce(char[] input){
		throw new UnsupportedOperationException();
	}
	
	public boolean isClean(){
		return (result == null);
	}
	
	public AbstractStackNode getCleanCopy(){
		return new NonTerminalStackNode(this);
	}
	
	public AbstractStackNode getCleanCopyWithNewId(int newId){
		return new NonTerminalStackNode(newId, nonTerminal);
	}
	
	public AbstractStackNode getCleanCopyWithPrefix(){
		return new NonTerminalStackNode(this, prefixesMap);
	}
	
	public void setResultStore(ContainerNode resultStore){
		result = resultStore;
	}
	
	public ContainerNode getResultStore(){
		return result;
	}
	
	public int getLength(){
		throw new UnsupportedOperationException();
	}
	
	public AbstractStackNode[] getChildren(){
		throw new UnsupportedOperationException();
	}
	
	public INode getResult(){
		return result;
	}

	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append(nonTerminal);
		sb.append(getId());
		sb.append('(');
		sb.append(startLocation);
		sb.append(',');
		sb.append('?');
		sb.append(')');
		
		return sb.toString();
	}
}
