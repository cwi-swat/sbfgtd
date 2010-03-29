package gll.tests;

import gll.SGLL;
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
	private final static ParseStackNode NONTERMINAL_A0 = new NonTerminalParseStackNode("A", 0);
	private final static ParseStackNode NONTERMINAL_B1 = new NonTerminalParseStackNode("B", 1);
	private final static ParseStackNode NONTERMINAL_B2 = new NonTerminalParseStackNode("B", 2);
	private final static ParseStackNode NONTERMINAL_C3 = new NonTerminalParseStackNode("C", 3);
	private final static ParseStackNode NONTERMINAL_D4 = new NonTerminalParseStackNode("D", 4);
	private final static ParseStackNode NONTERMINAL_D5 = new NonTerminalParseStackNode("D", 5);
	private final static ParseStackNode TERMINAL_a6 = new TerminalParseStackNode("a".toCharArray(), 6);
	private final static ParseStackNode TERMINAL_a7 = new TerminalParseStackNode("a".toCharArray(), 7);
	private final static ParseStackNode TERMINAL_aa8 = new TerminalParseStackNode("aa".toCharArray(), 8);
	
	public MergeAndSplit2(char[] input){
		super(input);
	}
	
	public void S(){
		expect(NONTERMINAL_D4);
		expect(NONTERMINAL_D5, TERMINAL_a6);
	}
	
	public void A(){
		expect(TERMINAL_a7);
	}
	
	public void B(){
		expect(NONTERMINAL_A0);
	}
	
	public void C(){
		expect(NONTERMINAL_B1, TERMINAL_a6);
		
		expect(NONTERMINAL_B2, TERMINAL_aa8);
	}
	
	public void D(){
		expect(NONTERMINAL_C3);
	}
	
	public static void main(String[] args){
		MergeAndSplit2 ms2 = new MergeAndSplit2("aaa".toCharArray());
		System.out.println(ms2.parse("S"));
		
		System.out.println("[S(D(C(B(A(a)),aa))),S(D(C(B(A(a)),a)),a)] <- good");
	}
}
