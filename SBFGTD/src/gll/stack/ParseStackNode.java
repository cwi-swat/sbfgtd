package gll.stack;

import gll.result.INode;

import java.util.ArrayList;
import java.util.List;

public abstract class ParseStackNode{
	protected List<ParseStackNode> nexts;
	protected List<ParseStackNode> edges;
	
	private final int id;
	
	protected int startLocation;
	
	protected List<INode[]> prefixes;
	
	public ParseStackNode(int id){
		super();
		
		this.id = id;
		
		nexts = null;
		edges = null;
		
		startLocation = -1;
		
		prefixes = null;
	}
	
	// General.
	public int getId(){
		return id;
	}
	
	public abstract boolean isTerminal();
	
	public abstract boolean isNonTerminal();
	
	public abstract String getMethodName();
	
	public abstract byte[] getTerminalData();
	
	public abstract String getNonTerminalName();
	
	// Sharing.
	public abstract ParseStackNode getCleanCopy();
	
	public abstract ParseStackNode getCleanCopyWithPrefix();
	
	public boolean isSimilar(ParseStackNode node){
		return (node.getId() == getId());
	}
	
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
	
	public void addEdges(List<ParseStackNode> edgesToAdd){
		for(int i = edgesToAdd.size() - 1; i >= 0; i--){
			ParseStackNode node = edgesToAdd.get(i);
			if(!edges.contains(node)){
				edges.add(node);
			}
		}
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
	
	// Results
	public void addPrefix(INode[] prefix){
		if(prefixes == null) prefixes = new ArrayList<INode[]>(1);
		
		prefixes.add(prefix);
	}
	
	public abstract void addResult(INode result);
	
	public abstract INode getResult();
	
	public List<INode[]> getResults(){
		if(prefixes == null){
			List<INode[]> results = new ArrayList<INode[]>(1);
			INode[] result = {getResult()};
			results.add(result);
			return results;
		}
		
		int nrOfPrefixes = prefixes.size();
		List<INode[]> results = new ArrayList<INode[]>();
		INode thisResult = getResult();
		for(int i = nrOfPrefixes - 1; i >= 0; i--){
			INode[] prefix = prefixes.get(i);
			int prefixLength = prefix.length;
			INode[] result = new INode[prefixLength + 1];
			System.arraycopy(prefix, 0, result, 0, prefixLength);
			result[prefixLength] = thisResult;
			
			results.add(result);
		}
		
		return results;
	}
}
