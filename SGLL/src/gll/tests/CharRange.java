package gll.tests;

import gll.SGLL;
import gll.result.AbstractNode;
import gll.stack.CharStackNode;
import gll.stack.AbstractStackNode;

/*
S ::= [a-z]
*/
public class CharRange extends SGLL{
	private final static AbstractStackNode CHAR_a0 = new CharStackNode(0, "[a-z]", new char[][]{{'a','z'}}, new char[]{});
	
	public CharRange(char[] input){
		super(input);
	}
	
	public void S(){
		expect(CHAR_a0);
	}
	
	public static void main(String[] args){
		CharRange cr = new CharRange("a".toCharArray());
		AbstractNode result = cr.parse("S");
		System.out.println(result);
		
		System.out.println("S([a-z](a)) <- good");
	}
}
