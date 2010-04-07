package gll;

import gll.result.INode;
import gll.result.ContainerNode;
import gll.stack.NonTerminalParseStackNode;
import gll.stack.ParseStackNode;
import gll.util.ArrayList;
import gll.util.IntegerHashMap;

import java.lang.reflect.Method;

public class SGLL implements IGLL{
	private final char[] input;
	
	private final ArrayList<ParseStackNode> todoList;
	
	// Updatable
	private final ArrayList<ParseStackNode> stacksToExpand;
	private ArrayList<ParseStackNode> stacksWithTerminalsToReduce;
	private final ArrayList<ParseStackNode> stacksWithNonTerminalsToReduce;
	private ArrayList<ParseStackNode[]> lastExpects;
	private ArrayList<ParseStackNode> possiblySharedExpects;
	private ArrayList<ParseStackNode> possiblySharedExpectsEndNodes;
	private ArrayList<ParseStackNode> possiblySharedNextNodes;
	private IntegerHashMap<ArrayList<ParseStackNode>> possiblySharedEdgeNodesMap;
	
	private int previousLocation;
	private int location;
	
	private ParseStackNode root;
	
	public SGLL(char[] input){
		super();
		
		this.input = input;
		
		todoList = new ArrayList<ParseStackNode>();
		
		stacksToExpand = new ArrayList<ParseStackNode>();
		stacksWithTerminalsToReduce = new ArrayList<ParseStackNode>();
		stacksWithNonTerminalsToReduce = new ArrayList<ParseStackNode>();
		
		possiblySharedNextNodes = new ArrayList<ParseStackNode>();
		possiblySharedEdgeNodesMap = new IntegerHashMap<ArrayList<ParseStackNode>>();
		
		previousLocation = -1;
		location = 0;
	}
	
