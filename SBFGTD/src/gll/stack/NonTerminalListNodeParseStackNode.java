package gll.stack;

import gll.result.Alternative;
import gll.result.INode;
import gll.util.ArrayList;

public class NonTerminalListNodeParseStackNode extends ParseStackNode{
	private final String nonTerminal;
	
	private boolean marked;
	
	private final ArrayList<INode> results;
	
	public NonTerminalListNodeParseStackNode(String nonTerminal, int id){
		super(id);
		
		this.nonTerminal = nonTerminal;
		
		marked = false;
		
		results = null;
	}
	
	public NonTerminalListNodeParseStackNode(String nonTerminal, int id, ArrayList<INode> results){
		super(id);
		
		this.nonTerminal = nonTerminal;
		
		marked = false;
		
		this.results = results;
	}
	
	private NonTerminalListNodeParseStackNode(NonTerminalListNodeParseStackNode nonTerminalListNodeParseStackNode){
		super(nonTerminalListNodeParseStackNode.id);

		nonTerminal = nonTerminalListNodeParseStackNode.nonTerminal;
		
		nexts = nonTerminalListNodeParseStackNode.nexts;
		edges = nonTerminalListNodeParseStackNode.edges;
		
		marked = false;
		
		results = new ArrayList<INode>();
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
	
	// Sharing disabled, since it breaks stuff.
	public boolean isSimilar(ParseStackNode node){
		return false;
	}
	
	public boolean reduce(char[] input){
		throw new UnsupportedOperationException();
	}
	
	public String getNodeName(){
		return nonTerminal;
	}
	
	public ParseStackNode getCleanCopy(){
		return new NonTerminalListNodeParseStackNode(this);
	}
	
	public ParseStackNode getCleanCopyWithPrefix(){
		throw new UnsupportedOperationException();
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
	
	public ParseStackNode[] getListChildren(){
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
