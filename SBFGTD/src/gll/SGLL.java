package gll;

import gll.result.AbstractContainerNode;
import gll.result.AbstractNode;
import gll.result.ListContainerNode;
import gll.result.SortContainerNode;
import gll.result.struct.Link;
import gll.stack.AbstractStackNode;
import gll.stack.NonTerminalStackNode;
import gll.util.ArrayList;
import gll.util.DoubleRotatingQueue;
import gll.util.HashMap;
import gll.util.IntegerKeyedHashMap;
import gll.util.LinearIntegerKeyedMap;
import gll.util.RotatingQueue;

import java.lang.reflect.Method;

public class SGLL implements IGLL{
	private final char[] input;
	
	private final ArrayList<AbstractStackNode> todoList;
	
	// Updatable
	private final ArrayList<AbstractStackNode> stacksToExpand;
	private final RotatingQueue<AbstractStackNode> stacksWithTerminalsToReduce;
	private final DoubleRotatingQueue<AbstractStackNode, AbstractNode> stacksWithNonTerminalsToReduce;
	
	private final ArrayList<AbstractStackNode[]> lastExpects;
	private final LinearIntegerKeyedMap<AbstractStackNode> sharedLastExpects;
	private final LinearIntegerKeyedMap<AbstractStackNode> sharedPrefixNext;
	private final HashMap<String, ArrayList<AbstractStackNode>> cachedEdgesForExpect;
	
	private final IntegerKeyedHashMap<AbstractStackNode> sharedNextNodes;
	
	private final IntegerKeyedHashMap<HashMap<String, AbstractContainerNode>> resultStoreCache;
	
	private int previousLocation;
	private int location;
	
	private AbstractStackNode root;
	
	private final HashMap<String, Method> methodCache;
	
	public SGLL(char[] input){
		super();
		
		this.input = input;
		
		todoList = new ArrayList<AbstractStackNode>();
		
		stacksToExpand = new ArrayList<AbstractStackNode>();
		stacksWithTerminalsToReduce = new RotatingQueue<AbstractStackNode>();
		stacksWithNonTerminalsToReduce = new DoubleRotatingQueue<AbstractStackNode, AbstractNode>();
		
		lastExpects = new ArrayList<AbstractStackNode[]>();
		sharedLastExpects = new LinearIntegerKeyedMap<AbstractStackNode>();
		sharedPrefixNext = new LinearIntegerKeyedMap<AbstractStackNode>();
		cachedEdgesForExpect = new HashMap<String, ArrayList<AbstractStackNode>>();
		
		sharedNextNodes = new IntegerKeyedHashMap<AbstractStackNode>();
		
		resultStoreCache = new IntegerKeyedHashMap<HashMap<String, AbstractContainerNode>>();
		
		previousLocation = -1;
		location = 0;
		
		methodCache = new HashMap<String, Method>();
	}
	
	protected void expect(AbstractStackNode... symbolsToExpect){
		lastExpects.add(symbolsToExpect);
	}
	
	protected void invokeExpects(String name){
		Method method = methodCache.get(name);
		if(method == null){
			try{
				method = getClass().getMethod(name);
				try{
					method.setAccessible(true); // Try to bypass the 'isAccessible' check to save time.
				}catch(SecurityException sex){
					// Ignore this if it happens.
				}
			}catch(Exception ex){
				// Not going to happen.
				ex.printStackTrace(); // Temp
			}
			methodCache.putUnsafe(name, method);
		}
		
		try{
			method.invoke(this);
		}catch(Exception ex){
			// Not going to happen.
			ex.printStackTrace(); // Temp
		}
	}
	
