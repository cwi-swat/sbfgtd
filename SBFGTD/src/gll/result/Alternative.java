package gll.result;

import java.util.List;

public class Alternative implements INode{
	private final List<INode> alternatives;
	
	public Alternative(List<INode> alternatives){
		super();
		
		this.alternatives = alternatives;
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
