package gtd.result;

import gtd.result.struct.Link;
import gtd.util.ArrayList;
import gtd.util.IndexedStack;

public class SortContainerNode extends AbstractContainerNode{
	private String cachedResult;
	
	public SortContainerNode(String name, boolean isNullable, boolean isSeparator){
		super(name, isNullable, isSeparator);
	}
	
	private static void gatherAlternatives(Link child, ArrayList<String[]> gatheredAlternatives, IndexedStack<AbstractNode> stack, int depth, CycleMark cycleMark){
		String result = child.node.print(stack, depth, cycleMark);
		gatherProduction(child, new String[]{result}, gatheredAlternatives, stack, depth, cycleMark);
	}
	
	private static void gatherProduction(Link child, String[] postFix, ArrayList<String[]> gatheredAlternatives, IndexedStack<AbstractNode> stack, int depth, CycleMark cycleMark){
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
		
		return (depth < cycleMark.depth) ? (cachedResult = sb.toString()) : sb.toString();
	}
	
	public String toString(){
		return print(new IndexedStack<AbstractNode>(), 0, new CycleMark());
	}
}
