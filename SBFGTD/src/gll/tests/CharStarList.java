package gll.tests;

import gll.SGLL;
import gll.stack.ParseStackNode;
import gll.stack.CharListParseStackNode;
import gll.stack.LiteralParseStackNode;

/*
S ::= [a-z]*
*/
public class CharStarList extends SGLL{
	private final static ParseStackNode CHAR_LIST0 = new CharListParseStackNode(0, new char[][]{{'a', 'z'}}, new char[]{}, "[a-z]", "[a-z]*", false);
	private final static ParseStackNode LITERAL_a1 = new LiteralParseStackNode(new char[]{'a'}, 1);
	
	public CharStarList(char[] input){
		super(input);
	}
	
	public void S(){
		expect(CHAR_LIST0);
	}
	
	public void List0(){
		expect(LITERAL_a1);
	}
	
	public static void main(String[] args){
		CharStarList csl = new CharStarList("abc".toCharArray());
		System.out.println(csl.parse("S"));
		
		System.out.println("S([a-z]*([a-z](a),[a-z](b),[a-z](c))) <- good");
	}
}
