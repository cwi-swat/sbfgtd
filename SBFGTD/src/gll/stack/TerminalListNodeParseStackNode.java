package gll.stack;

import gll.result.INode;
import gll.result.TerminalNode;

public class TerminalListNodeParseStackNode extends ParseStackNode{
	private final char[][] ranges;
	private final char[] characters;
	
	private final String productionName;
	
	private INode result;
	
	public TerminalListNodeParseStackNode(int id, char[][] ranges, char[] characters, String productionName){
		super(id);
		
		this.ranges = ranges;
		this.characters = characters;
		
		this.productionName = productionName;
	}
	
	public TerminalListNodeParseStackNode(TerminalListNodeParseStackNode terminalListNodeParseStackNode){
		super(terminalListNodeParseStackNode.id);

		ranges = terminalListNodeParseStackNode.ranges;
		characters = terminalListNodeParseStackNode.characters;
		
		productionName = terminalListNodeParseStackNode.productionName;
		
		nexts = terminalListNodeParseStackNode.nexts;
		edges = terminalListNodeParseStackNode.edges;
	}
	
	public boolean isReducable(){
		return true;
	}
	
	public boolean isList(){
		return false;
	}
	
	public String getMethodName(){
		return productionName;
	}
	
	public boolean isSimilar(ParseStackNode node){
		return false;
	}
	
	private INode createResult(char character){
		int productionNameLength = productionName.length();
		char[] resultText = new char[productionNameLength + 3];
		productionName.getChars(0, productionNameLength, resultText, 0);
		resultText[productionNameLength] = '(';
		resultText[productionNameLength + 1] = character;
		resultText[productionNameLength + 2] = ')';
		return new TerminalNode(resultText);
	}
	
	public boolean reduce(char[] input){
		if(input.length > startLocation){
			char next = input[startLocation];
			for(int i = ranges.length - 1; i >= 0; i--){
				char[] range = ranges[i];
				if(next >= range[0] && next <= range[1]){
					result = createResult(next);
					return true;
				}
			}
			
			for(int i = characters.length - 1; i >= 0; i--){
				if(next == characters[i]){
					result = createResult(next);
					return true;
				}
			}
		}
		
		return false;
	}
	
	public String getNodeName(){
		return productionName;
	}
	
	public ParseStackNode getCleanCopy(){
		return new TerminalListNodeParseStackNode(this);
	}
	
	public ParseStackNode getCleanCopyWithPrefix(){
		throw new UnsupportedOperationException();
	}
	
	public int getLength(){
		return 1;
	}
	
	public void mark(){
		throw new UnsupportedOperationException();
	}
	
	public boolean isMarked(){
		throw new UnsupportedOperationException();
	}
	
	public ParseStackNode[] getListChildren(){
		throw new UnsupportedOperationException();
	}
	
	public void addResult(INode result){
		throw new UnsupportedOperationException();
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
		sb.append(startLocation + 1);
		sb.append(')');
		
		return sb.toString();
	}
}
