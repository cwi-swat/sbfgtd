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
	
	public LeftRecursion(String start, byte[] input){
		super(start, input);
	}
	
	public void S(){
		expect(NONTERMINAL_A);
	}
	
	public void A(){
		expect(NONTERMINAL_A, TERMINAL_a);
		
		expect(TERMINAL_a);
	}
	
	public static void main(String[] args){
		LeftRecursion lr = new LeftRecursion("S", "aaa".getBytes());
		INode result = lr.parse();
		
		System.out.println(result);
	}
}