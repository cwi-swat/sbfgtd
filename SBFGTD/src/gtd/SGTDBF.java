package gtd;

import gtd.result.AbstractContainerNode;
import gtd.result.AbstractNode;
import gtd.result.ListContainerNode;
import gtd.result.SortContainerNode;
import gtd.result.struct.Link;
import gtd.stack.AbstractExpandableStackNode;
import gtd.stack.AbstractStackNode;
import gtd.stack.NonTerminalStackNode;
import gtd.util.ArrayList;
import gtd.util.DoubleStack;
import gtd.util.HashMap;
import gtd.util.IntegerKeyedHashMap;
import gtd.util.IntegerList;
import gtd.util.IntegerObjectList;
import gtd.util.Stack;

import java.lang.reflect.Method;

public class SGTDBF implements IGTD{
	protected final char[] input;
	
	private final DoubleStack<AbstractStackNode, AbstractNode>[] todoLists;
	
	private final Stack<AbstractStackNode> stacksToExpand;
	private DoubleStack<AbstractStackNode, AbstractNode> stacksWithTerminalsToReduce;
	private final DoubleStack<AbstractStackNode, AbstractNode> stacksWithNonTerminalsToReduce;
	
	private final HashMap<String, ArrayList<AbstractStackNode>> cachedEdgesForExpect;
	
	private final IntegerKeyedHashMap<AbstractStackNode> sharedNextNodes;
	
	private final IntegerKeyedHashMap<HashMap<String, AbstractContainerNode>> resultStoreCache;
	
	protected int location;
	
	private final HashMap<String, Method> methodCache;
	
	private final IntegerObjectList<IntegerList> propagatedPrefixes;
	private final IntegerObjectList<IntegerList> propagatedReductions; // Note: we can replace this thing, if we pick a more efficient solution.
	
	public SGTDBF(char[] input){
		super();
		
		this.input = input;
		
		todoLists = (DoubleStack<AbstractStackNode, AbstractNode>[]) new DoubleStack[input.length + 1];
		
		stacksToExpand = new Stack<AbstractStackNode>();
		stacksWithTerminalsToReduce = new DoubleStack<AbstractStackNode, AbstractNode>();
		stacksWithNonTerminalsToReduce = new DoubleStack<AbstractStackNode, AbstractNode>();
		
		cachedEdgesForExpect = new HashMap<String, ArrayList<AbstractStackNode>>();
		
		sharedNextNodes = new IntegerKeyedHashMap<AbstractStackNode>();
		
		resultStoreCache = new IntegerKeyedHashMap<HashMap<String, AbstractContainerNode>>();
		
		location = 0;
		
		methodCache = new HashMap<String, Method>();
		
		propagatedPrefixes = new IntegerObjectList<IntegerList>();
		propagatedReductions = new IntegerObjectList<IntegerList>();
	}
	
	protected AbstractStackNode[] invokeExpects(String name){
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
		
		AbstractStackNode[] expects = null;
		try{
			expects = (AbstractStackNode[]) method.invoke(this);
		}catch(Exception ex){
			// Not going to happen.
			ex.printStackTrace(); // Temp
		}
		return expects;
	}
	
	private AbstractStackNode updateNextNode(AbstractStackNode next, AbstractStackNode node, AbstractNode result){
		AbstractStackNode alternative = sharedNextNodes.get(next.getId());
		if(alternative != null){
			if(result.isEmpty()){
				if(alternative.isMatchable()){
					if(alternative.isEmptyLeafNode()){
						// Encountered stack 'overtake'.
						propagateEdgesAndPrefixes(node, result, alternative, alternative.getResult(), node.getEdges().size());
						return alternative;
					}
				}else{
					HashMap<String, AbstractContainerNode> levelResultStoreMap = resultStoreCache.get(location);
					AbstractContainerNode nextResult = levelResultStoreMap.get(alternative.getIdentifier());
					if(nextResult != null){
						// Encountered stack 'overtake'.
						propagateEdgesAndPrefixes(node, result, alternative, nextResult, node.getEdges().size());
						return alternative;
					}
				}
			}
			
			alternative.updateNode(node, result);
			
			return alternative;
		}
		
		if(next.isMatchable()){
			if((location + next.getLength()) > input.length) return null;
			
			AbstractNode nextResult = next.match(input, location);
			if(nextResult == null) return null;
			
			next = next.getCleanCopyWithResult(location, nextResult);
		}else{
			next = next.getCleanCopy(location);
		}
		
		if(!node.isMatchable() || result.isEmpty()){
			next.updateNode(node, result);
		}else{
			next.updateNodeAfterNonEmptyMatchable(node, result);
		}
		
		sharedNextNodes.putUnsafe(next.getId(), next);
		stacksToExpand.push(next);
		return next;
	}
	
