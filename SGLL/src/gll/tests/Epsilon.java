package gll.tests;

import gll.SGLL;
import gll.stack.EpsilonStackNode;
import gll.stack.LiteralStackNode;
import gll.stack.NonTerminalStackNode;
import gll.stack.StackNode;

/*
S ::= Aepsilon
A ::= a
*/
public class Epsilon extends SGLL{
	private final static StackNode NONTERMINAL_A0 = new NonTerminalStackNode(0, "A");
	private final static StackNode EPSILON_1 = new EpsilonStackNode(1);
	private final static StackNode NONTERMINAL_a2 = new LiteralStackNode(2, new char[]{'a'});
	
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
		System.out.println(e.parse("S"));
		
		System.out.println("S(A(a),) <- good");
	}
}