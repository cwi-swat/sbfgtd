package gll.nodes;

public class NonTerminalNode implements INode{
	private final String name;
	private final INode[] children;
	
	private final int length;
	
	public NonTerminalNode(String name, INode[] children){
		super();
		
		this.name = name;
		this.children = children;
		
		int length = 0;
		for(int i = children.length - 1; i >= 0; i--){
			length += children[0].getLength();
		}
		this.length = length;
	}
	
	public int getLength(){
		return length;
	}
	
	public String toString(){
		StringBuilder sb = new StringBuilder();
		
		int nrOfChildren = children.length;
		
		sb.append(name);
		sb.append('(');
		sb.append(children[0]);
		for(int i = 1; i < nrOfChildren; i++){
			sb.append(',');
			sb.append(children[i]);
		}
		sb.append(')');
		
		return sb.toString();
	}
}
