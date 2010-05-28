package gll.tests;

import gll.SGLL;
import gll.stack.EpsilonStackNode;
import gll.stack.LiteralStackNode;
import gll.stack.NonTerminalStackNode;
import gll.stack.AbstractStackNode;

/*
S ::= Aepsilon
A ::= a
*/
public class Epsilon extends SGLL{
	private final static AbstractStackNode NONTERMINAL_A0 = new NonTerminalStackNode(0, "A");
	private final static AbstractStackNode EPSILON_1 = new EpsilonStackNode(1);
	private final static AbstractStackNode NONTERMINAL_a2 = new LiteralStackNode(2, new char[]{'a'});
	
	public Epsilon(char[] input){
		super(input);
	}
	
	public void S(){
		expect(NONTERMINAL_A0, EPSILON_1);
	}
	
	public void A(){
		expect(NONTERMINAL_a2);
	}
	
	public static void main(String[] args){
		Epsilon e = new Epsilon("a".toCharArray());
		e.parse("S");
		System.out.println(e.getStringResult());
		
		System.out.println("S(A(a),) <- good");
	}
}