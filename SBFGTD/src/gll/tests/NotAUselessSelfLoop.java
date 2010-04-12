package gll.tests;

import gll.SGLL;
import gll.stack.NonTerminalStackNode;
import gll.stack.StackNode;
import gll.stack.LiteralStackNode;

/*
S ::= AA | B
A ::= CC | a
B ::= AA | CC
C ::= AA | a
*/
public class NotAUselessSelfLoop extends SGLL{
	private final static StackNode NONTERMINAL_A0 = new NonTerminalStackNode("A", 0);
	private final static StackNode NONTERMINAL_A1 = new NonTerminalStackNode("A", 1);
	private final static StackNode NONTERMINAL_B2 = new NonTerminalStackNode("B", 2);
	private final static StackNode NONTERMINAL_C3 = new NonTerminalStackNode("C", 3);
	private final static StackNode NONTERMINAL_C4 = new NonTerminalStackNode("C", 4);
	private final static StackNode LITERAL_a5 = new LiteralStackNode(new char[]{'a'}, 5);
	
	public NotAUselessSelfLoop(char[] input){
		super(input);
	}
	
	public void S(){
		expect(NONTERMINAL_A0, NONTERMINAL_A1);
		
		expect(NONTERMINAL_B2);
	}
	
	public void A(){
		expect(NONTERMINAL_C3, NONTERMINAL_C4);
		
		expect(LITERAL_a5);
	}
	
	public void B(){
		expect(NONTERMINAL_A0, NONTERMINAL_A1);

		expect(NONTERMINAL_C3, NONTERMINAL_C4);
	}
	
	public void C(){
		expect(NONTERMINAL_A0, NONTERMINAL_A1);
		
		expect(LITERAL_a5);
	}
	
	public static void main(String[] args){
		NotAUselessSelfLoop nausl = new NotAUselessSelfLoop("aaa".toCharArray());
		System.out.println(nausl.parse("S"));
		
		System.out.println("[S(A(C(a),C(a)),A(a)),S([B(A(C(a),C(a)),A(a)),B(C(a),C(A(a),A(a))),B(A(a),A(C(a),C(a))),B(C(A(a),A(a)),C(a))]),S(A(a),A(C(a),C(a)))] <- good");
	}
}
