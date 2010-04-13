package gll.result;

public class ContainerNode implements INode{
	private final String name;
	private final INode[] children;
	
	public ContainerNode(String name, INode[] children){
		super();
		
		this.name = name;
		this.children = children;
	}
	
	public boolean isEpsilon(){
		return false;
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