	private boolean updateAlternativeNextNode(AbstractStackNode next, AbstractStackNode node, AbstractNode result, IntegerObjectList<ArrayList<AbstractStackNode>> edgesMap, ArrayList<Link>[] prefixesMap){
		AbstractStackNode alternative = sharedNextNodes.get(next.getId());
		if(alternative != null){
			if(result.isEmpty()){
				if(alternative.isMatchable()){
					if(alternative.isEmptyLeafNode()){
						// Encountered stack 'overtake'.
						propagateAlternativeEdgesAndPrefixes(node, result, alternative, alternative.getResult(), node.getEdges().size(), edgesMap, prefixesMap);
						return true;
					}
				}else{
					HashMap<String, AbstractContainerNode> levelResultStoreMap = resultStoreCache.get(location);
					AbstractContainerNode nextResult = levelResultStoreMap.get(alternative.getIdentifier());
					if(nextResult != null){
						// Encountered stack 'overtake'.
						propagateAlternativeEdgesAndPrefixes(node, result, alternative, nextResult, node.getEdges().size(), edgesMap, prefixesMap);
						return true;
					}
				}
			}
			
			alternative.updatePrefixSharedNode(edgesMap, prefixesMap);
			
			return true;
		}
		
		if(next.isMatchable()){
			if((location + next.getLength()) > input.length) return false;
			
			AbstractNode nextResult = next.match(input, location);
			if(nextResult == null) return false;
			
			next = next.getCleanCopyWithResult(location, nextResult);
		}else{
			next = next.getCleanCopy(location);
		}
		
		next.updatePrefixSharedNode(edgesMap, prefixesMap);
		
		sharedNextNodes.putUnsafe(next.getId(), next);
		stacksToExpand.push(next);
		
		return true;
	}
	
	private void propagateReductions(AbstractStackNode node, AbstractNode nodeResultStore, AbstractStackNode next, AbstractNode nextResultStore, int potentialNewEdges){
		IntegerList touched = propagatedReductions.findValue(next.getId());
		if(touched == null){
			touched = new IntegerList();
			propagatedReductions.add(next.getId(), touched);
		}
		
		IntegerObjectList<ArrayList<AbstractStackNode>> edgesMap = node.getEdges();
		ArrayList<Link>[] prefixes = node.getPrefixesMap();
		
		int fromIndex = edgesMap.size() - potentialNewEdges;
		for(int i = edgesMap.size() - 1; i >= fromIndex; --i){
			int startLocation = edgesMap.getKey(i);
			
			if(touched.contains(startLocation)) continue;
			touched.add(startLocation);
			
			ArrayList<Link> edgePrefixes = new ArrayList<Link>();
			Link prefix = (prefixes != null) ? new Link(prefixes[i], nodeResultStore) : new Link(null, nodeResultStore);
			edgePrefixes.add(prefix);
			
			Link resultLink = new Link(edgePrefixes, nextResultStore);
			
			handleEdgesList(edgesMap.getValue(i), resultLink, edgesMap.getKey(i));
		}
	}
	