	public void expect(ParseStackNode... symbolsToExpect){
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
	
	private ParseStackNode updateNextNode(ParseStackNode node){
		for(int i = possiblySharedNextNodes.size() - 1; i >= 0; i--){
			ParseStackNode possibleAlternative = possiblySharedNextNodes.get(i);
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
	
	private ParseStackNode updateEdgeNode(ParseStackNode node){
		int startLocation = node.getStartLocation();
		ArrayList<ParseStackNode> possiblySharedEdgeNodes = possiblySharedEdgeNodesMap.get(startLocation);
		if(possiblySharedEdgeNodes != null){
			for(int i = possiblySharedEdgeNodes.size() - 1; i >= 0; i--){
				ParseStackNode possibleAlternative = possiblySharedEdgeNodes.get(i);
				if(possibleAlternative.isSimilar(node)){
					return possibleAlternative;
				}
			}
		}else{
			possiblySharedEdgeNodes = new ArrayList<ParseStackNode>();
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
	
	private void move(ParseStackNode node){
		INode[][] results = node.getResults();
		int[] resultStartLocations = node.getResultStartLocations();
		
		if(node.hasEdges()){
			ArrayList<ParseStackNode> edges = node.getEdges();
			for(int i = edges.size() - 1; i >= 0; i--){
				ParseStackNode edge = edges.get(i);
				edge = updateEdgeNode(edge);
				addResults(edge, results, resultStartLocations);
			}
		}
		if(node.hasNexts()){
			ArrayList<ParseStackNode> nexts = node.getNexts();
			for(int i = nexts.size() - 1; i >= 0; i--){
				ParseStackNode next = nexts.get(i);
				next = updateNextNode(next);
				addPrefixes(next, results, resultStartLocations);
			}
		}
	}
	
	private void addPrefixes(ParseStackNode next, INode[][] prefixes, int[] prefixStartLocations){
		for(int i = prefixes.length - 1; i >= 0; i--){
			next.addPrefix(prefixes[i], prefixStartLocations[i]);
		}
	}
	
	private void addResults(ParseStackNode edge, INode[][] results, int[] resultStartLocations){
		if(location == input.length && !edge.hasEdges() && !edge.hasNexts()){
			root = edge; // Root reached.
		}
		
		String name = edge.getNodeName();
		
		int nrOfResults = results.length;
		for(int i = nrOfResults - 1; i >= 0; i--){
			if(edge.getStartLocation() == resultStartLocations[i]){
				INode result = new ContainerNode(name, results[i]);
				edge.addResult(result);
			}
		}
	}
	
	private void reduceTerminal(ParseStackNode terminal){
		if(!terminal.reduce(input)) return;
		
		move(terminal);
	}
	
	private void reduceNonTerminal(ParseStackNode nonTerminal){
		move(nonTerminal);
	}
	
	private void reduce(){
		if(previousLocation != location){ // Epsilon fix.
			possiblySharedNextNodes = new ArrayList<ParseStackNode>();
			possiblySharedEdgeNodesMap = new IntegerHashMap<ArrayList<ParseStackNode>>();
		}
		
		// Reduce terminals.
		while(stacksWithTerminalsToReduce.size() > 0){
			ParseStackNode terminal = stacksWithTerminalsToReduce.remove(stacksWithTerminalsToReduce.size() - 1);
			reduceTerminal(terminal);

			todoList.remove(terminal);
		}
		
		// Reduce non-terminals.
		while(stacksWithNonTerminalsToReduce.size() > 0){
			ParseStackNode nonTerminal = stacksWithNonTerminalsToReduce.remove(stacksWithNonTerminalsToReduce.size() - 1);
			reduceNonTerminal(nonTerminal);
		}
	}
	
	private void findStacksToReduce(){
		// Find the stacks that will progress the least.
		int closestNextLocation = Integer.MAX_VALUE;
		for(int i = todoList.size() - 1; i >= 0; i--){
			ParseStackNode node = todoList.get(i);
			int nextLocation = node.getStartLocation() + node.getLength();
			if(nextLocation < closestNextLocation){
				stacksWithTerminalsToReduce = new ArrayList<ParseStackNode>();
				stacksWithTerminalsToReduce.add(node);
				closestNextLocation = nextLocation;
			}else if(nextLocation == closestNextLocation){
				stacksWithTerminalsToReduce.add(node);
			}
		}
		
		previousLocation = location;
		location = closestNextLocation;
	}
	
	private boolean share(ParseStackNode node, ParseStackNode stack){
		for(int j = possiblySharedExpects.size() - 1; j >= 0; j--){
			ParseStackNode possiblySharedNode = possiblySharedExpects.get(j);
			if(possiblySharedNode.isSimilar(node)){
				possiblySharedExpectsEndNodes.get(j).addEdge(stack);
				return true;
			}
		}
		return false;
	}
	
	private void handleExpects(ParseStackNode stackBeingWorkedOn){
		for(int i = lastExpects.size() - 1; i >= 0; i--){
			ParseStackNode[] expectedNodes = lastExpects.get(i);
			
			// Handle sharing (and loops).
			ParseStackNode first = expectedNodes[0];
			
			if(!share(first, stackBeingWorkedOn)){
				first = first.getCleanCopy();
				ParseStackNode current = first;
				ParseStackNode prev;
				
				for(int k = 1; k < expectedNodes.length; k++){
					prev = current;
					current = expectedNodes[k].getCleanCopy();
					prev.addNext(current);
				}
				
				current.addEdge(stackBeingWorkedOn);
				
				first.setStartLocation(location);
				
				stacksToExpand.add(first);
				possiblySharedExpects.add(first);
				possiblySharedExpectsEndNodes.add(current);
			}
		}
	}
	
	private void expandStack(ParseStackNode node){
		if(node.isReducable()){
			if((location + node.getLength()) <= input.length) todoList.add(node);
			return;
		}
		
		if(!node.isList()){
			callMethod(node.getMethodName());
			
			handleExpects(node);
		}else{ // List
			ParseStackNode[] listChildren = node.getChildren();
			
			ParseStackNode child = listChildren[0];
			if(!share(child, node)){
				child.setStartLocation(location);
				child.addEdge(node);
				
				stacksToExpand.add(child);
				possiblySharedExpects.add(child);
				possiblySharedExpectsEndNodes.add(child);
			}
			
			if(listChildren.length > 1){ // Star list or optional.
				child = listChildren[1];
				if(!share(child, node)){
					child.setStartLocation(location);
					child.addEdge(node);
					
					stacksToExpand.add(child);
					possiblySharedExpects.add(child);
					possiblySharedExpectsEndNodes.add(child);
				}
			}
		}
	}
	
	private void expand(){
		possiblySharedExpects = new ArrayList<ParseStackNode>();
		possiblySharedExpectsEndNodes = new ArrayList<ParseStackNode>();
		while(stacksToExpand.size() > 0){
			lastExpects = new ArrayList<ParseStackNode[]>();
			expandStack(stacksToExpand.remove(stacksToExpand.size() - 1));
		}
	}
	
	public INode parse(String start){
		// Initialize.
		ParseStackNode rootNode = new NonTerminalParseStackNode(start, START_SYMBOL_ID).getCleanCopy();
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
