package gll.tests;

import gll.SGLL;
import gll.stack.NonTerminalParseStackNode;
import gll.stack.ParseStackNode;
import gll.stack.TerminalParseStackNode;

/*
S ::= A | a
A ::= a
*/
public class Ambiguous extends SGLL{
	private final static ParseStackNode NONTERMINAL_A0 = new NonTerminalParseStackNode("A", 0);
	private final static ParseStackNode TERMINAL_a1 = new TerminalParseStackNode("a".toCharArray(), 1);
	
	public Ambiguous(char[] input){
		super(input);
	}
	
	public void S(){
		expect(NONTERMINAL_A0);
		
		expect(TERMINAL_a1);
	}
	
	public void A(){
		expect(TERMINAL_a1);
	}
	
	public static void main(String[] args){
		Ambiguous a = new Ambiguous("a".toCharArray());
		System.out.println(a.parse("S"));
		
		System.out.println("[S(A(a)),S(a)] <- good");
	}
}
