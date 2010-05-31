package gll.stack;

import gll.result.INode;
import gll.result.LiteralNode;

public final class CaseInsensitiveLiteralStackNode extends AbstractStackNode implements IReducableStackNode{
	private final char[][] ciLiteral;
	
	private LiteralNode result;
	
	public CaseInsensitiveLiteralStackNode(int id, char[] ciLiteral){
		super(id);
		
		int nrOfCharacters = ciLiteral.length;
		this.ciLiteral = new char[nrOfCharacters][];
		for(int i = nrOfCharacters - 1; i >= 0; i--){
			char character = ciLiteral[i];
			int type = Character.getType(character);
			if(type == Character.LOWERCASE_LETTER){
				this.ciLiteral[i] = new char[]{character, Character.toUpperCase(character)};
			}else if(type == Character.UPPERCASE_LETTER){
				this.ciLiteral[i] = new char[]{character, Character.toLowerCase(character)};
			}else{
				this.ciLiteral[i] = new char[]{character};
			}
		}
	}
	
	private CaseInsensitiveLiteralStackNode(CaseInsensitiveLiteralStackNode contextInsensitiveLiteralParseStackNode){
		super(contextInsensitiveLiteralParseStackNode);

		ciLiteral = contextInsensitiveLiteralParseStackNode.ciLiteral;
		
		result = null;
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
		
		result = new LiteralNode(resultLiteral);
		return true;
	}
	
	public boolean isClean(){
		return true;
	}
	
	public AbstractStackNode getCleanCopy(){
		return new CaseInsensitiveLiteralStackNode(this);
	}
	
	public AbstractStackNode getCleanCopyWithPrefix(){
		CaseInsensitiveLiteralStackNode cilpsn = new CaseInsensitiveLiteralStackNode(this);
		cilpsn.prefixes = prefixes;
		cilpsn.prefixStartLocations = prefixStartLocations;
		return cilpsn;
	}
	
	public int getLength(){
		return ciLiteral.length;
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
