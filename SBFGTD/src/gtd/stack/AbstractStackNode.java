package gtd.stack;

import gtd.result.AbstractNode;
import gtd.result.struct.Link;
import gtd.util.ArrayList;
import gtd.util.IntegerList;
import gtd.util.IntegerObjectList;

public abstract class AbstractStackNode{
	public final static int START_SYMBOL_ID = -1; // 0xffffffff
	public final static int DEFAULT_START_LOCATION = -1;
	
	protected AbstractStackNode[] production;
	protected AbstractStackNode[][] alternateProductions;
	protected IntegerObjectList<ArrayList<AbstractStackNode>> edgesMap;
	protected ArrayList<Link>[] prefixesMap;
	
	protected final int id;
	protected final int dot;
	
	protected final int startLocation;
	
	private boolean isEndNode;
	
	private boolean isSeparator;
	
	protected AbstractStackNode(int id, int dot){
		super();
		
		this.id = id;
		this.dot = dot;
		
		startLocation = DEFAULT_START_LOCATION;
	}
	
	protected AbstractStackNode(AbstractStackNode original, int startLocation){
		super();
		
		id = original.id;
		dot = original.dot;
		
		production = original.production;
		alternateProductions = original.alternateProductions;
		
		this.isEndNode = original.isEndNode;
		this.isSeparator = original.isSeparator;
		
		this.startLocation = startLocation;
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
	
	public void markAsSeparator(){
		isSeparator = true;
	}
	
	public boolean isSeparator(){
		return isSeparator;
	}
	
	public final boolean isMatchable(){
		return (this instanceof AbstractMatchableStackNode);
	}
	
	public final boolean isExpandable(){
		return (this instanceof AbstractExpandableStackNode);
	}
	
	public final boolean isEpsilon(){
		return (this instanceof EpsilonStackNode);
	}
	
	public abstract boolean isEmptyLeafNode();
	
	public abstract String getIdentifier();
	
	public abstract String getName();
	
	public abstract String getMethodName();
	
	public abstract AbstractNode match(char[] input, int location);
	
	// Sharing.
	public abstract AbstractStackNode getCleanCopy(int startLocation);
	
	public abstract AbstractStackNode getCleanCopyWithResult(int startLocation, AbstractNode result);
	
	public boolean isSimilar(AbstractStackNode node){
		return (node.id == id);
	}
	
	// Linking & prefixes.
	public int getDot(){
		return dot;
	}
	
	public void setProduction(AbstractStackNode[] production){
		this.production = production;
	}
	
	public void addProduction(AbstractStackNode[] production){
		if(this.production == null){
			this.production = production;
		}else{
			if(alternateProductions == null){
				alternateProductions = new AbstractStackNode[][]{production};
			}else{
				int nrOfAlternateProductions = alternateProductions.length;
				AbstractStackNode[][] newAlternateProductions = new AbstractStackNode[nrOfAlternateProductions + 1][];
				System.arraycopy(alternateProductions, 0, newAlternateProductions, 0, nrOfAlternateProductions);
				newAlternateProductions[nrOfAlternateProductions] = production;
				alternateProductions = newAlternateProductions;
			}
		}
	}
	
	public boolean hasNext(){
		return ((dot + 1) < production.length);
	}
	
	public AbstractStackNode[] getProduction(){
		return production;
	}
	
	public AbstractStackNode[][] getAlternateProductions(){
		return alternateProductions;
	}
	
	public void initEdges(){
		edgesMap = new IntegerObjectList<ArrayList<AbstractStackNode>>();
	}
	
	public boolean isInitialized(){
		return (edgesMap != null);
	}
	
	public ArrayList<AbstractStackNode> addEdge(AbstractStackNode edge){
		int startLocation = edge.getStartLocation();
		
		ArrayList<AbstractStackNode> edges = edgesMap.findValue(startLocation);
		if(edges == null){
			edges = new ArrayList<AbstractStackNode>(1);
			edgesMap.add(startLocation, edges);
		}
		
		edges.add(edge);
		
		return edges;
	}
	
	public void addEdges(ArrayList<AbstractStackNode> edges, int startLocation){
		edgesMap.add(startLocation, edges);
	}
	
	public void addEdgeWithPrefix(AbstractStackNode edge, Link prefix, int startLocation){
		int edgesMapSize = edgesMap.size();
		if(prefixesMap == null){
			prefixesMap = (ArrayList<Link>[]) new ArrayList[(edgesMapSize + 1) << 1];
		}else{
			int prefixesMapSize = prefixesMap.length;
			int possibleMaxSize = edgesMapSize + 1;
			if(prefixesMapSize < possibleMaxSize){
				ArrayList<Link>[] oldPrefixesMap = prefixesMap;
				prefixesMap = (ArrayList<Link>[]) new ArrayList[possibleMaxSize << 1];
				System.arraycopy(oldPrefixesMap, 0, prefixesMap, 0, edgesMapSize);
			}
		}
		
		ArrayList<AbstractStackNode> edges;
		int index = edgesMap.findKey(startLocation);
		if(index == -1){
			index = edgesMap.size();
			
			edges = new ArrayList<AbstractStackNode>(1);
			edgesMap.add(startLocation, edges);
		}else{
			edges = edgesMap.getValue(index);
		}
		edges.add(edge);
		
		ArrayList<Link> prefixes = prefixesMap[index];
		if(prefixes == null){
			prefixes = new ArrayList<Link>(1);
			prefixesMap[index] = prefixes;
		}
		prefixes.add(prefix);
	}
	
	private void addPrefix(Link prefix, int index){
		ArrayList<Link> prefixes = prefixesMap[index];
		if(prefixes == null){
			prefixes = new ArrayList<Link>(1);
			prefixesMap[index] = prefixes;
		}
		
		prefixes.add(prefix);
	}
	
	public void updateNodeAfterNonEmptyMatchable(AbstractStackNode predecessor, AbstractNode result){
		ArrayList<Link>[] prefixesMapToAdd = predecessor.prefixesMap;
		
		edgesMap = predecessor.edgesMap;

		prefixesMap = (ArrayList<Link>[]) new ArrayList[edgesMap.size()];
		
		if(prefixesMapToAdd == null){
			addPrefix(new Link(null, result), edgesMap.findKey(predecessor.getStartLocation()));
		}else{
			int nrOfPrefixes = edgesMap.size();
			for(int i = nrOfPrefixes - 1; i >= 0; --i){
				ArrayList<Link> prefixes = new ArrayList<Link>(1);
				prefixesMap[i] = prefixes;
				
				prefixes.add(new Link(prefixesMapToAdd[i], result));
			}
		}
	}
	
	public void updateNode(AbstractStackNode predecessor, AbstractNode result){
		IntegerObjectList<ArrayList<AbstractStackNode>> edgesMapToAdd = predecessor.edgesMap;
		ArrayList<Link>[] prefixesMapToAdd = predecessor.prefixesMap;
		
		if(edgesMap == null){
			edgesMap = new IntegerObjectList<ArrayList<AbstractStackNode>>(edgesMapToAdd);
			
			prefixesMap = (ArrayList<Link>[]) new ArrayList[edgesMap.size()];
			
			if(prefixesMapToAdd == null){
				int index = edgesMap.findKey(predecessor.getStartLocation());
				addPrefix(new Link(null, result), index);
			}else{
				int nrOfPrefixes = edgesMapToAdd.size();
				for(int i = nrOfPrefixes - 1; i >= 0; --i){
					ArrayList<Link> prefixes = prefixesMap[i];
					if(prefixes == null){
						prefixes = new ArrayList<Link>(1);
						prefixesMap[i] = prefixes;
					}
					
					prefixes.add(new Link(prefixesMapToAdd[i], result));
				}
			}
		}else if(edgesMap != edgesMapToAdd){
			int edgesMapSize = edgesMap.size();
			int possibleMaxSize = edgesMapSize + edgesMapToAdd.size();
			if(prefixesMap == null){
				prefixesMap = (ArrayList<Link>[]) new ArrayList[possibleMaxSize];
			}else{
				if(prefixesMap.length < possibleMaxSize){
					ArrayList<Link>[] oldPrefixesMap = prefixesMap;
					prefixesMap = (ArrayList<Link>[]) new ArrayList[possibleMaxSize];
					System.arraycopy(oldPrefixesMap, 0, prefixesMap, 0, edgesMapSize);
				}
			}
			
			if(prefixesMapToAdd == null){
				addPrefix(new Link(null, result), edgesMap.size());
				edgesMap.add(predecessor.getStartLocation(), edgesMapToAdd.getValue(0));
			}else{
				for(int i = edgesMapToAdd.size() - 1; i >= 0; --i){
					int startLocation = edgesMapToAdd.getKey(i);
					int index = edgesMap.findKeyBefore(startLocation, edgesMapSize);
					ArrayList<Link> prefixes;
					if(index == -1){
						index = edgesMap.size();
						edgesMap.add(startLocation, edgesMapToAdd.getValue(i));
						
						prefixes = new ArrayList<Link>(1);
						prefixesMap[index] = prefixes;
					}else{
						prefixes = prefixesMap[index];
					}
					
					prefixes.add(new Link(prefixesMapToAdd[i], result));
				}
			}
		}else{
			if(prefixesMapToAdd == null){
				int index = edgesMap.findKey(predecessor.getStartLocation());
				addPrefix(new Link(null, result), index);
			}else{
				int nrOfPrefixes = edgesMapToAdd.size();
				for(int i = nrOfPrefixes - 1; i >= 0; --i){
					prefixesMap[i].add(new Link(prefixesMapToAdd[i], result));
				}
			}
		}
	}
	
	public int updateOvertakenNode(AbstractStackNode predecessor, AbstractNode result, int potentialNewEdges, IntegerList touched){
		IntegerObjectList<ArrayList<AbstractStackNode>> edgesMapToAdd = predecessor.edgesMap;
		ArrayList<Link>[] prefixesMapToAdd = predecessor.prefixesMap;
		
		int edgesMapSize = edgesMap.size();
		int possibleMaxSize = edgesMapSize + potentialNewEdges;
		if(prefixesMap == null){
			prefixesMap = (ArrayList<Link>[]) new ArrayList[possibleMaxSize];
		}else{
			if(prefixesMap.length < possibleMaxSize){
				ArrayList<Link>[] oldPrefixesMap = prefixesMap;
				prefixesMap = (ArrayList<Link>[]) new ArrayList[possibleMaxSize];
				System.arraycopy(oldPrefixesMap, 0, prefixesMap, 0, edgesMapSize);
			}
		}
		
		int nrOfAddedEdges = 0;
		if(prefixesMapToAdd == null){
			int startLocation = predecessor.getStartLocation();
			if(touched.contains(startLocation)) return 0;
			
			addPrefix(new Link(null, result), edgesMap.size());
			edgesMap.add(startLocation, edgesMapToAdd.getValue(0));
			touched.add(startLocation);
			nrOfAddedEdges = 1;
		}else{
			int fromIndex = edgesMapToAdd.size() - potentialNewEdges;
			for(int i = edgesMapToAdd.size() - 1; i >= fromIndex; --i){
				int startLocation = edgesMapToAdd.getKey(i);
				if(touched.contains(startLocation)) continue;
				
				int index = edgesMap.findKey(startLocation);
				ArrayList<Link> prefixes;
				if(index == -1){
					index = edgesMap.size();
					edgesMap.add(startLocation, edgesMapToAdd.getValue(i));
					touched.add(startLocation);
					
					prefixes = new ArrayList<Link>(1);
					prefixesMap[index] = prefixes;
					
					++nrOfAddedEdges;
				}else{
					prefixes = prefixesMap[index];
				}
				
				prefixes.add(new Link(prefixesMapToAdd[i], result));
			}
		}
		
		return nrOfAddedEdges;
	}
	
	public void updatePrefixSharedNode(IntegerObjectList<ArrayList<AbstractStackNode>> edgesMap, ArrayList<Link>[] prefixesMap){
		this.edgesMap = edgesMap;
		this.prefixesMap = prefixesMap;
	}
	
	public IntegerObjectList<ArrayList<AbstractStackNode>> getEdges(){
		return edgesMap;
	}
	
	public ArrayList<Link>[] getPrefixesMap(){
		return prefixesMap;
	}
	
	// Location.
	public int getStartLocation(){
		return startLocation;
	}
	
	public abstract int getLength();
	
	// Expandable.
	public abstract AbstractStackNode[] getChildren();
	
	public abstract boolean canBeEmpty();
	
	public abstract AbstractStackNode getEmptyChild();
	
	// Results.
	public abstract AbstractNode getResult();
}
