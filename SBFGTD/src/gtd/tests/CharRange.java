package gtd.tests;

import gtd.SGTDBF;
import gtd.preprocessing.ExpectBuilder;
import gtd.result.AbstractNode;
import gtd.stack.AbstractStackNode;
import gtd.stack.CharRangeStackNode;

/*
S ::= [a-z]
*/
public class CharRange extends SGTDBF{
	private final static AbstractStackNode CHAR_a0 = new CharRangeStackNode(0, 0, "[a-z]", new char[][]{{'a','z'}});
	
	public CharRange(char[] input){
		super(input);
	}
	
	private final static AbstractStackNode[] S_EXPECT;
	static{
		ExpectBuilder eb = new ExpectBuilder();
		eb.addAlternative(CHAR_a0);
		S_EXPECT = eb.buildExpectMatrix();
	}
	
	public AbstractStackNode[] S(){
		return S_EXPECT;
	}
	
	public static void main(String[] args){
		CharRange cr = new CharRange("a".toCharArray());
		AbstractNode result = cr.parse("S");
		System.out.println(result);
		
		System.out.println("S([a-z](a)) <- good");
	}
}
