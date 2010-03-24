package gll.stack;

import gll.result.INode;
import gll.util.ArrayList;

public abstract class ParseStackNode{
	protected ArrayList<ParseStackNode> nexts;
	protected ArrayList<ParseStackNode> edges;
	
	private final int id;
	
	protected int startLocation;
	
	protected ArrayList<INode[]> prefixes;
	protected ArrayList<Integer> prefixLengths;
	
	public ParseStackNode(int id){
		super();
		
		this.id = id;
		
		nexts = null;
		edges = null;
		
		startLocation = -1;
		
		prefixes = null;
		prefixLengths = null;
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
	
	public ArrayList<ParseStackNode> getNexts(){
		return nexts;
	}
	
	public void addEdge(ParseStackNode edge){
		if(edges == null) edges = new ArrayList<ParseStackNode>();
		edges.add(edge);
	}
	
	public void addEdges(ArrayList<ParseStackNode> edgesToAdd){
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
	
	public ArrayList<ParseStackNode> getEdges(){
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
	public void addPrefix(INode[] prefix, Integer length){
		if(prefixes == null){
			prefixes = new ArrayList<INode[]>(1);
			prefixLengths = new ArrayList<Integer>();
		}
		
		prefixes.add(prefix);
		prefixLengths.add(length);
	}
	
	public abstract void addResult(INode result);
	
	public abstract INode getResult();
	
	public ArrayList<INode[]> getResults(){
		if(prefixes == null){
			ArrayList<INode[]> results = new ArrayList<INode[]>(1);
			INode[] result = {getResult()};
			results.add(result);
			return results;
		}
		
		int nrOfPrefixes = prefixes.size();
		ArrayList<INode[]> results = new ArrayList<INode[]>();
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
	
	public ArrayList<Integer> getResultLengths(){
		int length = (getEndLocation() - startLocation);
		if(prefixLengths == null){
			ArrayList<Integer> resultLengths = new ArrayList<Integer>(1);
			Integer result = Integer.valueOf(length);
			resultLengths.add(result);
			return resultLengths;
		}
		
		int nrOfPrefixes = prefixLengths.size();
		ArrayList<Integer> resultLengths = new ArrayList<Integer>();
		int thisResultLength = length;
		for(int i = nrOfPrefixes - 1; i >= 0; i--){
			resultLengths.add(Integer.valueOf(prefixLengths.get(i).intValue() + thisResultLength));
		}
		
		return resultLengths;
	}
}
