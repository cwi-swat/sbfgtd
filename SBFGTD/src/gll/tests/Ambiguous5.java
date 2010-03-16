package gll.tests;

import gll.SGLL;
import gll.stack.NonTerminalParseStackNode;
import gll.stack.ParseStackNode;
import gll.stack.TerminalParseStackNode;

/*
S ::= A
A ::= BB
B ::= aa | a
*/
public class Ambiguous5 extends SGLL{
	private final static ParseStackNode NONTERMINAL_A0 = new NonTerminalParseStackNode("A", 0);
	private final static ParseStackNode NONTERMINAL_B1 = new NonTerminalParseStackNode("B", 1);
	private final static ParseStackNode NONTERMINAL_B2 = new NonTerminalParseStackNode("B", 2);
	private final static ParseStackNode TERMINAL_a3 = new TerminalParseStackNode("a".getBytes(), 3);
	private final static ParseStackNode TERMINAL_aa4 = new TerminalParseStackNode("aa".getBytes(), 4);
	
	public Ambiguous5(byte[] input){
		super(input);
	}
	
	public void S(){
		expect(NONTERMINAL_A0);
	}
	
	public void A(){
		expect(NONTERMINAL_B1, NONTERMINAL_B2);
	}
	
	public void B(){
		expect(TERMINAL_a3);
		
		expect(TERMINAL_aa4);
	}
	
	public static void main(String[] args){
		Ambiguous5 a5 = new Ambiguous5("aaa".getBytes());
		a5.parse("S");
		
		System.out.println("parsetree(S([A(B(a),B(aa)),A(B(aa),B(a))])) <- good");
	}
}
