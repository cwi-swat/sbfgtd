package gll.tests;

import gll.SGLL;
import gll.stack.NonTerminalParseStackNode;
import gll.stack.ParseStackNode;
import gll.stack.TerminalParseStackNode;

/*
S ::= A
A ::= Aa | a
*/
public class LeftRecursion extends SGLL{
	private final static ParseStackNode NONTERMINAL_A0 = new NonTerminalParseStackNode("A", 0);
	private final static ParseStackNode NONTERMINAL_A1 = new NonTerminalParseStackNode("A", 1);
	private final static ParseStackNode TERMINAL_a2 = new TerminalParseStackNode("a".getBytes(), 2);
	
	public LeftRecursion(byte[] input){
		super(input);
	}
	
	public void S(){
		expect(NONTERMINAL_A0);
	}
	
	public void A(){
		expect(NONTERMINAL_A1, TERMINAL_a2);
		
		expect(TERMINAL_a2);
	}
	
	public static void main(String[] args){
		LeftRecursion lr = new LeftRecursion("aaa".getBytes());
		lr.parse("S");
		
		System.out.println("parsetree(S(A(A(A(a),a),a))) <- good");
	}
}