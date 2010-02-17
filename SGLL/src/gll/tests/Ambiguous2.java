package gll.tests;

import gll.SGLL;
import gll.nodes.INode;
import gll.stack.NonTerminalParseStackNode;
import gll.stack.ParseStackNode;
import gll.stack.TerminalParseStackNode;

/*
S ::= Aab | bab
A ::= B
B ::= b
*/
public class Ambiguous2 extends SGLL{
	private final static ParseStackNode NONTERMINAL_A = new NonTerminalParseStackNode("A");
	private final static ParseStackNode NONTERMINAL_B = new NonTerminalParseStackNode("B");
	private final static ParseStackNode TERMINAL_a = new TerminalParseStackNode("a".getBytes());
	private final static ParseStackNode TERMINAL_b = new TerminalParseStackNode("b".getBytes());
	
	public Ambiguous2(String start, byte[] input){
		super(start, input);
	}
	
	public void S(){
		expect(NONTERMINAL_A, TERMINAL_a, NONTERMINAL_B);
		
		expect(TERMINAL_b, TERMINAL_a, NONTERMINAL_B);
	}
	
	public void A(){
		expect(NONTERMINAL_B);
	}
	
	public void B(){
		expect(TERMINAL_b);
	}
	
	public static void main(String[] args){
		Ambiguous2 a2 = new Ambiguous2("S", "bab".getBytes());
		INode result = a2.parse();
		
		System.out.println(result);
	}
}
