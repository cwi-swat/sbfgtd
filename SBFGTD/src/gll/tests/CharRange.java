package gll.tests;

import gll.SGLL;
import gll.stack.CharStackNode;
import gll.stack.StackNode;

/*
S ::= [a-z]
*/
public class CharRange extends SGLL{
	private final static StackNode CHAR_a0 = new CharStackNode(new char[][]{{'a','z'}}, new char[]{}, 0, "[a-z]");
	
	public CharRange(char[] input){
		super(input);
	}
	
	public void S(){
		expect(CHAR_a0);
	}
	
	public static void main(String[] args){
		CharRange cr = new CharRange("a".toCharArray());
		System.out.println(cr.parse("S"));
		
		System.out.println("S([a-z](a)) <- good");
	}
}
