package gll.stack;

import gll.nodes.INode;

import java.util.ArrayList;
import java.util.List;

public abstract class ParseStackNode{
	protected List<ParseStackNode> nexts;
	protected List<ParseStackNode> edges;
	
	protected int startLocation;
	
	protected List<List<INode>> prefixResults;
	protected List<List<INode>> results;
	
	public ParseStackNode(){
		super();
		
		nexts = null;
		edges = null;
		
		startLocation = -1;
		
		prefixResults = null;
		results = null;
	}
	
	// General.
	public abstract boolean isTerminal();
	
	public abstract boolean isNonTerminal();
	
	public abstract String getMethodName();
	
	public abstract byte[] getTerminalData();
	
	public abstract String getNonTerminalName();
	
	// Sharing.
	public abstract ParseStackNode getCleanCopy();
	
	public abstract ParseStackNode getCleanWithPrefixCopy();
	
	public abstract boolean isSimilar(ParseStackNode node);
	
	// Linking.
	public void addNext(ParseStackNode next){
		if(nexts == null) nexts = new ArrayList<ParseStackNode>();
		nexts.add(next);
	}
	
	public boolean hasNexts(){
		return (nexts != null);
	}
	
	public List<ParseStackNode> getNexts(){
		return nexts;
	}
	
	public void addEdge(ParseStackNode edge){
		if(edges == null) edges = new ArrayList<ParseStackNode>();
		edges.add(edge);
	}
	
	public boolean hasEdges(){
		return (edges != null);
	}
	
	public List<ParseStackNode> getEdges(){
		return edges;
	}
	
	// Location
	public void setStartLocation(int startLocation){
		this.startLocation = startLocation;
	}
	public boolean startLocationIsSet(){
		return (startLocation != -1);
	}
	
	public int getStartLocation(){
		return startLocation;
	}
	
	public abstract void setEndLocation(int endLocation);
	
	public abstract boolean endLocationIsSet();
	
	public abstract int getEndLocation();
	
	public abstract int getLength();
	
	// Results.
	public abstract void addResult(INode result);
	
	public void addPrefixResults(List<List<INode>> prefixResults){
		if(this.prefixResults == null){
			this.prefixResults = prefixResults;
		}else{
			for(int i = prefixResults.size() - 1; i >= 0; i--){
				this.prefixResults.add(prefixResults.get(i));
			}
		}
	}
	
	public List<List<INode>> getResults(){
		if(results != null) return results;
		
		INode result = getResult();
		
		results = new ArrayList<List<INode>>();
		
		if(prefixResults == null){
			List<INode> prefix = new ArrayList<INode>();
			prefix.add(result);
			results.add(prefix);
			return results;
		}
		
		for(int i = prefixResults.size() - 1; i >= 0; i--){
			List<INode> prefix = new ArrayList<INode>();
			prefix.addAll(prefixResults.get(i));
			prefix.add(result);
			
			results.add(prefix);
		}
		
		return results;
	}
	
	public abstract INode getResult();
}
