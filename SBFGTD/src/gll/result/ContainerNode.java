package gll.result;

import gll.result.struct.Link;
import gll.util.ArrayList;
import gll.util.IndexedStack;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

// TODO Fix amb printing.
public class ContainerNode implements INode{
	private final String name;
	private Link firstAlternative;
	private ArrayList<Link> alternatives;
	
	public ContainerNode(String name){
		super();
		
		this.name = name;
	}
	
	public void addAlternative(Link children){
		if(firstAlternative == null){
			firstAlternative = children;
		}else{
			if(alternatives == null) alternatives = new ArrayList<Link>(1);
			alternatives.add(children);
		}
	}
	
	public boolean isEpsilon(){
		return false;
	}
	
	private void printAlternative(Link children, Writer out, IndexedStack<INode> stack, int childDepth) throws IOException{
		out.write(name);
		out.write('(');
		StringWriter sw = new StringWriter();
		children.node.print(sw, stack, childDepth);
		printProduction(children, out, sw.toString(), true, stack, childDepth);
		out.write(')');
	}
	
	private boolean printProduction(Link production, Writer out, String postFix, boolean last, IndexedStack<INode> stack, int childDepth) throws IOException{
		ArrayList<Link> prefixes = production.prefixes;
		if(prefixes == null){
			out.write(postFix);
			if(!last) out.write(',');
			return false;
		}

		int nrOfPrefixes = prefixes.size();
		if(nrOfPrefixes == 1){
			StringWriter sw = new StringWriter();
			prefixes.get(0).node.print(sw, stack, childDepth);
			String newPostFix = sw.toString() + ',' + postFix;
			return printProduction(prefixes.get(0), out, newPostFix, true, stack, childDepth);
		}
		
		for(int i = nrOfPrefixes - 1; i >= 0; i--){
			StringWriter sw = new StringWriter();
			Link prefix = prefixes.get(i);
			prefix.node.print(sw, stack, childDepth);
			String newPostFix = sw.toString() + postFix;
			printProduction(prefix, out, newPostFix, (i == 0), stack, childDepth);
		}
		return true;
	}
	
	public void print(Writer out, IndexedStack<INode> stack, int depth) throws IOException{
		int index = stack.contains(this);
		if(index != -1){ // Cycle found.
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
	
	private void printAlternative(Link children, StringBuilder sb){
		sb.append(name);
		sb.append('(');
		printProduction(children, sb, children.node.toString(), true);
		sb.append(')');
	}
	
	private boolean printProduction(Link production, StringBuilder sb, String postFix, boolean last){
		ArrayList<Link> prefixes = production.prefixes;
		int nrOfPrefixes = prefixes.size();
		if(nrOfPrefixes == 0){
			sb.append(postFix);
			if(!last) sb.append(',');
			return false;
		}
		
		boolean isAmbiguous = false;
		for(int i = nrOfPrefixes - 1; i >= 0; i--){
			Link prefix = prefixes.get(i);
			String nodeString = prefix.node.toString();
			String newPostFix = nodeString + ',' + postFix;
			isAmbiguous |= printProduction(prefix, sb, newPostFix, (i != 0));
		}
		return isAmbiguous;
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
