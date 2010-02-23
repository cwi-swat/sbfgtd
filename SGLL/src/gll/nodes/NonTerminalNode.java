package gll.nodes;

import java.util.ArrayList;
import java.util.List;

public class NonTerminalNode implements INode{
	private final String name;
	private final List<INode> children;
	
	private final int length;
	
	public NonTerminalNode(String name, List<INode> children){
		super();
		
		this.name = name;
		this.children = children;
		
		int length = 0;
		for(int i = children.size() - 1; i >= 0; i--){
			length += children.get(i).getLength();
		}
		this.length = length;
	}
	
	public NonTerminalNode(String name, INode child){
		super();
		
		this.name = name;
		this.children = new ArrayList<INode>(1);
		children.add(child);
		
		this.length = child.getLength();
	}
	
	public int getLength(){
		return length;
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
