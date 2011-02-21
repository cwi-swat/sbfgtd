package gtd.stack;

import gtd.result.AbstractNode;

public final class NonTerminalStackNode extends AbstractStackNode{
	private final String nonTerminal;
	
	public NonTerminalStackNode(int id, int dot, String nonTerminal){
		super(id, dot);
		
		this.nonTerminal = nonTerminal;
	}
	
	private NonTerminalStackNode(NonTerminalStackNode original){
		super(original);

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
	
	public AbstractStackNode getCleanCopy(){
		return new NonTerminalStackNode(this);
	}
	
	public int getLength(){
		throw new UnsupportedOperationException();
	}
	
	public AbstractStackNode[] getChildren(){
		throw new UnsupportedOperationException();
	}
	
	public AbstractNode getResult(){
		throw new UnsupportedOperationException();
	}

	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append(nonTerminal);
		sb.append(getId());
		sb.append('(');
		sb.append(startLocation);
		sb.append(')');
		
		return sb.toString();
	}
}
