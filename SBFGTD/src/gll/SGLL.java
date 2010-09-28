package gll;

import gll.result.AbstractContainerNode;
import gll.result.AbstractNode;
import gll.result.ListContainerNode;
import gll.result.SortContainerNode;
import gll.result.struct.Link;
import gll.stack.AbstractStackNode;
import gll.stack.NonTerminalStackNode;
import gll.util.ArrayList;
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
	private final RotatingQueue<AbstractStackNode> stacksWithNonTerminalsToReduce;
	
	private final ArrayList<AbstractStackNode[]> lastExpects;
	private final LinearIntegerKeyedMap<AbstractStackNode> sharedLastExpects;
	private final HashMap<String, ArrayList<AbstractStackNode>> cachedExpects;
	
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
		stacksWithNonTerminalsToReduce = new RotatingQueue<AbstractStackNode>();
		
		lastExpects = new ArrayList<AbstractStackNode[]>();
		sharedLastExpects = new LinearIntegerKeyedMap<AbstractStackNode>();
		cachedExpects = new HashMap<String, ArrayList<AbstractStackNode>>();
		
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
	
	private void updateNextNode(AbstractStackNode next, AbstractStackNode node){
		int id = next.getId();
		AbstractStackNode alternative = sharedNextNodes.get(id);
		if(alternative != null){
			alternative.updateNode(node);
			
			if(next.isEndNode()){
				if(!alternative.isClean() && alternative.getStartLocation() == location){
					if(alternative != node){ // List cycle fix.
						// Encountered self recursive epsilon cycle; update the prefixes.
						updatePrefixes(alternative, node);
					}
				}
			}
		}else{
			if(next.startLocationIsSet()){
				next = next.getCleanCopyWithoutPrefixes();
			}

			next.updateNode(node);
			next.setStartLocation(location);
			
			if(!next.isMatchable()){ // Is non-terminal or list.
				HashMap<String, AbstractContainerNode> levelResultStoreMap = resultStoreCache.get(location);
				if(levelResultStoreMap != null){
					AbstractContainerNode resultStore = levelResultStoreMap.get(next.getIdentifier());
					if(resultStore != null){ // Is nullable, add the known results.
						next.setResultStore(resultStore);
						stacksWithNonTerminalsToReduce.put(next);
					}
				}
			}
			
			sharedNextNodes.putUnsafe(id, next);
			stacksToExpand.add(next);
		}
	}
	
	private void updateAlternativeNextNode(AbstractStackNode next, AbstractStackNode node, LinearIntegerKeyedMap<ArrayList<AbstractStackNode>> edgesMap, ArrayList<Link>[] prefixesMap){
		int id = next.getId();
		AbstractStackNode alternative = sharedNextNodes.get(id);
		if(alternative != null){
			alternative.updateNode(node);
			
			if(next.isEndNode()){
				if(!alternative.isClean() && alternative.getStartLocation() == location){
					if(alternative != node){ // List cycle fix.
						// Encountered self recursive epsilon cycle; update the prefixes.
						updatePrefixes(alternative, node);
					}
				}
			}
		}else{
			if(next.startLocationIsSet()){
				next = next.getCleanCopyWithoutPrefixes();
			}
			
			next.updatePrefixSharedNode(edgesMap, prefixesMap); // Prevent unnecessary overhead; share whenever possible.
			next.setStartLocation(location);
			
			if(!next.isMatchable()){ // Is non-terminal or list.
				HashMap<String, AbstractContainerNode> levelResultStoreMap = resultStoreCache.get(location);
				if(levelResultStoreMap != null){
					AbstractContainerNode resultStore = levelResultStoreMap.get(next.getIdentifier());
					if(resultStore != null){ // Is nullable, add the known results.
						next.setResultStore(resultStore);
						stacksWithNonTerminalsToReduce.put(next);
					}
				}
			}
			
			sharedNextNodes.putUnsafe(id, next);
			stacksToExpand.add(next);
		}
	}
	
	private void updatePrefixes(AbstractStackNode next, AbstractStackNode node){
		LinearIntegerKeyedMap<ArrayList<AbstractStackNode>> edgesMap = node.getEdges();
		ArrayList<Link>[] prefixesMap = node.getPrefixesMap();
		AbstractNode result = node.getResult();
		
		// Update results (if necessary).
		for(int i = edgesMap.size() - 1; i >= 0; --i){
			int startLocation = edgesMap.getKey(i);
			ArrayList<AbstractStackNode> edgesPart = edgesMap.getValue(i);
			
			// Update one (because of sharing all will be updated).
			AbstractStackNode edge = edgesPart.get(0);
			ArrayList<Link> edgePrefixes = new ArrayList<Link>();
			Link prefix = constructPrefixesFor(edgesMap, prefixesMap, result, startLocation);
			edgePrefixes.add(prefix);
			AbstractNode resultStore = edge.getResultStore();
			resultStore.addAlternative(new Link(edgePrefixes, next.getResult()));
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
			HashMap<String, AbstractContainerNode> levelResultStoreMap = resultStoreCache.get(startLocation);
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
			
			LinearIntegerKeyedMap<AbstractStackNode> alternateNexts = node.getAlternateNexts();
			if(alternateNexts != null){
				LinearIntegerKeyedMap<ArrayList<AbstractStackNode>> edgesMap = next.getEdges();
				ArrayList<Link>[] prefixesMap = next.getPrefixesMap();
				
				for(int i = alternateNexts.size() - 1; i >= 0; --i){
					updateAlternativeNextNode(alternateNexts.getValue(i), node, edgesMap, prefixesMap);
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
		if(!terminal.match(input)) return;
		
		move(terminal);
	}
	
	private void reduceNonTerminal(AbstractStackNode nonTerminal){
		move(nonTerminal);
	}
	
	private void reduce(){
		if(previousLocation != location){ // Epsilon fix.
			sharedNextNodes.clear();
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
	
	private boolean shareListNode(AbstractStackNode node, AbstractStackNode stack){
		int id = node.getId();
		AbstractStackNode sharedNode = sharedNextNodes.get(id);
		if(sharedNode != null){
			sharedNode.addEdgeWithPrefix(stack, null, location);
			return true;
		}
		sharedNextNodes.putUnsafe(id, node);
		return false;
	}
	
	private void handleExpects(AbstractStackNode stackBeingWorkedOn){
		sharedLastExpects.dirtyClear();
		
		int nrOfExpects = lastExpects.size();
		ArrayList<AbstractStackNode> expects = new ArrayList<AbstractStackNode>(nrOfExpects);
		
		EXPECT : for(int i = nrOfExpects - 1; i >= 0; --i){
			AbstractStackNode[] expectedNodes = lastExpects.get(i);
			int numberOfNodes = expectedNodes.length;
			
			AbstractStackNode first = expectedNodes[0];
			int firstId = first.getId();
			
			// Handle prefix sharing.
			AbstractStackNode sharedNode = sharedLastExpects.findValue(firstId);
			
			if(sharedNode != null){
				int index = 1;
				for(; index < numberOfNodes; ++index){
					AbstractStackNode next = expectedNodes[index];
					int nextId = next.getId();
					AbstractStackNode nextShared = sharedNode.getNext();
					if(nextShared == null){
						AbstractStackNode last = expectedNodes[numberOfNodes - 1].getCleanCopy();
						last.markAsEndNode();
						
						for(int k = numberOfNodes - 2; k >= index; --k){
							AbstractStackNode current = expectedNodes[k].getCleanCopy();
							current.setNext(last);
							last = current;
						}
						
						sharedNode.addNext(last);
						continue EXPECT;
					}
					
					if(nextShared.getId() == nextId){
						sharedNode = nextShared;
						continue;
					}
					
					LinearIntegerKeyedMap<AbstractStackNode> alternateSharedNexts = sharedNode.getAlternateNexts();
					if(alternateSharedNexts != null){
						nextShared = alternateSharedNexts.findValue(nextId);
						if(nextShared != null){
							sharedNode = nextShared;
							continue;
						}
					}
					
					AbstractStackNode last = expectedNodes[numberOfNodes - 1].getCleanCopy();
					last.markAsEndNode();
					
					for(int k = numberOfNodes - 2; k >= index; --k){
						AbstractStackNode current = expectedNodes[k].getCleanCopy();
						current.setNext(last);
						last = current;
					}
					
					sharedNode.addNext(last);
					continue EXPECT;
				}
				
				sharedNode.markAsEndNode();
				
				continue EXPECT;
			}
			
			AbstractStackNode next = expectedNodes[numberOfNodes - 1].getCleanCopy();
			next.markAsEndNode();
			
			if(numberOfNodes - 2 >= 0){
				for(int j = numberOfNodes - 2; j >= 1; --j){
					AbstractStackNode current = expectedNodes[j].getCleanCopy();
					current.setNext(next);
					next = current;
				}
				
				first = first.getCleanCopy();
				first.setNext(next);
			}else{
				first = next; // Chain rule.
			}

			first.setStartLocation(location);
			first.addEdge(stackBeingWorkedOn);
			
			sharedLastExpects.add(firstId, first);
			
			stacksToExpand.add(first);
			
			expects.add(first);
		}
		
		cachedExpects.put(stackBeingWorkedOn.getName(), expects);
	}
	
	private void expandStack(AbstractStackNode node){
		if(node.isMatchable()){
			if((location + node.getLength()) <= input.length) todoList.add(node);
			return;
		}
		
		if(!node.isList()){
			ArrayList<AbstractStackNode> expects = cachedExpects.get(node.getName());
			if(expects != null){
				for(int i = expects.size() - 1; i >= 0; --i){
					expects.get(i).addEdge(node);
				}
			}else{
				invokeExpects(node.getMethodName());
				handleExpects(node);
			}
		}else{ // 'List'
			AbstractStackNode[] listChildren = node.getChildren();
			
			AbstractStackNode child = listChildren[0];
			if(!shareListNode(child, node)){
				stacksToExpand.add(child);
			}
			
			if(listChildren.length > 1){ // Star list or optional.
				// This is always epsilon (and unique for this position); so shouldn't be shared.
				stacksToExpand.add(listChildren[1]);
			}
		}
	}
	
	private void expand(){
		if(previousLocation != location){
			cachedExpects.clear();
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
		AbstractStackNode rootNode = new NonTerminalStackNode(START_SYMBOL_ID, start);
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
