package gll.stack;

import gll.result.INode;
import gll.result.TextNode;

public class CharListNodeParseStackNode extends ParseStackNode{
	private final char[][] ranges;
	private final char[] characters;
	
	private final String production;
	
	private INode result;
	
	public CharListNodeParseStackNode(int id, char[][] ranges, char[] characters, String production){
		super(id);
		
		this.ranges = ranges;
		this.characters = characters;
		
		this.production = production;
	}
	
	private CharListNodeParseStackNode(CharListNodeParseStackNode terminalListNodeParseStackNode){
		super(terminalListNodeParseStackNode.id);

		ranges = terminalListNodeParseStackNode.ranges;
		characters = terminalListNodeParseStackNode.characters;
		
		production = terminalListNodeParseStackNode.production;
		
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
		throw new UnsupportedOperationException();
	}
	
	private TextNode createResult(char character){
		int productionNameLength = production.length();
		char[] resultText = new char[productionNameLength + 3];
		production.getChars(0, productionNameLength, resultText, 0);
		resultText[productionNameLength] = '(';
		resultText[productionNameLength + 1] = character;
		resultText[productionNameLength + 2] = ')';
		return new TextNode(resultText);
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
		return production;
	}
	
	public ParseStackNode getCleanCopy(){
		return new CharListNodeParseStackNode(this);
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
		sb.append(production);
		sb.append(getId());
		sb.append('(');
		sb.append(startLocation);
		sb.append(',');
		sb.append(startLocation + getLength());
		sb.append(')');
		
		return sb.toString();
	}
}
