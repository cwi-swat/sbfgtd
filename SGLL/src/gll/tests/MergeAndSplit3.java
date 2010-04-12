package gll.tests;

import gll.SGLL;
import gll.stack.NonTerminalStackNode;
import gll.stack.StackNode;
import gll.stack.LiteralStackNode;

/*
S ::= A | C
A ::= Ba | a
B ::= Aa | a
C ::= B
*/
public class MergeAndSplit3 extends SGLL{
	private final static StackNode NONTERMINAL_A0 = new NonTerminalStackNode("A", 0);
	private final static StackNode NONTERMINAL_A1 = new NonTerminalStackNode("A", 1);
	private final static StackNode NONTERMINAL_B2 = new NonTerminalStackNode("B", 2);
	private final static StackNode NONTERMINAL_B3 = new NonTerminalStackNode("B", 3);
	private final static StackNode NONTERMINAL_C4 = new NonTerminalStackNode("C", 4);
	private final static StackNode LITERAL_a5 = new LiteralStackNode(new char[]{'a'}, 5);
	private final static StackNode LITERAL_a6 = new LiteralStackNode(new char[]{'a'}, 6);
	private final static StackNode LITERAL_a7 = new LiteralStackNode(new char[]{'a'}, 7);
	
	public MergeAndSplit3(char[] input){
		super(input);
	}
	
	public void S(){
		expect(NONTERMINAL_A0);

		expect(NONTERMINAL_C4);
	}
	
	public void A(){
		expect(NONTERMINAL_B2, LITERAL_a6);
		
		expect(LITERAL_a5);
	}
	
	public void B(){
		expect(NONTERMINAL_A1, LITERAL_a7);
		
		expect(LITERAL_a5);
	}
	
	public void C(){
		expect(NONTERMINAL_B3);
	}
	
	public static void main(String[] args){
		MergeAndSplit3 ms3 = new MergeAndSplit3("aaa".toCharArray());
		System.out.println(ms3.parse("S"));
		
		System.out.println("[S(A(B(A(a),a),a)),S(C(B(A(B(a),a),a)))] <- good");
	}
}
