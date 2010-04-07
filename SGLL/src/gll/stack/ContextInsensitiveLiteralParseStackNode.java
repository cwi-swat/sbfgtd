package gll.stack;

import gll.result.INode;
import gll.result.TextNode;

public class ContextInsensitiveLiteralParseStackNode extends ParseStackNode{
	private final char[][] ciLiteral;
	
	private TextNode result;
	
	public ContextInsensitiveLiteralParseStackNode(char[][] ciLiteral, int id){
		super(id);

		this.ciLiteral = ciLiteral;
	}
	
	private ContextInsensitiveLiteralParseStackNode(ContextInsensitiveLiteralParseStackNode contextInsensitiveLiteralParseStackNode){
		super(contextInsensitiveLiteralParseStackNode.id);

		ciLiteral = contextInsensitiveLiteralParseStackNode.ciLiteral;
		
		nexts = contextInsensitiveLiteralParseStackNode.nexts;
		edges = contextInsensitiveLiteralParseStackNode.edges;
		
		result = null;
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
	
	public boolean reduce(char[] input){
		int literalLength = ciLiteral.length;
		char[] resultLiteral = new char[literalLength];
		OUTER : for(int i = literalLength - 1; i >= 0; i--){
			char[] ciLiteralPart = ciLiteral[i];
			for(int j = ciLiteralPart.length - 1; j >= 0; j--){
				char character = ciLiteralPart[j];
				if(character == input[startLocation + i]){
					resultLiteral[i] = character;
					continue OUTER;
				}
			}
			return false; // Did not match.
		}
		
		result = new TextNode(resultLiteral);
		return true;
	}
	
	public String getNodeName(){
		throw new UnsupportedOperationException();
	}
	
	public ParseStackNode getCleanCopy(){
		return new ContextInsensitiveLiteralParseStackNode(this);
	}
	
	public ParseStackNode getCleanCopyWithPrefix(){
		ContextInsensitiveLiteralParseStackNode cilpsn = new ContextInsensitiveLiteralParseStackNode(this);
		cilpsn.prefixes = prefixes;
		cilpsn.prefixStartLocations = prefixStartLocations;
		return cilpsn;
	}
	
	public int getLength(){
		return ciLiteral.length;
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
		for(int i = 0; i < ciLiteral.length; i++){
			sb.append(ciLiteral[i][0]);
		}
		sb.append(getId());
		sb.append('(');
		sb.append(startLocation);
		sb.append(',');
		sb.append(startLocation + getLength());
		sb.append(')');
		
		return sb.toString();
	}
}
