package gll.stack;

import gll.result.AbstractNode;
import gll.result.ContainerNode;
import gll.result.struct.Link;
import gll.util.ArrayList;
import gll.util.LinearIntegerKeyedMap;

public abstract class AbstractStackNode{
	protected AbstractStackNode next;
	protected LinearIntegerKeyedMap<ArrayList<AbstractStackNode>> edgesMap;
	protected ArrayList<Link>[] prefixesMap;
	
	protected final int id;
	
	protected int startLocation;
	
	private boolean isEndNode;
	private boolean markedAsWithResults;
	
	public AbstractStackNode(int id){
		super();
		
		this.id = id;
		
		edgesMap = new LinearIntegerKeyedMap<ArrayList<AbstractStackNode>>();
		
		startLocation = -1;
	}
	
	protected AbstractStackNode(AbstractStackNode original){
		super();
		
		id = original.id;
		
		edgesMap = original.edgesMap;
		next = original.next;
		
		this.isEndNode = original.isEndNode;
	}
	
	protected AbstractStackNode(AbstractStackNode original, ArrayList<Link>[] prefixes){
		super();
		
		id = original.id;
		
		edgesMap = new LinearIntegerKeyedMap<ArrayList<AbstractStackNode>>(original.edgesMap);
		next = original.next;
		
		prefixesMap = prefixes;
		
		startLocation = original.startLocation;

		isEndNode = original.isEndNode;
	}
	
	// General.
	public int getId(){
		return id;
	}
	
	public void markAsEndNode(){
		isEndNode = true;
	}
	
	public boolean isEndNode(){
		return isEndNode;
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
	
	public abstract String getName();
	
	public abstract String getMethodName();
	
	public abstract boolean reduce(char[] input);
	
	// Sharing.
	public abstract boolean isClean();
	
	public abstract AbstractStackNode getCleanCopy();
	
	public abstract AbstractStackNode getCleanCopyWithMark();
	
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
		int startLocation = edge.getStartLocation();
		
		ArrayList<AbstractStackNode> edges = edgesMap.findValue(startLocation);
		if(edges == null){
			edges = new ArrayList<AbstractStackNode>(1);
			edgesMap.add(startLocation, edges);
		}
		
		edges.add(edge);
	}
	
	public void addEdges(LinearIntegerKeyedMap<ArrayList<AbstractStackNode>> edgesMapToAdd){
		if(edgesMap.size() == 0){
			edgesMap = new LinearIntegerKeyedMap<ArrayList<AbstractStackNode>>(edgesMapToAdd);
		}else if(edgesMap != edgesMapToAdd){
			for(int i = edgesMapToAdd.size() - 1; i >= 0; --i){
				int startLocation = edgesMapToAdd.getKey(i);
				if(edgesMap.findValue(startLocation) == null){
					edgesMap.add(startLocation, edgesMapToAdd.getValue(i));
				}
			}
		}
	}
	
	public boolean hasEdges(){
		return (edgesMap.size() != 0);
	}
	
	public LinearIntegerKeyedMap<ArrayList<AbstractStackNode>> getEdges(){
		return edgesMap;
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
	public void addPrefix(Link prefix, int prefixStartLocation){
		ArrayList<Link> prefixes;
		if(prefixesMap == null){
			prefixesMap = (ArrayList<Link>[]) new ArrayList[edgesMap.size()];
			prefixes = new ArrayList<Link>(1);
			prefixesMap[edgesMap.findKey(prefixStartLocation)] = prefixes;
		}else{
			int index = edgesMap.findKey(prefixStartLocation);
			int capacity = prefixesMap.length;
			if(index >= capacity){
				int newCapacity = capacity << 1;
				do{
					newCapacity <<= 1;
				}while(index >= newCapacity);
				ArrayList<Link>[] oldPrefixesMap = prefixesMap;
				prefixesMap = (ArrayList<Link>[]) new ArrayList[newCapacity];
				System.arraycopy(oldPrefixesMap, 0, prefixesMap, 0, capacity);
			}
			prefixes = prefixesMap[index];
			if(prefixes == null){
				prefixes = new ArrayList<Link>(1);
				prefixesMap[index] = prefixes;
			}
		}
		
		prefixes.add(prefix);
	}
	
	public ArrayList<Link>[] getPrefixesMap(){
		return prefixesMap;
	}
	
	public void markAsWithResults(){
		markedAsWithResults = true;
	}
	
	public boolean isMarkedAsWithResults(){
		return markedAsWithResults;
	}
	
	public abstract void setResultStore(ContainerNode resultStore);
	
	public abstract ContainerNode getResultStore();
	
	public abstract AbstractNode getResult();
}
