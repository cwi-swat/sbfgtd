package gll.stack;

import gll.result.AbstractNode;
import gll.result.AbstractContainerNode;
import gll.result.struct.Link;
import gll.util.ArrayList;

public final class NonTerminalStackNode extends AbstractStackNode{
	private final String nonTerminal;
	
	private AbstractContainerNode result;
	
	public NonTerminalStackNode(int id, String nonTerminal){
		super(id);
		
		this.nonTerminal = nonTerminal;
	}
	
	private NonTerminalStackNode(NonTerminalStackNode original){
		super(original);

		nonTerminal = original.nonTerminal;
	}
	
	private NonTerminalStackNode(NonTerminalStackNode original, ArrayList<Link>[] prefixes){
		super(original, prefixes);

		nonTerminal = original.nonTerminal;
	}
	
	public String getIdentifier(){
		return nonTerminal;
	}
	
	public String getMethodName(){
		return nonTerminal;
	}
	
	public String getName(){
		return nonTerminal;
	}
	
	public boolean match(char[] input){
		throw new UnsupportedOperationException();
	}
	
	public boolean isClean(){
		return (result == null);
	}
	
	public AbstractStackNode getCleanCopy(){
		return new NonTerminalStackNode(id, nonTerminal);
	}
	
	public AbstractStackNode getCleanCopyWithoutPrefixes(){
		return new NonTerminalStackNode(this);
	}

	public AbstractStackNode getCleanCopyWithPrefix(){
		return new NonTerminalStackNode(this, prefixesMap);
	}
	
	public void setResultStore(AbstractContainerNode resultStore){
		result = resultStore;
	}
	
	public AbstractContainerNode getResultStore(){
		return result;
	}
	
	public int getLength(){
		throw new UnsupportedOperationException();
	}
	
	public AbstractStackNode[] getChildren(){
		throw new UnsupportedOperationException();
	}
	
	public AbstractNode getResult(){
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
