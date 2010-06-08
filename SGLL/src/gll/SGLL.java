package gll;

import gll.result.ContainerNode;
import gll.result.INode;
import gll.stack.AbstractStackNode;
import gll.stack.NonTerminalStackNode;
import gll.util.ArrayList;
import gll.util.DoubleArrayList;
import gll.util.HashSet;
import gll.util.IndexedStack;
import gll.util.IntegerHashMap;
import gll.util.ObjectIntegerKeyHashMap;
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
	private final IntegerHashMap<ArrayList<AbstractStackNode>> possiblySharedEdgeNodesMap;
	
	private final ObjectIntegerKeyHashMap<String, ContainerNode> resultStoreCache;
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
		possiblySharedEdgeNodesMap = new IntegerHashMap<ArrayList<AbstractStackNode>>();
		
		resultStoreCache = new ObjectIntegerKeyHashMap<String, ContainerNode>();
		withResults = new HashSet<AbstractStackNode>();
		
		previousLocation = -1;
		location = 0;
	}
	
	public void expect(AbstractStackNode... symbolsToExpect){
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
	
	private void updateNextNode(AbstractStackNode node, INode[][] prefixes, int[] prefixStartLocations){
		for(int i = possiblySharedNextNodes.size() - 1; i >= 0; i--){
			AbstractStackNode possibleAlternative = possiblySharedNextNodes.get(i);
			if(possibleAlternative.isSimilar(node)){
				if(node.hasEdges()){
					possibleAlternative.addEdges(node.getEdges());
				}
				
				if(possibleAlternative.isClean()){
					addPrefixes(possibleAlternative, prefixes, prefixStartLocations);
				}else{
					// Something horrible happened; update the prefixes.
					updatePrefixes(possibleAlternative, prefixes, prefixStartLocations);
				}
				return;
			}
		}
		
		if(node.startLocationIsSet()){
			node = node.getCleanCopy();
		}
		
		node.setStartLocation(location);
		possiblySharedNextNodes.add(node);
		stacksToExpand.add(node);
		
		addPrefixes(node, prefixes, prefixStartLocations);
	}
	
	private void addPrefixes(AbstractStackNode next, INode[][] prefixes, int[] prefixStartLocations){
		for(int i = prefixes.length - 1; i >= 0; i--){
			next.addPrefix(prefixes[i], prefixStartLocations[i]);
		}
	}
	
	private void updatePrefixes(AbstractStackNode next, INode[][] prefixes, int[] prefixStartLocations){
		for(int i = prefixes.length - 1; i >= 0; i--){
			next.addPrefix(prefixes[i], prefixStartLocations[i]); // Not strictly necessary at the moment.
			ArrayList<AbstractStackNode> edges;
			if((edges = next.getEdges()) != null){
				INode thisResult = next.getResult();
				for(int j = prefixes.length - 1; j >= 0; j--){
					INode[] prefix = prefixes[j];
					int prefixStartLocation = prefixStartLocations[j];
					int prefixLength = prefix.length;
					INode[] result = new INode[prefixLength + 1];
					System.arraycopy(prefix, 0, result, 0, prefixLength);
					result[prefixLength] = thisResult;
					
					for(int k = edges.size() - 1; k >= 0; k--){
						AbstractStackNode edge = edges.get(k);
						if(withResults.contains(edge)){
							if(edge.getStartLocation() == prefixStartLocation){
								edge.addResult(result);
							}
						}
					}
				}
			}
		}
	}
	
	private void updateEdgeNode(AbstractStackNode node, INode[][] results, int[] resultStartLocations){
		int startLocation = node.getStartLocation();
		ArrayList<AbstractStackNode> possiblySharedEdgeNodes = possiblySharedEdgeNodesMap.get(startLocation);
		if(possiblySharedEdgeNodes != null){
			for(int i = possiblySharedEdgeNodes.size() - 1; i >= 0; i--){
				AbstractStackNode possibleAlternative = possiblySharedEdgeNodes.get(i);
				if(possibleAlternative.isSimilar(node)){
					if(withResults.contains(possibleAlternative)) addResults(possibleAlternative, results, resultStartLocations);
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
			resultStoreCache.put(nodeName, startLocation, resultStore);
			withResults.put(node);
			addResults(node, results, resultStartLocations);
		}
		
		if(location == input.length && !node.hasEdges() && !node.hasNext()){
			root = node; // Root reached.
		}
		
		possiblySharedEdgeNodes.add(node);
		stacksWithNonTerminalsToReduce.put(node);
	}
	
	private void addResults(AbstractStackNode edge, INode[][] results, int[] resultStartLocations){
		int nrOfResults = results.length;
		for(int i = nrOfResults - 1; i >= 0; i--){
			if(edge.getStartLocation() == resultStartLocations[i]){
				edge.addResult(results[i]);
			}
		}
	}
	
	private void move(AbstractStackNode node){
		INode[][] results = node.getResults();
		int[] resultStartLocations = node.getResultStartLocations();
		
		ArrayList<AbstractStackNode> edges;
		if((edges = node.getEdges()) != null){
			for(int i = edges.size() - 1; i >= 0; i--){
				updateEdgeNode(edges.get(i), results, resultStartLocations);
			}
		}
		
		AbstractStackNode next;
		if((next = node.getNext()) != null){
			updateNextNode(next, results, resultStartLocations);
		}
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
