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
	private final static ParseStackNode TERMINAL_a2 = new TerminalParseStackNode("a".toCharArray(), 2);
	private final static ParseStackNode TERMINAL_a3 = new TerminalParseStackNode("a".toCharArray(), 3);
	
	public LeftRecursion(char[] input){
		super(input);
	}
	
	public void S(){
		expect(NONTERMINAL_A0);
	}
	
	public void A(){
		expect(NONTERMINAL_A1, TERMINAL_a2);
		
		expect(TERMINAL_a3);
	}
	
	public static void main(String[] args){
		LeftRecursion lr = new LeftRecursion("aaa".toCharArray());
		System.out.println(lr.parse("S"));
		
		System.out.println("S(A(A(A(a),a),a)) <- good");
	}
}