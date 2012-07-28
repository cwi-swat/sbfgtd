package gtd;

import gtd.result.AbstractContainerNode;
import gtd.result.AbstractNode;
import gtd.result.ListContainerNode;
import gtd.result.SortContainerNode;
import gtd.result.struct.Link;
import gtd.stack.AbstractExpandableStackNode;
import gtd.stack.AbstractStackNode;
import gtd.stack.NonTerminalStackNode;
import gtd.stack.edge.EdgesSet;
import gtd.util.ArrayList;
import gtd.util.DoubleStack;
import gtd.util.EntryReturningIntegerKeyedHashMap;
import gtd.util.EntryReturningIntegerKeyedHashMap.Entry;
import gtd.util.HashMap;
import gtd.util.IntegerList;
import gtd.util.IntegerObjectList;
import gtd.util.Stack;

import java.lang.reflect.Method;

@SuppressWarnings("unchecked")
public class SGTDBF implements IGTD{
	protected final char[] input;
	
	private final DoubleStack<AbstractStackNode, AbstractNode>[] todoLists;
	
	private final Stack<AbstractStackNode> stacksToExpand;
	private DoubleStack<AbstractStackNode, AbstractNode> stacksWithTerminalsToReduce;
	private final DoubleStack<AbstractStackNode, AbstractNode> stacksWithNonTerminalsToReduce;
	
	private final HashMap<String, EdgesSet> cachedEdgesForExpect;
	
	private final EntryReturningIntegerKeyedHashMap<AbstractStackNode> sharedNextNodes;
	
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
		
		cachedEdgesForExpect = new HashMap<String, EdgesSet>();
		
		sharedNextNodes = new EntryReturningIntegerKeyedHashMap<AbstractStackNode>();
		
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
		Entry<AbstractStackNode> alternativeEntry = sharedNextNodes.get(next.getId());
		
		if(alternativeEntry != null){
			AbstractStackNode alternative = alternativeEntry.value;
			if(alternative == null){
				return null;
			}
			
			if(result.isEmpty()){
				if(alternative.isMatchable()){
					if(alternative.isEmptyLeafNode()){
						// Encountered stack 'overtake'.
						propagateEdgesAndPrefixes(node, result, alternative, alternative.getResult(), node.getEdges().size());
						return alternative;
					}
				}else{
					if(alternative.getStartLocation() == location){
						EdgesSet alternativeEdgesSet = alternative.getIncomingEdges();
						if(alternativeEdgesSet != null && alternativeEdgesSet.getLastVisistedLevel() == location){
							// Encountered stack 'overtake'.
							propagateEdgesAndPrefixes(node, result, alternative, alternativeEdgesSet.getLastResult(), node.getEdges().size());
							return alternative;
						}
					}
				}
			}
			
			alternative.updateNode(node, result);
			
			return alternative;
		}
		
