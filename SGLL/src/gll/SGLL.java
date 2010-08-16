package gll;

import gll.result.AbstractNode;
import gll.result.ContainerNode;
import gll.result.struct.Link;
import gll.stack.AbstractStackNode;
import gll.stack.NonTerminalStackNode;
import gll.util.ArrayList;
import gll.util.IntegerKeyedHashMap;
import gll.util.LinearIntegerKeyedMap;
import gll.util.ObjectIntegerKeyedHashMap;
import gll.util.RotatingQueue;

import java.lang.reflect.Method;

public class SGLL implements IGLL{
	private final char[] input;
	
	private final ArrayList<AbstractStackNode> todoList;
	
	// Updatable
	private final ArrayList<AbstractStackNode> stacksToExpand;
	private final RotatingQueue<AbstractStackNode> stacksWithTerminalsToReduce;
	private final RotatingQueue<AbstractStackNode> stacksWithNonTerminalsToReduce;
	private final ArrayList<AbstractStackNode[]> lastExpects;
	private final ArrayList<AbstractStackNode> possiblySharedExpects;
	private final ArrayList<AbstractStackNode> possiblySharedNextNodes;
	private final IntegerKeyedHashMap<ArrayList<AbstractStackNode>> possiblySharedEdgeNodesMap;
	
	private final ObjectIntegerKeyedHashMap<String, ContainerNode> resultStoreCache;
	
	private int previousLocation;
	private int location;
	
	private boolean nullableEncountered;
	
	private AbstractStackNode root;
	
	public SGLL(char[] input){
		super();
		
		this.input = input;
		
		todoList = new ArrayList<AbstractStackNode>();
		
		stacksToExpand = new ArrayList<AbstractStackNode>();
		stacksWithTerminalsToReduce = new RotatingQueue<AbstractStackNode>();
		stacksWithNonTerminalsToReduce = new RotatingQueue<AbstractStackNode>();
		
		lastExpects = new ArrayList<AbstractStackNode[]>();
		possiblySharedExpects = new ArrayList<AbstractStackNode>();
		
		possiblySharedNextNodes = new ArrayList<AbstractStackNode>();
		possiblySharedEdgeNodesMap = new IntegerKeyedHashMap<ArrayList<AbstractStackNode>>();
		
		resultStoreCache = new ObjectIntegerKeyedHashMap<String, ContainerNode>();
		
		previousLocation = -1;
		location = 0;
	}
	
