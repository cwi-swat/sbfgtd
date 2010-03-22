package gll.tests;

import gll.SGLL;
import gll.stack.NonTerminalParseStackNode;
import gll.stack.ParseStackNode;
import gll.stack.TerminalParseStackNode;

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
	private final static ParseStackNode TERMINAL_a5 = new TerminalParseStackNode("a".getBytes(), 5);
	
	public MergeAndSplit3(byte[] input){
		super(input);
	}
	
	public void S(){
		expect(NONTERMINAL_A0);

		expect(NONTERMINAL_C4);
	}
	
	public void A(){
		expect(NONTERMINAL_B2, TERMINAL_a5);
		
		expect(TERMINAL_a5);
	}
	
	public void B(){
		expect(NONTERMINAL_A1, TERMINAL_a5);
		
		expect(TERMINAL_a5);
	}
	
	public void C(){
		expect(NONTERMINAL_B3);
	}
	
	public static void main(String[] args){
		MergeAndSplit3 ms3 = new MergeAndSplit3("aaa".getBytes());
		System.out.println(ms3.parse("S"));
		
		System.out.println("[S(C(B(A(B(a),a),a))),S(A(B(A(a),a),a))] <- good");
	}
}
