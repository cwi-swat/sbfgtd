package gll;

import gll.result.ContainerNode;
import gll.result.INode;
import gll.result.struct.Link;
import gll.stack.AbstractStackNode;
import gll.stack.NonTerminalStackNode;
import gll.util.ArrayList;
import gll.util.DoubleArrayList;
import gll.util.HashSet;
import gll.util.IndexedStack;
import gll.util.IntegerKeyedHashMap;
import gll.util.LinearIntegerKeyedMap;
import gll.util.ObjectIntegerKeyedHashMap;
import gll.util.RotatingQueue;

import java.io.IOException;
import java.io.StringWriter;
import java.lang.reflect.Method;

public class SGLL implements IGLL{
	private final char[] input;
	
	private final ArrayList<AbstractStackNode> todoList;
	
	// Updatable
	private final ArrayList<AbstractStackNode> stacksToExpand;
	private final RotatingQueue<AbstractStackNode> stacksWithTerminalsToReduce;
	private final RotatingQueue<AbstractStackNode> stacksWithNonTerminalsToReduce;
	private final ArrayList<AbstractStackNode[]> lastExpects;
	private final DoubleArrayList<AbstractStackNode, AbstractStackNode> possiblySharedExpects;
	private final ArrayList<AbstractStackNode> possiblySharedNextNodes;
	private final IntegerKeyedHashMap<ArrayList<AbstractStackNode>> possiblySharedEdgeNodesMap;
	
	private final ObjectIntegerKeyedHashMap<String, ContainerNode> resultStoreCache;
	private final HashSet<AbstractStackNode> withResults;
	
	private int previousLocation;
	private int location;
	
	private AbstractStackNode root;
	
