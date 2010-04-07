package gll.tests;

import gll.SGLL;
import gll.stack.ParseStackNode;
import gll.stack.CharListParseStackNode;
import gll.stack.LiteralParseStackNode;

/*
S ::= [a-z]+
*/
public class CharPlusList extends SGLL{
	private final static ParseStackNode CHAR_LIST0 = new CharListParseStackNode(0, new char[][]{{'a', 'z'}}, new char[]{}, "[a-z]", "[a-z]+", true);
	private final static ParseStackNode LITERAL_a1 = new LiteralParseStackNode("a".toCharArray(), 1);
	
	public CharPlusList(char[] input){
		super(input);
	}
	
	public void S(){
		expect(CHAR_LIST0);
	}
	
	public void List0(){
		expect(LITERAL_a1);
	}
	
	public static void main(String[] args){
		CharPlusList cpl = new CharPlusList("abc".toCharArray());
		System.out.println(cpl.parse("S"));
		
		System.out.println("S([a-z]+([a-z](a),[a-z](b),[a-z](c))) <- good");
	}
}
