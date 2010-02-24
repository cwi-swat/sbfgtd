package gll.stack;

import gll.nodes.INode;

import java.util.ArrayList;
import java.util.List;

public class ParseStackFrame{
	private final List<ParseStackFrame> edges;
	
	private final ParseStackNode[] stackNodes;
	
	// Updatable
	private int index;
	private int level;
	
	private boolean containsSelfRecursion;
	
	public ParseStackFrame(ParseStackNode... stackNodes){
		super();
		
		this.edges = new ArrayList<ParseStackFrame>();
		
		this.stackNodes = stackNodes;
		
		index = -1;
		level = 0;
		containsSelfRecursion = false;
	}
	
	public ParseStackFrame(ParseStackFrame prevStackFrame, ParseStackNode... stackNodes){
		super();
		
		this.edges = new ArrayList<ParseStackFrame>();
		edges.add(prevStackFrame);
		
		this.stackNodes = stackNodes;
		
		index = -1;
		level = 0;
		containsSelfRecursion = false;
	}
	
	public ParseStackFrame(ParseStackFrame stackFrame){
		super();
		
		edges = stackFrame.edges;
		
		index = stackFrame.index;
		level = stackFrame.level;
		containsSelfRecursion = stackFrame.containsSelfRecursion;
		
		int nrOfStackNodes = stackFrame.stackNodes.length;
		stackNodes = new ParseStackNode[nrOfStackNodes];
		System.arraycopy(stackFrame.stackNodes, 0, stackNodes, 0, index + 1);
		for(int i = nrOfStackNodes - 1; i > index; i--){
			ParseStackNode node = stackFrame.stackNodes[i];
			if(node.isNonTerminal()){
				stackNodes[i] = new NonTerminalParseStackNode(node.getNonTerminalName());
			}else{
				stackNodes[i] = node;
			}
		}
	}
	
	public void markSelfRecursive(){
		containsSelfRecursion = true;
	}
	
	public boolean isMarkedSelfRecursive(){
		return containsSelfRecursion;
	}
	
	public int getLevel(){
		return level;
	}
	
	public void setLevel(int level){
		this.level = level;
	}
	
	public void moveLevel(int bytes){
		level += bytes;
	}
	
	public List<ParseStackFrame> getEdges(){
		return edges;
	}
	
	public void moveToNextNode(){
		++index;
	}
	
	public ParseStackNode getCurrentNode(){
		return stackNodes[index];
	}
	
	public ParseStackNode getNextNode(){
		return stackNodes[index + 1];
	}
	
	public boolean isProductive(){
		return (stackNodes.length > 1 || stackNodes[0].isTerminal());
	}
	
	public boolean isComplete(){
		return ((stackNodes.length - 1) == index);
	}
	
	public List<INode> getResults(){
		int nrOfNodes = stackNodes.length;
		List<INode> results = new ArrayList<INode>(nrOfNodes);
		for(int i = 0; i < nrOfNodes; i++){
			results.add(stackNodes[i].getResult());
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
	
	public boolean isMergable(ParseStackFrame otherFrame){
		if(level != otherFrame.level) return false;
		if(index != otherFrame.index) return false;
		
		ParseStackNode[] otherStackNodes = otherFrame.stackNodes;
		int nrOfStackNodes = stackNodes.length;
		if(nrOfStackNodes != otherStackNodes.length) return false;
		for(int i = nrOfStackNodes - 1; i >= 0; i--){
			if(!stackNodes[i].isMergable(otherStackNodes[i])) return false;
		}
		
		return true;
	}
	
	public void mergeWith(ParseStackFrame stackFrame){ // NOTE: assuming they are indeed mergable.
		List<ParseStackFrame> otherEdges = stackFrame.edges;
		if(edges != otherEdges){
			for(int i = otherEdges.size() - 1; i >= 0; i--){ // Make sure every edge is unique.
				ParseStackFrame edge = otherEdges.get(i);
				if(!edges.contains(edge)){
					edges.add(edge);
				}
			}
		}
		
		if(index > -1){
			INode[] results = stackFrame.getCurrentNode().getResults();
			for(int i = results.length - 1; i >= 0; i--){
				stackNodes[index].addResult(results[i]);
			}
		}
	}
	
	public boolean hasSameFirstNonTerminal(ParseStackFrame otherFrame){ // Assumes this frame is 'non-productive'
		return (stackNodes[0].getName() == otherFrame.stackNodes[0].getName());
	}
	
	public int getNextLevel(){ // Unsafe operation; only works when a terminal is the next symbol.
		return (level + stackNodes[index + 1].getLength());
	}
	
	public int getNextLevelLength(){ // Unsafe operation; only works when a terminal is the next symbol.
		return stackNodes[index + 1].getLength();
	}
}