	protected void expect(AbstractStackNode... symbolsToExpect){
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
	
	private void updateNextNode(AbstractStackNode next, AbstractStackNode node){
		LinearIntegerKeyedMap<ArrayList<AbstractStackNode>> edges = node.getEdges();
		AbstractNode result = node.getResult();
		
		for(int i = possiblySharedNextNodes.size() - 1; i >= 0; --i){
			AbstractStackNode possibleAlternative = possiblySharedNextNodes.get(i);
			if(possibleAlternative.isSimilar(next)){
				possibleAlternative.addEdges(edges);
				addPrefixes(possibleAlternative, node, result);
				
				if(next.isEndNode()){
					if(!possibleAlternative.isClean() && possibleAlternative.getStartLocation() == location){
						// Something horrible happened; update the prefixes.
						if(possibleAlternative != node){ // List cycle fix.
							updatePrefixes(possibleAlternative, node, edges, result);
						}
					}
				}
				return;
			}
		}
		
		if(next.startLocationIsSet()){
			next = next.getCleanCopyWithMark();
		}
		
		next.setStartLocation(location);
		next.addEdges(edges);
		possiblySharedNextNodes.add(next);
		stacksToExpand.add(next);
		
		addPrefixes(next, node, result);
	}
	
	private void addPrefixes(AbstractStackNode next, AbstractStackNode node, AbstractNode result){
		LinearIntegerKeyedMap<ArrayList<Link>> prefixesMap = node.getPrefixesMap();
		
		if(prefixesMap == null){
			next.addPrefix(new Link(null, result), node.getStartLocation());
		}else{
			int nrOfPrefixes = prefixesMap.size();
			for(int i = nrOfPrefixes - 1; i >= 0; --i){
				next.addPrefix(new Link(prefixesMap.getValue(i), result), prefixesMap.getKey(i));
			}
		}
	}
	
	private void updatePrefixes(AbstractStackNode next, AbstractStackNode node, LinearIntegerKeyedMap<ArrayList<AbstractStackNode>> edges, AbstractNode result){
		LinearIntegerKeyedMap<ArrayList<Link>> prefixesMap = node.getPrefixesMap();
		
		// Update results (if necessary).
		for(int i = edges.size() - 1; i >= 0; --i){
			int startLocation = edges.getKey(i);
			ArrayList<AbstractStackNode> edgesPart = edges.getValue(i);
			for(int j = edgesPart.size() - 1; j >= 0; --j){
				AbstractStackNode edge = edgesPart.get(j);
				
				if(edge.isMarkedAsWithResults()){
					Link prefix = constructPrefixesFor(prefixesMap, result, startLocation);
					if(prefix != null){
						ArrayList<Link> edgePrefixes = new ArrayList<Link>();
						edgePrefixes.add(prefix);
						ContainerNode resultStore = edge.getResultStore();
						resultStore.addAlternative(new Link(edgePrefixes, next.getResult()));
					}
				}
			}
		}
	}
	
	private boolean updateEdgeNode(AbstractStackNode node, ArrayList<Link> prefixes, AbstractNode result){
		int startLocation = node.getStartLocation();
		ArrayList<AbstractStackNode> possiblySharedEdgeNodes = possiblySharedEdgeNodesMap.get(startLocation);
		if(possiblySharedEdgeNodes != null){
			for(int i = possiblySharedEdgeNodes.size() - 1; i >= 0; --i){
				AbstractStackNode possibleAlternative = possiblySharedEdgeNodes.get(i);
				if(possibleAlternative.isSimilar(node)){
					if(possibleAlternative.isMarkedAsWithResults()){
						ContainerNode resultStore = possibleAlternative.getResultStore();
						resultStore.addAlternative(new Link(prefixes, result));
						return true;
					}
					return false;
				}
			}
		}else{
			possiblySharedEdgeNodes = new ArrayList<AbstractStackNode>();
			possiblySharedEdgeNodesMap.unsafePut(startLocation, possiblySharedEdgeNodes);
		}
		
		if(!node.isClean()){
			node = node.getCleanCopyWithPrefix();
		}
		
		String nodeName = node.getName();
		ContainerNode resultStore = resultStoreCache.get(nodeName, startLocation);
		if(resultStore == null){
			resultStore = new ContainerNode(nodeName, node.isList());
			resultStoreCache.unsafePut(nodeName, startLocation, resultStore);
			node.markAsWithResults();
			
			resultStore.addAlternative(new Link(prefixes, result));
		}
		node.setResultStore(resultStore);
		
		if(location == input.length && !node.hasEdges()){
			root = node; // Root reached.
		}
		
		possiblySharedEdgeNodes.add(node);
		stacksWithNonTerminalsToReduce.put(node);
		
		return false;
	}
	
	private void move(AbstractStackNode node){
		if(node.isEndNode()){
			LinearIntegerKeyedMap<ArrayList<AbstractStackNode>> edges = node.getEdges();
			LinearIntegerKeyedMap<ArrayList<Link>> prefixesMap = node.getPrefixesMap();
			AbstractNode result = node.getResult();
			
			for(int i = edges.size() - 1; i >= 0; --i){
				ArrayList<Link> prefixes = null;
				if(prefixesMap != null){
					prefixes = prefixesMap.findValue(edges.getKey(i));
				}
				
				//boolean ok = false;
				ArrayList<AbstractStackNode> edgeList = edges.getValue(i);
				for(int j = edgeList.size() - 1; j >= 0; --j){
					AbstractStackNode edge = edgeList.get(j);
					if(updateEdgeNode(edge, prefixes, result)) break;
				}
			}
		}
		
		AbstractStackNode next;
		if((next = node.getNext()) != null){
			updateNextNode(next, node);
		}
	}
	
	private void moveNullable(AbstractStackNode node, AbstractStackNode edge){
		nullableEncountered = true;
		
		LinearIntegerKeyedMap<ArrayList<Link>> prefixesMap = node.getPrefixesMap();
		ArrayList<Link> prefixes = null;
		if(prefixesMap != null){
			prefixes = prefixesMap.findValue(location);
		}
		
		updateEdgeNode(edge, prefixes, node.getResult());
	}
	
	private Link constructPrefixesFor(LinearIntegerKeyedMap<ArrayList<Link>> prefixesMap, AbstractNode result, int startLocation){
		if(prefixesMap == null){
			return new Link(null, result);
		}
		
		ArrayList<Link> prefixes = prefixesMap.findValue(startLocation);
		if(prefixes != null){
			return new Link(prefixes, result);
		}
		return null;
	}
	
	private void reduceTerminal(AbstractStackNode terminal){
		if(!terminal.reduce(input)) return;
		
		move(terminal);
	}
	
	private void reduceNonTerminal(AbstractStackNode nonTerminal){
		move(nonTerminal);
		
		/*System.out.println(nonTerminal);
		LinearIntegerKeyedMap<ArrayList<AbstractStackNode>> edgesMap = nonTerminal.getEdges();
		for(int i = edgesMap.size() - 1; i >= 0; --i){
			ArrayList<AbstractStackNode> edges = edgesMap.getValue(i);
			for(int j = edges.size() - 1; j >= 0; --j){
				AbstractStackNode edge = edges.get(j);
				System.out.println("\t"+edge);
			}
		}
		System.out.println();*/
	}
	
	private void reduce(){
		if(previousLocation != location){ // Epsilon fix.
			possiblySharedNextNodes.clear();
			possiblySharedEdgeNodesMap.clear();
			resultStoreCache.clear();
		}
		
		// Reduce terminals.
		while(!stacksWithTerminalsToReduce.isEmpty()){
			AbstractStackNode terminal = stacksWithTerminalsToReduce.unsafeGet();
			reduceTerminal(terminal);

			todoList.remove(terminal);
		}
		
		// Reduce non-terminals.
		while(!stacksWithNonTerminalsToReduce.isEmpty()){
			reduceNonTerminal(stacksWithNonTerminalsToReduce.unsafeGet());
		}
	}
	
	private void findStacksToReduce(){
		// Find the stacks that will progress the least.
		int closestNextLocation = Integer.MAX_VALUE;
		for(int i = todoList.size() - 1; i >= 0; --i){
			AbstractStackNode node = todoList.get(i);
			int nextLocation = node.getStartLocation() + node.getLength();
			if(nextLocation < closestNextLocation){
				stacksWithTerminalsToReduce.clear();
				stacksWithTerminalsToReduce.put(node);
				closestNextLocation = nextLocation;
			}else if(nextLocation == closestNextLocation){
				stacksWithTerminalsToReduce.put(node);
			}
		}
		
		previousLocation = location;
		location = closestNextLocation;
	}
	
	private boolean shareNode(AbstractStackNode node, AbstractStackNode stack){
		if(!node.isEpsilon()){
			for(int j = possiblySharedExpects.size() - 1; j >= 0; --j){
				AbstractStackNode possiblySharedNode = possiblySharedExpects.get(j);
				if(possiblySharedNode.isSimilar(node)){
					if(!possiblySharedNode.isClean()){ // Is nullable.
						AbstractStackNode last;
						AbstractStackNode next = possiblySharedNode;
						do{
							last = next;
						}while((next = next.getNext()) != null);
						moveNullable(last, stack);
					}
					possiblySharedNode.addEdge(stack);
					return true;
				}
			}
		}
		return false;
	}
	
	private void handleExpects(AbstractStackNode stackBeingWorkedOn){
		for(int i = lastExpects.size() - 1; i >= 0; --i){
			AbstractStackNode[] expectedNodes = lastExpects.get(i);
			int numberOfNodes = expectedNodes.length;
			AbstractStackNode first = expectedNodes[0];
			
			// Handle sharing (and loops).
			if(!shareNode(first, stackBeingWorkedOn)){
				AbstractStackNode next = expectedNodes[numberOfNodes - 1].getCleanCopy();
				next.markAsEndNode();
				
				for(int k = numberOfNodes - 2; k >= 0; --k){
					AbstractStackNode current = expectedNodes[k].getCleanCopy();
					current.addNext(next);
					next = current;
				}
				
				next.addEdge(stackBeingWorkedOn);
				
				next.setStartLocation(location);
				
				stacksToExpand.add(next);
				possiblySharedExpects.add(next);
			}
		}
	}
	
	private void expandStack(AbstractStackNode node){
		if(node.isReducable()){
			if((location + node.getLength()) <= input.length) todoList.add(node);
			return;
		}
		
		if(!node.isList()){
			callMethod(node.getMethodName());
			
			handleExpects(node);
		}else{ // List
			AbstractStackNode[] listChildren = node.getChildren();
			
			AbstractStackNode child = listChildren[0];
			if(!shareNode(child, node)){
				stacksToExpand.add(child);
				possiblySharedExpects.add(child);
				possiblySharedNextNodes.add(child); // For epsilon list cycles.
			}
			
			if(listChildren.length > 1){ // Star list or optional.
				// This is always epsilon; so shouldn't be shared.
				stacksToExpand.add(listChildren[1]);
			}
		}
	}
	
	private void expand(){
		if(previousLocation != location){
			possiblySharedExpects.clear();
		}
		while(stacksToExpand.size() > 0){
			lastExpects.dirtyClear();
			expandStack(stacksToExpand.remove(stacksToExpand.size() - 1));
		}
	}
	
	protected boolean isInLookAhead(char[][] ranges, char[] characters){
		char next = input[location];
		for(int i = ranges.length - 1; i >= 0; --i){
			char[] range = ranges[i];
			if(next >= range[0] && next <= range[1]) return true;
		}
		
		for(int i = characters.length - 1; i >= 0; --i){
			if(next == characters[i]) return true;
		}
		
		return false;
	}
	
	public AbstractNode parse(String start){
		// Initialize.
		AbstractStackNode rootNode = new NonTerminalStackNode(START_SYMBOL_ID, start);
		rootNode.initNoEdges();
		rootNode.setStartLocation(0);
		stacksToExpand.add(rootNode);
		expand();
		
		do{
			if(!nullableEncountered) findStacksToReduce();

			nullableEncountered = false;
			reduce();
			
			if(!nullableEncountered) expand();
		}while((todoList.size() > 0) || nullableEncountered);
		
		if(root == null) throw new RuntimeException("Parse Error before: "+(location == Integer.MAX_VALUE ? 0 : location));
		
		return root.getResult();
	}
}
