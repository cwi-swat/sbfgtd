package gll.tests;

import gll.SGLL;
import gll.stack.NonTerminalParseStackNode;
import gll.stack.ParseStackNode;
import gll.stack.LiteralParseStackNode;

/*
S ::= A | C
A ::= Ba | a
B ::= Aa | a
C ::= B
*/
public class MergeAndSplit3 extends SGLL{
	private final static ParseStackNode NONTERMINAL_A0 = new NonTerminalParseStackNode("A", 0);
	private final static ParseStackNode NONTERMINAL_A1 = new NonTerminalParseStackNode("A", 1);
	private final static ParseStackNode NONTERMINAL_B2 = new NonTerminalParseStackNode("B", 2);
	private final static ParseStackNode NONTERMINAL_B3 = new NonTerminalParseStackNode("B", 3);
	private final static ParseStackNode NONTERMINAL_C4 = new NonTerminalParseStackNode("C", 4);
	private final static ParseStackNode LITERAL_a5 = new LiteralParseStackNode("a".toCharArray(), 5);
	private final static ParseStackNode LITERAL_a6 = new LiteralParseStackNode("a".toCharArray(), 6);
	private final static ParseStackNode LITERAL_a7 = new LiteralParseStackNode("a".toCharArray(), 7);
	
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
