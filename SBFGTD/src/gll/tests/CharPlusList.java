package gll.tests;

import gll.SGLL;
import gll.stack.CharStackNode;
import gll.stack.ListStackNode;
import gll.stack.StackNode;

/*
S ::= [a-z]+
*/
public class CharPlusList extends SGLL{
	private final static StackNode CHAR0 = new CharStackNode(0, "[a-z]", new char[][]{{'a', 'z'}}, new char[]{});
	private final static StackNode LIST1 = new ListStackNode(1, CHAR0, "[a-z]+", true);
	
	public CharPlusList(char[] input){
		super(input);
	}
	
	public void S(){
		expect(LIST1);
	}
	
	public static void main(String[] args){
		CharPlusList cpl = new CharPlusList("abc".toCharArray());
		System.out.println(cpl.parse("S"));
		
		System.out.println("S([a-z]+([a-z]+([a-z]+([a-z](a)),[a-z](b)),[a-z](c))) <- good");
	}
}