	public SGLL(char[] input){
		super();
		
		this.input = input;
		
		todoList = new ArrayList<AbstractStackNode>();
		
		stacksToExpand = new ArrayList<AbstractStackNode>();
		stacksWithTerminalsToReduce = new RotatingQueue<AbstractStackNode>();
		stacksWithNonTerminalsToReduce = new RotatingQueue<AbstractStackNode>();
		
		lastExpects = new ArrayList<AbstractStackNode[]>();
		possiblySharedExpects = new DoubleArrayList<AbstractStackNode, AbstractStackNode>();
		
		possiblySharedNextNodes = new ArrayList<AbstractStackNode>();
		possiblySharedEdgeNodesMap = new IntegerKeyedHashMap<ArrayList<AbstractStackNode>>();
		
		resultStoreCache = new ObjectIntegerKeyedHashMap<String, ContainerNode>();
		withResults = new HashSet<AbstractStackNode>();
		
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
	
	private void updateProductionEndNode(AbstractStackNode sharedNode, AbstractStackNode node){
		AbstractStackNode prev = node;
		AbstractStackNode next = node.getNext();
		
		AbstractStackNode sharedPrev = sharedNode;
		AbstractStackNode sharedNext = sharedNode.getNext();
		do{
			prev = next;
			next = next.getNext();
			
			sharedPrev = sharedNext;
			sharedNext = sharedNext.getNext();
			
			if(prev.hasEdges()){
				sharedPrev.addEdges(prev.getEdges());
			}
		}while(sharedNext != null);
	}
	
	private void updateNextNode(AbstractStackNode next, AbstractStackNode node){
		for(int i = possiblySharedNextNodes.size() - 1; i >= 0; i--){
			AbstractStackNode possibleAlternative = possiblySharedNextNodes.get(i);
			if(possibleAlternative.isSimilar(next)){
				if(next.hasEdges()){
					possibleAlternative.addEdges(next.getEdges());
				}else{
					// Don't lose any edges.
					updateProductionEndNode(possibleAlternative, next);
				}
				
				if(possibleAlternative.isClean()){
					addPrefixes(possibleAlternative, constructPrefixes(node.getPrefixesMap(), node.getResult(), node.getStartLocation()));
				}else{
					// Something horrible happened; update the prefixes.
					updatePrefixes(possibleAlternative, node);
				}
				return;
			}
		}
		
		if(next.startLocationIsSet()){
			next = next.getCleanCopy();
		}
		
		next.setStartLocation(location);
		possiblySharedNextNodes.add(next);
		stacksToExpand.add(next);
		
		addPrefixes(next, constructPrefixes(node.getPrefixesMap(), node.getResult(), node.getStartLocation()));
	}
	
	private void addPrefixes(AbstractStackNode next, Link[] prefixes){
		for(int i = prefixes.length - 1; i >= 0; i--){
			next.addPrefix(prefixes[i]);
		}
	}
	
	private void updatePrefixes(AbstractStackNode next, AbstractStackNode node){
		LinearIntegerKeyedMap<ArrayList<Link>> prefixesMap = node.getPrefixesMap();
		Link[] prefixes = constructPrefixes(prefixesMap, node.getResult(), node.getStartLocation());
		
		// Update results (if necessary).
		ArrayList<AbstractStackNode> edges;
		if((edges = next.getEdges()) != null){
			for(int i = edges.size() - 1; i >= 0; i--){
				AbstractStackNode edge = edges.get(i);
				if(withResults.contains(edge)){
					int productionStartLocation = edge.getStartLocation();
					ArrayList<Link> nodePrefixes = new ArrayList<Link>();
					for(int j = prefixes.length - 1; j >= 0; j--){
						Link prefix = prefixes[j];
						if(prefix.productionStart == productionStartLocation) nodePrefixes.add(prefix);
					}
					
					edge.addResult(new Link(nodePrefixes, node.getResult(), productionStartLocation));
				}
			}
		}
		
		// Update prefixes.
		addPrefixes(next, prefixes);
	}
	
	private void updateEdgeNode(AbstractStackNode node, LinearIntegerKeyedMap<ArrayList<Link>> prefixesMap, INode result){
		int startLocation = node.getStartLocation();
		ArrayList<AbstractStackNode> possiblySharedEdgeNodes = possiblySharedEdgeNodesMap.get(startLocation);
		if(possiblySharedEdgeNodes != null){
			for(int i = possiblySharedEdgeNodes.size() - 1; i >= 0; i--){
				AbstractStackNode possibleAlternative = possiblySharedEdgeNodes.get(i);
				if(possibleAlternative.isSimilar(node)){
					if(withResults.contains(possibleAlternative)) addResult(possibleAlternative, prefixesMap, result);
					return;
				}
			}
		}else{
			possiblySharedEdgeNodes = new ArrayList<AbstractStackNode>();
			possiblySharedEdgeNodesMap.unsafePut(startLocation, possiblySharedEdgeNodes);
		}
		
		if(!node.isClean()){
			node = node.getCleanCopyWithPrefix();
			node.setStartLocation(startLocation);
		}
		
		String nodeName = node.getName();
		ContainerNode resultStore = resultStoreCache.get(nodeName, startLocation);
		node.setResultStore(resultStore);
		if(resultStore == null){
			resultStore = new ContainerNode(nodeName);
			node.setResultStore(resultStore);
			resultStoreCache.unsafePut(nodeName, startLocation, resultStore);
			withResults.unsafePut(node);
			addResult(node, prefixesMap, result);
		}
		
		if(location == input.length && !node.hasEdges() && !node.hasNext()){
			root = node; // Root reached.
		}
		
		possiblySharedEdgeNodes.add(node);
		stacksWithNonTerminalsToReduce.put(node);
	}
	
	private void addResult(AbstractStackNode edge, LinearIntegerKeyedMap<ArrayList<Link>> prefixesMap, INode result){
		int startLocation = edge.getStartLocation();
		if(prefixesMap != null){
			ArrayList<Link> prefixes = prefixesMap.findValue(startLocation);
			if(prefixes != null){
				edge.addResult(new Link(prefixes, result, startLocation));
			}
		}else{
			edge.addResult(new Link(null, result, startLocation));
		}
	}
	
	private void move(AbstractStackNode node){
		ArrayList<AbstractStackNode> edges;
		if((edges = node.getEdges()) != null){
			for(int i = edges.size() - 1; i >= 0; i--){
				updateEdgeNode(edges.get(i), node.getPrefixesMap(), node.getResult());
			}
		}
		
		AbstractStackNode next;
		if((next = node.getNext()) != null){
			updateNextNode(next, node);
		}
	}
	
	private Link[] constructPrefixes(LinearIntegerKeyedMap<ArrayList<Link>> prefixesMap, INode result, int startLocation){
		if(prefixesMap == null){
			return new Link[]{new Link(null, result, startLocation)};
		}
		
		int nrOfPrefixes = prefixesMap.size();
		Link[] results = new Link[nrOfPrefixes];
		for(int i = nrOfPrefixes - 1; i >= 0; i--){
			results[i] = new Link(prefixesMap.getValue(i), result, prefixesMap.getKey(i));
		}
		
		return results;
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
			possiblySharedEdgeNodesMap.clear();
			resultStoreCache.clear();
			withResults.clear();
		}
		
		// Reduce terminals.
		while(!stacksWithTerminalsToReduce.isEmpty()){
			AbstractStackNode terminal = stacksWithTerminalsToReduce.unsafeGet();
			reduceTerminal(terminal);

			todoList.remove(terminal);
		}
		
		// Reduce non-terminals.
		while(!stacksWithNonTerminalsToReduce.isEmpty()){
			AbstractStackNode nonTerminal = stacksWithNonTerminalsToReduce.unsafeGet();
			reduceNonTerminal(nonTerminal);
		}
	}
	