		if(next.isMatchable()){
			if((location + next.getLength()) > input.length) return null;
			
			AbstractNode nextResult = next.match(input, location);
			if(nextResult == null){
				sharedNextNodes.putUnsafe(next.getId(), null);
				return null;
			}
			
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
	
	private boolean updateAlternativeNextNode(AbstractStackNode next, AbstractStackNode node, AbstractNode result, IntegerObjectList<EdgesSet> edgesMap, ArrayList<Link>[] prefixesMap){
		Entry<AbstractStackNode> alternativeEntry = sharedNextNodes.get(next.getId());
		if(alternativeEntry != null){
			AbstractStackNode alternative = alternativeEntry.value;
			if(alternative == null){
				return false;
			}
			
			if(result.isEmpty()){
				if(alternative.isMatchable()){
					if(alternative.isEmptyLeafNode()){
						// Encountered stack 'overtake'.
						propagateAlternativeEdgesAndPrefixes(node, result, alternative, alternative.getResult(), node.getEdges().size(), edgesMap, prefixesMap);
						return true;
					}
				}else{
					if(alternative.getStartLocation() == location){
						EdgesSet alternativeEdgesSet = alternative.getIncomingEdges();
						if(alternativeEdgesSet != null && alternativeEdgesSet.getLastVisistedLevel() == location){
							// Encountered stack 'overtake'.
							propagateAlternativeEdgesAndPrefixes(node, result, alternative, alternativeEdgesSet.getLastResult(), node.getEdges().size(), edgesMap, prefixesMap);
							return true;
						}
					}
				}
			}
			
			alternative.updatePrefixSharedNode(edgesMap, prefixesMap);
			
			return true;
		}
		
		if(next.isMatchable()){
			if((location + next.getLength()) > input.length) return false;
			
			AbstractNode nextResult = next.match(input, location);
			if(nextResult == null){
				sharedNextNodes.putUnsafe(next.getId(), null);
				return false;
			}
			
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
		
		IntegerObjectList<EdgesSet> edgesMap = node.getEdges();
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
			
			handleEdgeSet(edgesMap.getValue(i), resultLink, edgesMap.getKey(i));
		}
	}
	
	private void propagatePrefixes(AbstractStackNode next, AbstractNode nextResult, int nrOfAddedEdges){
		// Proceed with the tail of the production.
		int nextDot = next.getDot() + 1;
		AbstractStackNode[] prod = next.getProduction();
		AbstractStackNode nextNext = prod[nextDot];
		Entry<AbstractStackNode> nextNextAlternativeEntry = sharedNextNodes.get(nextNext.getId());
		AbstractStackNode nextNextAlternative = null;
		if(nextNextAlternativeEntry != null){
			nextNextAlternative = nextNextAlternativeEntry.value;
			if(nextNextAlternative != null){
				if(nextNextAlternative.isMatchable()){
					if(nextNextAlternative.isEmptyLeafNode()){
						propagateEdgesAndPrefixes(next, nextResult, nextNextAlternative, nextNextAlternative.getResult(), nrOfAddedEdges);
					}else{
						nextNextAlternative.updateNode(next, nextResult);
					}
				}else{
					EdgesSet nextNextAlternativeEdgesSet = nextNextAlternative.getIncomingEdges();
					if(nextNextAlternativeEdgesSet != null && nextNextAlternativeEdgesSet.getLastVisistedLevel() == location){
						propagateEdgesAndPrefixes(next, nextResult, nextNextAlternative, nextNextAlternativeEdgesSet.getLastResult(), nrOfAddedEdges);
					}else{
						nextNextAlternative.updateNode(next, nextResult);
					}
				}
			}
		}
		
		// Handle alternative nexts (and prefix sharing).
		AbstractStackNode[][] alternateProds = next.getAlternateProductions();
		if(alternateProds != null){
			if(nextNextAlternative == null){ // If the first continuation has not been initialized yet (it may be a matchable that didn't match), create a dummy version to construct the necessary edges and prefixes.
				if(!nextNext.isMatchable()) return; // Matchable, abort.
				nextNextAlternative = nextNext.getCleanCopy(location);
				nextNextAlternative.updateNode(next, nextResult);
			}
			
			IntegerObjectList<EdgesSet> nextNextEdgesMap = nextNextAlternative.getEdges();
			ArrayList<Link>[] nextNextPrefixesMap = nextNextAlternative.getPrefixesMap();
			
			for(int i = alternateProds.length - 1; i >= 0; --i){
				prod = alternateProds[i];
				if(nextDot == prod.length) continue;
				AbstractStackNode alternativeNext = prod[nextDot];
				
				Entry<AbstractStackNode> nextNextAltAlternativeEntry = sharedNextNodes.get(alternativeNext.getId());
				if(nextNextAltAlternativeEntry != null){
					AbstractStackNode nextNextAltAlternative = nextNextAltAlternativeEntry.value;
					if(nextNextAltAlternative == null) continue;
					
					if(nextNextAltAlternative.isMatchable()){
						if(nextNextAltAlternative.isEmptyLeafNode()){
							propagateAlternativeEdgesAndPrefixes(next, nextResult, nextNextAltAlternative, nextNextAltAlternative.getResult(), nrOfAddedEdges, nextNextEdgesMap, nextNextPrefixesMap);
						}else{
							nextNextAltAlternative.updatePrefixSharedNode(nextNextEdgesMap, nextNextPrefixesMap);
						}
					}else{
						EdgesSet nextAlternativeEdgesSet = nextNextAlternative.getIncomingEdges();
						if(nextAlternativeEdgesSet != null && nextAlternativeEdgesSet.getLastVisistedLevel() == location){
							propagateAlternativeEdgesAndPrefixes(next, nextResult, nextNextAltAlternative, nextAlternativeEdgesSet.getLastResult(), nrOfAddedEdges, nextNextEdgesMap, nextNextPrefixesMap);
						}else{
							nextNextAltAlternative.updatePrefixSharedNode(nextNextEdgesMap, nextNextPrefixesMap);
						}
					}
				}
			}
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
			propagatePrefixes(next, nextResult, nrOfAddedEdges);
		}
	}
	
	private void propagateAlternativeEdgesAndPrefixes(AbstractStackNode node, AbstractNode nodeResult, AbstractStackNode next, AbstractNode nextResult, int potentialNewEdges, IntegerObjectList<EdgesSet> edgesMap, ArrayList<Link>[] prefixesMap){
		next.updatePrefixSharedNode(edgesMap, prefixesMap);
		
		if(potentialNewEdges == 0) return;
		
		if(next.isEndNode()){
			propagateReductions(node, nodeResult, next, nextResult, potentialNewEdges);
		}
		
		if(next.hasNext()){
			propagatePrefixes(next, nextResult, potentialNewEdges);
		}
	}
	
	private void updateEdges(AbstractStackNode node, AbstractNode result){
		IntegerObjectList<EdgesSet> edgesMap = node.getEdges();
		ArrayList<Link>[] prefixesMap = node.getPrefixesMap();
		
		for(int i = edgesMap.size() - 1; i >= 0; --i){
			Link resultLink = new Link((prefixesMap != null) ? prefixesMap[i] : null, result);
			
			handleEdgeSet(edgesMap.getValue(i), resultLink, edgesMap.getKey(i));
		}
	}
	
	private void updateNullableEdges(AbstractStackNode node, AbstractNode result){
		IntegerList touched = propagatedReductions.findValue(node.getId());
		if(touched == null){
			touched = new IntegerList();
			propagatedReductions.add(node.getId(), touched);
		}
		
		IntegerObjectList<EdgesSet> edgesMap = node.getEdges();
		ArrayList<Link>[] prefixesMap = node.getPrefixesMap();
		
		for(int i = edgesMap.size() - 1; i >= 0; --i){
			int startLocation = edgesMap.getKey(i);
			
			if(touched.contains(startLocation)) continue;
			touched.add(startLocation);
			
			Link resultLink = new Link((prefixesMap != null) ? prefixesMap[i] : null, result);
			
			handleEdgeSet(edgesMap.getValue(i), resultLink, startLocation);
		}
	}
	
	private void handleEdgeSet(EdgesSet edgeSet, Link resultLink, int startLocation){
		AbstractContainerNode resultStore = null;
		if(edgeSet.getLastVisistedLevel() != location){
			AbstractStackNode edge = edgeSet.get(0);
			
			resultStore = (!edge.isExpandable()) ? new SortContainerNode(edge.getName(), startLocation == location, edge.isSeparator()) : new ListContainerNode(edge.getName(), startLocation == location, edge.isSeparator());
			
			stacksWithNonTerminalsToReduce.push(edge, resultStore);
			
			for(int j = edgeSet.size() - 1; j >= 1; --j){
				edge = edgeSet.get(j);
				stacksWithNonTerminalsToReduce.push(edge, resultStore);
			}
		
			edgeSet.setLastVisistedLevel(location);
			edgeSet.setLastResult(resultStore);
		}else{
			resultStore = edgeSet.getLastResult();
		}
		
		resultStore.addAlternative(resultLink);
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
			IntegerObjectList<EdgesSet> edgesMap = null;
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
	
	private void handleExpects(AbstractStackNode stackBeingWorkedOn, EdgesSet cachedEdges, AbstractStackNode[] expects){
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
			first.addEdges(cachedEdges, location);
		}
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
			EdgesSet cachedEdges = cachedEdgesForExpect.get(node.getName());
			if(cachedEdges == null){
				cachedEdges = new EdgesSet(1);
				
				cachedEdgesForExpect.put(node.getName(), cachedEdges);
				
				AbstractStackNode[] expects = invokeExpects(node.getMethodName());
				if(expects == null) return;
				
				handleExpects(node, cachedEdges, expects);
			}else{
				if(cachedEdges.getLastVisistedLevel() == location){ // Is nullable, add the known results.
					stacksWithNonTerminalsToReduce.push(node, cachedEdges.getLastResult());
				}
			}
			
			cachedEdges.add(node);
			
			node.setIncomingEdges(cachedEdges);
		}else{ // 'List'
			EdgesSet cachedEdges = cachedEdgesForExpect.get(node.getName());
			if(cachedEdges == null){
				cachedEdges = new EdgesSet();
				
				cachedEdgesForExpect.put(node.getName(), cachedEdges);
				
				AbstractStackNode[] listChildren = node.getChildren();
				
				for(int i = listChildren.length - 1; i >= 0; --i){
					AbstractStackNode child = listChildren[i];
					int childId = child.getId();
					
					Entry<AbstractStackNode> sharedChildEntry = sharedNextNodes.get(childId);
					AbstractStackNode sharedChild;
					if(sharedChildEntry != null && ((sharedChild = sharedChildEntry.value) != null)){
						sharedChild.setEdgesSetWithPrefix(cachedEdges, null, location);
					}else{
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
						
						child.initEdges();
						child.setEdgesSetWithPrefix(cachedEdges, null, location);
						
						sharedNextNodes.putUnsafe(childId, child);
					}
				}
				
				if(node.canBeEmpty()){ // Star list or optional.
					AbstractStackNode empty = node.getEmptyChild().getCleanCopy(location);
					empty.initEdges();
					empty.addEdges(cachedEdges, location);
					
					stacksToExpand.push(empty);
				}
			}
			
			if(cachedEdges.getLastVisistedLevel() == location){ // Is nullable, add the known results.
				stacksWithNonTerminalsToReduce.push(node, cachedEdges.getLastResult());
			}
			
			cachedEdges.add(node);
			
			node.setIncomingEdges(cachedEdges);
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
			EdgesSet rootNodeEdgesSet = rootNode.getIncomingEdges();
			if(rootNodeEdgesSet != null && rootNodeEdgesSet.getLastVisistedLevel() == input.length){
				return rootNodeEdgesSet.getLastResult(); // Success.
			}
		}
		
		// Parse error.
		throw new RuntimeException("Parse Error before: "+(location == Integer.MAX_VALUE ? 0 : location));
	}
}
