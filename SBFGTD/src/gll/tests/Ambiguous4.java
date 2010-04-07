package gll.tests;

import gll.SGLL;
import gll.stack.NonTerminalParseStackNode;
import gll.stack.ParseStackNode;
import gll.stack.LiteralParseStackNode;

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
	private final static ParseStackNode LITERAL_b4 = new LiteralParseStackNode(new char[]{'b'}, 4);
	private final static ParseStackNode LITERAL_bb5 = new LiteralParseStackNode(new char[]{'b','b'}, 5);
	
	public Ambiguous4(char[] input){
		super(input);
	}
	
	public void S(){
		expect(NONTERMINAL_A0, NONTERMINAL_A1);
	}
	
	public void A(){
		expect(NONTERMINAL_B2, NONTERMINAL_B3);
	}
	
	public void B(){
		expect(LITERAL_b4);
		
		expect(LITERAL_bb5);
	}
	
	public static void main(String[] args){
		Ambiguous4 a4 = new Ambiguous4("bbbbbb".toCharArray());
		System.out.println(a4.parse("S"));
		
		System.out.println("[S([A(B(b),B(bb)),A(B(bb),B(b))],[A(B(b),B(bb)),A(B(bb),B(b))]),S(A(B(b),B(b)),A(B(bb),B(bb))),S(A(B(bb),B(bb)),A(B(b),B(b)))] <- good");
	}
}
