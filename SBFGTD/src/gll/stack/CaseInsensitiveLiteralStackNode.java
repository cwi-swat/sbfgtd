package gll.stack;

import gll.result.AbstractNode;
import gll.result.ContainerNode;
import gll.result.LiteralNode;
import gll.result.struct.Link;
import gll.util.ArrayList;

public final class CaseInsensitiveLiteralStackNode extends AbstractStackNode implements IMatchableStackNode{
	private final char[][] ciLiteral;
	
	private LiteralNode result;
	
	public CaseInsensitiveLiteralStackNode(int id, char[] ciLiteral){
		super(id);
		
		int nrOfCharacters = ciLiteral.length;
		this.ciLiteral = new char[nrOfCharacters][];
		for(int i = nrOfCharacters - 1; i >= 0; --i){
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
	
	private CaseInsensitiveLiteralStackNode(int id, char[][] ciLiteral){
		super(id);

		this.ciLiteral = ciLiteral;
	}
	
	private CaseInsensitiveLiteralStackNode(CaseInsensitiveLiteralStackNode original){
		super(original);

		ciLiteral = original.ciLiteral;
	}
	
	private CaseInsensitiveLiteralStackNode(CaseInsensitiveLiteralStackNode original, ArrayList<Link>[] prefixes){
		super(original, prefixes);

		ciLiteral = original.ciLiteral;
	}
	
	public String getIdentifier(){
		throw new UnsupportedOperationException();
	}
	
	public String getName(){
		throw new UnsupportedOperationException();
	}
	
	public String getMethodName(){
		throw new UnsupportedOperationException();
	}
	
	public boolean match(char[] input){
		int literalLength = ciLiteral.length;
		char[] resultLiteral = new char[literalLength];
		OUTER : for(int i = literalLength - 1; i >= 0; --i){
			char[] ciLiteralPart = ciLiteral[i];
			for(int j = ciLiteralPart.length - 1; j >= 0; --j){
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
		return new CaseInsensitiveLiteralStackNode(id, ciLiteral);
	}
	
	public AbstractStackNode getCleanCopyWithMark(){
		return new CaseInsensitiveLiteralStackNode(this);
	}

	public AbstractStackNode getCleanCopyWithPrefix(){
		return new CaseInsensitiveLiteralStackNode(this, prefixesMap);
	}
	
	public void setResultStore(ContainerNode resultStore){
		throw new UnsupportedOperationException();
	}
	
	public ContainerNode getResultStore(){
		throw new UnsupportedOperationException();
	}
	
	public int getLength(){
		return ciLiteral.length;
	}
	
	public AbstractStackNode[] getChildren(){
		throw new UnsupportedOperationException();
	}
	
	public AbstractNode getResult(){
		return result;
	}
	
	public String toString(){
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < ciLiteral.length; ++i){
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
