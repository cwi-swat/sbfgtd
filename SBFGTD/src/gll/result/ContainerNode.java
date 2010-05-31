package gll.result;

import gll.util.ArrayList;
import gll.util.IndexedStack;

import java.io.IOException;
import java.io.Writer;

public class ContainerNode implements INode{
	private final String name;
	private INode[] firstAlternative;
	private ArrayList<INode[]> alternatives;
	
	public ContainerNode(String name){
		super();
		
		this.name = name;
	}
	
	public void addAlternative(INode[] children){
		if(firstAlternative == null){
			firstAlternative = children;
		}else{
			if(alternatives == null) alternatives = new ArrayList<INode[]>(1);
			alternatives.add(children);
		}
	}
	
	public boolean isEpsilon(){
		return false;
	}
	
	private void printAlternative(INode[] children, Writer out, IndexedStack<INode> stack, int childDepth) throws IOException{
		int nrOfChildren = children.length;
		
		out.write(name);
		out.write('(');
		children[0].print(out, stack, childDepth);
		for(int i = 1; i < nrOfChildren; i++){
			out.write(',');
			children[i].print(out, stack, childDepth);
		}
		out.write(')');
	}
	
	public void print(Writer out, IndexedStack<INode> stack, int depth) throws IOException{
		int index = stack.contains(this);
		if(index != -1){
			out.write("cycle(");
			out.write(name);
			out.write(',');
			out.write(""+(depth - index));
			out.write(")");
			return;
		}
		
		int childDepth = depth + 1;
		
		stack.push(this, depth); // Push
		
		if(alternatives == null){
			printAlternative(firstAlternative, out, stack, childDepth);
		}else{
			out.write('[');
			for(int i = alternatives.size() - 1; i >= 1; i--){
				printAlternative(alternatives.get(i), out, stack, childDepth);
				out.write(',');
			}
			printAlternative(alternatives.get(0), out, stack, childDepth);
			out.write(',');
			printAlternative(firstAlternative, out, stack, childDepth);
			out.write(']');
		}
		
		stack.purge(); // Pop
	}
	
	private void printAlternative(INode[] children, StringBuilder sb){
		int nrOfChildren = children.length;
		
		sb.append(name);
		sb.append('(');
		sb.append(children[0]);
		for(int i = 1; i < nrOfChildren; i++){
			sb.append(',');
			sb.append(children[i]);
		}
		sb.append(')');
	}
	
	public String toString(){
		StringBuilder sb = new StringBuilder();
		
		if(alternatives == null){
			printAlternative(firstAlternative, sb);
		}else{
			sb.append('[');
			for(int i = alternatives.size() - 1; i >= 1; i--){
				printAlternative(alternatives.get(i), sb);
				sb.append(',');
			}
			printAlternative(alternatives.get(0), sb);
			sb.append(',');
			printAlternative(firstAlternative, sb);
			sb.append(']');
		}
		
		return sb.toString();
	}
}
