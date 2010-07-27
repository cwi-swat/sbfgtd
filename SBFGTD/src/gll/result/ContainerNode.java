package gll.result;

import gll.result.struct.Link;
import gll.util.ArrayList;
import gll.util.IndexedStack;

public class ContainerNode implements INode{
	private final String name;
	private Link firstAlternative;
	private ArrayList<Link> alternatives;
	
	private final boolean isListContainer;
	
	public ContainerNode(String name, boolean isListContainer){
		super();
		
		this.name = name;
		this.isListContainer = isListContainer;
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
	
	private void gatherAlternatives(Link child, ArrayList<String[]> gatheredAlternatives, IndexedStack<INode> stack, int depth){
		String result = child.node.toString(stack, depth);
		gatherProduction(child, new String[]{result}, gatheredAlternatives, stack, depth);
	}
	
	private void gatherProduction(Link child, String[] postFix, ArrayList<String[]> gatheredAlternatives, IndexedStack<INode> stack, int depth){
		ArrayList<Link> prefixes = child.prefixes;
		if(prefixes == null){
			gatheredAlternatives.add(postFix);
			return;
		}
		
		for(int i = prefixes.size() - 1; i >= 0; i--){
			Link prefix = prefixes.get(i);
			
			int length = postFix.length;
			String[] newPostFix = new String[length + 1];
			System.arraycopy(postFix, 0, newPostFix, 1, length);
			newPostFix[0] = prefix.node.toString(stack, depth);
			gatherProduction(prefix, newPostFix, gatheredAlternatives, stack, depth);
		}
	}
	
	private void gatherListAlternatives(Link child, ArrayList<String[]> gatheredAlternatives, IndexedStack<INode> stack, int depth){
		String result = child.node.toString(stack, depth);
		
		IndexedStack<ArrayList<Link>> listElementStack = new IndexedStack<ArrayList<Link>>();
		
		ArrayList<Link> childPrefixes = child.prefixes;
		
		listElementStack.push(childPrefixes, 0);
		
		gatherList(childPrefixes, new String[]{result}, gatheredAlternatives, stack, depth, listElementStack, 1, new ArrayList<ArrayList<Link>>());
		
		listElementStack.pop();
	}
	
	private void gatherList(ArrayList<Link> prefixes, String[] postFix, ArrayList<String[]> gatheredAlternatives, IndexedStack<INode> stack, int depth, IndexedStack<ArrayList<Link>> listElementStack, int elementNr, ArrayList<ArrayList<Link>> blackList){
		if(prefixes == null){
			gatheredAlternatives.add(postFix);
			return;
		}
		
		for(int i = prefixes.size() - 1; i >= 0; i--){
			Link prefix = prefixes.get(i);
			
			if(prefix == null){
				gatheredAlternatives.add(postFix);
				continue;
			}
			
			ArrayList<Link> prefixPrefixes = prefix.prefixes;
			
			if(blackList.contains(prefixPrefixes)) continue;
			
			int index = listElementStack.contains(prefixPrefixes);
			if(index != -1){
				int length = postFix.length;
				String[] newPostFix = new String[length];
				System.arraycopy(postFix, 0, newPostFix, 0, length);
				
				newPostFix[0] = "repeat("+newPostFix[0]+")";
				blackList.add(prefixPrefixes);
				gatherList(prefixPrefixes, newPostFix, gatheredAlternatives, stack, depth, listElementStack, elementNr + 1, blackList);
			}else{
				int length = postFix.length;
				String[] newPostFix = new String[length + 1];
				System.arraycopy(postFix, 0, newPostFix, 1, length);
				
				listElementStack.push(prefixPrefixes, elementNr);
				
				newPostFix[0] = prefix.node.toString(stack, depth);
				gatherList(prefixPrefixes, newPostFix, gatheredAlternatives, stack, depth, listElementStack, elementNr + 1, blackList);
				
				listElementStack.pop();
			}
		}
	}
	
	private void printAlternative(String[] children, StringBuilder out){
		out.append(name);
		out.append('(');
		out.append(children[0]);
		for(int i = 1; i < children.length; i++){
			out.append(',');
			out.append(children[i]);
		}
		out.append(')');
	}
	
	private void print(StringBuilder out, IndexedStack<INode> stack, int depth){
		int index = stack.contains(this);
		if(index != -1){ // Cycle found.
			out.append("cycle(");
			out.append(name);
			out.append(',');
			out.append(""+(depth - index));
			out.append(")");
			return;
		}
		
		int childDepth = depth + 1;
		
		stack.push(this, depth); // Push
		
		// Gather
		ArrayList<String[]> gatheredAlternatives = new ArrayList<String[]>();
		if(!isListContainer){
			gatherAlternatives(firstAlternative, gatheredAlternatives, stack, childDepth);
			if(alternatives != null){
				for(int i = alternatives.size() - 1; i >= 0; i--){
					gatherAlternatives(alternatives.get(i), gatheredAlternatives, stack, childDepth);
				}
			}
		}else{
			gatherListAlternatives(firstAlternative, gatheredAlternatives, stack, childDepth);
			if(alternatives != null){
				for(int i = alternatives.size() - 1; i >= 0; i--){
					gatherListAlternatives(alternatives.get(i), gatheredAlternatives, stack, childDepth);
				}
			}
		}
		
		// Print
		int nrOfAlternatives = gatheredAlternatives.size();
		if(nrOfAlternatives == 1){
			printAlternative(gatheredAlternatives.get(0), out);
		}else{
			out.append('[');
			printAlternative(gatheredAlternatives.get(nrOfAlternatives - 1), out);
			for(int i = nrOfAlternatives - 2; i >= 0; i--){
				out.append(',');
				printAlternative(gatheredAlternatives.get(i), out);
			}
			out.append(']');
		}
		
		stack.purge(); // Pop
	}
	
	public String toString(){
		StringBuilder sb = new StringBuilder();
		print(sb, new IndexedStack<INode>(), 0);
		
		return sb.toString();
	}
	
	public String toString(IndexedStack<INode> stack, int depth){
		StringBuilder sb = new StringBuilder();
		print(sb, stack, depth);
		
		return sb.toString();
	}
}
