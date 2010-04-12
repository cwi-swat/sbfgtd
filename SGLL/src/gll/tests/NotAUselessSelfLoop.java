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
	private final static StackNode NONTERMINAL_A0 = new NonTerminalStackNode(0, "A");
	private final static StackNode NONTERMINAL_A1 = new NonTerminalStackNode(1, "A");
	private final static StackNode NONTERMINAL_B2 = new NonTerminalStackNode(2, "B");
	private final static StackNode NONTERMINAL_C3 = new NonTerminalStackNode(3, "C");
	private final static StackNode NONTERMINAL_C4 = new NonTerminalStackNode(4, "C");
	private final static StackNode LITERAL_a5 = new LiteralStackNode(5, new char[]{'a'});
	
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
