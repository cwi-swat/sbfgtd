package gll;

import gll.result.INode;
import gll.stack.NonTerminalStackNode;
import gll.stack.StackNode;
import gll.util.ArrayList;
import gll.util.IntegerHashMap;

import java.lang.reflect.Method;

public class SGLL implements IGLL{
	private final char[] input;
	
	private final ArrayList<StackNode> todoList;
	
	// Updatable
	private final ArrayList<StackNode> stacksToExpand;
	private ArrayList<StackNode> stacksWithTerminalsToReduce;
	private final ArrayList<StackNode> stacksWithNonTerminalsToReduce;
	private ArrayList<StackNode[]> lastExpects;
	private ArrayList<StackNode> possiblySharedExpects;
	private ArrayList<StackNode> possiblySharedExpectsEndNodes;
	private ArrayList<StackNode> possiblySharedNextNodes;
	private IntegerHashMap<ArrayList<StackNode>> possiblySharedEdgeNodesMap;
	
	private int previousLocation;
	private int location;
	
	private StackNode root;
	
	public SGLL(char[] input){
		super();
		
		this.input = input;
		
		todoList = new ArrayList<StackNode>();
		
		stacksToExpand = new ArrayList<StackNode>();
		stacksWithTerminalsToReduce = new ArrayList<StackNode>();
		stacksWithNonTerminalsToReduce = new ArrayList<StackNode>();
		
		possiblySharedNextNodes = new ArrayList<StackNode>();
		possiblySharedEdgeNodesMap = new IntegerHashMap<ArrayList<StackNode>>();
		
		previousLocation = -1;
		location = 0;
	}
	
	public void expect(StackNode... symbolsToExpect){
		lastExpects.add(symbolsToExpect);
	}
	
	private void callMethod(String methodName){
		try{
			Method method = getClass().getMethod(methodName);
			method.invoke(this);
		}catch(Exception ex){
			// Not going to happen.
			ex.printStackTrace(); // Temp
		}
	}
	
	private StackNode updateNextNode(StackNode node){
		for(int i = possiblySharedNextNodes.size() - 1; i >= 0; i--){
			StackNode possibleAlternative = possiblySharedNextNodes.get(i);
			if(possibleAlternative.isSimilar(node)){
				if(node.hasEdges()){
					possibleAlternative.addEdges(node.getEdges());
				}
				return possibleAlternative;
			}
		}
		
		if(node.startLocationIsSet()){
			node = node.getCleanCopy();
		}
		
		node.setStartLocation(location);
		possiblySharedNextNodes.add(node);
		stacksToExpand.add(node);
		return node;
	}
	
	private StackNode updateEdgeNode(StackNode node){
		int startLocation = node.getStartLocation();
		ArrayList<StackNode> possiblySharedEdgeNodes = possiblySharedEdgeNodesMap.get(startLocation);
		if(possiblySharedEdgeNodes != null){
			for(int i = possiblySharedEdgeNodes.size() - 1; i >= 0; i--){
				StackNode possibleAlternative = possiblySharedEdgeNodes.get(i);
				if(possibleAlternative.isSimilar(node)){
					return possibleAlternative;
				}
			}
		}else{
			possiblySharedEdgeNodes = new ArrayList<StackNode>();
			possiblySharedEdgeNodesMap.unsafePut(startLocation, possiblySharedEdgeNodes);
		}
		
		if(node.isMarked()){
			node = node.getCleanCopyWithPrefix();
			node.setStartLocation(startLocation);
		}
		node.mark();
		
		possiblySharedEdgeNodes.add(node);
		stacksWithNonTerminalsToReduce.add(node);
		
		return node;
	}
	
	private void move(StackNode node){
		INode[][] results = node.getResults();
		int[] resultStartLocations = node.getResultStartLocations();
		
		ArrayList<StackNode> edges;
		StackNode next;
		if((edges = node.getEdges()) != null){
			for(int i = edges.size() - 1; i >= 0; i--){
				StackNode edge = edges.get(i);
				edge = updateEdgeNode(edge);
				addResults(edge, results, resultStartLocations);
			}
		}else if((next = node.getNext()) != null){
			next = updateNextNode(next);
			addPrefixes(next, results, resultStartLocations);
		}
	}
	
	private void addPrefixes(StackNode next, INode[][] prefixes, int[] prefixStartLocations){
		for(int i = prefixes.length - 1; i >= 0; i--){
			next.addPrefix(prefixes[i], prefixStartLocations[i]);
		}
	}
	
	private void addResults(StackNode edge, INode[][] results, int[] resultStartLocations){
		if(location == input.length && !edge.hasEdges() && !edge.hasNext()){
			root = edge; // Root reached.
		}
		
		int nrOfResults = results.length;
		for(int i = nrOfResults - 1; i >= 0; i--){
			if(edge.getStartLocation() == resultStartLocations[i]){
				edge.addResult(results[i]);
			}
		}
	}
	
	private void reduceTerminal(StackNode terminal){
		if(!terminal.reduce(input)) return;
		
		move(terminal);
	}
	
	private void reduceNonTerminal(StackNode nonTerminal){
		move(nonTerminal);
	}
	
