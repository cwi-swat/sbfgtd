package gll.stack;

import gll.SGLL;

import java.util.ArrayList;
import java.util.List;

public abstract class ParseStackNode{
	protected List<ParseStackNode> nexts;
	protected List<ParseStackNode> edges;
	
	private final int id;
	
	protected int startLocation;
	
	public ParseStackNode(int id){
		super();
		
		this.id = id;
		
		nexts = null;
		edges = null;
		
		startLocation = -1;
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
				SGLL.edges++;
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
	
	public abstract int getLength();
}
