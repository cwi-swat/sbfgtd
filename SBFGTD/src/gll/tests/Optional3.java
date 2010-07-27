package gll.tests;

import gll.SGLL;
import gll.result.AbstractNode;
import gll.stack.LiteralStackNode;
import gll.stack.NonTerminalStackNode;
import gll.stack.OptionalStackNode;
import gll.stack.AbstractStackNode;

/*
S ::= aO? | aA
O ::= A
A ::= a
*/
public class Optional3 extends SGLL{
	private final static AbstractStackNode LITERAL_a0 = new LiteralStackNode(0, new char[]{'a'});
	private final static AbstractStackNode LITERAL_a1 = new LiteralStackNode(1, new char[]{'a'});
	private final static AbstractStackNode LITERAL_a2 = new LiteralStackNode(2, new char[]{'a'});
	private final static AbstractStackNode NONTERMINAL_A3 = new NonTerminalStackNode(3, "A");
	private final static AbstractStackNode NONTERMINAL_A4 = new NonTerminalStackNode(4, "A");
	private final static AbstractStackNode NON_TERMINAL_O5 = new NonTerminalStackNode(5, "O");
	private final static AbstractStackNode OPTIONAL_6 = new OptionalStackNode(6, NON_TERMINAL_O5, "O?");
	
	public Optional3(char[] input){
		super(input);
	}
	
	public void S(){
		expect(LITERAL_a0, OPTIONAL_6);
		
		expect(LITERAL_a1, NONTERMINAL_A3);
	}
	
	public void A(){
		expect(LITERAL_a2);
	}
	
	public void O(){
		expect(NONTERMINAL_A4);
	}
	
	public static void main(String[] args){
		Optional3 o3 = new Optional3("aa".toCharArray());
		AbstractNode result = o3.parse("S");
		System.out.println(result);
		
		System.out.println("[S(a,O?(O(A(a)))),S(a,A(a))] <- good");
	}
}

