package gll;

import gll.result.INode;
import gll.result.NonTerminalNode;
import gll.stack.NonTerminalParseStackNode;
import gll.stack.ParseStackNode;
import gll.util.ArrayList;
import gll.util.HashMap;
import gll.util.IntegerList;

import java.lang.reflect.Method;

public class SGLL implements IGLL{
	private final byte[] input;
	
	private final ArrayList<ParseStackNode> todoList;
	
	// Updatable
	private final ArrayList<ParseStackNode> stacksToExpand;
	private ArrayList<ParseStackNode> stacksWithTerminalsToReduce;
	private final ArrayList<ParseStackNode> stacksWithNonTerminalsToReduce;
	private ArrayList<ParseStackNode[]> lastExpects;
	private ArrayList<ParseStackNode> possiblySharedExpects;
	private ArrayList<ParseStackNode> possiblySharedExpectsEndNodes;
	private ArrayList<ParseStackNode> possiblySharedNextNodes;
	private HashMap<Integer, ArrayList<ParseStackNode>> possiblySharedEdgeNodesMap;
	
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
		Integer startIndex = new Integer(startLocation);
		ArrayList<ParseStackNode> possiblySharedEdgeNodes = possiblySharedEdgeNodesMap.get(startIndex);
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
		}
		
		if(node.endLocationIsSet()){
			node = node.getCleanCopyWithPrefix();
			node.setStartLocation(startLocation);
		}
		node.setEndLocation(location);
		
		possiblySharedEdgeNodes.add(node);
		stacksWithNonTerminalsToReduce.add(node);
		
		return node;
	}
	
	private void move(ParseStackNode node){
		ArrayList<INode[]> results = node.getResults();
		IntegerList resultLengths = node.getResultLengths();
		
		if(node.hasEdges()){
			ArrayList<ParseStackNode> edges = node.getEdges();
			for(int i = edges.size() - 1; i >= 0; i--){
				ParseStackNode edge = edges.get(i);
				edge = updateEdgeNode(edge);
				addResults(edge, results, resultLengths);
			}
		}else if(node.hasNexts()){
			ArrayList<ParseStackNode> nexts = node.getNexts();
			for(int i = nexts.size() - 1; i >= 0; i--){
				ParseStackNode next = nexts.get(i);
				next = updateNextNode(next);
				addPrefixes(next, results, resultLengths);
			}
		}else if(location == input.length){ // Do it afterward, so epsilons work.
			return; // EOF reached.
		}
	}
	
	private void addPrefixes(ParseStackNode next, ArrayList<INode[]> prefixes, IntegerList prefixLengths){
		for(int i = prefixes.size() - 1; i >= 0; i--){
			next.addPrefix(prefixes.get(i), prefixLengths.get(i));
		}
	}
	
	private void addResults(ParseStackNode edge, ArrayList<INode[]> results, IntegerList resultLengths){
		if(location == input.length && !edge.hasEdges() && !edge.hasNexts()){
			root = edge; // Root reached.
		}
		
		String name = edge.getNonTerminalName();
		
		int nrOfResults = results.size();
		for(int i = nrOfResults - 1; i >= 0; i--){
			if(edge.getLength() == resultLengths.get(i)){
				INode result = new NonTerminalNode(name, results.get(i));
				edge.addResult(result);
			}
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
		possiblySharedEdgeNodesMap = new HashMap<Integer, ArrayList<ParseStackNode>>();
		
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
			
			first.setStartLocation(location);
			
			stacksToExpand.add(first);
			possiblySharedExpects.add(first);
			possiblySharedExpectsEndNodes.add(current);
		}
	}
	
	private void expandStack(ParseStackNode node){
		if(node.isTerminal()){
			if((location + node.getLength() <= input.length) || (node.getLength() == 0)) todoList.add(node);
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
	
	public INode parse(String start){
		// Initialize.
		ParseStackNode rootNode = new NonTerminalParseStackNode(start, -1).getCleanCopy();
		rootNode.setStartLocation(0);
		stacksToExpand.add(rootNode);
		expand();
		
		do{
			findStacksToReduce();
			
			reduce();
			
			expand();
		}while(todoList.size() > 0);
		
		if(location != input.length) System.err.println("Parse Error before: "+location);
		
		return root.getResult();
	}
}
