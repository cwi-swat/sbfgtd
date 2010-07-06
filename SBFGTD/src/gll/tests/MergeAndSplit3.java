package gll.tests;

import gll.SGLL;
import gll.result.INode;
import gll.stack.NonTerminalStackNode;
import gll.stack.AbstractStackNode;
import gll.stack.LiteralStackNode;

/*
S ::= A | C
A ::= Ba | a
B ::= Aa | a
C ::= B
*/
public class MergeAndSplit3 extends SGLL{
	private final static AbstractStackNode NONTERMINAL_A0 = new NonTerminalStackNode(0, "A");
	private final static AbstractStackNode NONTERMINAL_A1 = new NonTerminalStackNode(1, "A");
	private final static AbstractStackNode NONTERMINAL_B2 = new NonTerminalStackNode(2, "B");
	private final static AbstractStackNode NONTERMINAL_B3 = new NonTerminalStackNode(3, "B");
	private final static AbstractStackNode NONTERMINAL_C4 = new NonTerminalStackNode(4, "C");
	private final static AbstractStackNode LITERAL_a5 = new LiteralStackNode(5, new char[]{'a'});
	private final static AbstractStackNode LITERAL_a6 = new LiteralStackNode(6, new char[]{'a'});
	private final static AbstractStackNode LITERAL_a7 = new LiteralStackNode(7, new char[]{'a'});
	
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
		INode result = ms3.parse("S");
		System.out.println(result);
		
		System.out.println("[S(C(B(A(B(a),a),a))),S(A(B(A(a),a),a))] <- good");
	}
}