	private void propagateEdgesAndPrefixes(AbstractStackNode node, AbstractNode nodeResult, AbstractStackNode next, AbstractNode nextResult, int potentialNewEdges){
		IntegerList touched = propagatedPrefixes.findValue(node.getId());
		if(touched == null){
			touched = new IntegerList();
			propagatedPrefixes.add(node.getId(), touched);
		}
		
		int nrOfAddedEdges = next.updateOvertakenNode(node, nodeResult, potentialNewEdges, touched);
		if(nrOfAddedEdges == 0) return;
		
		if(next.isEndNode()){
			propagateReductions(node, nodeResult, next, nextResult, nrOfAddedEdges);
		}
		
		if(next.hasNext()){
			HashMap<String, AbstractContainerNode> levelResultStoreMap = resultStoreCache.get(location);

			IntegerObjectList<ArrayList<AbstractStackNode>> nextNextEdgesMap = null;
			ArrayList<Link>[] nextNextPrefixesMap = null;
			
			// Proceed with the tail of the production.
			int nextDot = next.getDot() + 1;
			AbstractStackNode[] prod = node.getProduction();
			AbstractStackNode nextNext = prod[nextDot];
			AbstractStackNode nextNextAlternative = sharedNextNodes.get(nextNext.getId());
			if(nextNextAlternative != null){
				if(nextNextAlternative.isMatchable()){
					if(nextNextAlternative.isEmptyLeafNode()){
						propagateEdgesAndPrefixes(next, nextResult, nextNextAlternative, nextNextAlternative.getResult(), nrOfAddedEdges);
					}else{
						nextNextAlternative.updateNode(next, nextResult);
					}
				}else{
					AbstractContainerNode nextNextResultStore = levelResultStoreMap.get(nextNextAlternative.getIdentifier());
					if(nextNextResultStore != null){
						propagateEdgesAndPrefixes(next, nextResult, nextNextAlternative, nextNextResultStore, nrOfAddedEdges);
					}else{
						nextNextAlternative.updateNode(next, nextResult);
					}
				}
				
				nextNextEdgesMap = nextNextAlternative.getEdges();
				nextNextPrefixesMap = nextNextAlternative.getPrefixesMap();
			}
			
			// Handle alternative nexts (and prefix sharing).
			AbstractStackNode[][] alternateProds = node.getAlternateProductions();
			if(alternateProds != null){
				for(int i = alternateProds.length - 1; i >= 0; --i){
					prod = alternateProds[i];
					if(nextDot == prod.length) continue;
					AbstractStackNode alternativeNext = prod[nextDot];
					
					AbstractStackNode nextNextAltAlternative = sharedNextNodes.get(alternativeNext.getId());
					if(nextNextAltAlternative != null){
						if(nextNextAltAlternative.isMatchable()){
							if(nextNextAltAlternative.isEmptyLeafNode()){
								if(nextNextEdgesMap != null){
									AbstractNode nextNextAltAlternativeResult = nextNextAltAlternative.getResult();
									if(nextNextAltAlternativeResult != null){
										propagateAlternativeEdgesAndPrefixes(next, nextResult, nextNextAltAlternative, nextNextAltAlternativeResult, nrOfAddedEdges, nextNextEdgesMap, nextNextPrefixesMap);
									}
								}else{
									propagateEdgesAndPrefixes(next, nextResult, nextNextAltAlternative, nextNextAltAlternative.getResult(), nrOfAddedEdges);
								}
							}else{
								if(nextNextEdgesMap != null){
									nextNextAltAlternative.updatePrefixSharedNode(nextNextEdgesMap, nextNextPrefixesMap);
								}else{
									nextNextAltAlternative.updateNode(next, nextResult);
									nextNextEdgesMap = nextNextAltAlternative.getEdges();
									nextNextPrefixesMap = nextNextAltAlternative.getPrefixesMap();
								}
							}
						}else{
							AbstractContainerNode nextAltResultStore = levelResultStoreMap.get(nextNextAltAlternative.getIdentifier());
							if(nextAltResultStore != null){
								if(nextNextEdgesMap != null){
									propagateAlternativeEdgesAndPrefixes(next, nextResult, nextNextAltAlternative, nextAltResultStore, nrOfAddedEdges, nextNextEdgesMap, nextNextPrefixesMap);
								}else{
									propagateEdgesAndPrefixes(next, nextResult, nextNextAltAlternative, nextAltResultStore, nrOfAddedEdges);
								}
							}else{
								if(nextNextEdgesMap != null){
									nextNextAltAlternative.updatePrefixSharedNode(nextNextEdgesMap, nextNextPrefixesMap);
								}else{
									nextNextAltAlternative.updateNode(next, nextResult);
									nextNextEdgesMap = nextNextAltAlternative.getEdges();
									nextNextPrefixesMap = nextNextAltAlternative.getPrefixesMap();
								}
							}
						}
					}
				}
			}
		}
	}
	
