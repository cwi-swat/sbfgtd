package gll.stack;

import gll.nodes.Alternative;
import gll.nodes.INode;

import java.util.ArrayList;
import java.util.List;

public class NonTerminalParseStackNode extends ParseStackNode{
	private final String nonTerminal;

	private final List<INode> parseResults;
	
	public NonTerminalParseStackNode(String nonTerminal){
		super();
		
		this.nonTerminal = nonTerminal;

		this.parseResults = new ArrayList<INode>();
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
		parseResults.add(result);
	}
	
	public INode getResult(){
		List<INode> r = parseResults;
		int nrOfR = r.size();
		if(nrOfR == 1){
			return r.get(0);
		}
		
		INode[] alternatives = new INode[nrOfR];
		for(int j = nrOfR - 1; j >= 0; j--){
			alternatives[j] = r.get(j);
		}
		return new Alternative(alternatives);
	}
}
