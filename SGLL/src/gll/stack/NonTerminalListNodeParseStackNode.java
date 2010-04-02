package gll.stack;

import gll.result.Alternative;
import gll.result.INode;
import gll.util.ArrayList;

public class NonTerminalListNodeParseStackNode extends ParseStackNode{
	private final String nonTerminal;
	
	private boolean marked;
	
	private final int sequenceNumber;
	
	private final ArrayList<INode> results;
	
	protected NonTerminalListNodeParseStackNode(String nonTerminal, int id){
		super(id);
		
		this.nonTerminal = nonTerminal;
		
		sequenceNumber = 0;
		
		results = new ArrayList<INode>();
	}
	
	private NonTerminalListNodeParseStackNode(NonTerminalListNodeParseStackNode nonTerminalListNodeParseStackNode){
		super(nonTerminalListNodeParseStackNode.id);

		nonTerminal = nonTerminalListNodeParseStackNode.nonTerminal;
		
		sequenceNumber = nonTerminalListNodeParseStackNode.sequenceNumber + 1; // Hacky, but it will work.
		
		nexts = nonTerminalListNodeParseStackNode.nexts;
		edges = nonTerminalListNodeParseStackNode.edges;
		
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
	
	// NOTE: Dirty hack to get sharing working again.
	public boolean isSimilar(ParseStackNode node){
		return super.isSimilar(node) && sequenceNumber == (((NonTerminalListNodeParseStackNode) node).sequenceNumber + 1);
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
		NonTerminalListNodeParseStackNode ntlnpsn = new NonTerminalListNodeParseStackNode(this);
		ntlnpsn.prefixes = prefixes;
		ntlnpsn.prefixStartLocations = prefixStartLocations;
		return ntlnpsn;
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
