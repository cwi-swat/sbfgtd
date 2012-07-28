package gtd.stack;

import gtd.result.AbstractNode;
import gtd.result.LiteralNode;

public final class CaseInsensitiveLiteralStackNode extends AbstractMatchableStackNode{
	private final char[][] ciLiteral;
	
	private final AbstractNode result;
	
	public CaseInsensitiveLiteralStackNode(int id, int dot, char[] ciLiteral){
		super(id, dot);
		
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
		
		result = null;
	}
	
	private CaseInsensitiveLiteralStackNode(CaseInsensitiveLiteralStackNode original, int startLocation){
		super(original, startLocation);

		ciLiteral = original.ciLiteral;
		
		result = null;
	}
	
	private CaseInsensitiveLiteralStackNode(CaseInsensitiveLiteralStackNode original, int startLocation, AbstractNode result){
		super(original, startLocation);
		
		this.ciLiteral = original.ciLiteral;
		
		this.result = result;
	}
	
	public boolean isEmptyLeafNode(){
		return false;
	}
	
	public AbstractNode match(char[] input, int location){
		int literalLength = ciLiteral.length;
		char[] resultLiteral = new char[literalLength];
		OUTER : for(int i = literalLength - 1; i >= 0; --i){
			char[] ciLiteralPart = ciLiteral[i];
			for(int j = ciLiteralPart.length - 1; j >= 0; --j){
				char character = ciLiteralPart[j];
				if(character == input[location + i]){
					resultLiteral[i] = character;
					continue OUTER;
				}
			}
			return null; // Did not match.
		}
		
		return new LiteralNode(resultLiteral);
	}
	
	public AbstractStackNode getCleanCopy(int startLocation){
		return new CaseInsensitiveLiteralStackNode(this, startLocation);
	}
	
	public AbstractStackNode getCleanCopyWithResult(int startLocation, AbstractNode result){
		return new CaseInsensitiveLiteralStackNode(this, startLocation, result);
	}
	
	public int getLength(){
		return ciLiteral.length;
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
		sb.append(')');
		
		return sb.toString();
	}
}
