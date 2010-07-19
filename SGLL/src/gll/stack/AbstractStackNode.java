package gll.stack;

import gll.result.ContainerNode;
import gll.result.INode;
import gll.result.struct.Link;
import gll.util.ArrayList;
import gll.util.LinearIntegerKeyedMap;

public abstract class AbstractStackNode{
	protected AbstractStackNode next;
	protected ArrayList<AbstractStackNode> edges;
	
	protected final int id;
	
	protected int startLocation;
	
	protected LinearIntegerKeyedMap<ArrayList<Link>> prefixesMap;
	
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
	
	protected AbstractStackNode(AbstractStackNode original, LinearIntegerKeyedMap<ArrayList<Link>> prefixes){
		super();
		
		id = original.id;
		
		next = original.next;
		edges = original.edges;
		
		this.prefixesMap = prefixes;
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
	
	public abstract String getName();
	
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
	public void addPrefix(Link prefix, int prefixStartLocation){
		ArrayList<Link> prefixes;
		if(prefixesMap == null){
			prefixesMap = new LinearIntegerKeyedMap<ArrayList<Link>>();
			prefixes = new ArrayList<Link>(1);
			prefixesMap.add(prefixStartLocation, prefixes);
		}else{
			prefixes = prefixesMap.findValue(prefixStartLocation);
			if(prefixes == null){
				prefixes = new ArrayList<Link>(1);
				prefixesMap.add(prefixStartLocation, prefixes);
			}
		}
		
		prefixes.add(prefix);
	}
	
	public LinearIntegerKeyedMap<ArrayList<Link>> getPrefixesMap(){
		return prefixesMap;
	}
	
	public abstract void setResultStore(ContainerNode resultStore);
	
	public abstract ContainerNode getResultStore();
	
	public abstract INode getResult();
}
