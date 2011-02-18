package gtd;

import gtd.result.AbstractContainerNode;
import gtd.result.AbstractNode;
import gtd.result.ListContainerNode;
import gtd.result.SortContainerNode;
import gtd.result.struct.Link;
import gtd.stack.AbstractStackNode;
import gtd.stack.NonTerminalStackNode;
import gtd.util.ArrayList;
import gtd.util.DoubleStack;
import gtd.util.HashMap;
import gtd.util.IntegerKeyedHashMap;
import gtd.util.LinearIntegerKeyedMap;
import gtd.util.Stack;

import java.lang.reflect.Method;

public class SGTDBF implements IGTD{
	private final char[] input;
	
	private final Stack<AbstractStackNode>[] todoLists;
	
	// Updatable
	private final ArrayList<AbstractStackNode> stacksToExpand;
	private Stack<AbstractStackNode> stacksWithTerminalsToReduce;
	private final DoubleStack<AbstractStackNode, AbstractNode> stacksWithNonTerminalsToReduce;
	
	private final ArrayList<AbstractStackNode[]> lastExpects;
	private final LinearIntegerKeyedMap<AbstractStackNode> sharedLastExpects;
	private final LinearIntegerKeyedMap<AbstractStackNode> sharedPrefixNext;
	private final HashMap<String, ArrayList<AbstractStackNode>> cachedEdgesForExpect;
	
	private final IntegerKeyedHashMap<AbstractStackNode> sharedNextNodes;
	
	private final IntegerKeyedHashMap<HashMap<String, AbstractContainerNode>> resultStoreCache;
	
	private int location;
	private boolean shiftedLevel;
	
	private final HashMap<String, Method> methodCache;
	
	public SGTDBF(char[] input){
		super();
		
		this.input = input;
		
		todoLists = (Stack<AbstractStackNode>[]) new Stack[input.length + 1];
		
		stacksToExpand = new ArrayList<AbstractStackNode>();
		stacksWithTerminalsToReduce = new Stack<AbstractStackNode>();
		stacksWithNonTerminalsToReduce = new DoubleStack<AbstractStackNode, AbstractNode>();
		
		lastExpects = new ArrayList<AbstractStackNode[]>();
		sharedLastExpects = new LinearIntegerKeyedMap<AbstractStackNode>();
		sharedPrefixNext = new LinearIntegerKeyedMap<AbstractStackNode>();
		cachedEdgesForExpect = new HashMap<String, ArrayList<AbstractStackNode>>();
		
		sharedNextNodes = new IntegerKeyedHashMap<AbstractStackNode>();
		
		resultStoreCache = new IntegerKeyedHashMap<HashMap<String, AbstractContainerNode>>();
		
		location = 0;
		shiftedLevel = false;
		
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
		AbstractStackNode alternative = sharedNextNodes.get(next.getId());
		if(alternative != null){
			if(result.isEmpty()){
				if(alternative.getId() != node.getId() && !(alternative.isSeparator() || node.isSeparator())){ // (Separated) list cycle fix.
					HashMap<String, AbstractContainerNode> levelResultStoreMap = resultStoreCache.get(location);
					AbstractContainerNode resultStore = levelResultStoreMap.get(alternative.getIdentifier());
					if(resultStore != null){
						// Encountered stack 'overtake'.
						propagateEdgesAndPrefixes(node, result, alternative, resultStore);
						return alternative;
					}
				}
			}
			
			alternative.updateNode(node, result);
			
			return alternative;
		}
		
		next = next.getCleanCopy();
		next.updateNode(node, result);
		next.setStartLocation(location);
		
		sharedNextNodes.putUnsafe(next.getId(), next);
		stacksToExpand.add(next);
		return next;
	}
	
	// TODO Fix stack 'overtakes'.
	private void updateAlternativeNextNode(AbstractStackNode next, AbstractStackNode node, AbstractNode result, LinearIntegerKeyedMap<ArrayList<AbstractStackNode>> edgesMap, ArrayList<Link>[] prefixesMap){
		AbstractStackNode alternative = sharedNextNodes.get(next.getId());
		if(alternative != null){
			if(result.isEmpty()){
				if(alternative.getId() != node.getId() && !(alternative.isSeparator() || node.isSeparator())){ // (Separated) list cycle fix.
					HashMap<String, AbstractContainerNode> levelResultStoreMap = resultStoreCache.get(location);
					AbstractContainerNode resultStore = levelResultStoreMap.get(alternative.getIdentifier());
					if(resultStore != null){
						// Encountered stack 'overtake'.
						propagateAlternativeEdgesAndPrefixes(node, result, alternative, resultStore, edgesMap, prefixesMap);
						return;
					}
				}
			}
			
			alternative.updatePrefixSharedNode(edgesMap, prefixesMap);
		}else{
			next = next.getCleanCopy();
			next.updatePrefixSharedNode(edgesMap, prefixesMap);
			next.setStartLocation(location);
			
			sharedNextNodes.putUnsafe(next.getId(), next);
			stacksToExpand.add(next);
		}
	}
	
