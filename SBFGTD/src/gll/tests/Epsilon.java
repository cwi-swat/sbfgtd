package gll.tests;

import gll.SGLL;
import gll.stack.NonTerminalParseStackNode;
import gll.stack.TerminalParseStackNode;

/*
S ::= Aepsilon
A ::= a
*/
public class Epsilon extends SGLL{
	private final static NonTerminalParseStackNode NONTERMINAL_A0 = new NonTerminalParseStackNode("A", 0);
	private final static TerminalParseStackNode EPSILON_1 = new TerminalParseStackNode("".getBytes(), 1);
	private final static TerminalParseStackNode NONTERMINAL_a2 = new TerminalParseStackNode("a".getBytes(), 2);
	
	public Epsilon(byte[] input){
		super(input);
	}
	
	public void S(){
		expect(NONTERMINAL_A0, EPSILON_1);
	}
	
	public void A(){
		expect(NONTERMINAL_a2);
	}
	
	public static void main(String[] args){
		Epsilon e = new Epsilon("a".getBytes());
		System.out.println(e.parse("S"));
		
		System.out.println("S(A(a),) <- good");
	}
}