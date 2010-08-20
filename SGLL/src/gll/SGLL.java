package gll;

import gll.result.AbstractNode;
import gll.result.ContainerNode;
import gll.result.struct.Link;
import gll.stack.AbstractStackNode;
import gll.stack.NonTerminalStackNode;
import gll.util.ArrayList;
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
						if(possibleAlternative != node){ // List cycle fix.
							// Encountered self recursive epsilon cycle; update the prefixes.
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
		addPrefixes(next, node, result);
		
		if(!next.isReducable()){ // Is non-terminal or list.
			ContainerNode resultStore = resultStoreCache.get(next.getIdentifier(), location);
			if(resultStore != null){ // Is nullable, add the known results.
				next.setResultStore(resultStore);
				stacksWithNonTerminalsToReduce.put(next);
			}
		}
		
		possiblySharedNextNodes.add(next);
		stacksToExpand.add(next);
	}
	
	private void updatePrefixes(AbstractStackNode next, AbstractStackNode node, LinearIntegerKeyedMap<ArrayList<AbstractStackNode>> edgesMap, AbstractNode result){
		ArrayList<Link>[] prefixesMap = node.getPrefixesMap();
		
		// Update results (if necessary).
		for(int i = edgesMap.size() - 1; i >= 0; --i){
			int startLocation = edgesMap.getKey(i);
			ArrayList<AbstractStackNode> edgesPart = edgesMap.getValue(i);
			
			// Update one (because of sharing all will be updated).
			AbstractStackNode edge = edgesPart.get(0);
			ArrayList<Link> edgePrefixes = new ArrayList<Link>();
			Link prefix = constructPrefixesFor(edgesMap, prefixesMap, result, startLocation);
			edgePrefixes.add(prefix);
			ContainerNode resultStore = edge.getResultStore();
			resultStore.addAlternative(new Link(edgePrefixes, next.getResult()));
		}
	}
	
	private void addPrefixes(AbstractStackNode next, AbstractStackNode node, AbstractNode result){
		LinearIntegerKeyedMap<ArrayList<AbstractStackNode>> edgesMap = node.getEdges();
		ArrayList<Link>[] prefixesMap = node.getPrefixesMap();
		
		if(prefixesMap == null){
			next.addPrefix(new Link(null, result), node.getStartLocation());
		}else{
			int nrOfPrefixes = edgesMap.size();
			for(int i = nrOfPrefixes - 1; i >= 0; --i){
				next.addPrefix(new Link(prefixesMap[i], result), edgesMap.getKey(i));
			}
		}
	}
	
	private void updateEdges(AbstractStackNode node){
		LinearIntegerKeyedMap<ArrayList<AbstractStackNode>> edgesMap = node.getEdges();
		ArrayList<Link>[] prefixesMap = node.getPrefixesMap();
		AbstractNode result = node.getResult();
		
		for(int i = edgesMap.size() - 1; i >= 0; --i){
			int startLocation = edgesMap.getKey(i);
			ArrayList<AbstractStackNode> edgeList = edgesMap.getValue(i);
			
			AbstractStackNode edge = edgeList.get(0);
			String identifier = edge.getIdentifier();
			String nodeName = edge.getName();
			ContainerNode resultStore = resultStoreCache.get(identifier, startLocation);
			Link resultLink = new Link((prefixesMap != null) ? prefixesMap[i] : null, result);
			if(resultStore != null){
				resultStore.addAlternative(resultLink);
			}else{
				resultStore = new ContainerNode(nodeName, edge.isList());
				resultStoreCache.unsafePut(identifier, startLocation, resultStore);
				resultStore.addAlternative(resultLink);
				
				if(!edge.isClean()){
					edge = edge.getCleanCopyWithPrefix();
				}
				edge.setResultStore(resultStore);
				stacksWithNonTerminalsToReduce.put(edge);
				if(location == input.length && !edge.hasEdges()){
					root = edge; // Root reached.
				}
				
				for(int j = edgeList.size() - 1; j >= 1; --j){
					edge = edgeList.get(j);
					if(!edge.isClean()){
						edge = edge.getCleanCopyWithPrefix();
					}
					edge.setResultStore(resultStore);
					stacksWithNonTerminalsToReduce.put(edge);
					if(location == input.length && !edge.hasEdges()){
						root = edge; // Root reached.
					}
				}
			}
		}
	}
	
	private void move(AbstractStackNode node){
		if(node.isEndNode()){
			updateEdges(node);
		}
		
		AbstractStackNode next;
		if((next = node.getNext()) != null){
			updateNextNode(next, node);
		}
	}
	
	private void moveNullable(AbstractStackNode node, AbstractStackNode edge){
		nullableEncountered = true;
		
		LinearIntegerKeyedMap<ArrayList<AbstractStackNode>> edgesMap = node.getEdges();
		ArrayList<Link>[] prefixesMap = node.getPrefixesMap();
		ArrayList<Link> prefixes = null;
		if(prefixesMap != null){
			prefixes = prefixesMap[edgesMap.findKey(location)];
		}
		
		ContainerNode resultStore = resultStoreCache.get(edge.getIdentifier(), location);
		resultStore.addAlternative(new Link(prefixes, node.getResult()));
	}
	
	private Link constructPrefixesFor(LinearIntegerKeyedMap<ArrayList<AbstractStackNode>> edgesMap, ArrayList<Link>[] prefixesMap, AbstractNode result, int startLocation){
		if(prefixesMap == null){
			return new Link(null, result);
		}
		
		int index = edgesMap.findKey(startLocation);
		return new Link(prefixesMap[index], result);
	}
	
	private void reduceTerminal(AbstractStackNode terminal){
		if(!terminal.reduce(input)) return;
		
		move(terminal);
	}
	
	private void reduceNonTerminal(AbstractStackNode nonTerminal){
		move(nonTerminal);
	}
	
	private void reduce(){
		if(previousLocation != location){ // Epsilon fix.
			possiblySharedNextNodes.clear();
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
		for(int j = possiblySharedExpects.size() - 1; j >= 0; --j){
			AbstractStackNode possiblySharedNode = possiblySharedExpects.get(j);
			if(possiblySharedNode.isSimilar(node)){
				possiblySharedNode.addEdge(stack);
				return true;
			}
		}
		return false;
	}
	
	private boolean shareListNode(AbstractStackNode node, AbstractStackNode stack){
		for(int j = possiblySharedNextNodes.size() - 1; j >= 0; --j){
			AbstractStackNode possiblySharedNode = possiblySharedNextNodes.get(j);
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
				possiblySharedNode.addPrefix(null, location);
				return true;
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
		}else{ // 'List'
			AbstractStackNode[] listChildren = node.getChildren();
			
			AbstractStackNode child = listChildren[0];
			if(!shareListNode(child, node)){
				stacksToExpand.add(child);
				possiblySharedNextNodes.add(child); // For epsilon list cycles.
			}
			
			if(listChildren.length > 1){ // Star list or optional.
				// This is always epsilon (and unique for this position); so shouldn't be shared.
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
