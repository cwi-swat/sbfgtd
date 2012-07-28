package gtd.tests;

import gtd.SGTDBF;
import gtd.preprocessing.ExpectBuilder;
import gtd.result.AbstractNode;
import gtd.stack.AbstractStackNode;
import gtd.stack.CharRangeStackNode;
import gtd.stack.ListStackNode;

/*
S ::= [a-z]*
*/
public class CharStarList extends SGTDBF{
	private final static AbstractStackNode CHAR0 = new CharRangeStackNode(0, 0, "[a-z]", new char[][]{{'a', 'z'}});
	private final static AbstractStackNode LIST1 = new ListStackNode(1, 0, CHAR0, "[a-z]*", false);
	
	public CharStarList(char[] input){
		super(input);
	}
	
	private final static AbstractStackNode[] S_EXPECT;
	static{
		ExpectBuilder eb = new ExpectBuilder();
		eb.addAlternative(LIST1);
		S_EXPECT = eb.buildExpectMatrix();
	}
	
	public AbstractStackNode[] S(){
		return S_EXPECT;
	}
	
	public static void main(String[] args){
		CharStarList csl = new CharStarList("abc".toCharArray());
		AbstractNode result = csl.parse("S");
		System.out.println(result);
		
		System.out.println("S([a-z]*([a-z](a),[a-z](b),[a-z](c))) <- good");
	}
}
