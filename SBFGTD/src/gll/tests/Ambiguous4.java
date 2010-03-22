package gll.tests;

import gll.SGLL;
import gll.stack.NonTerminalParseStackNode;
import gll.stack.ParseStackNode;
import gll.stack.TerminalParseStackNode;

/*
S ::= AA
A ::= BB
B ::= bb | b
*/
public class Ambiguous4 extends SGLL{
	private final static ParseStackNode NONTERMINAL_A0 = new NonTerminalParseStackNode("A", 0);
	private final static ParseStackNode NONTERMINAL_A1 = new NonTerminalParseStackNode("A", 1);
	private final static ParseStackNode NONTERMINAL_B2 = new NonTerminalParseStackNode("B", 2);
	private final static ParseStackNode NONTERMINAL_B3 = new NonTerminalParseStackNode("B", 3);
	private final static ParseStackNode TERMINAL_b4 = new TerminalParseStackNode("b".getBytes(), 4);
	private final static ParseStackNode TERMINAL_bb5 = new TerminalParseStackNode("bb".getBytes(), 5);
	
	public Ambiguous4(byte[] input){
		super(input);
	}
	
	public void S(){
		expect(NONTERMINAL_A0, NONTERMINAL_A1);
	}
	
	public void A(){
		expect(NONTERMINAL_B2, NONTERMINAL_B3);
	}
	
	public void B(){
		expect(TERMINAL_bb5);
		
		expect(TERMINAL_b4);
	}
	
	public static void main(String[] args){
		Ambiguous4 a4 = new Ambiguous4("bbbbbb".getBytes());
		System.out.println(a4.parse("S"));
		
		System.out.println("[S(A(B(b),B(b)),A(B(bb),B(bb))),S(A(B(bb),B(bb)),A(B(b),B(b))),S([A(B(b),B(bb)),A(B(bb),B(b))],[A(B(b),B(bb)),A(B(bb),B(b))])] <- good");
	}
}
