package gll.tests;

import gll.SGLL;
import gll.nodes.INode;
import gll.stack.NonTerminalParseStackNode;
import gll.stack.ParseStackNode;
import gll.stack.TerminalParseStackNode;

/*
S ::= AA
A ::= BB
B ::= bb | b
*/
public class Ambiguous4 extends SGLL{
	private final static ParseStackNode NONTERMINAL_A = new NonTerminalParseStackNode("A");
	private final static ParseStackNode NONTERMINAL_B = new NonTerminalParseStackNode("B");
	private final static ParseStackNode TERMINAL_b = new TerminalParseStackNode("b".getBytes());
	private final static ParseStackNode TERMINAL_bb = new TerminalParseStackNode("bb".getBytes());
	
	public Ambiguous4(byte[] input){
		super(input);
	}
	
	public void S(){
		expect(NONTERMINAL_A, NONTERMINAL_A);
	}
	
	public void A(){
		expect(NONTERMINAL_B, NONTERMINAL_B);
	}
	
	public void B(){
		expect(TERMINAL_bb);
		
		expect(TERMINAL_b);
	}
	
	public static void main(String[] args){
		Ambiguous4 a4 = new Ambiguous4("bbbbbb".getBytes());
		INode result = a4.parse("S");
		
		System.out.println(result);
		System.out.println("parsetree([S(A(B(b),B(b)),A(B(bb),B(bb))),S([A(B(b),B(bb)),A(B(bb),B(b))],[A(B(b),B(bb)),A(B(bb),B(b))]),S(A(B(bb),B(bb)),A(B(b),B(b)))]) <- good");
	}
}
