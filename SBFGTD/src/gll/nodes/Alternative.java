package gll.nodes;

public class Alternative implements INode{
	private final INode[] alternatives;
	
	public Alternative(INode[] alternatives){
		super();
		
		this.alternatives = alternatives;
	}
	
	public String toString(){
		StringBuilder sb = new StringBuilder();
		
		sb.append('[');
		for(int i = alternatives.length - 1; i >= 2; i--){
			sb.append(alternatives[i]);
			sb.append(',');
		}
		sb.append(alternatives[1]);
		sb.append(',');
		sb.append(alternatives[0]);
		sb.append(']');
		
		return sb.toString();
	}
}
