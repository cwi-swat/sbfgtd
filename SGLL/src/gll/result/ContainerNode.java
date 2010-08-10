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
	
	private void gatherAlternatives(Link child, ArrayList<String[]> gatheredAlternatives, IndexedStack<AbstractNode> stack, int depth){
		String result = child.node.toString(stack, depth);
		gatherProduction(child, new String[]{result}, gatheredAlternatives, stack, depth);
	}
	
	private void gatherProduction(Link child, String[] postFix, ArrayList<String[]> gatheredAlternatives, IndexedStack<AbstractNode> stack, int depth){
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
	
	private void gatherListAlternatives(Link child, ArrayList<String[]> gatheredAlternatives, IndexedStack<AbstractNode> stack, int depth){
		AbstractNode childNode = child.node;
		String result = childNode.toString(stack, depth);
		
		IndexedStack<AbstractNode> listElementStack = new IndexedStack<AbstractNode>();
		
		if(childNode.isContainer()) listElementStack.push(childNode, 0);
		int start = gatheredAlternatives.size();
		IntegerList foundCycles = gatherList(child, new String[]{result}, gatheredAlternatives, stack, depth, listElementStack, 1);
		if(foundCycles != null){
			gatheredAlternatives.resetTo(start);
			gatherList(child, new String[]{"repeat("+result+")"}, gatheredAlternatives, stack, depth, listElementStack, 1);
		}
		if(childNode.isContainer()) listElementStack.pop();
	}
	
	private IntegerList gatherList(Link child, String[] postFix, ArrayList<String[]> gatheredAlternatives, IndexedStack<AbstractNode> stack, int depth, IndexedStack<AbstractNode> listElementStack, int elementNr){
		ArrayList<Link> prefixes = child.prefixes;
		if(prefixes == null){
			gatheredAlternatives.add(postFix);
			return null;
		}
		
		IntegerList cycles = null;
		
		LinearIntegerKeyedMap<IntegerList> cyclesList = null;
		for(int i = prefixes.size() - 1; i >= 0; i--){
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
			
			newPostFix[0] = prefixNode.toString(stack, depth);
			IntegerList foundCycles = gatherList(prefix, newPostFix, gatheredAlternatives, stack, depth, listElementStack, elementNr + 1);
			
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
				for(int j = foundCycles.size() - 1; j >= 0; j--){
					int oldLength = postFix.length;
					int repeatLength = elementNr - foundCycles.get(j);
					String[] cyclePostFix = new String[oldLength - repeatLength + 1];
					System.arraycopy(postFix, repeatLength, cyclePostFix, 1, oldLength - repeatLength);
					
					StringBuilder buffer = new StringBuilder();
					buffer.append("repeat(");
					buffer.append(prefixes.get(cycleIndex).node.toString(listElementStack, depth));
					for(int i = 0; i < repeatLength; i++){
						buffer.append(',');
						buffer.append(postFix[i]);
					}
					buffer.append(')');
					cyclePostFix[0] = buffer.toString();
					
					if(cycleIndex == 0 && prefixes.size() == 1){
						gatheredAlternatives.add(cyclePostFix); // This cycle is the only thing in the list.
					}else{
						for(int i = prefixes.size() - 1; i >= 0; i--){
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
							
							newPostFix[0] = prefixNode.toString(stack, depth);
							gatherList(prefix, newPostFix, gatheredAlternatives, stack, depth, listElementStack, elementNr + 1);
							
							if(prefixNode.isContainer()) listElementStack.pop();
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
		for(int i = 1; i < children.length; i++){
			out.append(',');
			out.append(children[i]);
		}
		out.append(')');
	}
	
	private void print(StringBuilder out, IndexedStack<AbstractNode> stack, int depth){
		int index = stack.findIndex(this);
		if(index != -1){ // Cycle found.
			out.append("cycle(");
			out.append(name);
			out.append(',');
			out.append((depth - index));
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
		print(sb, new IndexedStack<AbstractNode>(), 0);
		
		return sb.toString();
	}
	
	public String toString(IndexedStack<AbstractNode> stack, int depth){
		StringBuilder sb = new StringBuilder();
		print(sb, stack, depth);
		
		return sb.toString();
	}
}
