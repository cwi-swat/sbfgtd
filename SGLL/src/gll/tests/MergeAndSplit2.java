package gll.tests;

import gll.SGLL;
import gll.stack.NonTerminalStackNode;
import gll.stack.AbstractStackNode;
import gll.stack.LiteralStackNode;

/*
S ::= D | Da
D ::= C
C ::= Baa | Ba
B ::= A
A ::= a
*/
public class MergeAndSplit2 extends SGLL{
	private final static AbstractStackNode NONTERMINAL_A0 = new NonTerminalStackNode(0, "A");
	private final static AbstractStackNode NONTERMINAL_B1 = new NonTerminalStackNode(1, "B");
	private final static AbstractStackNode NONTERMINAL_B2 = new NonTerminalStackNode(2, "B");
	private final static AbstractStackNode NONTERMINAL_C3 = new NonTerminalStackNode(3, "C");
	private final static AbstractStackNode NONTERMINAL_D4 = new NonTerminalStackNode(4, "D");
	private final static AbstractStackNode NONTERMINAL_D5 = new NonTerminalStackNode(5, "D");
	private final static AbstractStackNode LITERAL_a6 = new LiteralStackNode(6, new char[]{'a'});
	private final static AbstractStackNode LITERAL_a7 = new LiteralStackNode(7, new char[]{'a'});
	private final static AbstractStackNode LITERAL_a8 = new LiteralStackNode(8, new char[]{'a'});
	private final static AbstractStackNode LITERAL_aa9 = new LiteralStackNode(9, new char[]{'a','a'});
	
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
		ms2.parse("S");
		System.out.println(ms2.getStringResult());
		
		System.out.println("[S(D(C(B(A(a)),aa))),S(D(C(B(A(a)),a)),a)] <- good");
	}
}
