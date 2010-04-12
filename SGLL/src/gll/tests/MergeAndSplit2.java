package gll.tests;

import gll.SGLL;
import gll.stack.NonTerminalStackNode;
import gll.stack.StackNode;
import gll.stack.LiteralStackNode;

/*
S ::= D | Da
D ::= C
C ::= Baa | Ba
B ::= A
A ::= a
*/
public class MergeAndSplit2 extends SGLL{
	private final static StackNode NONTERMINAL_A0 = new NonTerminalStackNode("A", 0);
	private final static StackNode NONTERMINAL_B1 = new NonTerminalStackNode("B", 1);
	private final static StackNode NONTERMINAL_B2 = new NonTerminalStackNode("B", 2);
	private final static StackNode NONTERMINAL_C3 = new NonTerminalStackNode("C", 3);
	private final static StackNode NONTERMINAL_D4 = new NonTerminalStackNode("D", 4);
	private final static StackNode NONTERMINAL_D5 = new NonTerminalStackNode("D", 5);
	private final static StackNode LITERAL_a6 = new LiteralStackNode(new char[]{'a'}, 6);
	private final static StackNode LITERAL_a7 = new LiteralStackNode(new char[]{'a'}, 7);
	private final static StackNode LITERAL_a8 = new LiteralStackNode(new char[]{'a'}, 8);
	private final static StackNode LITERAL_aa9 = new LiteralStackNode(new char[]{'a','a'}, 9);
	
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
