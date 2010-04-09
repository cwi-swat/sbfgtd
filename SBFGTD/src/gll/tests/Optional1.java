package gll.tests;

import gll.SGLL;
import gll.stack.LiteralParseStackNode;
import gll.stack.NonTerminalParseStackNode;
import gll.stack.OptionalParseStackNode;
import gll.stack.ParseStackNode;

/*
S ::= aO?
O ::= a
*/
public class Optional1 extends SGLL{
	private final static ParseStackNode LITERAL_a0 = new LiteralParseStackNode(new char[]{'a'}, 0);
	private final static ParseStackNode LITERAL_a1 = new LiteralParseStackNode(new char[]{'a'}, 1);
	private final static ParseStackNode NON_TERMINAL_O2 = new NonTerminalParseStackNode("O", 2);
	private final static ParseStackNode OPTIONAL_3 = new OptionalParseStackNode(3, NON_TERMINAL_O2, "O?");
	
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
		System.out.println(o1.parse("S"));
		System.out.println("S(a,O?(O(a))) <- good");
	}
}
