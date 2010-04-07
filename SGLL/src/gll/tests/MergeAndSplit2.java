package gll.tests;

import gll.SGLL;
import gll.stack.NonTerminalParseStackNode;
import gll.stack.ParseStackNode;
import gll.stack.LiteralParseStackNode;

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
	private final static ParseStackNode LITERAL_a6 = new LiteralParseStackNode(new char[]{'a'}, 6);
	private final static ParseStackNode LITERAL_a7 = new LiteralParseStackNode(new char[]{'a'}, 7);
	private final static ParseStackNode LITERAL_a8 = new LiteralParseStackNode(new char[]{'a'}, 8);
	private final static ParseStackNode LITERAL_aa9 = new LiteralParseStackNode(new char[]{'a','a'}, 9);
	
	public MergeAndSplit2(char[] input){
		super(input);
	}
	
	public void S(){
		expect(NONTERMINAL_D4);
		expect(NONTERMINAL_D5, LITERAL_a6);
	}
	
	public void A(){
		expect(LITERAL_a7);
	}
	
	public void B(){
		expect(NONTERMINAL_A0);
	}
	
	public void C(){
		expect(NONTERMINAL_B1, LITERAL_a8);
		
		expect(NONTERMINAL_B2, LITERAL_aa9);
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
