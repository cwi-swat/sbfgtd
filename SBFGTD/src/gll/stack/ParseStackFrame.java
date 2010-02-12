package gll.stack;

import gll.nodes.INode;

import java.util.ArrayList;
import java.util.List;

public class ParseStackFrame{
	private final List<ParseStackFrame> edges;
	
	private final ParseStackNode[] stackNodes;
	
	// Updatable
	private int index;
	
	public ParseStackFrame(ParseStackNode... stackNodes){
		super();
		
		this.edges = new ArrayList<ParseStackFrame>();
		
		this.stackNodes = stackNodes;
		
		index = -1;
	}
	
	public ParseStackFrame(ParseStackFrame stackFrame){
		super();
		
		edges = stackFrame.edges;
		
		index = stackFrame.index;
		
		int nrOfStackNodes = stackFrame.stackNodes.length;
		stackNodes = new ParseStackNode[nrOfStackNodes];
		System.arraycopy(stackFrame.stackNodes, 0, stackNodes, 0, nrOfStackNodes);
		ParseStackNode node = stackFrame.stackNodes[index];
		if(node.isNonTerminal()){
			stackNodes[index] = new NonTerminalParseStackNode(node.getNonTerminalName());
		}
	}
	
	public void addEdge(ParseStackFrame edge){
		edges.add(edge);
	}
	
	public List<ParseStackFrame> getEdges(){
		return edges;
	}
	
	public void nextNode(){
		++index;
	}
	
	public ParseStackNode getCurrentNode(){
		return stackNodes[index];
	}
	
	public boolean isComplete(){
		return ((stackNodes.length - 1) == index);
	}
	
	public INode[] getResults(){
		int nrOfNodes = stackNodes.length;
		INode[] results = new INode[nrOfNodes];
		for(int i = nrOfNodes - 1; i >= 0; i--){
			results[i] = stackNodes[i].getResult();
		}
		return results;
	}
	
	public String toString(){
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < stackNodes.length; i++){
			sb.append(stackNodes[i].getMethodName());
		}
		return sb.toString();
	}
}
