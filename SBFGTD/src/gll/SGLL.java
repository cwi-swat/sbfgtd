package gll;

import gll.nodes.Alternative;
import gll.nodes.INode;
import gll.nodes.NonTerminalNode;
import gll.stack.NonTerminalParseStackNode;
import gll.stack.ParseStackNode;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class SGLL implements IGLL{
	private final byte[] input;
	
	private final List<ParseStackNode> todoList;
	
	// Updatable
	private final List<ParseStackNode> stacksToExpand;
	private List<ParseStackNode> stacksWithTerminalsToReduce;
	private final List<ParseStackNode> stacksWithNonTerminalsToReduce;
	private List<ParseStackNode[]> lastExpects;
	private List<ParseStackNode> possiblySharedExpects;
	private List<ParseStackNode> possiblySharedNextNodes;
	private List<ParseStackNode> possiblySharedEdgeNodes;
	
	private int location;
	
	private ParseStackNode rootNode;
	
	public SGLL(byte[] input){
		super();
		
		this.input = input;
		
		todoList = new ArrayList<ParseStackNode>();
		
		stacksToExpand = new ArrayList<ParseStackNode>();
		stacksWithTerminalsToReduce = new ArrayList<ParseStackNode>();
		stacksWithNonTerminalsToReduce = new ArrayList<ParseStackNode>();
		
		location = 0;
		
		rootNode = null;
	}
	
	public void expect(ParseStackNode... symbolsToExpect){
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
	
	private INode buildResult(String nonTerminalName, List<List<INode>> results){ // NOTE: I'm not entirely sure about this.
		List<INode> resultsCollection = new ArrayList<INode>();
		for(int i = results.size() - 1; i >= 0; i--){
			INode node = new NonTerminalNode(nonTerminalName, results.get(i));
			resultsCollection.add(node);
		}
		
		return new Alternative(resultsCollection);
	}
	
	private ParseStackNode updateNextNode(ParseStackNode node){
		if(node.startLocationIsSet()){
			if(node.getStartLocation() == location){
				return node;
			}
			
			for(int i = possiblySharedNextNodes.size() - 1; i >= 0; i--){
				ParseStackNode possibleAlternative = possiblySharedNextNodes.get(i);
				if(possibleAlternative.isSimilar(node)){
					return possibleAlternative;
				}
			}
			
			ParseStackNode copy = node.getCleanCopy();
			copy.setStartLocation(location);
			
			possiblySharedNextNodes.add(copy);
			stacksToExpand.add(copy);
			
			return copy;
		}
		
		node.setStartLocation(location);
		stacksToExpand.add(node);
		return node;
	}
	
	private ParseStackNode updateEdgeNode(ParseStackNode node){
		if(node.endLocationIsSet()){
			if(node.getEndLocation() == location){
				return node;
			}
			
			for(int i = possiblySharedEdgeNodes.size() - 1; i >= 0; i--){
				ParseStackNode possibleAlternative = possiblySharedEdgeNodes.get(i);
				if(possibleAlternative.isSimilar(node)){
					return possibleAlternative;
				}
			}
			
			ParseStackNode copy = node.getCleanWithPrefixCopy();
			copy.setStartLocation(node.getStartLocation());
			copy.setEndLocation(location);
			
			possiblySharedEdgeNodes.add(copy);
			stacksWithNonTerminalsToReduce.add(copy);
			
			return copy;
		}
		
		node.setEndLocation(location);
		stacksWithNonTerminalsToReduce.add(node);
		return node;
	}
	
	private void move(ParseStackNode node){
		List<List<INode>> results = node.getResults();
		
		// Move
		if(node.hasNexts()){
			List<ParseStackNode> nexts = node.getNexts();
			for(int i = nexts.size() - 1; i >= 0; i--){
				ParseStackNode next = nexts.get(i);
				next = updateNextNode(next);
				next.addPrefixResults(results);
			}
		}
		
		if(node.hasEdges()){
			List<ParseStackNode> edges = node.getEdges();
			for(int i = edges.size() - 1; i >= 0; i--){
				ParseStackNode edge = edges.get(i);
				edge = updateEdgeNode(edge);
				INode result = buildResult(edge.getNonTerminalName(), results);
				edge.addResult(result);
			}
		}else if(!node.hasNexts() && location == input.length){
			rootNode = node;
		}
	}
	
	private void reduceTerminal(ParseStackNode terminal){
		byte[] terminalData = terminal.getTerminalData();
		int startLocation = terminal.getStartLocation();
		
		for(int i = terminalData.length - 1; i >= 0; i--){
			if(terminalData[i] != input[startLocation + i]) return; // Did not match.
		}
		
		move(terminal);
	}
	
	private void reduceNonTerminal(ParseStackNode nonTerminal){
		move(nonTerminal);
	}
	
	private void reduce(){
		possiblySharedNextNodes = new ArrayList<ParseStackNode>();
		possiblySharedEdgeNodes = new ArrayList<ParseStackNode>();
		
		// Reduce terminals.
		while(stacksWithTerminalsToReduce.size() > 0){
			ParseStackNode terminal = stacksWithTerminalsToReduce.remove(stacksWithTerminalsToReduce.size() - 1);
			reduceTerminal(terminal);

			todoList.remove(terminal);
		}
		
		// Reduce non-terminals.
		while(stacksWithNonTerminalsToReduce.size() > 0){
			ParseStackNode nonTerminal = stacksWithNonTerminalsToReduce.remove(stacksWithNonTerminalsToReduce.size() - 1);
			reduceNonTerminal(nonTerminal);
		}
	}
	
	private void findStacksToReduce(){
		// Find the stacks that will progress the least.
		int closestNextLocation = Integer.MAX_VALUE;
		for(int i = todoList.size() - 1; i >= 0; i--){
			ParseStackNode node = todoList.get(i);
			int nextLocation = node.getStartLocation() + node.getLength();
			if(nextLocation < closestNextLocation){
				stacksWithTerminalsToReduce = new ArrayList<ParseStackNode>();
				stacksWithTerminalsToReduce.add(node);
				closestNextLocation = nextLocation;
			}else if(nextLocation == closestNextLocation){
				stacksWithTerminalsToReduce.add(node);
			}
		}
		
		location = closestNextLocation;
	}
	
	private void handleExpects(ParseStackNode stackBeingWorkedOn){
		OUTER : for(int i = lastExpects.size() - 1; i >= 0; i--){
			ParseStackNode[] expectedNodes = lastExpects.get(i);
			
			// Handle sharing (and loops).
			ParseStackNode first = expectedNodes[0].getCleanCopy();
			ParseStackNode current = first;
			ParseStackNode prev;
			int k = 1;
			for(int j = possiblySharedExpects.size() - 1; j >= 0; j--){
				ParseStackNode possiblySharedNode = possiblySharedExpects.get(j);
				ParseStackNode prevPossiblySharedNode = possiblySharedNode;
				if(possiblySharedNode.equalSymbol(current)){
					SHARE: for(; k < expectedNodes.length; k++){ // Shared.
						prevPossiblySharedNode = possiblySharedNode;
						List<ParseStackNode> nexts = possiblySharedNode.getNexts();
						if(nexts == null){
							break;
						}
						current = expectedNodes[k];
						for(int l = nexts.size() - 1; l >= 0; l--){
							possiblySharedNode = nexts.get(l);
							if(possiblySharedNode.equalSymbol(current)){
								continue SHARE;
							}
						}
						break;
					}
					
					if(k < expectedNodes.length){
						current = prevPossiblySharedNode;
						for(; k < expectedNodes.length; k++){ // Unshared.
							prev = current;
							current = expectedNodes[k].getCleanCopy();
							prev.addNext(current);
						}
						current.addEdge(stackBeingWorkedOn);
					}else{
						possiblySharedNode.addEdge(stackBeingWorkedOn);
					}
					
					continue OUTER;
				}
			}
			
			for(; k < expectedNodes.length; k++){ // Unshared.
				prev = current;
				current = expectedNodes[k].getCleanCopy();
				prev.addNext(current);
			}
			
			current.addEdge(stackBeingWorkedOn);
			
			first.setStartLocation(location);
			
			stacksToExpand.add(first);
			possiblySharedExpects.add(first);
		}
	}
	
	private void expandStack(ParseStackNode node){
		if(node.isTerminal()){
			if(location != node.getStartLocation()){
				throw new RuntimeException("WTF?");
			}
			if(location + node.getLength() <= input.length) todoList.add(node);
			return;
		}
		
		callMethod(node.getMethodName());
		
		handleExpects(node);
	}
	
	private void expand(){
		possiblySharedExpects = new ArrayList<ParseStackNode>();
		while(stacksToExpand.size() > 0){
			lastExpects = new ArrayList<ParseStackNode[]>();
			expandStack(stacksToExpand.remove(stacksToExpand.size() - 1));
		}
	}
	
	public INode parse(String start){
		// Initialize.
		ParseStackNode rootNode = new NonTerminalParseStackNode(start);
		rootNode.setStartLocation(0);
		stacksToExpand.add(rootNode);
		expand();
		
		do{
			findStacksToReduce();
			
			reduce();
			
			expand();
		}while(todoList.size() > 0);
		
		if(this.rootNode == null) throw new RuntimeException("Parse error.");
		
		return new NonTerminalNode("parsetree", this.rootNode.getResult());
	}
}
