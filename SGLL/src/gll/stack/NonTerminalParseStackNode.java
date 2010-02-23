package gll.stack;

import gll.nodes.Alternative;
import gll.nodes.INode;

import java.util.ArrayList;
import java.util.List;

public class NonTerminalParseStackNode extends ParseStackNode{
	private final String nonTerminal;

	private final List<INode> parseResults;
	private int length;
	
	public NonTerminalParseStackNode(String nonTerminal){
		super();
		
		this.nonTerminal = nonTerminal;

		this.parseResults = new ArrayList<INode>();
		this.length = -1;
	}
	
	public String getName(){
		return nonTerminal;
	}
	
	public String getMethodName(){
		return getName();
	}
	
	public byte[] getTerminalData(){
		return null;
	}
	
	public String getNonTerminalName(){
		return nonTerminal;
	}
	
	public void addResult(INode result){
		int resultLength = result.getLength();
		if(length == -1){
			length = resultLength;
		}else if(length != resultLength){
			throw new RuntimeException("Result length is different."); // Temp
		}
		
		parseResults.add(result);
	}
	
	public INode[] getResults(){
		int nrOfR = parseResults.size();
		INode[] results = new INode[nrOfR];
		for(int j = nrOfR - 1; j >= 0; j--){
			results[j] = parseResults.get(j);
		}
		return results;
	}
	
	public INode getResult(){
		return new Alternative(parseResults);
	}
	
	public int getLength(){
		if(length == -1) throw new RuntimeException("Length not set yet"); // Temp
		return length;
	}
	
	public boolean isMergable(ParseStackNode other){
		if(!(other instanceof NonTerminalParseStackNode)) return false;
		
		NonTerminalParseStackNode otherNode = (NonTerminalParseStackNode) other;
		
		if(length != otherNode.length) return false;
		
		return nonTerminal.equals(otherNode.nonTerminal);
	}
}
