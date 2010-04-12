package gll.tests;

import gll.SGLL;
import gll.stack.NonTerminalStackNode;
import gll.stack.StackNode;
import gll.stack.LiteralStackNode;

/*
S ::= A | a
A ::= a
*/
public class Ambiguous1 extends SGLL{
	private final static StackNode NONTERMINAL_A0 = new NonTerminalStackNode("A", 0);
	private final static StackNode LITERAL_a1 = new LiteralStackNode(new char[]{'a'}, 1);
	
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
