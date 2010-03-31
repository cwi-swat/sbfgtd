package gll.stack;

import gll.IGLL;
import gll.result.INode;
import gll.util.ArrayList;

// TODO Add list code.
public class NonTerminalListParseStackNode extends ParseStackNode{
	private final static char[] EMPTY = new char[]{};
	
	private final String listChild;

	private final String nodeName;
	private final String methodName;
	
	private boolean firstRequired;
	
	private boolean marked;
	
	private INode result;
	
	public NonTerminalListParseStackNode(int id, String listChild, boolean isPlusList){
		super(id);
		
		this.listChild = listChild;
		
		firstRequired = isPlusList;

		nodeName = "List".concat(String.valueOf(id)); // TODO Here till I find something better.
		methodName = "List".concat(String.valueOf(id));
		
		marked = false;
		
		result = null;
		
		addNext((!firstRequired) ? this : new NonTerminalListParseStackNode(this, false));
		edges = new ArrayList<ParseStackNode>();
	}
	
	public NonTerminalListParseStackNode(NonTerminalListParseStackNode nonTerminalListParseStackNode){
		super(nonTerminalListParseStackNode.id);
		
		listChild = nonTerminalListParseStackNode.listChild;
		
		firstRequired = nonTerminalListParseStackNode.firstRequired;

		nodeName = nonTerminalListParseStackNode.nodeName;
		methodName = nonTerminalListParseStackNode.methodName;
		
		nexts = nonTerminalListParseStackNode.nexts;
		edges = nonTerminalListParseStackNode.edges;
		
		marked = false;
		
		result = null;
	}
	
	public NonTerminalListParseStackNode(NonTerminalListParseStackNode nonTerminalListParseStackNode, boolean firstRequired){
		super(nonTerminalListParseStackNode.id);
		
		listChild = nonTerminalListParseStackNode.listChild;
		
		this.firstRequired = firstRequired;
		
		nodeName = nonTerminalListParseStackNode.nodeName;
		methodName = nonTerminalListParseStackNode.methodName;
		
		nexts = nonTerminalListParseStackNode.nexts;
		edges = nonTerminalListParseStackNode.edges;
		
		marked = false;
		
		result = null;
	}
	
	public boolean isNonTerminal(){
		return false;
	}
	
	public boolean isTerminal(){
		return false;
	}
	
	public boolean isList(){
		return true;
	}
	
	public String getMethodName(){
		return methodName;
	}
	
	public char[] getTerminalData(){
		throw new UnsupportedOperationException();
	}
	
	public String getNodeName(){
		throw new UnsupportedOperationException();
	}
	
	public ParseStackNode getCleanCopy(){
		return new NonTerminalListParseStackNode(this);
	}
	
	public ParseStackNode getCleanCopyWithPrefix(){
		NonTerminalListParseStackNode ntpsn = new NonTerminalListParseStackNode(this);
		ntpsn.prefixes = prefixes;
		ntpsn.prefixStartLocations = prefixStartLocations;
		return ntpsn;
	}
	
	public int getLength(){
		throw new UnsupportedOperationException();
	}
	
	public void mark(){
		marked = true;
	}
	
	public boolean isMarked(){
		return marked;
	}
	
	public ParseStackNode[] getNextChildren(char[] input, int position){
		NonTerminalParseStackNode ntpsn = new NonTerminalParseStackNode(listChild, (id | IGLL.LIST_CHILD_FLAG));
		if(!firstRequired){
			return new ParseStackNode[]{ntpsn};
		}
		
		return new ParseStackNode[]{ntpsn, new TerminalParseStackNode(EMPTY, id | IGLL.LIST_CHILD_FLAG)};
	}
	
	public void addResult(INode result){
		this.result = result;
	}
	
	public INode getResult(){
		return result;
	}
}
