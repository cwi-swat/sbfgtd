package gll.tests;

import gll.SGLL;
import gll.stack.EpsilonParseStackNode;
import gll.stack.LiteralParseStackNode;
import gll.stack.NonTerminalParseStackNode;
import gll.stack.ParseStackNode;

/*
S ::= Aepsilon
A ::= a
*/
public class Epsilon extends SGLL{
	private final static ParseStackNode NONTERMINAL_A0 = new NonTerminalParseStackNode("A", 0);
	private final static ParseStackNode EPSILON_1 = new EpsilonParseStackNode(1);
	private final static ParseStackNode NONTERMINAL_a2 = new LiteralParseStackNode(new char[]{'a'}, 2);
	
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