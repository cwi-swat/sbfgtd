package gtd.result;

import gtd.result.struct.Link;
import gtd.util.ArrayList;
import gtd.util.HashMap;
import gtd.util.IndexedStack;

public class ListContainerNode extends AbstractContainerNode{
	private String cachedResult;
	
	public ListContainerNode(String name, boolean isNullable, boolean isSeparator){
		super(name, isNullable, isSeparator);
	}
	
	private void gatherAlternatives(Link child, ArrayList<String[]> gatheredAlternatives, IndexedStack<AbstractNode> stack, int depth, CycleMark cycleMark, HashMap<ArrayList<Link>, String> sharedPrefixCache){
		AbstractNode childNode = child.node;
		String result = childNode.print(stack, depth, cycleMark);
		
		ArrayList<AbstractNode> blackList = new ArrayList<AbstractNode>();
		if(childNode.isEmpty()){
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
					gatherProduction(child, new String[]{buffer.toString()}, gatheredAlternatives, stack, depth, cycleMark, blackList, sharedPrefixCache);
				}else{
					gatherProduction(child, new String[]{result, buffer.toString()}, gatheredAlternatives, stack, depth, cycleMark, blackList, sharedPrefixCache);
				}
				return;
			}
		}
		gatherProduction(child, new String[]{result}, gatheredAlternatives, stack, depth, cycleMark, blackList, sharedPrefixCache);
	}
	
	private void gatherProduction(Link child, String[] postFix, ArrayList<String[]> gatheredAlternatives, IndexedStack<AbstractNode> stack, int depth, CycleMark cycleMark, ArrayList<AbstractNode> blackList, HashMap<ArrayList<Link>, String> sharedPrefixCache){
		do{
			ArrayList<Link> prefixes = child.prefixes;
			if(prefixes == null){
				gatheredAlternatives.add(postFix);
				return;
			}
			
			if(prefixes.size() == 1){
				Link prefix = prefixes.get(0);
				
				if(prefix == null){
					gatheredAlternatives.add(postFix);
					return;
				}
				
				AbstractNode prefixNode = prefix.node;
				if(blackList.contains(prefixNode)){
					return;
				}
				
				String result = prefixNode.print(stack, depth, cycleMark);
				
				if(prefixNode.isEmpty() && !prefixNode.isSeparator()){ // Possibly a cycle.
					String[] cycle = gatherCycle(prefix, new String[]{result}, stack, depth, cycleMark, blackList);
					if(cycle != null){
						String[] newPostFix = buildCycle(cycle, postFix, result);
						
						child = prefix;
						postFix = newPostFix;
						continue;
					}
				}
				
				int length = postFix.length;
				String[] newPostFix = new String[length + 1];
				System.arraycopy(postFix, 0, newPostFix, 1, length);
				newPostFix[0] = result;
				
				child = prefix;
				postFix = newPostFix;
				continue;
			}
			
			gatherAmbiguousProduction(prefixes, postFix, gatheredAlternatives, stack, depth, cycleMark, blackList, sharedPrefixCache);
			
			break;
		}while(true);
	}
	
	private void gatherAmbiguousProduction(ArrayList<Link> prefixes, String[] postFix, ArrayList<String[]> gatheredAlternatives, IndexedStack<AbstractNode> stack, int depth, CycleMark cycleMark, ArrayList<AbstractNode> blackList, HashMap<ArrayList<Link>, String> sharedPrefixCache){
		String prefixResult = sharedPrefixCache.get(prefixes);
		if(prefixResult != null){
			int length = postFix.length;
			String[] newPostFix = new String[length + 1];
			System.arraycopy(postFix, 0, newPostFix, 1, length);
			newPostFix[0] = prefixResult;
			
			gatheredAlternatives.add(newPostFix);
			return;
		}
		
		ArrayList<String[]> gatheredPrefixes = new ArrayList<String[]>();
		
		for(int i = prefixes.size() - 1; i >= 0; --i){
			Link prefix = prefixes.get(i);
			
			if(prefix == null){
				gatheredAlternatives.add(postFix);
			}else{
				AbstractNode prefixNode = prefix.node;
				if(blackList.contains(prefixNode)){
					continue;
				}
				
				String result = prefixNode.print(stack, depth, cycleMark);
				
				if(prefixNode.isEmpty() && !prefixNode.isSeparator()){ // Possibly a cycle.
					String[] cycle = gatherCycle(prefix, new String[]{result}, stack, depth, cycleMark, blackList);
					if(cycle != null){
						String[] newPostFix = buildCycle(cycle, new String[]{}, result);
						
						gatherProduction(prefix, newPostFix, gatheredPrefixes, stack, depth, cycleMark, blackList, sharedPrefixCache);
						continue;
					}
				}
				
				gatherProduction(prefix, new String[]{result}, gatheredPrefixes, stack, depth, cycleMark, blackList, sharedPrefixCache);
			}
		}
		
		int nrOfGatheredPrefixes = gatheredPrefixes.size();
		if(nrOfGatheredPrefixes == 0) return;
		
		if(nrOfGatheredPrefixes == 1){
			String[] prefixAlternative = gatheredPrefixes.get(0);
			
			int length = postFix.length;
			int prefixLength = prefixAlternative.length;
			String[] newPostFix = new String[length + prefixLength];
			System.arraycopy(postFix, 0, newPostFix, prefixLength, length);
			System.arraycopy(prefixAlternative, 0, newPostFix, 0, prefixLength);
			
			gatheredAlternatives.add(newPostFix);
		}else{
			StringBuilder sb = new StringBuilder();
			sb.append('[');
			
			String[] prefixAlternative = gatheredPrefixes.get(0);
			
			sb.append(name);
			sb.append('(');
			sb.append(prefixAlternative[0]);
			for(int j = 1; j < prefixAlternative.length; ++j){
				sb.append(',');
				sb.append(prefixAlternative[j]);
			}
			sb.append(')');
			
			for(int i = nrOfGatheredPrefixes - 1; i >= 1; --i){
				sb.append(',');
				
				prefixAlternative = gatheredPrefixes.get(i);
				
				sb.append(name);
				sb.append('(');
				sb.append(prefixAlternative[0]);
				for(int j = 1; j < prefixAlternative.length; ++j){
					sb.append(',');
					sb.append(prefixAlternative[j]);
				}
				sb.append(')');
			}
	
			sb.append(']');
			
			prefixResult = sb.toString();
			sharedPrefixCache.put(prefixes, prefixResult);
			
			int length = postFix.length;
			String[] newPostFix = new String[length + 1];
			System.arraycopy(postFix, 0, newPostFix, 1, length);
			newPostFix[0] = prefixResult;
			
			gatheredAlternatives.add(newPostFix);
		}
	}
	
	private static String[] gatherCycle(Link child, String[] postFix, IndexedStack<AbstractNode> stack, int depth, CycleMark cycleMark, ArrayList<AbstractNode> blackList){
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
				if(prefix == null) continue;
				AbstractNode prefixNode = prefix.node;
				
				if(prefixNode == originNode){
					return postFix;
				}
				
				if(prefixNode.isEmpty()){
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
	
	private static String[] buildCycle(String[] cycle, String[] postFix, String result){
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
		
		return newPostFix;
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
		if(depth == cycleMark.depth){
			cycleMark.reset();
		}
		
		if(cachedResult != null && (depth <= cycleMark.depth)){
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
		HashMap<ArrayList<Link>, String> sharedPrefixCache = new HashMap<ArrayList<Link>, String>();
		ArrayList<String[]> gatheredAlternatives = new ArrayList<String[]>();
		gatherAlternatives(firstAlternative, gatheredAlternatives, stack, childDepth, cycleMark, sharedPrefixCache);
		if(alternatives != null){
			for(int i = alternatives.size() - 1; i >= 0; --i){
				gatherAlternatives(alternatives.get(i), gatheredAlternatives, stack, childDepth, cycleMark, sharedPrefixCache);
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
		
		return (depth < cycleMark.depth) ? (cachedResult = sb.toString()) : sb.toString();
	}
	
	public String toString(){
		return print(new IndexedStack<AbstractNode>(), 0, new CycleMark());
	}
}