	private AbstractStackNode updateNextNode(AbstractStackNode next, AbstractStackNode node, AbstractNode result){
		int id = next.getId();
		AbstractStackNode alternative = sharedNextNodes.get(id);
		if(alternative != null){
			alternative.updateNode(node, result);
			
			if(alternative.isEndNode()){
				if(result.isNullable() && !node.isMatchable() && !next.isMatchable() && node.getIdentifier() == next.getIdentifier()){
					if(alternative.getId() != node.getId()){ // List cycle fix.
						HashMap<String, AbstractContainerNode> levelResultStoreMap = resultStoreCache.get(location);
						AbstractContainerNode resultStore = levelResultStoreMap.get(alternative.getIdentifier());
						if(resultStore != null){
							// Encountered self recursive epsilon cycle; update the prefixes.
							updatePrefixes(node, resultStore);
						}
					}
				}
			}
			return alternative;
		}
		
		next = next.getCleanCopy();
		next.updateNode(node, result);
		next.setStartLocation(location);
		
		if(!next.isMatchable()){ // Is non-terminal or list.
			HashMap<String, AbstractContainerNode> levelResultStoreMap = resultStoreCache.get(location);
			if(levelResultStoreMap != null){
				AbstractContainerNode resultStore = levelResultStoreMap.get(next.getIdentifier());
				if(resultStore != null){ // Is nullable, queue for reduction.
					stacksWithNonTerminalsToReduce.put(next, resultStore);
				}
			}
		}
		
		sharedNextNodes.putUnsafe(id, next);
		stacksToExpand.add(next);
		return next;
	}
	
	private void updateAlternativeNextNode(AbstractStackNode next, LinearIntegerKeyedMap<ArrayList<AbstractStackNode>> edgesMap, ArrayList<Link>[] prefixesMap){
		int id = next.getId();
		AbstractStackNode alternative = sharedNextNodes.get(id);
		if(alternative != null){
			alternative.updatePrefixSharedNode(edgesMap, prefixesMap);
		}else{
			next = next.getCleanCopy();
			next.updatePrefixSharedNode(edgesMap, prefixesMap);
			next.setStartLocation(location);
			
			if(!next.isMatchable()){ // Is non-terminal or list.
				HashMap<String, AbstractContainerNode> levelResultStoreMap = resultStoreCache.get(location);
				if(levelResultStoreMap != null){
					AbstractContainerNode resultStore = levelResultStoreMap.get(next.getIdentifier());
					if(resultStore != null){ // Is nullable, queue for reduction.
						stacksWithNonTerminalsToReduce.put(next, resultStore);
					}
				}
			}
			
			sharedNextNodes.putUnsafe(id, next);
			stacksToExpand.add(next);
		}
	}
	
	private void updatePrefixes(AbstractStackNode node, AbstractContainerNode resultStore){
		LinearIntegerKeyedMap<ArrayList<AbstractStackNode>> edgesMap = node.getEdges();
		ArrayList<Link>[] prefixesMap = node.getPrefixesMap();
		
		for(int i = edgesMap.size() - 1; i >= 0; --i){
			int edgeStartLocation = edgesMap.getKey(i);
			
			ArrayList<Link> edgePrefixes = new ArrayList<Link>();
			Link prefix = constructPrefixesFor(prefixesMap, i, resultStore);
			edgePrefixes.add(prefix);
			
			ArrayList<AbstractStackNode> edgesPart = edgesMap.getValue(i);
			// Update one (because of sharing all will be updated).
			AbstractStackNode edge = edgesPart.get(0);
			HashMap<String, AbstractContainerNode> levelResultStoreMap = resultStoreCache.get(edgeStartLocation);
			levelResultStoreMap.get(edge.getIdentifier()).addAlternative(new Link(edgePrefixes, resultStore));
		}
	}
	
