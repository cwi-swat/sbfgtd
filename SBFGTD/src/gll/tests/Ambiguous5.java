package gll.tests;

import gll.SGLL;
import gll.nodes.INode;
import gll.stack.NonTerminalParseStackNode;
import gll.stack.ParseStackNode;
import gll.stack.TerminalParseStackNode;

// TODO This is broken.

/*
S ::= A
A ::= BB
B ::= aa | a
*/
public class Ambiguous5 extends SGLL{
	private final static ParseStackNode NONTERMINAL_A = new NonTerminalParseStackNode("A");
	private final static ParseStackNode NONTERMINAL_B = new NonTerminalParseStackNode("B");
	private final static ParseStackNode TERMINAL_a = new TerminalParseStackNode("a".getBytes());
	private final static ParseStackNode TERMINAL_aa = new TerminalParseStackNode("aa".getBytes());
	
	public Ambiguous5(String start, byte[] input){
		super(start, input);
	}
	
	public void S(){
		expect(NONTERMINAL_A, NONTERMINAL_A);
	}
	
	public void A(){
		expect(NONTERMINAL_B);
	}
	
	public void B(){
		expect(TERMINAL_a);
		
		expect(TERMINAL_aa);
	}
	
	public static void main(String[] args){
		Ambiguous5 a5 = new Ambiguous5("S", "aaa".getBytes());
		INode result = a5.parse();
		
		System.out.println(result);
	}
}
