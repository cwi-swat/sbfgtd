package gll.stack;

import gll.result.INode;
import gll.util.ArrayList;
import gll.util.IntegerList;

public abstract class ParseStackNode{
	protected ArrayList<ParseStackNode> nexts;
	protected ArrayList<ParseStackNode> edges;
	
	protected final int id;
	
	protected int startLocation;
	
	protected ArrayList<INode[]> prefixes;
	protected IntegerList prefixStartLocations;
	
	public ParseStackNode(int id){
		super();
		
		this.id = id;
		
		nexts = null;
		edges = null;
		
		startLocation = -1;
		
		prefixes = null;
		prefixStartLocations = null;
	}
	
	// General.
	public int getId(){
		return id;
	}
	
	public abstract boolean isReducable();
	
	public abstract boolean isList();
	
	public abstract String getMethodName();
	
	public abstract boolean reduce(char[] input, int location);
	
	public abstract String getNodeName();
	
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
	
	// Location.
	public void setStartLocation(int startLocation){
		this.startLocation = startLocation;
	}
	
	public boolean startLocationIsSet(){
		return (startLocation != -1);
	}
	
	public int getStartLocation(){
		return startLocation;
	}
	
	public abstract void mark();
	
	public abstract boolean isMarked();
	
	public abstract int getLength();
	
	// Lists.
	public abstract ParseStackNode[] getListChildren();
	
	// Results.
	public void addPrefix(INode[] prefix, int length){
		if(prefixes == null){
			prefixes = new ArrayList<INode[]>(1);
			prefixStartLocations = new IntegerList(1);
		}
		
		prefixes.add(prefix);
		prefixStartLocations.add(length);
	}
	
	public abstract void addResult(INode result);
	
	public abstract INode getResult();
	
	public INode[][] getResults(){
		if(prefixes == null){
			INode[][] results = new INode[1][1];
			results[0][0] = getResult();
			return results;
		}
		
		int nrOfPrefixes = prefixes.size();
		INode[][] results = new INode[nrOfPrefixes][];
		INode thisResult = getResult();
		for(int i = 0; i < nrOfPrefixes; i++){
			INode[] prefix = prefixes.get(i);
			int prefixLength = prefix.length;
			INode[] result = new INode[prefixLength + 1];
			System.arraycopy(prefix, 0, result, 0, prefixLength);
			result[prefixLength] = thisResult;
			
			results[i] = result;
		}
		
		return results;
	}
	
	public int[] getResultStartLocations(){
		if(prefixStartLocations == null){
			return new int[]{startLocation};
		}
		
		return prefixStartLocations.getBackingArray();
	}
}
