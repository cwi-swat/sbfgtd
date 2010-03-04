package gll.tests;

import gll.SGLL;
import gll.nodes.INode;
import gll.stack.NonTerminalParseStackNode;
import gll.stack.ParseStackNode;
import gll.stack.TerminalParseStackNode;

/*
S ::= A | a
A ::= a
*/
public class Ambiguous extends SGLL{
	private final static ParseStackNode NONTERMINAL_A = new NonTerminalParseStackNode("A");
	private final static ParseStackNode TERMINAL_a = new TerminalParseStackNode("a".getBytes());
	
	public Ambiguous(byte[] input){
		super(input);
	}
	
	public void S(){
		expect(NONTERMINAL_A);
		
		expect(TERMINAL_a);
	}
	
	public void A(){
		expect(TERMINAL_a);
	}
	
	public static void main(String[] args){
		Ambiguous a = new Ambiguous("a".getBytes());
		INode result = a.parse("S");
		
		System.out.println(result);
		System.out.println("parsetree([S(A(a)),S(a)]) <- good");
	}
}
