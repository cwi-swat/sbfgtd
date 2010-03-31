package gll.stack;

import gll.IGLL;
import gll.result.INode;
import gll.util.ArrayList;

public class TerminalListParseStackNode extends ParseStackNode{
	private final static char[] EMPTY = new char[]{};
	
	private final String productionName;
	private final String methodName;
	
	private final char[][] ranges;
	private final char[] characters;
	
	private final boolean isPlusList;
	
	private boolean marked;
	
	private INode result;
	
	public TerminalListParseStackNode(int id, char[][] ranges, char[] characters, boolean isPlusList, String productionName){
		super(id);
		
		this.ranges = ranges;
		this.characters = characters;
		
		this.isPlusList = isPlusList;
		
		this.productionName = productionName;
		methodName = "List".concat(String.valueOf(id));
		
		marked = false;
		
		result = null;
		
		nexts = new ArrayList<ParseStackNode>();
		edges = new ArrayList<ParseStackNode>();
		addNext(this); // Plus or star list.
	}
	
	public TerminalListParseStackNode(TerminalListParseStackNode terminalListParseStackNode){
		super(terminalListParseStackNode.id);
		
		ranges = terminalListParseStackNode.ranges;
		characters = terminalListParseStackNode.characters;
		
		isPlusList = terminalListParseStackNode.isPlusList;
		
		productionName = terminalListParseStackNode.productionName;
		methodName = terminalListParseStackNode.methodName;
		
		nexts = terminalListParseStackNode.nexts;
		edges = terminalListParseStackNode.edges;
		
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
		return productionName;
	}
	
	public ParseStackNode getCleanCopy(){
		return new TerminalListParseStackNode(this);
	}
	
	public ParseStackNode getCleanCopyWithPrefix(){
		TerminalListParseStackNode tpsn = new TerminalListParseStackNode(this);
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
		TerminalListNodeParseStackNode ntpsn = new TerminalListNodeParseStackNode((id | IGLL.LIST_CHILD_FLAG), ranges, characters, productionName);
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
