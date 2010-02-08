package gll.stack;

import gll.nodes.Alternative;
import gll.nodes.INode;

import java.util.ArrayList;
import java.util.List;

public class ParseStackFrame{
	public final ParseStackNode[] stackNodes;
	public final List<INode>[] parseResults;
	
	public final int frameNumber;
	
	// Updatable
	public int index;
	
	public ParseStackFrame(int frameNumber, ParseStackNode... stackNodes){
		super();
		
		this.stackNodes = stackNodes;
		this.parseResults = (List<INode>[]) new List[stackNodes.length];
		
		this.frameNumber = frameNumber;
		
		index = -1;
	}
	
	public void nextSymbol(){
		index++;
	}
	
	public ParseStackNode getCurrentNode(){
		return stackNodes[index];
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
}
