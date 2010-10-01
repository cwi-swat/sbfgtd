package gll.tests;

import gll.SGLL;
import gll.result.AbstractNode;
import gll.stack.AbstractStackNode;
import gll.stack.LiteralStackNode;
import gll.stack.NonTerminalStackNode;

/*
S ::= AB | AC
A ::= a
B ::= a
C ::= a

NOTE: This test, tests prefix sharing.
*/
public class Ambiguous8 extends SGLL{
	private final static AbstractStackNode NONTERMINAL_A0 = new NonTerminalStackNode(0, 0, "A");
	private final static AbstractStackNode NONTERMINAL_B1 = new NonTerminalStackNode(1, 1, "B");
	private final static AbstractStackNode NONTERMINAL_C2 = new NonTerminalStackNode(2, 1, "C");
	private final static AbstractStackNode LITERAL_a3 = new LiteralStackNode(3, 0, new char[]{'a'});
	private final static AbstractStackNode LITERAL_a4 = new LiteralStackNode(4, 0, new char[]{'a'});
	private final static AbstractStackNode LITERAL_a5 = new LiteralStackNode(5, 0, new char[]{'a'});
	
	public Ambiguous8(char[] input){
		super(input);
	}
	
	public void S(){
		expect(NONTERMINAL_A0, NONTERMINAL_B1);
		
		expect(NONTERMINAL_A0, NONTERMINAL_C2);
	}
	
	public void A(){
		expect(LITERAL_a3);
	}
	
	public void B(){
		expect(LITERAL_a4);
		
	}
	
	public void C(){
		expect(LITERAL_a5);
	}
	
	public static void main(String[] args){
		Ambiguous8 a8 = new Ambiguous8("aa".toCharArray());
		AbstractNode result = a8.parse("S");
		System.out.println(result);
		
		System.out.println("[S(A(a),B(a)),S(A(a),C(a))] <- good");
	}
}
