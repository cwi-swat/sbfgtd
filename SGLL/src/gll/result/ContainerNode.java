package gll.result;

import gll.result.struct.Link;
import gll.util.ArrayList;
import gll.util.IndexedStack;
import gll.util.IntegerList;
import gll.util.LinearIntegerKeyedMap;

public class ContainerNode extends AbstractNode{
	private final String name;
	private Link firstAlternative;
	private ArrayList<Link> alternatives;
	
	private final boolean isListContainer;
	
	private String cachedResult;
	
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
	
	private void gatherAlternatives(Link child, ArrayList<String[]> gatheredAlternatives, IndexedStack<AbstractNode> stack, int depth, CycleMark cycleMark){
		String result = child.node.print(stack, depth, cycleMark);
		gatherProduction(child, new String[]{result}, gatheredAlternatives, stack, depth, cycleMark);
	}
	
	private void gatherProduction(Link child, String[] postFix, ArrayList<String[]> gatheredAlternatives, IndexedStack<AbstractNode> stack, int depth, CycleMark cycleMark){
		ArrayList<Link> prefixes = child.prefixes;
		if(prefixes == null){
			gatheredAlternatives.add(postFix);
			return;
		}
		
		for(int i = prefixes.size() - 1; i >= 0; --i){
			Link prefix = prefixes.get(i);
			
			int length = postFix.length;
			String[] newPostFix = new String[length + 1];
			System.arraycopy(postFix, 0, newPostFix, 1, length);
			newPostFix[0] = prefix.node.print(stack, depth, cycleMark);
			gatherProduction(prefix, newPostFix, gatheredAlternatives, stack, depth, cycleMark);
		}
	}
	
	private void gatherListAlternatives(Link child, ArrayList<String[]> gatheredAlternatives, IndexedStack<AbstractNode> stack, int depth, CycleMark cycleMark){
		AbstractNode childNode = child.node;
		String result = childNode.print(stack, depth, cycleMark);
		
		IndexedStack<AbstractNode> listElementStack = new IndexedStack<AbstractNode>();
		
		if(childNode.isContainer()) listElementStack.push(childNode, 0);
		int start = gatheredAlternatives.size();
		IntegerList foundCycles = gatherList(child, new String[]{result}, gatheredAlternatives, stack, depth, cycleMark, listElementStack, 1);
		if(foundCycles != null){
			gatheredAlternatives.resetTo(start);
			gatherList(child, new String[]{"repeat("+result+")"}, gatheredAlternatives, stack, depth, cycleMark, listElementStack, 1);
		}
		if(childNode.isContainer()) listElementStack.pop();
	}
	
	private IntegerList gatherList(Link child, String[] postFix, ArrayList<String[]> gatheredAlternatives, IndexedStack<AbstractNode> stack, int depth, CycleMark cycleMark, IndexedStack<AbstractNode> listElementStack, int elementNr){
		ArrayList<Link> prefixes = child.prefixes;
		if(prefixes == null){
			gatheredAlternatives.add(postFix);
			return null;
		}
		
		IntegerList cycles = null;
		
		LinearIntegerKeyedMap<IntegerList> cyclesList = null;
		for(int i = prefixes.size() - 1; i >= 0; --i){
			int start = gatheredAlternatives.size();
			
			Link prefix = prefixes.get(i);
			
			if(prefix == null){
				gatheredAlternatives.add(postFix);
				continue;
			}
			
			AbstractNode prefixNode = prefix.node;
			
			int index = listElementStack.findIndex(prefixNode);
			if(index != -1){
				if(cycles == null) cycles = new IntegerList();
				cycles.add(index);
				continue;
			}
			
			int length = postFix.length;
			String[] newPostFix = new String[length + 1];
			System.arraycopy(postFix, 0, newPostFix, 1, length);
			
			if(prefixNode.isContainer()) listElementStack.push(prefixNode, elementNr);
			
			newPostFix[0] = prefixNode.print(stack, depth, cycleMark);
			IntegerList foundCycles = gatherList(prefix, newPostFix, gatheredAlternatives, stack, depth, cycleMark, listElementStack, elementNr + 1);
			
			if(prefixNode.isContainer()) listElementStack.pop();
			
			if(foundCycles != null){
				gatheredAlternatives.resetTo(start);
				
				if(cyclesList == null){
					cyclesList = new LinearIntegerKeyedMap<IntegerList>();
				}
				cyclesList.add(i, foundCycles);
			}
		}
		
		if(cycles != null) return cycles;
		
		if(cyclesList != null){
			for(int k = cyclesList.size() - 1; k >= 0; k--){
				int cycleIndex = cyclesList.getKey(k);
				IntegerList foundCycles = cyclesList.getValue(k);
				for(int j = foundCycles.size() - 1; j >= 0; --j){
					int oldLength = postFix.length;
					int repeatLength = elementNr - foundCycles.get(j);
					String[] cyclePostFix = new String[oldLength - repeatLength + 1];
					System.arraycopy(postFix, repeatLength, cyclePostFix, 1, oldLength - repeatLength);
					
					StringBuilder buffer = new StringBuilder();
					buffer.append("repeat(");
					buffer.append(prefixes.get(cycleIndex).node.print(listElementStack, depth, cycleMark));
					for(int i = 0; i < repeatLength; ++i){
						buffer.append(',');
						buffer.append(postFix[i]);
					}
					buffer.append(')');
					cyclePostFix[0] = buffer.toString();
					
					if(cycleIndex == 0 && prefixes.size() == 1){
						gatheredAlternatives.add(cyclePostFix); // This cycle is the only thing in the list.
					}else{
						for(int i = prefixes.size() - 1; i >= 0; --i){
							if(i == cycleIndex) continue;
							
							Link prefix = prefixes.get(i);
							
							if(prefix == null){
								gatheredAlternatives.add(cyclePostFix);
								continue;
							}
							
							AbstractNode prefixNode = prefix.node;
			
							int length = cyclePostFix.length;
							String[] newPostFix = new String[length + 1];
							System.arraycopy(cyclePostFix, 0, newPostFix, 1, length);
							
							if(prefixNode.isContainer()) listElementStack.push(prefixNode, elementNr);
							
							newPostFix[0] = prefixNode.print(stack, depth, cycleMark);
							gatherList(prefix, newPostFix, gatheredAlternatives, stack, depth, cycleMark, listElementStack, elementNr + 1);
							
							if(prefixNode.isContainer()) listElementStack.dirtyPurge();
						}
					}
				}
			}
		}
		
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
		if(!isListContainer){
			gatherAlternatives(firstAlternative, gatheredAlternatives, stack, childDepth, cycleMark);
			if(alternatives != null){
				for(int i = alternatives.size() - 1; i >= 0; --i){
					gatherAlternatives(alternatives.get(i), gatheredAlternatives, stack, childDepth, cycleMark);
				}
			}
		}else{
			gatherListAlternatives(firstAlternative, gatheredAlternatives, stack, childDepth, cycleMark);
			if(alternatives != null){
				for(int i = alternatives.size() - 1; i >= 0; --i){
					gatherListAlternatives(alternatives.get(i), gatheredAlternatives, stack, childDepth, cycleMark);
				}
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
