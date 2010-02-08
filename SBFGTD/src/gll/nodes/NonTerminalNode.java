package gll.nodes;

public class NonTerminalNode implements INode{
	public final String name;
	public final INode[] children;
	
	public NonTerminalNode(String name, INode[] children){
		super();
		
		this.name = name;
		this.children = children;
	}
	
	public String toString(){
		StringBuilder sb = new StringBuilder();

		sb.append(name);
		sb.append('(');
		for(int i = children.length - 1; i >= 2; i--){
			sb.append(children[i]);
			sb.append(',');
		}
		sb.append(children[0]);
		sb.append(')');
		
		return sb.toString();
	}
}
