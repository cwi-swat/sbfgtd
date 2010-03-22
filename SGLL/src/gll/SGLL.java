package gll;

import gll.result.INode;
import gll.result.NonTerminalNode;
import gll.stack.NonTerminalParseStackNode;
import gll.stack.ParseStackNode;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SGLL implements IGLL{
	private final byte[] input;
	
	private final List<ParseStackNode> todoList;
	
	// Updatable
	private final List<ParseStackNode> stacksToExpand;
	private List<ParseStackNode> stacksWithTerminalsToReduce;
	private final List<ParseStackNode> stacksWithNonTerminalsToReduce;
	private List<ParseStackNode[]> lastExpects;
	private List<ParseStackNode> possiblySharedExpects;
	private List<ParseStackNode> possiblySharedExpectsEndNodes;
	private List<ParseStackNode> possiblySharedNextNodes;
	private Map<Integer, List<ParseStackNode>> possiblySharedEdgeNodesMap;
	
	private int location;
	
	private ParseStackNode root;
	
	public SGLL(byte[] input){
		super();
		
		this.input = input;
		
		todoList = new ArrayList<ParseStackNode>();
		
		stacksToExpand = new ArrayList<ParseStackNode>();
		stacksWithTerminalsToReduce = new ArrayList<ParseStackNode>();
		stacksWithNonTerminalsToReduce = new ArrayList<ParseStackNode>();
		
		location = 0;
		
		root = null;
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
				//System.out.println(possibleAlternative+" "+node);
				if(node.hasEdges()){
					possibleAlternative.addEdges(node.getEdges());
				}
				return possibleAlternative;
			}
		}
		
		node.setStartLocation(location);
		//System.out.println(node);
		possiblySharedNextNodes.add(node);
		stacksToExpand.add(node);
		nodes++;
		return node;
	}
	
	private ParseStackNode updateEdgeNode(ParseStackNode node){
		int startLocation = node.getStartLocation();
		Integer startIndex = new Integer(startLocation);
		List<ParseStackNode> possiblySharedEdgeNodes = possiblySharedEdgeNodesMap.get(startIndex);
		if(possiblySharedEdgeNodes != null){
			for(int i = possiblySharedEdgeNodes.size() - 1; i >= 0; i--){
				ParseStackNode possibleAlternative = possiblySharedEdgeNodes.get(i);
				if(possibleAlternative.isSimilar(node)){
					return possibleAlternative;
				}
			}
		}else{
			possiblySharedEdgeNodes = new ArrayList<ParseStackNode>();
			possiblySharedEdgeNodesMap.put(startIndex, possiblySharedEdgeNodes);
			
			if(!node.endLocationIsSet()){
				nodes++;
				edges += node.getEdges() != null ? node.getEdges().size() : 0;
				node = node.getCleanCopy();
				node.setStartLocation(startLocation);
			}
		}
		
		possiblySharedEdgeNodes.add(node);
		stacksWithNonTerminalsToReduce.add(node);
		
		return node;
	}
	
	private void move(ParseStackNode node){
		node.setEndLocation(location);
		
		List<INode[]> results = node.getResults();
		
		if(node.hasEdges()){
			List<ParseStackNode> edges = node.getEdges();
			for(int i = edges.size() - 1; i >= 0; i--){
				ParseStackNode edge = edges.get(i);
				edge = updateEdgeNode(edge);
				addResults(edge, results);
			}
		}else if(location == input.length){
			return; // EOF reached.
		}else if(node.hasNexts()){
			List<ParseStackNode> nexts = node.getNexts();
			for(int i = nexts.size() - 1; i >= 0; i--){
				ParseStackNode next = nexts.get(i);
				next = updateNextNode(next);
				addPrefixes(next, results);
			}
		}
	}
	
	private void addPrefixes(ParseStackNode next, List<INode[]> prefixes){
		for(int i = prefixes.size() - 1; i >= 0; i--){
			next.addPrefix(prefixes.get(i));
		}
	}
	
	private void addResults(ParseStackNode edge, List<INode[]> results){
		if(location == input.length && !edge.hasEdges() && !edge.hasNexts()){
			root = edge; // Root reached.
		}
		
		String name = edge.getNonTerminalName();
		
		int nrOfResults = results.size();
		if(nrOfResults > 1){
			edge.addResult(new NonTerminalNode(name, results.get(0)));
			return;
		}
		
		for(int i = nrOfResults - 1; i >= 0; i--){
			INode result = new NonTerminalNode(name, results.get(i));
			edge.addResult(result);
		}
	}
	
	private void reduceTerminal(ParseStackNode terminal){
		byte[] terminalData = terminal.getTerminalData();
		int startLocation = terminal.getStartLocation();
		
		for(int i = terminalData.length - 1; i >= 0; i--){
			if(terminalData[i] != input[startLocation + i]) return; // Did not match.
		}
		
		move(terminal);
	}
	
	private void reduceNonTerminal(ParseStackNode nonTerminal){
		move(nonTerminal);
	}
	
	private void reduce(){
		possiblySharedNextNodes = new ArrayList<ParseStackNode>();
		possiblySharedEdgeNodesMap = new HashMap<Integer, List<ParseStackNode>>();
		
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
		
		location = closestNextLocation;
	}
	
	private void handleExpects(ParseStackNode stackBeingWorkedOn){
		OUTER : for(int i = lastExpects.size() - 1; i >= 0; i--){
			ParseStackNode[] expectedNodes = lastExpects.get(i);
			
			// Handle sharing (and loops).
			ParseStackNode first = expectedNodes[0];
			
			for(int j = possiblySharedExpects.size() - 1; j >= 0; j--){
				ParseStackNode possiblySharedNode = possiblySharedExpects.get(j);
				if(possiblySharedNode.isSimilar(first)){
					possiblySharedExpectsEndNodes.get(j).addEdge(stackBeingWorkedOn);
					edges++;
					continue OUTER; // Shared.
				}
			}
			
			first = first.getCleanCopy();
			ParseStackNode current = first;
			ParseStackNode prev;
			
			for(int k = 1; k < expectedNodes.length; k++){
				prev = current;
				current = expectedNodes[k].getCleanCopy();
				prev.addNext(current);
			}
			
			current.addEdge(stackBeingWorkedOn);
			edges++;
			
			first.setStartLocation(location);
			//System.out.println(first);
			nodes++;
			
			stacksToExpand.add(first);
			possiblySharedExpects.add(first);
			possiblySharedExpectsEndNodes.add(current);
		}
	}
	
	private void expandStack(ParseStackNode node){
		if(node.isTerminal()){
			if(location + node.getLength() <= input.length) todoList.add(node);
			return;
		}
		
		callMethod(node.getMethodName());
		
		handleExpects(node);
	}
	
	private void expand(){
		possiblySharedExpects = new ArrayList<ParseStackNode>();
		possiblySharedExpectsEndNodes = new ArrayList<ParseStackNode>();
		while(stacksToExpand.size() > 0){
			lastExpects = new ArrayList<ParseStackNode[]>();
			expandStack(stacksToExpand.remove(stacksToExpand.size() - 1));
		}
	}
	
	int nodes = 0;
	public static int edges = 0;
	
	public INode parse(String start){
		edges = 0;
		// Initialize.
		ParseStackNode rootNode = new NonTerminalParseStackNode(start, -1).getCleanCopy();
		nodes++;
		rootNode.setStartLocation(0);
		//System.out.println(rootNode);
		stacksToExpand.add(rootNode);
		expand();
		
		do{
			findStacksToReduce();
			
			reduce();
			
			expand();
		}while(todoList.size() > 0);
		
		if(location != input.length) System.err.println("Parse Error before: "+location);
		
		System.out.println("Nodes "+nodes);
		System.out.println("Edges: "+edges);
		
		return root.getResult();
	}
}
