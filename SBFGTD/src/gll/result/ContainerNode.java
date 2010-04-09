package gll.result;

public class ContainerNode implements INode{
	private final String name;
	private final INode[] items;
	
	public ContainerNode(String name, INode[] items){
		super();
		
		this.name = name;
		this.items = items;
	}
	
	public int items(){
		return items.length;
	}
	
	public INode[] getItems(){
		return items;
	}
	
	public String toString(){
		StringBuilder sb = new StringBuilder();
		
		int nrOfChildren = items.length;
		
		sb.append(name);
		sb.append('(');
		sb.append(items[0]);
		for(int i = 1; i < nrOfChildren; i++){
			sb.append(',');
			sb.append(items[i]);
		}
		sb.append(')');
		
		return sb.toString();
	}
}
