package gll.stack;

import gll.result.INode;
import gll.util.ArrayList;
import gll.util.IntegerList;

public abstract class AbstractStackNode{
	protected final static int DEFAULT_LIST_EPSILON_ID = -1;
	
	protected AbstractStackNode next;
	protected ArrayList<AbstractStackNode> edges;
	
	protected final int id;
	
	protected int startLocation;
	
	protected ArrayList<INode[]> prefixes;
	protected IntegerList prefixStartLocations;
	
	public AbstractStackNode(int id){
		super();
		
		this.id = id;
		
		startLocation = -1;
	}
	
	protected AbstractStackNode(AbstractStackNode parseStackNode){
		super();
		
		id = parseStackNode.id;
		
		next = parseStackNode.next;
		edges = parseStackNode.edges;
	}
	
	protected AbstractStackNode(AbstractStackNode original, ArrayList<INode[]> prefixes, IntegerList prefixStartLocations){
		super();
		
		id = original.id;
		
		next = original.next;
		edges = original.edges;
		
		this.prefixes = prefixes;
		this.prefixStartLocations = prefixStartLocations;
	}
	
	// General.
	public int getId(){
		return id;
	}
	
	public final boolean isReducable(){
		return (this instanceof IReducableStackNode);
	}
	
	public final boolean isEpsilon(){
		return (this instanceof EpsilonStackNode);
	}
	
	public final boolean isList(){
		return (this instanceof IListStackNode);
	}
	
	public abstract String getMethodName();
	
	public abstract boolean reduce(char[] input);
	
	// Sharing.
	public abstract boolean isClean();
	
	public abstract AbstractStackNode getCleanCopy();
	
	public abstract AbstractStackNode getCleanCopyWithPrefix();
	
	public boolean isSimilar(AbstractStackNode node){
		return (node.getId() == getId());
	}
	
	// Linking.
	public void addNext(AbstractStackNode next){
		this.next = next;
	}
	
	public boolean hasNext(){
		return (next != null);
	}
	
	public AbstractStackNode getNext(){
		return next;
	}
	
	public void addEdge(AbstractStackNode edge){
		if(edges == null) edges = new ArrayList<AbstractStackNode>(1);
		edges.add(edge);
	}
	
	public void addEdges(ArrayList<AbstractStackNode> edgesToAdd){
		if(edges != edgesToAdd){
			OUTER : for(int i = edgesToAdd.size() - 1; i >= 0; i--){
				AbstractStackNode node = edgesToAdd.get(i);
				for(int j = edges.size() - 1; j >= 0; j--){
					AbstractStackNode edge = edges.get(j);
					if(edge == node || (edge.getId() == node.getId() && edge.getStartLocation() == node.getStartLocation())){
						break OUTER;
					}
				}
				edges.add(node);
			}
		}
	}
	
	public boolean hasEdges(){
		return (edges != null);
	}
	
	public ArrayList<AbstractStackNode> getEdges(){
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
	
	public abstract int getLength();
	
	// Lists.
	public abstract AbstractStackNode[] getChildren();
	
	// Results.
	public void addPrefix(INode[] prefix, int length){
		if(prefixes == null){
			prefixes = new ArrayList<INode[]>(1);
			prefixStartLocations = new IntegerList(1);
		}
		
		prefixes.add(prefix);
		prefixStartLocations.add(length);
	}
	
	public abstract void initializeResultStore();
	
	public abstract void addResult(INode[] children);
	
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
