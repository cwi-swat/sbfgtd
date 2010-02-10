package gll.stack;

import gll.nodes.Alternative;
import gll.nodes.INode;

import java.util.ArrayList;
import java.util.List;

public class ParseStackFrame{
	private final List<ParseStackFrame> edges;
	
	private final ParseStackNode[] stackNodes;
	private final List<INode>[] parseResults;
	
	public final int frameNumber;
	
	// Updatable
	private int index;
	
	public ParseStackFrame(int frameNumber, ParseStackNode... stackNodes){
		super();
		
		this.edges = new ArrayList<ParseStackFrame>();
		
		this.stackNodes = stackNodes;
		this.parseResults = (List<INode>[]) new List[stackNodes.length];
		
		this.frameNumber = frameNumber;
		
		index = -1;
	}
	
	public ParseStackFrame(ParseStackFrame stackFrame){
		super();
		
		edges = stackFrame.edges;
		
		stackNodes = stackFrame.stackNodes;
		
		int nrOfStackNodes = stackNodes.length;
		parseResults = (List<INode>[]) new List[nrOfStackNodes];
		for(int i = 0; i < nrOfStackNodes; i++){
			List<INode> result = stackFrame.parseResults[i];
			if(result == null) break;
			
			List<INode> newResult = new ArrayList<INode>();
			for(int j = result.size() - 1; j >= 0; j--){
				newResult.add(result.get(j));
			}
			parseResults[i] = newResult;
		}
		
		frameNumber = stackFrame.frameNumber;
		
		index = stackFrame.index;
	}
	
	public void addEdge(ParseStackFrame edge){
		edges.add(edge);
	}
	
	public List<ParseStackFrame> getEdges(){
		return edges;
	}
	
	public void nextSymbol(){
		index++;
	}
	
	public ParseStackNode getCurrentNode(){
		return stackNodes[index];
	}
	
	public boolean isComplete(){
		return ((stackNodes.length - 1) == index);
	}
	
	public void addResult(INode result){
		List<INode> results = parseResults[index];
		if(results == null){
			results = new ArrayList<INode>();
			parseResults[index] = results;
		}
		
		results.add(result);
	}
	
	public INode[] getResults(){
		int nrOfNodes = stackNodes.length;
		INode[] results = new INode[nrOfNodes];
		for(int i = nrOfNodes - 1; i >= 0; i--){
			List<INode> r = parseResults[i];
			int nrOfR = r.size();
			if(nrOfR == 1){
				results[i] = r.get(0);
			}else{
				INode[] alternatives = new INode[nrOfR];
				for(int j = nrOfR - 1; j >= 0; j--){
					alternatives[j] = r.get(j);
				}
				results[i] = new Alternative(alternatives);
			}
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
