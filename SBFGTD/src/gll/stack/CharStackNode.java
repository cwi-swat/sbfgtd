package gll.stack;

import gll.result.CharNode;
import gll.result.ContainerNode;
import gll.result.INode;
import gll.util.ArrayList;
import gll.util.IntegerList;

public final class CharStackNode extends AbstractStackNode implements IReducableStackNode{
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
	
	private CharStackNode(CharStackNode original){
		super(original);
		
		ranges = original.ranges;
		characters = original.characters;
		
		production = original.production;
	}
	
	private CharStackNode(CharStackNode original, ArrayList<INode[]> prefixes, IntegerList prefixStartLocations){
		super(original, prefixes, prefixStartLocations);
		
		ranges = original.ranges;
		characters = original.characters;
		
		production = original.production;
	}
	
	public String getName(){
		throw new UnsupportedOperationException();
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
	
	public boolean isClean(){
		return true;
	}
	
	public AbstractStackNode getCleanCopy(){
		return new CharStackNode(this);
	}
	
	public AbstractStackNode getCleanCopyWithPrefix(){
		return new CharStackNode(this, prefixes, prefixStartLocations);
	}
	
	public void setResultStore(ContainerNode resultStore){
		throw new UnsupportedOperationException();
	}
	
	public int getLength(){
		return 1;
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