	private void reduce(){
		if(previousLocation != location){ // Epsilon fix.
			possiblySharedNextNodes = new ArrayList<StackNode>();
			possiblySharedEdgeNodesMap = new IntegerHashMap<ArrayList<StackNode>>();
		}
		
		// Reduce terminals.
		while(stacksWithTerminalsToReduce.size() > 0){
			StackNode terminal = stacksWithTerminalsToReduce.remove(stacksWithTerminalsToReduce.size() - 1);
			reduceTerminal(terminal);

			todoList.remove(terminal);
		}
		
		// Reduce non-terminals.
		while(stacksWithNonTerminalsToReduce.size() > 0){
			StackNode nonTerminal = stacksWithNonTerminalsToReduce.remove(stacksWithNonTerminalsToReduce.size() - 1);
			reduceNonTerminal(nonTerminal);
		}
	}
	
	private void findStacksToReduce(){
		// Find the stacks that will progress the least.
		int closestNextLocation = Integer.MAX_VALUE;
		for(int i = todoList.size() - 1; i >= 0; i--){
			StackNode node = todoList.get(i);
			int nextLocation = node.getStartLocation() + node.getLength();
			if(nextLocation < closestNextLocation){
				stacksWithTerminalsToReduce = new ArrayList<StackNode>();
				stacksWithTerminalsToReduce.add(node);
				closestNextLocation = nextLocation;
			}else if(nextLocation == closestNextLocation){
				stacksWithTerminalsToReduce.add(node);
			}
		}
		
		previousLocation = location;
		location = closestNextLocation;
	}
	
	private boolean shareNode(StackNode node, StackNode stack){
		for(int j = possiblySharedExpects.size() - 1; j >= 0; j--){
			StackNode possiblySharedNode = possiblySharedExpects.get(j);
			if(possiblySharedNode.isSimilar(node)){
				possiblySharedExpectsEndNodes.get(j).addEdge(stack);
				return true;
			}
		}
		return false;
	}
	
	private void handleExpects(StackNode stackBeingWorkedOn){
		for(int i = lastExpects.size() - 1; i >= 0; i--){
			StackNode[] expectedNodes = lastExpects.get(i);
			
			// Handle sharing (and loops).
			if(!shareNode(expectedNodes[0], stackBeingWorkedOn)){
				int numberOfNodes = expectedNodes.length;
				StackNode last = expectedNodes[numberOfNodes - 1].getCleanCopy();
				StackNode next = last;
				
				for(int k = numberOfNodes - 2; k >= 0; k--){
					StackNode current = expectedNodes[k].getCleanCopy();
					current.addNext(next);
					next = current;
				}
				
				last.addEdge(stackBeingWorkedOn);
				
				next.setStartLocation(location);
				
				stacksToExpand.add(next);
				possiblySharedExpects.add(next);
				possiblySharedExpectsEndNodes.add(last);
			}
		}
	}
	
	private void expandStack(StackNode node){
		if(node.isReducable()){
			if((location + node.getLength()) <= input.length) todoList.add(node);
			return;
		}
		
		if(!node.isList()){
			callMethod(node.getMethodName());
			
			handleExpects(node);
		}else{ // List
			StackNode[] listChildren = node.getChildren();
			
			StackNode child = listChildren[0];
			if(!shareNode(child, node)){
				child.setStartLocation(location);
				
				stacksToExpand.add(child);
				possiblySharedExpects.add(child);
				possiblySharedExpectsEndNodes.add(child);
			}
			
			if(listChildren.length > 1){ // Star list or optional.
				child = listChildren[1];
				if(!shareNode(child, node)){
					child.setStartLocation(location);
					
					stacksToExpand.add(child);
					possiblySharedExpects.add(child);
					possiblySharedExpectsEndNodes.add(child);
				}
			}
		}
	}
	
	private void expand(){
		if(previousLocation != location){
			possiblySharedExpects = new ArrayList<StackNode>();
			possiblySharedExpectsEndNodes = new ArrayList<StackNode>();
		}
		while(stacksToExpand.size() > 0){
			lastExpects = new ArrayList<StackNode[]>(1);
			expandStack(stacksToExpand.remove(stacksToExpand.size() - 1));
		}
	}
	
	protected boolean isInLookAhead(char[][] ranges, char[] characters){
		char next = input[location];
		for(int i = ranges.length - 1; i >= 0; i--){
			char[] range = ranges[i];
			if(next >= range[0] && next <= range[1]) return true;
		}
		
		for(int i = characters.length - 1; i >= 0; i--){
			if(next == characters[i]) return true;
		}
		
		return false;
	}
	
	public INode parse(String start){
		// Initialize.
		StackNode rootNode = new NonTerminalStackNode(START_SYMBOL_ID, start).getCleanCopy();
		rootNode.setStartLocation(0);
		stacksToExpand.add(rootNode);
		expand();
		
		do{
			findStacksToReduce();
			
			reduce();
			
			expand();
		}while(todoList.size() > 0);
		
		if(root == null) throw new RuntimeException("Parse Error before: "+(location == Integer.MAX_VALUE ? 0 : location));
		
		return root.getResult();
	}
}
