package gll.tests;

import gll.SGLL;
import gll.result.AbstractNode;
import gll.stack.LiteralStackNode;
import gll.stack.NonTerminalStackNode;
import gll.stack.OptionalStackNode;
import gll.stack.AbstractStackNode;

/*
S ::= aO?
O ::= a
*/
public class Optional1 extends SGLL{
	private final static AbstractStackNode LITERAL_a0 = new LiteralStackNode(0, new char[]{'a'});
	private final static AbstractStackNode LITERAL_a1 = new LiteralStackNode(1, new char[]{'a'});
	private final static AbstractStackNode NON_TERMINAL_O2 = new NonTerminalStackNode(2, "O");
	private final static AbstractStackNode OPTIONAL_3 = new OptionalStackNode(3, NON_TERMINAL_O2, "O?");
	
	public Optional1(char[] input){
		super(input);
	}
	
	public void S(){
		expect(LITERAL_a0, OPTIONAL_3);
	}
	
	public void O(){
		expect(LITERAL_a1);
	}
	
	public static void main(String[] args){
		Optional1 o1 = new Optional1("aa".toCharArray());
		AbstractNode result = o1.parse("S");
		System.out.println(result);
		
		System.out.println("S(a,O?(O(a))) <- good");
	}
}
