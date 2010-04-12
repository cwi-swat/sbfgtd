package gll.tests;

import gll.SGLL;
import gll.stack.LiteralStackNode;
import gll.stack.NonTerminalStackNode;
import gll.stack.OptionalStackNode;
import gll.stack.StackNode;

/*
S ::= aO?
O ::= a
*/
public class Optional2 extends SGLL{
	private final static StackNode LITERAL_a0 = new LiteralStackNode(0, new char[]{'a'});
	private final static StackNode LITERAL_a1 = new LiteralStackNode(1, new char[]{'a'});
	private final static StackNode NON_TERMINAL_O2 = new NonTerminalStackNode(2, "O");
	private final static StackNode OPTIONAL_3 = new OptionalStackNode(3, NON_TERMINAL_O2, "O?");
	
	public Optional2(char[] input){
		super(input);
	}
	
	public void S(){
		expect(LITERAL_a0, OPTIONAL_3);
	}
	
	public void O(){
		expect(LITERAL_a1);
	}
	
	public static void main(String[] args){
		Optional2 o2 = new Optional2("a".toCharArray());
		System.out.println(o2.parse("S"));
		System.out.println("S(a,O?()) <- good");
	}
}
