package gll.tests;

import gll.SGLL;
import gll.stack.CharListParseStackNode;
import gll.stack.ParseStackNode;

/*
S ::= [a-z]+
*/
public class CharPlusList extends SGLL{
	private final static ParseStackNode CHAR_LIST0 = new CharListParseStackNode(0, new char[][]{{'a', 'z'}}, new char[]{}, "[a-z]", "[a-z]+", true);
	
	public CharPlusList(char[] input){
		super(input);
	}
	
	public void S(){
		expect(CHAR_LIST0);
	}
	
	public static void main(String[] args){
		CharPlusList cpl = new CharPlusList("abc".toCharArray());
		System.out.println(cpl.parse("S"));
		
		System.out.println("S([a-z]+([a-z]+([a-z]+([a-z](a)),[a-z](b)),[a-z](c))) <- good");
	}
}
