package gll.tests;

import gll.SGLL;
import gll.stack.NonTerminalParseStackNode;
import gll.stack.ParseStackNode;
import gll.stack.TerminalParseStackNode;

/*
S ::= AB
A ::= a
B ::= b
*/
public class Simple2 extends SGLL{
	private final static ParseStackNode NONTERMINAL_A0 = new NonTerminalParseStackNode("A", 0);
	private final static ParseStackNode NONTERMINAL_B1 = new NonTerminalParseStackNode("B", 1);
	private final static ParseStackNode TERMINAL_a2 = new TerminalParseStackNode("a".getBytes(), 2);
	private final static ParseStackNode TERMINAL_b3 = new TerminalParseStackNode("b".getBytes(), 3);
	
	public Simple2(byte[] input){
		super(input);
	}
	
	public void S(){
		expect(NONTERMINAL_A0, NONTERMINAL_B1);
	}
	
	public void A(){
		expect(TERMINAL_a2);
	}
	
	public void B(){
		expect(TERMINAL_b3);
	}
	
	public static void main(String[] args){
		Simple2 s2 = new Simple2("ab".getBytes());
		System.out.println(s2.parse("S"));
		
		System.out.println("S(A(a),B(b)) <- good");
	}
}