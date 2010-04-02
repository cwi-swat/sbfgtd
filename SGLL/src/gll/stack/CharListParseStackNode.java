package gll.stack;

import gll.IGLL;
import gll.result.INode;

public class CharListParseStackNode extends ParseStackNode{
	private final static char[] EMPTY = new char[]{};
	
	private final String productionName;
	private final String methodName;
	
	private final char[][] ranges;
	private final char[] characters;
	
	private final boolean isPlusList;
	
	private boolean marked;
	
	private INode result;
	
	public CharListParseStackNode(int id, char[][] ranges, char[] characters, String productionName, boolean isPlusList){
		super(id);
		
		this.ranges = ranges;
		this.characters = characters;
		
		this.productionName = productionName;
		
		this.isPlusList = isPlusList;
		
		methodName = "List".concat(String.valueOf(id));
	}
	
	private CharListParseStackNode(CharListParseStackNode terminalListParseStackNode){
		super(terminalListParseStackNode.id);
		
		ranges = terminalListParseStackNode.ranges;
		characters = terminalListParseStackNode.characters;
		
		isPlusList = terminalListParseStackNode.isPlusList;
		
		productionName = terminalListParseStackNode.productionName;
		methodName = terminalListParseStackNode.methodName;
		
		nexts = terminalListParseStackNode.nexts;
		edges = terminalListParseStackNode.edges;
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
		return productionName + (isPlusList ? '+' : '*'); // Meh
	}
	
	public ParseStackNode getCleanCopy(){
		return new CharListParseStackNode(this);
	}
	
	public ParseStackNode getCleanCopyWithPrefix(){
		CharListParseStackNode tpsn = new CharListParseStackNode(this);
		tpsn.prefixes = prefixes;
		tpsn.prefixStartLocations = prefixStartLocations;
		return tpsn;
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
		CharListNodeParseStackNode ntpsn = new CharListNodeParseStackNode((id | IGLL.LIST_CHILD_FLAG), ranges, characters, productionName);
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
