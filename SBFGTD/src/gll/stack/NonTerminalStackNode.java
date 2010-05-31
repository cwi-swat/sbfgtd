package gll.stack;

import gll.result.ContainerNode;
import gll.result.INode;

public final class NonTerminalStackNode extends AbstractStackNode{
	private final String nonTerminal;
	
	private boolean marked;
	
	private final INode result;
	
	public NonTerminalStackNode(int id, String nonTerminal){
		super(id);
		
		this.nonTerminal = nonTerminal;
		
		result = null;
	}
	
	private NonTerminalStackNode(NonTerminalStackNode nonTerminalParseStackNode){
		super(nonTerminalParseStackNode);

		nonTerminal = nonTerminalParseStackNode.nonTerminal;
		
		result = new ContainerNode(nonTerminal);
	}
	
	public String getMethodName(){
		return nonTerminal;
	}
	
	public boolean reduce(char[] input){
		throw new UnsupportedOperationException();
	}
	
	public AbstractStackNode getCleanCopy(){
		return new NonTerminalStackNode(this);
	}
	
	public AbstractStackNode getCleanCopyWithPrefix(){
		NonTerminalStackNode ntpsn = new NonTerminalStackNode(this);
		ntpsn.prefixes = prefixes;
		ntpsn.prefixStartLocations = prefixStartLocations;
		return ntpsn;
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
