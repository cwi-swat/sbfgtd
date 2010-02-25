package gll.nodes;

import java.util.ArrayList;
import java.util.List;

public class NonTerminalNode implements INode{
	private final String name;
	private final List<INode> children;
	
	public NonTerminalNode(String name, List<INode> children){
		super();
		
		this.name = name;
		this.children = children;
	}
	
	public NonTerminalNode(String name, INode child){
		super();
		
		this.name = name;
		this.children = new ArrayList<INode>(1);
		children.add(child);
	}
	
	public String toString(){
		StringBuilder sb = new StringBuilder();
		
		int nrOfChildren = children.size();
		
		sb.append(name);
		sb.append('(');
		sb.append(children.get(0));
		for(int i = 1; i < nrOfChildren; i++){
			sb.append(',');
			sb.append(children.get(i));
		}
		sb.append(')');
		
		return sb.toString();
	}
}