	private void propagateAlternativeEdgesAndPrefixes(AbstractStackNode node, AbstractNode nodeResult, AbstractStackNode next, AbstractNode nextResult, int potentialNewEdges, IntegerObjectList<ArrayList<AbstractStackNode>> edgesMap, ArrayList<Link>[] prefixesMap){
		next.updatePrefixSharedNode(edgesMap, prefixesMap);
		
		if(next.isEndNode()){
			propagateReductions(node, nodeResult, next, nextResult, potentialNewEdges);
		}
		
		if(potentialNewEdges != 0 && next.hasNext()){
			// Proceed with the tail of the production.
			int nextDot = next.getDot() + 1;
			AbstractStackNode[] prod = node.getProduction();
			AbstractStackNode nextNext = prod[nextDot];
			AbstractStackNode nextNextAlternative = sharedNextNodes.get(nextNext.getId());
			if(nextNextAlternative == null) return;

			HashMap<String, AbstractContainerNode> levelResultStoreMap = resultStoreCache.get(location);
			if(nextNextAlternative.isMatchable()){
				if(nextNextAlternative.isEmptyLeafNode()){
					propagateEdgesAndPrefixes(next, nextResult, nextNextAlternative, nextNextAlternative.getResult(), potentialNewEdges);
				}else{
					nextNextAlternative.updateNode(next, nextResult);
				}
			}else{
				AbstractContainerNode nextResultStore = levelResultStoreMap.get(nextNextAlternative.getIdentifier());
				if(nextResultStore != null){
					propagateEdgesAndPrefixes(next, nextResult, nextNextAlternative, nextResultStore, potentialNewEdges);
				}else{
					nextNextAlternative.updateNode(next, nextResult);
				}
			}

			// Handle alternative nexts (and prefix sharing).
			AbstractStackNode[][] alternateProds = node.getAlternateProductions();
			if(alternateProds != null){
				IntegerObjectList<ArrayList<AbstractStackNode>> nextEdgesMap = next.getEdges();
				ArrayList<Link>[] nextPrefixesMap = next.getPrefixesMap();
				
				for(int i = alternateProds.length - 1; i >= 0; --i){
					prod = alternateProds[i];
					if(nextDot == prod.length) continue;
					AbstractStackNode alternativeNext = prod[nextDot];
					
					AbstractStackNode nextNextAltAlternative = sharedNextNodes.get(alternativeNext.getId());
					if(nextNextAlternative.isMatchable()){
						if(nextNextAlternative.isEmptyLeafNode()){
							propagateAlternativeEdgesAndPrefixes(next, nextResult, nextNextAltAlternative, nextNextAltAlternative.getResult(), potentialNewEdges, nextEdgesMap, nextPrefixesMap);
						}else{
							nextNextAltAlternative.updatePrefixSharedNode(nextEdgesMap, nextPrefixesMap);
						}
					}else{
						AbstractContainerNode nextAltResultStore = levelResultStoreMap.get(nextNextAltAlternative.getIdentifier());
						if(nextAltResultStore != null){
							propagateAlternativeEdgesAndPrefixes(next, nextResult, nextNextAltAlternative, nextAltResultStore, potentialNewEdges, nextEdgesMap, nextPrefixesMap);
						}else{
							nextNextAltAlternative.updatePrefixSharedNode(nextEdgesMap, nextPrefixesMap);
						}
					}
				}
			}
		}
	}
	
	private void updateEdges(AbstractStackNode node, AbstractNode result){
		IntegerObjectList<ArrayList<AbstractStackNode>> edgesMap = node.getEdges();
		ArrayList<Link>[] prefixesMap = node.getPrefixesMap();
		
		for(int i = edgesMap.size() - 1; i >= 0; --i){
			Link resultLink = new Link((prefixesMap != null) ? prefixesMap[i] : null, result);
			
			handleEdgesList(edgesMap.getValue(i), resultLink, edgesMap.getKey(i));
		}
	}
	
	private void updateNullableEdges(AbstractStackNode node, AbstractNode result){
		IntegerList touched = propagatedReductions.findValue(node.getId());
		if(touched == null){
			touched = new IntegerList();
			propagatedReductions.add(node.getId(), touched);
		}
		
		IntegerObjectList<ArrayList<AbstractStackNode>> edgesMap = node.getEdges();
		ArrayList<Link>[] prefixesMap = node.getPrefixesMap();
		
		for(int i = edgesMap.size() - 1; i >= 0; --i){
			int startLocation = edgesMap.getKey(i);
			
			if(touched.contains(startLocation)) continue;
			touched.add(startLocation);
			
			Link resultLink = new Link((prefixesMap != null) ? prefixesMap[i] : null, result);
			
			handleEdgesList(edgesMap.getValue(i), resultLink, startLocation);
		}
	}
	
