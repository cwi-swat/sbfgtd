package gll.result;

import gll.util.ArrayList;

public class Alternative implements INode{
	private final ArrayList<INode> alternatives;
	
	public Alternative(ArrayList<INode> alternatives){
		super();
		
		this.alternatives = alternatives;
	}
	
	public int items(){
		throw new UnsupportedOperationException();
	}
	
	public INode[] getItems(){
		return alternatives.get(0).getItems(); // TODO Fix flattening for ambiguous stuff.
	}
	
	public String toString(){
		if(alternatives.size() == 1) return alternatives.get(0).toString();
		
		StringBuilder sb = new StringBuilder();
		
		sb.append('[');
		for(int i = alternatives.size() - 1; i >= 2; i--){
			sb.append(alternatives.get(i));
			sb.append(',');
		}
		sb.append(alternatives.get(1));
		sb.append(',');
		sb.append(alternatives.get(0));
		sb.append(']');
		
		return sb.toString();
	}
}
