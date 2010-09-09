package gll.result;

import gll.result.struct.Link;
import gll.util.ArrayList;
import gll.util.IndexedStack;

public class ListContainerNode extends AbstractNode{
	private final String name;
	private Link firstAlternative;
	private ArrayList<Link> alternatives;
	
	private final boolean isNullable;
	private final boolean isSeparator;
	
	private String cachedResult;
	
	public ListContainerNode(String name, boolean isNullable, boolean isSeparator){
		super();
		
		this.name = name;
		this.isNullable = isNullable;
		this.isSeparator = isSeparator;
	}
	
	public void addAlternative(Link children){
		if(firstAlternative == null){
			firstAlternative = children;
		}else{
			if(alternatives == null) alternatives = new ArrayList<Link>(1);
			alternatives.add(children);
		}
	}
	
	protected boolean isNullable(){
		return isNullable;
	}
	
	protected boolean isSeparator(){
		return isSeparator;
	}
	
	private void gatherAlternatives(Link child, ArrayList<String[]> gatheredAlternatives, IndexedStack<AbstractNode> stack, int depth, CycleMark cycleMark){
		AbstractNode childNode = child.node;
		String result = childNode.print(stack, depth, cycleMark);
		
		ArrayList<AbstractNode> blackList = new ArrayList<AbstractNode>();
		if(childNode.isNullable()){
			String[] cycle = gatherCycle(child, new String[]{result}, stack, depth, cycleMark, blackList);
			if(cycle != null){
				StringBuilder buffer = new StringBuilder();
				buffer.append("repeat(");
				buffer.append(cycle[0]);
				int cycleLength = cycle.length;
				for(int j = 1; j < cycleLength; ++j){
					buffer.append(',');
					buffer.append(cycle[j]);
				}
				buffer.append(')');
				
				if(cycleLength == 1){
					gatherProduction(child, new String[]{buffer.toString()}, gatheredAlternatives, stack, depth, cycleMark, blackList);
				}else{
					gatherProduction(child, new String[]{result, buffer.toString()}, gatheredAlternatives, stack, depth, cycleMark, blackList);
				}
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
				
				if(prefixNode.isNullable() && !prefixNode.isSeparator()){ // Possibly a cycle.
					String[] cycle = gatherCycle(prefix, new String[]{result}, stack, depth, cycleMark, blackList);
					if(cycle != null){
						StringBuilder buffer = new StringBuilder();
						buffer.append("repeat(");
						buffer.append(cycle[0]);
						int cycleLength = cycle.length;
						for(int j = 1; j < cycleLength; ++j){
							buffer.append(',');
							buffer.append(cycle[j]);
						}
						buffer.append(')');

						int length = postFix.length;
						String[] newPostFix;
						if(cycleLength == 1){
							newPostFix = new String[length + 1];
							System.arraycopy(postFix, 0, newPostFix, 1, length);
							newPostFix[0] = buffer.toString();
						}else{
							newPostFix = new String[length + 2];
							System.arraycopy(postFix, 0, newPostFix, 2, length);
							newPostFix[1] = buffer.toString();
							newPostFix[0] = result;
						}
						
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
	
	private String[] gatherCycle(Link child, String[] postFix, IndexedStack<AbstractNode> stack, int depth, CycleMark cycleMark, ArrayList<AbstractNode> blackList){
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
					return postFix;
				}
				
				if(prefixNode.isNullable()){
					//blackList.add(prefixNode);
					
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
	
	protected String print(IndexedStack<AbstractNode> stack, int depth, CycleMark cycleMark){
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
