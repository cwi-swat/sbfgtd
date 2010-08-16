package gll.tests;

import gll.SGLL;
import gll.result.AbstractNode;
import gll.stack.AbstractStackNode;
import gll.stack.LiteralStackNode;
import gll.stack.NonTerminalStackNode;

/*
* S ::= A
* A ::= AA | a
*/
public class Ambiguous7 extends SGLL{
	private final static AbstractStackNode NONTERMINAL_A0 = new NonTerminalStackNode(0, "A");
	private final static AbstractStackNode NONTERMINAL_A1 = new NonTerminalStackNode(1, "A");
	private final static AbstractStackNode NONTERMINAL_A2 = new NonTerminalStackNode(2, "A");
	private final static AbstractStackNode LITERAL_a3 = new LiteralStackNode(3, new char[]{'a'});
	
	public Ambiguous7(char[] input){
		super(input);
	}
	
	public void S(){
		expect(NONTERMINAL_A0);
	}
	
	public void A(){
		expect(NONTERMINAL_A1, NONTERMINAL_A2);
		
		expect(LITERAL_a3);
	}
	
	public static void main(String[] args){
		Ambiguous7 a7 = new Ambiguous7("aaaa".toCharArray());
		AbstractNode result = a7.parse("S");
		System.out.println(result);
		
		System.out.println("S([A(A(a),[A(A(a),A(A(a),A(a))),A(A(A(a),A(a)),A(a))]),A(A(A(a),A(a)),A(A(a),A(a))),A([A(A(a),A(A(a),A(a))),A(A(A(a),A(a)),A(a))],A(a))]) <- good");
	}
}