	private void findStacksToReduce(){
		// Find the stacks that will progress the least.
		int closestNextLocation = Integer.MAX_VALUE;
		for(int i = todoList.size() - 1; i >= 0; i--){
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
			for(int j = possiblySharedExpects.size() - 1; j >= 0; j--){
				AbstractStackNode possiblySharedNode = possiblySharedExpects.getFirst(j);
				if(possiblySharedNode.isSimilar(node)){
					possiblySharedExpects.getSecond(j).addEdge(stack);
					return true;
				}
			}
		}
		return false;
	}
	
	private void handleExpects(AbstractStackNode stackBeingWorkedOn){
		for(int i = lastExpects.size() - 1; i >= 0; i--){
			AbstractStackNode[] expectedNodes = lastExpects.get(i);
			int numberOfNodes = expectedNodes.length;
			AbstractStackNode first = expectedNodes[0];
			
			// Handle sharing (and loops).
			if(!shareNode(first, stackBeingWorkedOn)){
				AbstractStackNode last = expectedNodes[numberOfNodes - 1].getCleanCopy();
				AbstractStackNode next = last;
				
				for(int k = numberOfNodes - 2; k >= 0; k--){
					AbstractStackNode current = expectedNodes[k].getCleanCopy();
					current.addNext(next);
					next = current;
				}
				
				last.addEdge(stackBeingWorkedOn);
				
				next.setStartLocation(location);
				
				stacksToExpand.add(next);
				possiblySharedExpects.add(next, last);
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
				possiblySharedExpects.add(child, child);
			}
			
			if(listChildren.length > 1){ // Star list or optional.
				child = listChildren[1];
				// This is always epsilon; so shouldn't be shared.
				stacksToExpand.add(child);
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
		for(int i = ranges.length - 1; i >= 0; i--){
			char[] range = ranges[i];
			if(next >= range[0] && next <= range[1]) return true;
		}
		
		for(int i = characters.length - 1; i >= 0; i--){
			if(next == characters[i]) return true;
		}
		
		return false;
	}
	
	protected String getStringResult(){
		INode result = root.getResult();
		
		StringWriter out = new StringWriter();
		try{
			result.print(out, new IndexedStack<INode>(), 0);
		}catch(IOException ioex){
			// Ignore; never happens.
		}
		return out.toString();
	}
	
	public INode parse(String start){
		// Initialize.
		AbstractStackNode rootNode = new NonTerminalStackNode(START_SYMBOL_ID, start).getCleanCopy();
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
