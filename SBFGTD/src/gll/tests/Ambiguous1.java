package gll.tests;

import gll.SGLL;
import gll.stack.NonTerminalParseStackNode;
import gll.stack.ParseStackNode;
import gll.stack.LiteralParseStackNode;

/*
S ::= A | a
A ::= a
*/
public class Ambiguous1 extends SGLL{
	private final static ParseStackNode NONTERMINAL_A0 = new NonTerminalParseStackNode("A", 0);
	private final static ParseStackNode LITERAL_a1 = new LiteralParseStackNode("a".toCharArray(), 1);
	
	public Ambiguous1(char[] input){
		super(input);
	}
	
	public void S(){
		expect(NONTERMINAL_A0);
		
		expect(LITERAL_a1);
	}
	
	public void A(){
		expect(LITERAL_a1);
	}
	
	public static void main(String[] args){
		Ambiguous1 a1 = new Ambiguous1("a".toCharArray());
		System.out.println(a1.parse("S"));
		
		System.out.println("[S(A(a)),S(a)] <- good");
	}
}