	private void handleEdgesList(ArrayList<AbstractStackNode> edgeList, Link resultLink, int startLocation){
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
		
		if(resultStore != null){
			resultStore.addAlternative(resultLink);
		}else{
			resultStore = (!edge.isExpandable()) ? new SortContainerNode(nodeName, startLocation == location, edge.isSeparator()) : new ListContainerNode(nodeName, startLocation == location, edge.isSeparator());
			levelResultStoreMap.putUnsafe(identifier, resultStore);
			resultStore.addAlternative(resultLink);
			
			stacksWithNonTerminalsToReduce.push(edge, resultStore);
			
			for(int j = edgeList.size() - 1; j >= 1; --j){
				edge = edgeList.get(j);
				stacksWithNonTerminalsToReduce.push(edge, resultStore);
			}
		}
	}
	
	private void moveToNext(AbstractStackNode node, AbstractNode result){
		int nextDot = node.getDot() + 1;

		AbstractStackNode[] prod = node.getProduction();
		AbstractStackNode newNext = prod[nextDot];
		newNext.setProduction(prod);
		AbstractStackNode next = updateNextNode(newNext, node, result);
		
		// Handle alternative nexts (and prefix sharing).
		AbstractStackNode[][] alternateProds = node.getAlternateProductions();
		if(alternateProds != null){
			IntegerObjectList<ArrayList<AbstractStackNode>> edgesMap = null;
			ArrayList<Link>[] prefixesMap = null;
			if(next != null){
				edgesMap = next.getEdges();
				prefixesMap = next.getPrefixesMap();
			}
			
			for(int i = alternateProds.length - 1; i >= 0; --i){
				prod = alternateProds[i];
				if(nextDot == prod.length) continue;
				AbstractStackNode newAlternativeNext = prod[nextDot];
				
				if(edgesMap != null){
					updateAlternativeNextNode(newAlternativeNext, node, result, edgesMap, prefixesMap);
				}else{
					AbstractStackNode alternativeNext = updateNextNode(newAlternativeNext, node, result);
					
					if(alternativeNext != null){
						edgesMap = alternativeNext.getEdges();
						prefixesMap = alternativeNext.getPrefixesMap();
					}
				}
			}
		}
	}
	
	private void move(AbstractStackNode node, AbstractNode result){
		if(node.isEndNode()){
			if(!result.isEmpty() || node.getId() == AbstractExpandableStackNode.DEFAULT_LIST_EPSILON_ID){
				updateEdges(node, result);
			}else{
				updateNullableEdges(node, result);
			}
		}
		
		if(node.hasNext()){
			moveToNext(node, result);
		}
	}
	
	private void reduce(){
		// Reduce terminals.
		while(!stacksWithTerminalsToReduce.isEmpty()){
			move(stacksWithTerminalsToReduce.peekFirst(), stacksWithTerminalsToReduce.popSecond());
		}
		
		// Reduce non-terminals.
		while(!stacksWithNonTerminalsToReduce.isEmpty()){
			move(stacksWithNonTerminalsToReduce.peekFirst(), stacksWithNonTerminalsToReduce.popSecond());
		}
	}
	
	private boolean findFirstStackToReduce(){
		for(int i = location; i < todoLists.length; ++i){
			DoubleStack<AbstractStackNode, AbstractNode> terminalsTodo = todoLists[i];
			if(!(terminalsTodo == null || terminalsTodo.isEmpty())){
				stacksWithTerminalsToReduce = terminalsTodo;
				
				location = i;
				return true;
			}
		}
		return false;
	}
	
