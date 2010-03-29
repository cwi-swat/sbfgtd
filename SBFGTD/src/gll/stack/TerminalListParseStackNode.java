package gll.stack;

import gll.IGLL;
import gll.result.Alternative;
import gll.result.INode;
import gll.util.ArrayList;

// TODO Add list code.
public class TerminalListParseStackNode extends ParseStackNode{
	private final static TerminalParseStackNode NO_MATCHING_TERMINAL_FOUND = new TerminalParseStackNode(new char[]{0}, IGLL.TERMINAL_LIST_CHILD_NOT_FOUND_ID);
	
	private final String methodName;
	
	private final char[][] ranges;
	private final char[] characters;
	
	private boolean firstRequired;
	
	private int endLocation;
	
	private final ArrayList<INode> results;
	
	public TerminalListParseStackNode(int id, char[][] ranges, char[] characters, boolean isPlusList){
		super(id);
		
		this.ranges = ranges;
		this.characters = characters;
		
		firstRequired = isPlusList;
		
		methodName = String.valueOf(id);
		
		results = null;
		
		endLocation = -1;
	}
	
	public TerminalListParseStackNode(TerminalListParseStackNode terminalListParseStackNode){
		super(terminalListParseStackNode.id);
		
		ranges = terminalListParseStackNode.ranges;
		characters = terminalListParseStackNode.characters;
		
		firstRequired = terminalListParseStackNode.firstRequired;

		methodName = terminalListParseStackNode.methodName;
		
		endLocation = -1;
		
		results = new ArrayList<INode>();
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
		return endLocation - startLocation;
	}
	
	public void setEndLocation(int endLocation){
		this.endLocation = endLocation;
	}
	
	public boolean endLocationIsSet(){
		return (endLocation != -1);
	}
	
	public int getEndLocation(){
		return endLocation;
	}
	
	public ParseStackNode getNextListChild(char[] input, int position){
		char next = input[position];
		for(int i = ranges.length - 1; i >= 0; i--){
			char[] range = ranges[i];
			if(next >= range[0] && next <= range[1]){
				return new TerminalParseStackNode(new char[]{next}, IGLL.TERMINAL_LIST_CHILD_ID);
			}
		}
		
		for(int i = characters.length - 1; i >= 0; i--){
			if(next == characters[i]){
				return new TerminalParseStackNode(new char[]{next}, IGLL.TERMINAL_LIST_CHILD_ID);
			}
		}
		
		return NO_MATCHING_TERMINAL_FOUND;
	}
	
	public void addResult(INode result){
		results.add(result);
	}
	
	public INode getResult(){
		return new Alternative(results); // TODO Check if this is ok.
	}
}
