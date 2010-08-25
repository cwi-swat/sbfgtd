package gll;

import gll.result.AbstractNode;
import gll.result.ContainerNode;
import gll.result.struct.Link;
import gll.stack.AbstractStackNode;
import gll.stack.NonTerminalStackNode;
import gll.util.ArrayList;
import gll.util.HashMap;
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
	private final HashMap<String, AbstractStackNode[]> cachedExpects;
	
	private final ArrayList<AbstractStackNode> possiblySharedNextNodes;
	
	private final ObjectIntegerKeyedHashMap<String, ContainerNode> resultStoreCache;
	
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
		cachedExpects = new HashMap<String, AbstractStackNode[]>();
		
		possiblySharedNextNodes = new ArrayList<AbstractStackNode>();
		
		resultStoreCache = new ObjectIntegerKeyedHashMap<String, ContainerNode>();
		
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
		AbstractNode result = node.getResult();
		
		for(int i = possiblySharedNextNodes.size() - 1; i >= 0; --i){
			AbstractStackNode possibleAlternative = possiblySharedNextNodes.get(i);
			if(possibleAlternative.isSimilar(next)){
				possibleAlternative.updateNode(node);
				
				if(next.isEndNode()){
					if(!possibleAlternative.isClean() && possibleAlternative.getStartLocation() == location){
						if(possibleAlternative != node){ // List cycle fix.
							// Encountered self recursive epsilon cycle; update the prefixes.
							updatePrefixes(possibleAlternative, node);
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
		next.updateNode(node);
		
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
			ContainerNode resultStore = edge.getResultStore();
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
	
	private boolean shareListNode(AbstractStackNode node, AbstractStackNode stack){
		for(int j = possiblySharedNextNodes.size() - 1; j >= 0; --j){
			AbstractStackNode possiblySharedNode = possiblySharedNextNodes.get(j);
			if(possiblySharedNode.isSimilar(node)){
				possiblySharedNode.addEdge(stack);
				possiblySharedNode.addPrefix(null, location);
				return true;
			}
		}
		return false;
	}
	
	private void handleExpects(AbstractStackNode stackBeingWorkedOn){
		int nrOfExpects = lastExpects.size();
		AbstractStackNode[] expects = new AbstractStackNode[nrOfExpects];
		
		for(int i = nrOfExpects - 1; i >= 0; --i){
			AbstractStackNode[] expectedNodes = lastExpects.get(i);
			int numberOfNodes = expectedNodes.length;
			
			AbstractStackNode next = expectedNodes[numberOfNodes - 1].getCleanCopy();
			next.markAsEndNode();
			
			for(int k = numberOfNodes - 2; k >= 0; --k){
				AbstractStackNode current = expectedNodes[k].getCleanCopy();
				current.addNext(next);
				next = current;
			}

			next.setStartLocation(location);
			next.addEdge(stackBeingWorkedOn);
			
			stacksToExpand.add(next);
			
			expects[i] = next;
		}
		
		cachedExpects.put(stackBeingWorkedOn.getName(), expects);
	}
	
	private void expandStack(AbstractStackNode node){
		if(node.isReducable()){
			if((location + node.getLength()) <= input.length) todoList.add(node);
			return;
		}
		
		if(!node.isList()){
			AbstractStackNode[] expects = cachedExpects.get(node.getName());
			if(expects != null){
				for(int i = expects.length - 1; i >= 0; --i){
					expects[i].addEdge(node);
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
			cachedExpects.clear();
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
			findStacksToReduce();
			
			reduce();
			
			expand();
		}while(todoList.size() > 0);
		
		if(root == null) throw new RuntimeException("Parse Error before: "+(location == Integer.MAX_VALUE ? 0 : location));
		
		return root.getResult();
	}
}
