package gll.result;

import gll.result.struct.Link;
import gll.util.ArrayList;
import gll.util.IndexedStack;

import java.io.IOException;
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
	
	private void gatherAlternatives(Link child, ArrayList<INode[]> gatheredAlternatives){
		gatherProduction(child, new INode[]{child.node}, gatheredAlternatives);
	}
	
	private void gatherProduction(Link child, INode[] postFix, ArrayList<INode[]> gatheredAlternatives){
		ArrayList<Link> prefixes = child.prefixes;
		if(prefixes == null){
			gatheredAlternatives.add(postFix);
			return;
		}
		
		int nrOfPrefixes = prefixes.size();
		if(nrOfPrefixes == 1){
			Link prefix = prefixes.get(0);
			
			int length = postFix.length;
			INode[] newPostFix = new INode[length + 1];
			System.arraycopy(postFix, 0, newPostFix, 1, length);
			newPostFix[0] = prefix.node;
			gatherProduction(prefix, newPostFix, gatheredAlternatives);
			return;
		}
		
		for(int i = nrOfPrefixes - 1; i >= 0; i--){
			Link prefix = prefixes.get(i);
			
			int length = postFix.length;
			INode[] newPostFix = new INode[length + 1];
			System.arraycopy(postFix, 0, newPostFix, 1, length);
			newPostFix[0] = prefix.node;
			gatherProduction(child.prefixes.get(i), newPostFix, gatheredAlternatives);
		}
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
		
		// Gather
		ArrayList<INode[]> gatheredAlternatives = new ArrayList<INode[]>();
		gatherAlternatives(firstAlternative, gatheredAlternatives);
		if(alternatives != null){
			for(int i = alternatives.size() - 1; i >= 0; i--){
				gatherAlternatives(alternatives.get(i), gatheredAlternatives);
			}
		}
		
		// Print
		int nrOfAlternatives = gatheredAlternatives.size();
		if(nrOfAlternatives == 1){
			printAlternative(gatheredAlternatives.get(0), out, stack, childDepth);
		}else{
			out.write('[');
			printAlternative(gatheredAlternatives.get(0), out, stack, childDepth);
			for(int i = nrOfAlternatives - 1; i >= 1; i--){
				out.write(',');
				printAlternative(gatheredAlternatives.get(i), out, stack, childDepth);
			}
			out.write(']');
		}
		
		stack.purge(); // Pop
	}
	
	private void printAlternative(INode[] children, Writer out, IndexedStack<INode> stack, int childDepth) throws IOException{
		out.write(name);
		out.write('(');
		children[0].print(out, stack, childDepth);
		for(int i = 1; i < children.length; i++){
			out.write(',');
			children[i].print(out, stack, childDepth);
		}
		out.write(')');
	}
	
	private void printAlternative(INode[] children, StringBuilder sb){
		sb.append(name);
		sb.append('(');
		sb.append(children[0]);
		for(int i = 1; i < children.length; i++){
			sb.append(',');
			sb.append(children[i]);
		}
		sb.append(')');
	}
	
	public String toString(){
		StringBuilder sb = new StringBuilder();
		
		// Gather
		ArrayList<INode[]> gatheredAlternatives = new ArrayList<INode[]>();
		gatherAlternatives(firstAlternative, gatheredAlternatives);
		if(alternatives != null){
			for(int i = alternatives.size() - 1; i >= 0; i--){
				gatherAlternatives(alternatives.get(i), gatheredAlternatives);
			}
		}
		
		// Print
		int nrOfAlternatives = gatheredAlternatives.size();
		if(nrOfAlternatives == 1){
			printAlternative(gatheredAlternatives.get(0), sb);
		}else{
			sb.append('[');
			printAlternative(gatheredAlternatives.get(0), sb);
			for(int i = nrOfAlternatives - 1; i >= 1; i--){
				sb.append(',');
				printAlternative(gatheredAlternatives.get(i), sb);
			}
			sb.append(']');
		}
		
		return sb.toString();
	}
}
