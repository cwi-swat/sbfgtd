package gll.tests;

import gll.SGLL;
import gll.nodes.INode;
import gll.stack.NonTerminalParseStackNode;
import gll.stack.TerminalParseStackNode;

// TODO This is broken

/*
S ::= A
A ::= Aa | a
*/
public class LeftRecursion extends SGLL{
	private final String NONTERMINAL_A = "A";
	private final byte[] TERMINAL_a = "a".getBytes();
	
	public LeftRecursion(String start, byte[] input){
		super(start, input);
	}
	
	public void S(){
		expect(new NonTerminalParseStackNode(NONTERMINAL_A));
	}
	
	public void A(){
		expect(new NonTerminalParseStackNode(NONTERMINAL_A), new TerminalParseStackNode(TERMINAL_a));
		
		expect(new TerminalParseStackNode(TERMINAL_a));
	}
	
	public static void main(String[] args){
		LeftRecursion lr = new LeftRecursion("S", "aaa".getBytes());
		INode result = lr.parse();
		
		System.out.println(result);
	}
}