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
	
	public Ambiguous(String start, byte[] input){
		super(start, input);
	}
	
	public void S(){
		expect(NONTERMINAL_A);
		
		expect(TERMINAL_a);
	}
	
	public void A(){
		expect(TERMINAL_a);
	}
	
	public static void main(String[] args){
		Ambiguous a = new Ambiguous("S", "a".getBytes());
		INode result = a.parse();
		
		System.out.println(result);
	}
}