	private boolean findStacksToReduce(){
		for(int i = location + 1; i < todoLists.length; ++i){
			DoubleStack<AbstractStackNode, AbstractNode> terminalsTodo = todoLists[i];
			if(!(terminalsTodo == null || terminalsTodo.isEmpty())){
				stacksWithTerminalsToReduce = terminalsTodo;
				
				todoLists[location] = null;
				location = i;
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
	
	private void handleExpects(AbstractStackNode stackBeingWorkedOn, AbstractStackNode[] expects){
		ArrayList<AbstractStackNode> cachedEdges = null;
		
		for(int i = expects.length - 1; i >= 0; --i){
			AbstractStackNode first = expects[i];
			
			if(first.isMatchable()){
				int endLocation = location + first.getLength();
				if(endLocation > input.length) continue;
				
				AbstractNode result = first.match(input, location);
				if(result == null) continue; // Discard if it didn't match.
				
				DoubleStack<AbstractStackNode, AbstractNode> terminalsTodo = todoLists[endLocation];
				if(terminalsTodo == null){
					terminalsTodo = new DoubleStack<AbstractStackNode, AbstractNode>();
					todoLists[endLocation] = terminalsTodo;
				}
				
				first = first.getCleanCopyWithResult(location, result);
				terminalsTodo.push(first, result);
			}else{
				first = first.getCleanCopy(location);
				stacksToExpand.push(first);
			}
			
			first.initEdges();
			if(cachedEdges == null){
				cachedEdges = first.addEdge(stackBeingWorkedOn);
			}else{
				first.addEdges(cachedEdges, location);
			}
		}
		
		cachedEdgesForExpect.put(stackBeingWorkedOn.getName(), cachedEdges);
	}
	
	private void expandStack(AbstractStackNode node){
		if(node.isMatchable()){
			int endLocation = location + node.getLength();
			DoubleStack<AbstractStackNode, AbstractNode> terminalsTodo = todoLists[endLocation];
			if(terminalsTodo == null){
				terminalsTodo = new DoubleStack<AbstractStackNode, AbstractNode>();
				todoLists[endLocation] = terminalsTodo;
			}
			
			terminalsTodo.push(node, node.getResult());
		}else if(!node.isExpandable()){
			ArrayList<AbstractStackNode> cachedEdges = cachedEdgesForExpect.get(node.getName());
			if(cachedEdges != null){
				cachedEdges.add(node);
				
				HashMap<String, AbstractContainerNode> levelResultStoreMap = resultStoreCache.get(location);
				if(levelResultStoreMap != null){
					AbstractContainerNode resultStore = levelResultStoreMap.get(node.getIdentifier());
					if(resultStore != null){ // Is nullable, add the known results.
						stacksWithNonTerminalsToReduce.push(node, resultStore);
					}
				}
			}else{
				AbstractStackNode[] expects = invokeExpects(node.getMethodName());
				if(expects == null) return;
				handleExpects(node, expects);
			}
		}else{ // 'List'
			AbstractStackNode[] listChildren = node.getChildren();
			
			for(int i = listChildren.length - 1; i >= 0; --i){
				AbstractStackNode child = listChildren[i];
				int childId = child.getId();
				if(!shareListNode(childId, node)){
					if(child.isMatchable()){
						int endLocation = location + child.getLength();
						if(endLocation > input.length) continue;
						
						AbstractNode result = child.match(input, location);
						if(result == null) continue; // Discard if it didn't match.
						
						DoubleStack<AbstractStackNode, AbstractNode> terminalsTodo = todoLists[endLocation];
						if(terminalsTodo == null){
							terminalsTodo = new DoubleStack<AbstractStackNode, AbstractNode>();
							todoLists[endLocation] = terminalsTodo;
						}
						
						child = child.getCleanCopyWithResult(location, result);
						terminalsTodo.push(child, result);
					}else{
						child = child.getCleanCopy(location);
						stacksToExpand.push(child);
					}
					
					sharedNextNodes.putUnsafe(childId, child);
					
					child.initEdges();
					child.addEdgeWithPrefix(node, null, location);
				}
			}
			
			if(node.canBeEmpty()){ // Star list or optional.
				// This is always epsilon (and unique for this position); so shouldn't be shared.
				AbstractStackNode empty = node.getEmptyChild().getCleanCopy(location);
				empty.initEdges();
				empty.addEdge(node);
				
				stacksToExpand.push(empty);
			}
		}
	}
	
	private void expand(){
		while(!stacksToExpand.isEmpty()){
			expandStack(stacksToExpand.pop());
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
		rootNode = rootNode.getCleanCopy(0);
		rootNode.initEdges();
		stacksToExpand.push(rootNode);
		expand();
		
		if(findFirstStackToReduce()){
			boolean shiftedLevel = (location != 0);
			do{
				if(shiftedLevel){ // Nullable fix for the first level.
					sharedNextNodes.clear();
					resultStoreCache.clear();
					cachedEdgesForExpect.clear();
					propagatedPrefixes.dirtyClear();
					propagatedReductions.dirtyClear();
				}
				
				do{
					reduce();
					
					expand();
				}while(!stacksWithNonTerminalsToReduce.isEmpty() || !stacksWithTerminalsToReduce.isEmpty());
				shiftedLevel = true;
			}while(findStacksToReduce());
		}
		
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
