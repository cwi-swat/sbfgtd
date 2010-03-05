package gll.stack;

import gll.nodes.Alternative;
import gll.nodes.INode;

import java.util.ArrayList;
import java.util.List;

public final class NonTerminalParseStackNode extends ParseStackNode{
	private final String nonTerminal;

	private final List<INode> parseResults;
	
	private int endLocation;
	
	public NonTerminalParseStackNode(String nonTerminal){
		super();
		
		this.nonTerminal = nonTerminal;
		
		this.parseResults = new ArrayList<INode>();
		
		this.endLocation = -1;
	}
	
	public NonTerminalParseStackNode(NonTerminalParseStackNode nonTerminalParseStackNode){
		super();

		this.nonTerminal = nonTerminalParseStackNode.nonTerminal;
		
		this.parseResults = new ArrayList<INode>();
		
		this.endLocation = -1;
		
		this.nexts = nonTerminalParseStackNode.nexts;
		this.edges = nonTerminalParseStackNode.edges;
	}
	
	public boolean isTerminal(){
		return false;
	}
	
	public boolean isNonTerminal(){
		return true;
	}
	
	public String getMethodName(){
		return nonTerminal;
	}
	
	public byte[] getTerminalData(){
		throw new UnsupportedOperationException();
	}
	
	public String getNonTerminalName(){
		return nonTerminal;
	}
	
	public ParseStackNode getCleanCopy(){
		return new NonTerminalParseStackNode(this);
	}
	
	public ParseStackNode getCleanWithPrefixCopy(){
		NonTerminalParseStackNode copy = new NonTerminalParseStackNode(this);
		copy.prefixResults = prefixResults;
		return copy;
	}
	
	public boolean isSimilar(ParseStackNode node){
		return (node.isNonTerminal() && (node.getNonTerminalName() == nonTerminal) && (node.getNexts() == nexts) && (node.getEdges() == edges));
	}
	
	public boolean equalSymbol(ParseStackNode node){
		return (node.isNonTerminal() && (node.getNonTerminalName() == nonTerminal));
	}
	
	public void setEndLocation(int endLocation){
		this.endLocation = endLocation;
	}
	
	public boolean endLocationIsSet(){
		return (endLocation != -1);
	}
	
	public int getEndLocation(){
		return endLocation;
	}
	
	public int getLength(){
		throw new UnsupportedOperationException();
	}
	
	public void addResult(INode result){
		parseResults.add(result);
	}
	
	public INode getResult(){
		return new Alternative(parseResults);
	}
	
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append(nonTerminal);
		sb.append('(');
		sb.append(startLocation);
		sb.append(',');
		sb.append(endLocation);
		sb.append(',');
		sb.append(parseResults.toString());
		sb.append(')');
		
		return sb.toString();
	}
}
