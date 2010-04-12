package gll.stack;

import gll.result.Alternative;
import gll.result.INode;
import gll.util.ArrayList;

public final class NonTerminalStackNode extends StackNode{
	private final String nonTerminal;
	
	private boolean marked;
	
	private final ArrayList<INode> results;
	
	public NonTerminalStackNode(String nonTerminal, int id){
		super(id);
		
		this.nonTerminal = nonTerminal;
		
		results = null;
	}
	
	private NonTerminalStackNode(NonTerminalStackNode nonTerminalParseStackNode){
		super(nonTerminalParseStackNode);

		nonTerminal = nonTerminalParseStackNode.nonTerminal;
		
		results = new ArrayList<INode>(1);
	}
	
	public boolean isReducable(){
		return false;
	}
	
	public boolean isList(){
		return false;
	}
	
	public String getMethodName(){
		return nonTerminal;
	}
	
	public boolean reduce(char[] input){
		throw new UnsupportedOperationException();
	}
	
	public String getNodeName(){
		return nonTerminal;
	}
	
	public StackNode getCleanCopy(){
		return new NonTerminalStackNode(this);
	}
	
	public StackNode getCleanCopyWithPrefix(){
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
	
	public StackNode[] getChildren(){
		throw new UnsupportedOperationException();
	}
	
	public void addResult(INode result){
		results.add(result);
	}
	
	public INode getResult(){
		return new Alternative(results);
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
