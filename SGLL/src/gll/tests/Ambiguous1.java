package gll.tests;

import gll.SGLL;
import gll.result.AbstractNode;
import gll.stack.NonTerminalStackNode;
import gll.stack.AbstractStackNode;
import gll.stack.LiteralStackNode;

/*
S ::= A | a
A ::= a
*/
public class Ambiguous1 extends SGLL{
	private final static AbstractStackNode NONTERMINAL_A0 = new NonTerminalStackNode(0, "A");
	private final static AbstractStackNode LITERAL_a1 = new LiteralStackNode(1, new char[]{'a'});
	private final static AbstractStackNode LITERAL_a2 = new LiteralStackNode(2, new char[]{'a'});
	
	public Ambiguous1(char[] input){
		super(input);
	}
	
	public void S(){
		expect(NONTERMINAL_A0);
		
		expect(LITERAL_a1);
	}
	
	public void A(){
		expect(LITERAL_a2);
	}
	
	public static void main(String[] args){
		Ambiguous1 a1 = new Ambiguous1("a".toCharArray());
		AbstractNode result = a1.parse("S");
		System.out.println(result);
		
		System.out.println("[S(A(a)),S(a)] <- good");
	}
}