	private void propagateReductions(AbstractStackNode node, AbstractNode nodeResultStore, AbstractStackNode next, AbstractNode nextResultStore){
		LinearIntegerKeyedMap<ArrayList<AbstractStackNode>> edgesMap = node.getEdges();
		ArrayList<Link>[] prefixes = node.getPrefixesMap();
		
		for(int i = edgesMap.size() - 1; i >= 0; --i){
			int startPosition = edgesMap.getKey(i);
			ArrayList<AbstractStackNode> edgesPart = edgesMap.getValue(i);
			
			ArrayList<Link> edgePrefixes = new ArrayList<Link>();
			Link prefix = constructPrefixesFor(edgesMap, prefixes, nodeResultStore, startPosition);
			edgePrefixes.add(prefix);
			
			// Update one (because of sharing all will be updated).
			AbstractStackNode edge = edgesPart.get(0);
			HashMap<String, AbstractContainerNode> levelResultStoreMap = resultStoreCache.get(startPosition);
			String identifier = edge.getIdentifier();
			AbstractContainerNode resultStore = levelResultStoreMap.get(identifier);
			if(resultStore == null){ // If there are no previous reductions, handle this.
				String nodeName = edge.getName();
				int startLocation = edge.getStartLocation();
				resultStore = (!edge.isList()) ? new SortContainerNode(nodeName, startLocation == location, edge.isSeparator()) : new ListContainerNode(nodeName, startLocation == location, edge.isSeparator());
				levelResultStoreMap.putUnsafe(identifier, resultStore);
				
				stacksWithNonTerminalsToReduce.push(edge, resultStore);
				
				for(int j = edgesPart.size() - 1; j >= 1; --j){
					edge = edgesPart.get(j);
					stacksWithNonTerminalsToReduce.push(edge, resultStore);
				}
			}
			resultStore.addAlternative(new Link(edgePrefixes, nextResultStore));
		}
	}
	
	private void propagateEdgesAndPrefixes(AbstractStackNode node, AbstractNode nodeResult, AbstractStackNode next, AbstractNode nextResult){
		if(next.isEndNode()){
			propagateReductions(node, nodeResult, next, nextResult);
			return;
		}
		
		
		
		
		// TODO Implement.
	}
	
	private void propagateAlternativeEdgesAndPrefixes(AbstractStackNode node, AbstractNode nodeResult, AbstractStackNode next, AbstractNode nextResult, LinearIntegerKeyedMap<ArrayList<AbstractStackNode>> edgesMap, ArrayList<Link>[] prefixesMap){
		if(next.isEndNode()){
			propagateReductions(node, nodeResult, next, nextResult);
			return;
		}
		
		
		
		
		// TODO Implement.
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
				
				stacksWithNonTerminalsToReduce.push(edge, resultStore);
				
				for(int j = edgeList.size() - 1; j >= 1; --j){
					edge = edgeList.get(j);
					stacksWithNonTerminalsToReduce.push(edge, resultStore);
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

			AbstractStackNode[] prod = node.getProduction();
			AbstractStackNode next = prod[nextDot];
			next.setProduction(prod);
			next = updateNextNode(next, node, result);
			
			ArrayList<AbstractStackNode[]> alternateProds = node.getAlternateProductions();
			if(alternateProds != null){
				int nextNextDot = nextDot + 1;
				
				// Handle alternative nexts (and prefix sharing).
				sharedPrefixNext.dirtyClear();
				
				sharedPrefixNext.add(next.getId(), next);
				
				LinearIntegerKeyedMap<ArrayList<AbstractStackNode>> edgesMap = next.getEdges();
				ArrayList<Link>[] prefixesMap = next.getPrefixesMap();
				
				for(int i = alternateProds.size() - 1; i >= 0; --i){
					prod = alternateProds.get(i);
					if(nextDot == prod.length) continue;
					AbstractStackNode alternativeNext = prod[nextDot];
					int alternativeNextId = alternativeNext.getId();
					
					AbstractStackNode sharedNext = sharedPrefixNext.findValue(alternativeNextId);
					if(sharedNext == null){
						alternativeNext.setProduction(prod);
						updateAlternativeNextNode(alternativeNext, node, result, edgesMap, prefixesMap);
						
						sharedPrefixNext.add(alternativeNextId, alternativeNext);
					}else if(nextNextDot < prod.length){
						if(alternativeNext.isEndNode()) sharedNext.markAsEndNode();
						
						sharedNext.addProduction(prod);
					}
				}
			}
		}
	}
	
