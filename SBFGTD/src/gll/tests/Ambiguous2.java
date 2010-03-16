package gll.tests;

import gll.SGLL;
import gll.stack.NonTerminalParseStackNode;
import gll.stack.ParseStackNode;
import gll.stack.TerminalParseStackNode;

/*
S ::= Aab | bab
A ::= B
B ::= b
*/
public class Ambiguous2 extends SGLL{
	private final static ParseStackNode NONTERMINAL_A0 = new NonTerminalParseStackNode("A", 0);
	private final static ParseStackNode NONTERMINAL_B1 = new NonTerminalParseStackNode("B", 1);
	private final static ParseStackNode TERMINAL_b2 = new TerminalParseStackNode("b".getBytes(), 2);
	private final static ParseStackNode TERMINAL_ab3 = new TerminalParseStackNode("ab".getBytes(), 3);
	private final static ParseStackNode TERMINAL_bab4 = new TerminalParseStackNode("bab".getBytes(), 4);
	
	public Ambiguous2(byte[] input){
		super(input);
	}
	
	public void S(){
		expect(NONTERMINAL_A0, TERMINAL_ab3);
		
		expect(TERMINAL_bab4);
	}
	
	public void A(){
		expect(NONTERMINAL_B1);
	}
	
	public void B(){
		expect(TERMINAL_b2);
	}
	
	public static void main(String[] args){
		Ambiguous2 a2 = new Ambiguous2("bab".getBytes());
		a2.parse("S");
		
		System.out.println("parsetree([S(A(B(b)),ab),S(bab)]) <- good");
	}
}
