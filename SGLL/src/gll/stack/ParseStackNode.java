package gll.stack;

import java.util.ArrayList;
import java.util.List;

public abstract class ParseStackNode{
	public List<ParseStackNode> edges;
	
	public ParseStackNode(){
		super();
		
		edges = new ArrayList<ParseStackNode>();
	}
	
	public void addEdge(ParseStackNode edge){
		edges.add(edge);
	}
	
	public int numberOfEdges(){
		return edges.size();
	}
	
	public boolean isTerminal(){
		return (this instanceof TerminalParseStackNode);
	}
	
	public boolean isNonTerminal(){
		return (this instanceof NonTerminalParseStackNode);
	}
	
	public abstract String getMethodName();
	
	public abstract byte[] getTerminalData();
	
	public abstract String getNonTerminalName();
}
