package gll.tests;

import gll.SGLL;
import gll.nodes.INode;
import gll.stack.NonTerminalParseStackNode;
import gll.stack.ParseStackNode;
import gll.stack.TerminalParseStackNode;

/*
S ::= D | Da
D ::= C
C ::= Baa | Ba
B ::= A
A ::= a
*/
public class MergeAndSplit2 extends SGLL{
	private final static ParseStackNode NONTERMINAL_A = new NonTerminalParseStackNode("A");
	private final static ParseStackNode NONTERMINAL_B = new NonTerminalParseStackNode("B");
	private final static ParseStackNode NONTERMINAL_C = new NonTerminalParseStackNode("C");
	private final static ParseStackNode NONTERMINAL_D = new NonTerminalParseStackNode("D");
	private final static ParseStackNode TERMINAL_a = new TerminalParseStackNode("a".getBytes());
	private final static ParseStackNode TERMINAL_aa = new TerminalParseStackNode("aa".getBytes());
	
	public MergeAndSplit2(byte[] input){
		super(input);
	}
	
	public void S(){
		expect(NONTERMINAL_D);
		expect(NONTERMINAL_D, TERMINAL_a);
	}
	
	public void A(){
		expect(TERMINAL_a);
	}
	
	public void B(){
		expect(NONTERMINAL_A);
	}
	
	public void C(){
		expect(NONTERMINAL_B, TERMINAL_a);
		
		expect(NONTERMINAL_B, TERMINAL_aa);
	}
	
	public void D(){
		expect(NONTERMINAL_C);
	}
	
	public static void main(String[] args){
		MergeAndSplit2 ms2 = new MergeAndSplit2("aaa".getBytes());
		INode result = ms2.parse("S");
		
		System.out.println(result);
		System.out.println("parsetree([S(D(C(B(A(a)),aa))),S(D(C(B(A(a)),a)),a)]) <- good");
	}
}
