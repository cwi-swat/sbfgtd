package gtd.stack;

import gtd.result.AbstractNode;
import gtd.result.LiteralNode;

public final class CaseInsensitiveLiteralStackNode extends AbstractStackNode implements IMatchableStackNode{
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
	
	private CaseInsensitiveLiteralStackNode(CaseInsensitiveLiteralStackNode original){
		super(original);

		ciLiteral = original.ciLiteral;
		
		result = null;
	}
	
	private CaseInsensitiveLiteralStackNode(CaseInsensitiveLiteralStackNode original, AbstractNode result){
		super(original);
		
		this.ciLiteral = original.ciLiteral;
		
		this.result = result;
	}
	
	public boolean isEmptyLeafNode(){
		return false;
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
	
	public AbstractStackNode getCleanCopy(){
		return new CaseInsensitiveLiteralStackNode(this);
	}
	
	public AbstractStackNode getCleanCopyWithResult(AbstractNode result){
		return new CaseInsensitiveLiteralStackNode(this, result);
	}
	
	public int getLength(){
		return ciLiteral.length;
	}
	
	public AbstractStackNode[] getChildren(){
		throw new UnsupportedOperationException();
	}
	
	public boolean canBeEmpty(){
		throw new UnsupportedOperationException();
	}
	
	public AbstractStackNode getEmptyChild(){
		throw new UnsupportedOperationException();
	}

	public AbstractNode getResult(){
		throw new UnsupportedOperationException();
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
