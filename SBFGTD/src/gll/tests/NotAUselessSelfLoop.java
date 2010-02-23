package gll.tests;

import gll.SGLL;
import gll.nodes.INode;
import gll.stack.NonTerminalParseStackNode;
import gll.stack.ParseStackNode;
import gll.stack.TerminalParseStackNode;

// TODO Broken

/*
S ::= AA | B
A ::= CC | a
B ::= AA | CC
C ::= AA | a
*/
public class NotAUselessSelfLoop extends SGLL{
	private final static ParseStackNode NONTERMINAL_A = new NonTerminalParseStackNode("A");
	private final static ParseStackNode NONTERMINAL_B = new NonTerminalParseStackNode("B");
	private final static ParseStackNode NONTERMINAL_C = new NonTerminalParseStackNode("C");
	private final static ParseStackNode TERMINAL_a = new TerminalParseStackNode("a".getBytes());
	
	public NotAUselessSelfLoop(String start, byte[] input){
		super(start, input);
	}
	
	public void S(){
		expect(NONTERMINAL_A, NONTERMINAL_A);
		
		expect(NONTERMINAL_B);
	}
	
	public void A(){
		expect(NONTERMINAL_C, NONTERMINAL_C);
		
		expect(TERMINAL_a);
	}
	
	public void B(){
		expect(NONTERMINAL_A, NONTERMINAL_A);

		expect(NONTERMINAL_C, NONTERMINAL_C);
	}
	
	public void C(){
		expect(NONTERMINAL_A, NONTERMINAL_A);
		
		expect(TERMINAL_a);
	}
	
	public static void main(String[] args){
		NotAUselessSelfLoop nausl = new NotAUselessSelfLoop("S", "aaa".getBytes());
		INode result = nausl.parse();
		
		System.out.println(result);
		System.out.println("parsetree([S([B(A(a),A(C(a),C(a))),B(A(C(a),C(a)),A(a)),B(C(A(a),A(a)),C(a)),B(C(a)),C(A(a),A(a))]),S(A(a),A(C(a),C(a))),S(A(C(a),C(a)),A(a))]) <- good");
	}
}