	private Link constructPrefixesFor(LinearIntegerKeyedMap<ArrayList<AbstractStackNode>> edgesMap, ArrayList<Link>[] prefixesMap, AbstractNode result, int startLocation){
		if(prefixesMap == null){
			return new Link(null, result);
		}
		
		int index = edgesMap.findKey(startLocation);
		return new Link(prefixesMap[index], result);
	}
	
	private void reduceTerminal(AbstractStackNode terminal){
		move(terminal, terminal.getResult());
	}
	
	private void reduceNonTerminal(AbstractStackNode nonTerminal, AbstractNode result){
		move(nonTerminal, result);
	}
	
	private void reduce(){
		// Reduce terminals.
		while(!stacksWithTerminalsToReduce.isEmpty()){
			reduceTerminal(stacksWithTerminalsToReduce.pop());
		}
		
		// Reduce non-terminals.
		while(!stacksWithNonTerminalsToReduce.isEmpty()){
			reduceNonTerminal(stacksWithNonTerminalsToReduce.peekFirst(), stacksWithNonTerminalsToReduce.popSecond());
		}
	}
	
	private boolean findFirstStackToReduce(){
		for(int i = location; i < todoLists.length; ++i){
			Stack<AbstractStackNode> terminalsTodo = todoLists[i];
			if(!(terminalsTodo == null || terminalsTodo.isEmpty())){
				stacksWithTerminalsToReduce = terminalsTodo;
				
				location = i;
				shiftedLevel = (location != 0);
				return true;
			}
		}
		return false;
	}
	
	private boolean findStacksToReduce(){
		if(!stacksWithTerminalsToReduce.isEmpty()){
			shiftedLevel = false;
			return true;
		}
		
		for(int i = location + 1; i < todoLists.length; ++i){
			Stack<AbstractStackNode> terminalsTodo = todoLists[i];
			if(!(terminalsTodo == null || terminalsTodo.isEmpty())){
				stacksWithTerminalsToReduce = terminalsTodo;
				
				todoLists[location] = null;
				location = i;
				shiftedLevel = true;
				return true;
			}
		}
		return false;
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
				sharedNode.addProduction(expectedNodes);
				if(expectedNodes.length == 1) sharedNode.markAsEndNode();
				continue;
			}
			
			first = first.getCleanCopy();
			first.setStartLocation(location);
			first.setProduction(expectedNodes);
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
			int endLocation = location + node.getLength();
			if(endLocation <= input.length){
				if(!node.match(input)) return; // Discard if it didn't match.
				
				Stack<AbstractStackNode> terminalsTodo = todoLists[endLocation];
				if(terminalsTodo == null){
					terminalsTodo = new Stack<AbstractStackNode>();
					todoLists[endLocation] = terminalsTodo;
				}
				terminalsTodo.push(node);
			}
			return;
		}
		
		if(!node.isList()){
			HashMap<String, AbstractContainerNode> levelResultStoreMap = resultStoreCache.get(location);
			if(levelResultStoreMap != null){
				AbstractContainerNode resultStore = levelResultStoreMap.get(node.getIdentifier());
				if(resultStore != null){ // Is nullable, add the known results.
					stacksWithNonTerminalsToReduce.push(node, resultStore);
				}
			}
			
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
		rootNode.setProduction(new AbstractStackNode[]{rootNode});
		rootNode.initEdges();
		rootNode.setStartLocation(0);
		stacksToExpand.add(rootNode);
		expand();
		
		findFirstStackToReduce();
		do{
			do{
				if(shiftedLevel){ // Nullable fix.
					sharedNextNodes.clear();
					resultStoreCache.clear();
					cachedEdgesForExpect.clear();
				}
				
				reduce();
				
				expand();
			}while(!stacksWithNonTerminalsToReduce.isEmpty());
		}while(findStacksToReduce());
		
		if(location == input.length){
			HashMap<String, AbstractContainerNode> levelResultStoreMap = resultStoreCache.get(0);
			if(levelResultStoreMap != null){
				AbstractContainerNode result = levelResultStoreMap.get(start);
				if(result != null){
					return result; // Success.
				}
			}
		}
		
		// Parse error.
		throw new RuntimeException("Parse Error before: "+(location == Integer.MAX_VALUE ? 0 : location));
	}
}
