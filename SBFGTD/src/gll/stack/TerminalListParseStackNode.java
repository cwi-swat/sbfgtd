package gll.stack;

import gll.IGLL;
import gll.result.INode;

public class TerminalListParseStackNode extends ParseStackNode{
	private final static TerminalParseStackNode NO_MATCHING_TERMINAL_FOUND = new TerminalParseStackNode(new char[]{0}, IGLL.TERMINAL_LIST_CHILD_NOT_FOUND_ID);
	
	private final String methodName;
	
	private final char[][] ranges;
	private final char[] characters;
	
	private final boolean firstRequired;
	
	private boolean marked;
	
	private INode result;
	
	public TerminalListParseStackNode(int id, char[][] ranges, char[] characters, boolean isPlusList){
		super(id);
		
		this.ranges = ranges;
		this.characters = characters;
		
		firstRequired = isPlusList;
		
		methodName = String.valueOf(id);
		
		marked = false;
		
		result = null;
	}
	
	public TerminalListParseStackNode(TerminalListParseStackNode terminalListParseStackNode){
		super(terminalListParseStackNode.id);
		
		ranges = terminalListParseStackNode.ranges;
		characters = terminalListParseStackNode.characters;
		
		firstRequired = terminalListParseStackNode.firstRequired;

		methodName = terminalListParseStackNode.methodName;
		
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
	
	public String getNonTerminalName(){
		throw new UnsupportedOperationException();
	}
	
	public ParseStackNode getCleanCopy(){
		return new TerminalListParseStackNode(this);
	}
	
	public ParseStackNode getCleanCopyWithPrefix(){
		TerminalListParseStackNode ntpsn = new TerminalListParseStackNode(this);
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
	
	public ParseStackNode getNextListChild(char[] input, int position){
		char next = input[position];
		for(int i = ranges.length - 1; i >= 0; i--){
			char[] range = ranges[i];
			if(next >= range[0] && next <= range[1]){
				TerminalParseStackNode tpsn = new TerminalParseStackNode(new char[]{next}, IGLL.TERMINAL_LIST_CHILD_ID);
				tpsn.addEdge(this);
				return tpsn;
			}
		}
		
		for(int i = characters.length - 1; i >= 0; i--){
			if(next == characters[i]){
				TerminalParseStackNode tpsn = new TerminalParseStackNode(new char[]{next}, IGLL.TERMINAL_LIST_CHILD_ID);
				tpsn.addEdge(this);
				return tpsn;
			}
		}
		
		return NO_MATCHING_TERMINAL_FOUND;
	}
	
	public void addResult(INode result){
		this.result = result;
	}
	
	public INode getResult(){
		return result;
	}
}
