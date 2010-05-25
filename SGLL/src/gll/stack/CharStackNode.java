package gll.stack;

import gll.result.CharNode;
import gll.result.INode;

public final class CharStackNode extends AbstractStackNode{
	private final char[][] ranges;
	private final char[] characters;
	
	private final String production;
	
	private INode result;
	
	public CharStackNode(int id, String production, char[][] ranges, char[] characters){
		super(id);
		
		this.production = production;

		this.ranges = ranges;
		this.characters = characters;
	}
	
	private CharStackNode(CharStackNode charParseStackNode){
		super(charParseStackNode);
		
		ranges = charParseStackNode.ranges;
		characters = charParseStackNode.characters;
		
		production = charParseStackNode.production;
	}
	
	public boolean isReducable(){
		return true;
	}
	
	public boolean isList(){
		return false;
	}
	
	public boolean isEpsilon(){
		return false;
	}
	
	public String getMethodName(){
		throw new UnsupportedOperationException();
	}
	
	public boolean reduce(char[] input){
		char next = input[startLocation];
		for(int i = ranges.length - 1; i >= 0; i--){
			char[] range = ranges[i];
			if(next >= range[0] && next <= range[1]){
				result = new CharNode(production, next);
				return true;
			}
		}
		
		for(int i = characters.length - 1; i >= 0; i--){
			if(next == characters[i]){
				result = new CharNode(production, next);
				return true;
			}
		}
		
		return false;
	}
	
	public AbstractStackNode getCleanCopy(){
		return new CharStackNode(this);
	}
	
	public AbstractStackNode getCleanCopyWithPrefix(){
		CharStackNode cpsn = new CharStackNode(this);
		cpsn.prefixes = prefixes;
		cpsn.prefixStartLocations = prefixStartLocations;
		return cpsn;
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
	
	public AbstractStackNode[] getChildren(){
		throw new UnsupportedOperationException();
	}
	
	public void addResult(INode[] children){
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