	private void updateEdges(AbstractStackNode node, AbstractNode result){
		LinearIntegerKeyedMap<ArrayList<AbstractStackNode>> edgesMap = node.getEdges();
		ArrayList<Link>[] prefixesMap = node.getPrefixesMap();
		
		for(int i = edgesMap.size() - 1; i >= 0; --i){
			int startLocation = edgesMap.getKey(i);
			ArrayList<AbstractStackNode> edgeList = edgesMap.getValue(i);
			
			AbstractStackNode edge = edgeList.get(0);
			String identifier = edge.getIdentifier();
			String nodeName = edge.getName();
			HashMap<String, AbstractContainerNode>  levelResultStoreMap = resultStoreCache.get(startLocation);
			AbstractContainerNode resultStore = null;
			if(levelResultStoreMap != null){
				resultStore = levelResultStoreMap.get(identifier);
			}else{
				levelResultStoreMap = new HashMap<String, AbstractContainerNode>();
				resultStoreCache.putUnsafe(startLocation, levelResultStoreMap);
			}
			Link resultLink = new Link((prefixesMap != null) ? prefixesMap[i] : null, result);
			if(resultStore != null){
				resultStore.addAlternative(resultLink);
			}else{
				resultStore = (!edge.isList()) ? new SortContainerNode(nodeName, startLocation == location, edge.isSeparator()) : new ListContainerNode(nodeName, startLocation == location, edge.isSeparator());
				levelResultStoreMap.putUnsafe(identifier, resultStore);
				resultStore.addAlternative(resultLink);
				
				stacksWithNonTerminalsToReduce.put(edge, resultStore);
				if(location == input.length && !edge.hasEdges()){
					root = edge; // Root reached.
				}
				
				for(int j = edgeList.size() - 1; j >= 1; --j){
					edge = edgeList.get(j);
					stacksWithNonTerminalsToReduce.put(edge, resultStore);
					if(location == input.length && !edge.hasEdges()){
						root = edge; // Root reached.
					}
				}
			}
		}
	}
	
	private void move(AbstractStackNode node, AbstractNode result){
		if(node.isEndNode()){
			updateEdges(node, result);
		}
		
		if(node.hasNext()){
			int nextDot = node.getDot() + 1;

			AbstractStackNode[] prod = node.getNext();
			AbstractStackNode next = prod[nextDot];
			next.setNext(prod);
			next = updateNextNode(next, node, result);
			
			ArrayList<AbstractStackNode[]> alternateProds = node.getAlternateNexts();
			if(alternateProds != null){
				int nextNextDot = nextDot + 1;
				
				// Handle alternative nexts (and prefix sharing).
				sharedPrefixNext.dirtyClear();
				
				sharedPrefixNext.add(next.getId(), next);
				
				LinearIntegerKeyedMap<ArrayList<AbstractStackNode>> edgesMap = next.getEdges();
				ArrayList<Link>[] prefixesMap = next.getPrefixesMap();
				
				for(int i = alternateProds.size() - 1; i >= 0; --i){
					prod = alternateProds.get(i);
					AbstractStackNode alternativeNext = prod[nextDot];
					int alternativeNextId = alternativeNext.getId();
					
					AbstractStackNode sharedNext = sharedPrefixNext.findValue(alternativeNextId);
					if(sharedNext == null){
						alternativeNext.setNext(prod);
						updateAlternativeNextNode(alternativeNext, edgesMap, prefixesMap);
						
						sharedPrefixNext.add(alternativeNextId, alternativeNext);
					}else if(nextNextDot < prod.length){
						if(sharedNext.hasNext()){
							sharedNext.addNext(prod);
						}else{
							sharedNext.setNext(prod);
						}
					}
				}
			}
		}
	}
	
	private Link constructPrefixesFor(ArrayList<Link>[] prefixesMap, int index, AbstractNode result){
		if(prefixesMap == null){
			return new Link(null, result);
		}
		
		return new Link(prefixesMap[index], result);
	}
	
	private void reduceTerminal(AbstractStackNode terminal){
		if(!terminal.match(input)) return;
		
		move(terminal, terminal.getResult());
	}
	
	private void reduceNonTerminal(AbstractStackNode nonTerminal, AbstractNode result){
		move(nonTerminal, result);
	}
	
