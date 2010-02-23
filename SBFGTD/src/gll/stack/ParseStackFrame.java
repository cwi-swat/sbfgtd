package gll.stack;

import gll.nodes.INode;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ParseStackFrame{
	private final Set<ParseStackFrame> edges;
	
	private final ParseStackNode[] stackNodes;
	
	private final int frameNumber;
	
	// Updatable
	private int index;
	private int level;
	
	private boolean containsSelfRecursion;
	
	public ParseStackFrame(ParseStackNode... stackNodes){
		super();
		
		this.edges = new HashSet<ParseStackFrame>();
		
		this.frameNumber = 0;
		
		this.stackNodes = stackNodes;
		
		index = -1;
		level = 0;
		containsSelfRecursion = false;
	}
	
	public ParseStackFrame(ParseStackFrame prevStackFrame, ParseStackNode... stackNodes){
		super();
		
		this.edges = new HashSet<ParseStackFrame>();
		edges.add(prevStackFrame);
		
		this.frameNumber = prevStackFrame.frameNumber + 1;
		this.stackNodes = stackNodes;
		
		index = -1;
		level = 0;
		containsSelfRecursion = false;
	}
	
	public ParseStackFrame(ParseStackFrame stackFrame){
		super();
		
		edges = stackFrame.edges;
		
		frameNumber = stackFrame.frameNumber;
		
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
	
	public void mergeWith(ParseStackFrame stackFrame){ // NOTE: assuming they are indeed mergable.
		edges.addAll(stackFrame.edges);
		
		if(index > -1){
			INode[] results = stackFrame.getCurrentNode().getResults();
			for(int i = results.length - 1; i >= 0; i--){
				stackNodes[index].addResult(results[i]);
			}
		}
	}
	
	public int getFrameNumber(){
		return frameNumber;
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
	
	public Set<ParseStackFrame> getEdges(){
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
