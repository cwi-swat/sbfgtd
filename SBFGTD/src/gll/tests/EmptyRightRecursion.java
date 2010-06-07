package gll.tests;

import gll.SGLL;
import gll.stack.AbstractStackNode;
import gll.stack.EpsilonStackNode;
import gll.stack.LiteralStackNode;
import gll.stack.NonTerminalStackNode;

/*
S ::= AB
A ::= a
B ::= B | epsilon
*/
public class EmptyRightRecursion extends SGLL{
	private final static AbstractStackNode NONTERMINAL_A0 = new NonTerminalStackNode(0, "A");
	private final static AbstractStackNode NONTERMINAL_B1 = new NonTerminalStackNode(1, "B");
	private final static AbstractStackNode NONTERMINAL_B2 = new NonTerminalStackNode(2, "B");
	private final static AbstractStackNode LITERAL_a3 = new LiteralStackNode(3, new char[]{'a'});
	private final static AbstractStackNode EPSILON_4 = new EpsilonStackNode(4);
	
	public EmptyRightRecursion(char[] input){
		super(input);
	}
	
	public void S(){
		expect(NONTERMINAL_A0, NONTERMINAL_B1);
	}
	
	public void A(){
		expect(LITERAL_a3);
	}
	
	public void B(){
		expect(NONTERMINAL_B2);
		
		expect(EPSILON_4);
	}
	
	public static void main(String[] args){
		EmptyRightRecursion erre = new EmptyRightRecursion("a".toCharArray());
		erre.parse("S");
		System.out.println(erre.getStringResult());
		
		System.out.println("S(A(a),[cycle(B,1),B()])");
	}
}
