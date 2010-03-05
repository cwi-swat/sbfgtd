package gll.stack;

import gll.nodes.INode;
import gll.nodes.TerminalNode;

import java.util.HashMap;
import java.util.Map;

public final class TerminalParseStackNode extends ParseStackNode{
	private final static Map<byte[], TerminalNode> terminalNodeCache = new HashMap<byte[], TerminalNode>(); 
	
	private static TerminalNode getCachedNode(byte[] terminal){
		TerminalNode node = terminalNodeCache.get(terminal);
		if(node == null){
			node = new TerminalNode(terminal);
			terminalNodeCache.put(terminal, node);
		}
		
		return node;
	}
	
	private final byte[] terminal;
	private final INode result;
	
	public TerminalParseStackNode(byte[] terminal){
		super();

		this.terminal = terminal;
		this.result = getCachedNode(terminal);
	}
	
	private TerminalParseStackNode(TerminalParseStackNode terminalParseStackNode){
		super();

		this.terminal = terminalParseStackNode.terminal;
		this.result = terminalParseStackNode.result;
		
		this.nexts = terminalParseStackNode.nexts;
		this.edges = terminalParseStackNode.edges;
	}
	
	public boolean isTerminal(){
		return true;
	}
	
	public boolean isNonTerminal(){
		return false;
	}
	
	public String getMethodName(){
		throw new UnsupportedOperationException();
	}
	
	public byte[] getTerminalData(){
		return terminal;
	}
	
	public String getNonTerminalName(){
		throw new UnsupportedOperationException();
	}
	
	public ParseStackNode getCleanCopy(){
		return new TerminalParseStackNode(this);
	}
	
	public ParseStackNode getCleanWithPrefixCopy(){
		TerminalParseStackNode copy = new TerminalParseStackNode(this);
		copy.prefixResults = prefixResults;
		return copy;
	}
	
	public boolean isSimilar(ParseStackNode node){
		return (node.isTerminal() && (node.getTerminalData() == terminal) && (node.getNexts() == nexts) && (node.getEdges() == edges));
	}
	
	public boolean equalSymbol(ParseStackNode node){
		return (node.isTerminal() && (node.getTerminalData() == terminal));
	}
	
	public void setEndLocation(int endLocation){
		throw new UnsupportedOperationException();
	}
	
	public boolean endLocationIsSet(){
		throw new UnsupportedOperationException();
	}
	
	public int getEndLocation(){
		throw new UnsupportedOperationException();
	}
	
	public int getLength(){
		return terminal.length;
	}
	
	public void addResult(INode result){
		throw new UnsupportedOperationException();
	}
	
	public INode getResult(){
		return result;
	}
	
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append(new String(terminal));
		sb.append('(');
		sb.append(startLocation);
		sb.append(" + ");
		sb.append(getLength());
		sb.append(')');
		
		return sb.toString();
	}
}
