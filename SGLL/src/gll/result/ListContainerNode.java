package gll.result;

import gll.result.struct.Link;
import gll.util.ArrayList;
import gll.util.IndexedStack;

public class ListContainerNode extends AbstractNode{
	private final String name;
	private Link firstAlternative;
	private ArrayList<Link> alternatives;
	
	private final boolean isNullable;
	
	private String cachedResult;
	
	public ListContainerNode(String name, boolean isNullable){
		super();
		
		this.name = name;
		this.isNullable = isNullable;
	}
	
	public void addAlternative(Link children){
		if(firstAlternative == null){
			firstAlternative = children;
		}else{
			if(alternatives == null) alternatives = new ArrayList<Link>(1);
			alternatives.add(children);
		}
	}
	
	public boolean isNullable(){
		return isNullable;
	}
	
	private void gatherAlternatives(Link child, ArrayList<String[]> gatheredAlternatives, IndexedStack<AbstractNode> stack, int depth, CycleMark cycleMark){
		AbstractNode childNode = child.node;
		String result = childNode.print(stack, depth, cycleMark);
		
		ArrayList<AbstractNode> blackList = new ArrayList<AbstractNode>();
		if(childNode.isNullable()){
			String cycle = gatherCycle(child, new String[]{result}, stack, depth, cycleMark, blackList);
			if(cycle != null){
				gatherProduction(child, new String[]{cycle}, gatheredAlternatives, stack, depth, cycleMark, blackList);
				return;
			}
		}
		gatherProduction(child, new String[]{result}, gatheredAlternatives, stack, depth, cycleMark, blackList);
	}
	
	private void gatherProduction(Link child, String[] postFix, ArrayList<String[]> gatheredAlternatives, IndexedStack<AbstractNode> stack, int depth, CycleMark cycleMark, ArrayList<AbstractNode> blackList){
		ArrayList<Link> prefixes = child.prefixes;
		if(prefixes == null){
			gatheredAlternatives.add(postFix);
			return;
		}
		
		int nrOfPrefixes = prefixes.size();
		
		for(int i = nrOfPrefixes - 1; i >= 0; --i){
			Link prefix = prefixes.get(i);
			
			if(prefix == null){
				gatheredAlternatives.add(postFix);
			}else{
				AbstractNode prefixNode = prefix.node;
				if(blackList.contains(prefixNode)){
					continue;
				}
				
				String result = prefixNode.print(stack, depth, cycleMark);
				
				if(prefixNode.isNullable()){ // Possibly a cycle.
					String cycle = gatherCycle(prefix, new String[]{result}, stack, depth, cycleMark, blackList);
					if(cycle != null){
						int length = postFix.length;
						String[] newPostFix = new String[length + 1];
						System.arraycopy(postFix, 0, newPostFix, 1, length);
						newPostFix[0] = cycle;
						
						gatherProduction(prefix, newPostFix, gatheredAlternatives, stack, depth, cycleMark, blackList);
						continue;
					}
				}
				
				int length = postFix.length;
				String[] newPostFix = new String[length + 1];
				System.arraycopy(postFix, 0, newPostFix, 1, length);
				newPostFix[0] = result;
				gatherProduction(prefix, newPostFix, gatheredAlternatives, stack, depth, cycleMark, blackList);
			}
		}
	}
	
	private String gatherCycle(Link child, String[] postFix, IndexedStack<AbstractNode> stack, int depth, CycleMark cycleMark, ArrayList<AbstractNode> blackList){
		AbstractNode originNode = child.node;
		
		blackList.add(originNode);
		
		OUTER : do{
			ArrayList<Link> prefixes = child.prefixes;
			if(prefixes == null){
				return null;
			}
			
			int nrOfPrefixes = prefixes.size();
			
			for(int i = nrOfPrefixes - 1; i >= 0; --i){
				Link prefix = prefixes.get(i);
				AbstractNode prefixNode = prefix.node;
				
				if(prefixNode == originNode){
					StringBuilder buffer = new StringBuilder();
					buffer.append("repeat(");
					buffer.append(postFix[0]);
					for(int j = 1; j < postFix.length; ++j){
						buffer.append(',');
						buffer.append(postFix[j]);
					}
					buffer.append(')');
					
					return buffer.toString();
				}
				
				if(prefixNode.isNullable()){
					blackList.add(prefixNode);
					
					int length = postFix.length;
					String[] newPostFix = new String[length + 1];
					System.arraycopy(postFix, 0, newPostFix, 1, length);
					newPostFix[0] = prefixNode.print(stack, depth, cycleMark);
					
					child = prefix;
					postFix = newPostFix;
					continue OUTER;
				}
			}
			break;
		}while(true);
		
		return null;
	}
	
	private void printAlternative(String[] children, StringBuilder out){
		out.append(name);
		out.append('(');
		out.append(children[0]);
		for(int i = 1; i < children.length; ++i){
			out.append(',');
			out.append(children[i]);
		}
		out.append(')');
	}
	
	public String print(IndexedStack<AbstractNode> stack, int depth, CycleMark cycleMark){
		if(cachedResult != null && (depth <= cycleMark.depth)){
			if(depth == cycleMark.depth){
				cycleMark.reset();
			}
			return cachedResult;
		}
		
		StringBuilder sb = new StringBuilder();
		
		int index = stack.findIndex(this);
		if(index != -1){ // Cycle found.
			sb.append("cycle(");
			sb.append(name);
			sb.append(',');
			sb.append((depth - index));
			sb.append(")");
			
			cycleMark.setMark(index);
			
			return sb.toString();
		}
		
		int childDepth = depth + 1;
		
		stack.push(this, depth); // Push
		
		// Gather
		ArrayList<String[]> gatheredAlternatives = new ArrayList<String[]>();
		gatherAlternatives(firstAlternative, gatheredAlternatives, stack, childDepth, cycleMark);
		if(alternatives != null){
			for(int i = alternatives.size() - 1; i >= 0; --i){
				gatherAlternatives(alternatives.get(i), gatheredAlternatives, stack, childDepth, cycleMark);
			}
		}
		
		// Print
		int nrOfAlternatives = gatheredAlternatives.size();
		if(nrOfAlternatives == 1){
			printAlternative(gatheredAlternatives.get(0), sb);
		}else{
			sb.append('[');
			printAlternative(gatheredAlternatives.get(nrOfAlternatives - 1), sb);
			for(int i = nrOfAlternatives - 2; i >= 0; --i){
				sb.append(',');
				printAlternative(gatheredAlternatives.get(i), sb);
			}
			sb.append(']');
		}
		
		stack.dirtyPurge(); // Pop
		
		return (depth <= cycleMark.depth) ? (cachedResult = sb.toString()) : sb.toString();
	}
	
	public String toString(){
		return print(new IndexedStack<AbstractNode>(), 0, new CycleMark());
	}
}
