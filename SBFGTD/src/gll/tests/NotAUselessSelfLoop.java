package gll.tests;

import gll.SGLL;
import gll.stack.NonTerminalParseStackNode;
import gll.stack.ParseStackNode;
import gll.stack.TerminalParseStackNode;

/*
S ::= AA | B
A ::= CC | a
B ::= AA | CC
C ::= AA | a
*/
public class NotAUselessSelfLoop extends SGLL{
	private final static ParseStackNode NONTERMINAL_A0 = new NonTerminalParseStackNode("A", 0);
	private final static ParseStackNode NONTERMINAL_A1 = new NonTerminalParseStackNode("A", 1);
	private final static ParseStackNode NONTERMINAL_B2 = new NonTerminalParseStackNode("B", 2);
	private final static ParseStackNode NONTERMINAL_C3 = new NonTerminalParseStackNode("C", 3);
	private final static ParseStackNode NONTERMINAL_C4 = new NonTerminalParseStackNode("C", 4);
	private final static ParseStackNode TERMINAL_a5 = new TerminalParseStackNode("a".getBytes(), 5);
	
	public NotAUselessSelfLoop(byte[] input){
		super(input);
	}
	
	public void S(){
		expect(NONTERMINAL_A0, NONTERMINAL_A1);
		
		expect(NONTERMINAL_B2);
	}
	
	public void A(){
		expect(NONTERMINAL_C3, NONTERMINAL_C4);
		
		expect(TERMINAL_a5);
	}
	
	public void B(){
		expect(NONTERMINAL_A0, NONTERMINAL_A1);

		expect(NONTERMINAL_C3, NONTERMINAL_C4);
	}
	
	public void C(){
		expect(NONTERMINAL_A0, NONTERMINAL_A1);
		
		expect(TERMINAL_a5);
	}
	
	public static void main(String[] args){
		NotAUselessSelfLoop nausl = new NotAUselessSelfLoop("aaa".getBytes());
		System.out.println(nausl.parse("S"));
		
		System.out.println("[S(A(C(a),C(a)),A(a)),S(A(a),A(C(a),C(a))),S([B(A(C(a),C(a)),A(a)),B(C(a),C(A(a),A(a))),B(A(a),A(C(a),C(a))),B(C(A(a),A(a)),C(a))])] <- good");
	}
}