	private void reduce(){
		if(previousLocation != location){ // Epsilon fix.
			sharedNextNodes.clear();
			resultStoreCache.clear();
		}
		
		// Reduce terminals.
		while(!stacksWithTerminalsToReduce.isEmpty()){
			AbstractStackNode terminal = stacksWithTerminalsToReduce.getDirtyUnsafe();
			reduceTerminal(terminal);

			todoList.remove(terminal);
		}
		
		// Reduce non-terminals.
		while(!stacksWithNonTerminalsToReduce.isEmpty()){
			reduceNonTerminal(stacksWithNonTerminalsToReduce.peekFirstUnsafe(), stacksWithNonTerminalsToReduce.getSecondDirtyUnsafe());
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
	
	private boolean shareListNode(int id, AbstractStackNode stack){
		AbstractStackNode sharedNode = sharedNextNodes.get(id);
		if(sharedNode != null){
			sharedNode.addEdgeWithPrefix(stack, null, location);
			return true;
		}
		return false;
	}
	
	private void handleExpects(AbstractStackNode stackBeingWorkedOn){
		sharedLastExpects.dirtyClear();
		
		ArrayList<AbstractStackNode> cachedEdges = null;
		
		for(int i = lastExpects.size() - 1; i >= 0; --i){
			AbstractStackNode[] expectedNodes = lastExpects.get(i);
			
			expectedNodes[expectedNodes.length - 1].markAsEndNode(); // Meh.
			
			AbstractStackNode first = expectedNodes[0];
			
			// Handle prefix sharing.
			int firstId = first.getId();
			AbstractStackNode sharedNode;
			if((sharedNode = sharedLastExpects.findValue(firstId)) != null){
				sharedNode.addNext(expectedNodes);
				continue;
			}
			
			first = first.getCleanCopy();
			first.setStartLocation(location);
			first.setNext(expectedNodes);
			first.initEdges();
			if(cachedEdges == null){
				cachedEdges = first.addEdge(stackBeingWorkedOn);
			}else{
				first.addEdges(cachedEdges, location);
			}
			
			sharedLastExpects.add(firstId, first);
			
			stacksToExpand.add(first);
		}
		
		cachedEdgesForExpect.put(stackBeingWorkedOn.getName(), cachedEdges);
	}
	
	private void expandStack(AbstractStackNode node){
		if(node.isMatchable()){
			if((location + node.getLength()) <= input.length) todoList.add(node);
			return;
		}
		
		if(!node.isList()){
			ArrayList<AbstractStackNode> cachedEdges = cachedEdgesForExpect.get(node.getName());
			if(cachedEdges != null){
				cachedEdges.add(node);
			}else{
				invokeExpects(node.getMethodName());
				handleExpects(node);
			}
		}else{ // 'List'
			AbstractStackNode[] listChildren = node.getChildren();
			
			AbstractStackNode child = listChildren[0];
			int childId = child.getId();
			if(!shareListNode(childId, node)){
				child = child.getCleanCopy();
				
				sharedNextNodes.putUnsafe(childId, child);
				
				child.setStartLocation(location);
				child.initEdges();
				child.addEdgeWithPrefix(node, null, location);
				
				stacksToExpand.add(child);
			}
			
			if(listChildren.length > 1){ // Star list or optional.
				// This is always epsilon (and unique for this position); so shouldn't be shared.
				AbstractStackNode empty = listChildren[1].getCleanCopy();
				empty.setStartLocation(location);
				empty.initEdges();
				empty.addEdge(node);
				
				stacksToExpand.add(empty);
			}
		}
	}
	
	private void expand(){
		if(previousLocation != location){
			cachedEdgesForExpect.clear();
		}
		while(stacksToExpand.size() > 0){
			lastExpects.dirtyClear();
			expandStack(stacksToExpand.remove(stacksToExpand.size() - 1));
		}
	}
	
	protected boolean isAtEndOfInput(){
		return (location == input.length);
	}
	
	protected boolean isInLookAhead(char[][] ranges, char[] characters){
		if(location == input.length) return false;
		
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
		AbstractStackNode rootNode = new NonTerminalStackNode(AbstractStackNode.START_SYMBOL_ID, 0, start);
		rootNode.initEdges();
		rootNode.setStartLocation(0);
		stacksToExpand.add(rootNode);
		expand();
		
		do{
			findStacksToReduce();
			
			reduce();
			
			expand();
		}while(todoList.size() > 0);
		
		if(root == null) throw new RuntimeException("Parse Error before: "+(location == Integer.MAX_VALUE ? 0 : location));
		
		HashMap<String, AbstractContainerNode> levelResultStoreMap = resultStoreCache.get(0);
		return levelResultStoreMap.get(start);
	}
}
