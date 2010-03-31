package gll.stack;

import gll.IGLL;
import gll.result.INode;
import gll.util.ArrayList;

public class NonTerminalListParseStackNode extends ParseStackNode{
	private final static char[] EMPTY = new char[]{};
	
	private final String listChild;
	
	private final boolean isPlusList;

	private final String nodeName;
	private final String methodName;
	
	private boolean marked;
	
	private INode result;
	
	public NonTerminalListParseStackNode(int id, String listChild, boolean isPlusList){
		super(id);
		
		this.listChild = listChild;
		
		this.isPlusList = isPlusList;

		nodeName = "List".concat(String.valueOf(id)); // TODO Here till I find something better.
		methodName = "List".concat(String.valueOf(id));
		
		marked = false;
		
		result = null;
	}
	
	public NonTerminalListParseStackNode(NonTerminalListParseStackNode nonTerminalListParseStackNode){
		super(nonTerminalListParseStackNode.id);
		
		listChild = nonTerminalListParseStackNode.listChild;
		
		isPlusList = nonTerminalListParseStackNode.isPlusList;

		nodeName = nonTerminalListParseStackNode.nodeName;
		methodName = nonTerminalListParseStackNode.methodName;
		
		nexts = nonTerminalListParseStackNode.nexts;
		edges = nonTerminalListParseStackNode.edges;
		
		marked = false;
		
		result = null;
	}
	
	public boolean isReducable(){
		return false;
	}
	
	public boolean isList(){
		return true;
	}
	
	public String getMethodName(){
		return methodName;
	}
	
	public boolean reduce(char[] input, int location){
		throw new UnsupportedOperationException();
	}
	
	public String getNodeName(){
		return nodeName;
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
	
	public ParseStackNode[] getListChildren(){
		NonTerminalListNodeParseStackNode ntpsn = new NonTerminalListNodeParseStackNode(listChild, (id | IGLL.LIST_CHILD_FLAG), new ArrayList<INode>());
		ntpsn.addNext(ntpsn); // Self 'next' loop.
		if(isPlusList){
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
