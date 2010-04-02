package gll.stack;

import gll.IGLL;
import gll.result.INode;

public class NonTerminalListParseStackNode extends ParseStackNode{
	private final static char[] EMPTY = new char[]{};
	
	private final String productionName;
	
	private final boolean isPlusList;
	
	private final String methodName;
	
	private boolean marked;
	
	private INode result;
	
	public NonTerminalListParseStackNode(int id, String productionName, boolean isPlusList){
		super(id);
		
		this.productionName = productionName;
		
		this.isPlusList = isPlusList;
		
		methodName = "List".concat(String.valueOf(id));
	}
	
	private NonTerminalListParseStackNode(NonTerminalListParseStackNode nonTerminalListParseStackNode){
		super(nonTerminalListParseStackNode.id);
		
		productionName = nonTerminalListParseStackNode.productionName;
		
		isPlusList = nonTerminalListParseStackNode.isPlusList;
		
		methodName = nonTerminalListParseStackNode.methodName;
		
		nexts = nonTerminalListParseStackNode.nexts;
		edges = nonTerminalListParseStackNode.edges;
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
	
	public boolean reduce(char[] input){
		throw new UnsupportedOperationException();
	}
	
	public String getNodeName(){
		return productionName + (isPlusList ? '+' : '*');
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
		NonTerminalListNodeParseStackNode ntpsn = new NonTerminalListNodeParseStackNode(productionName, (id | IGLL.LIST_CHILD_FLAG));
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

	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append(productionName);
		sb.append(getId());
		sb.append('(');
		sb.append(startLocation);
		sb.append(',');
		sb.append('?');
		sb.append(')');
		
		return sb.toString();
	}
}
