package gll.tests;

import gll.SGLL;
import gll.nodes.INode;
import gll.stack.NonTerminalParseStackNode;
import gll.stack.ParseStackNode;
import gll.stack.TerminalParseStackNode;

/*
S ::= A
A ::= Aa | a
*/
public class LeftRecursion extends SGLL{
	private final static ParseStackNode NONTERMINAL_A = new NonTerminalParseStackNode("A");
	private final static ParseStackNode TERMINAL_a = new TerminalParseStackNode("a".getBytes());
	
	public LeftRecursion(byte[] input){
		super(input);
	}
	
	public void S(){
		expect(NONTERMINAL_A);
	}
	
	public void A(){
		expect(NONTERMINAL_A, TERMINAL_a);
		
		expect(TERMINAL_a);
	}
	
	public static void main(String[] args){
		LeftRecursion lr = new LeftRecursion("aaa".getBytes());
		INode result = lr.parse("S");
		
		System.out.println(result);
		System.out.println("parsetree(S(A(A(A(a),a),a))) <- good");
	}
}