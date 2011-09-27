package gtd.stack.edge;

import gtd.result.AbstractContainerNode;
import gtd.stack.AbstractStackNode;

public class EdgesSet{
	private final static int DEFAULT_SIZE = 4;
	
	private AbstractStackNode[] edges;
	private int size;
	
	private int lastVisitedLevel = -1;
	private AbstractContainerNode lastResult;
	
	public EdgesSet(){
		super();
		
		edges = new AbstractStackNode[DEFAULT_SIZE];
		size = 0;
	}
	
	public EdgesSet(int initialSize){
		super();
		
		edges = new AbstractStackNode[initialSize];
		size = 0;
	}
	
	private void enlarge(){
		AbstractStackNode[] oldEdges = edges;
		edges = new AbstractStackNode[size << 1];
		System.arraycopy(oldEdges, 0, edges, 0, size);
	}
	
	public void add(AbstractStackNode edge){
		while(size >= edges.length){
			enlarge();
		}
		
		edges[size++] = edge;
	}
	
	public boolean contains(AbstractStackNode node){
		for(int i = size - 1; i >= 0; --i){
			if(edges[i] == node) return true;
		}
		return false;
	}
	
	public boolean containsBefore(AbstractStackNode node, int limit){
		for(int i = limit - 1; i >= 0; --i){
			if(edges[i] == node) return true;
		}
		return false;
	}
	
	public boolean containsAfter(AbstractStackNode node, int limit){
		if(limit >= 0){ // Bounds check elimination helper.
			for(int i = size - 1; i >= limit; --i){
				if(edges[i] == node) return true;
			}
		}
		return false;
	}
	
	public AbstractStackNode get(int index){
		return edges[index];
	}
	
	public void setLastVisistedLevel(int lastVisitedLevel){
		this.lastVisitedLevel = lastVisitedLevel;
	}
	
	public int getLastVisistedLevel(){
		return lastVisitedLevel;
	}
	
	public void setLastResult(AbstractContainerNode lastResult){
		this.lastResult = lastResult;
	}
	
	public AbstractContainerNode getLastResult(){
		return lastResult;
	}
	
	public int size(){
		return size;
	}
	
	public void clear(){
		size = 0;
	}
}
