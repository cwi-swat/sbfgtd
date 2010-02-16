package gll.tests;

import gll.SGLL;
import gll.nodes.INode;
import gll.stack.NonTerminalParseStackNode;
import gll.stack.ParseStackNode;
import gll.stack.TerminalParseStackNode;

/*
S ::= AA
A ::= BB
B ::= bb | b
*/
public class Ambiguous4 extends SGLL{
	private final ParseStackNode NONTERMINAL_A = new NonTerminalParseStackNode("A");
	private final ParseStackNode NONTERMINAL_B = new NonTerminalParseStackNode("B");
	private final ParseStackNode TERMINAL_b = new TerminalParseStackNode("b".getBytes());
	private final ParseStackNode TERMINAL_bb = new TerminalParseStackNode("bb".getBytes());
	
	public Ambiguous4(String start, byte[] input){
		super(start, input);
	}
	
	public void S(){
		expect(NONTERMINAL_A, NONTERMINAL_A);
	}
	
	public void A(){
		expect(NONTERMINAL_B, NONTERMINAL_B);
	}
	
	public void B(){
		expect(TERMINAL_bb);
		
		expect(TERMINAL_b);
	}
	
	public static void main(String[] args){
		Ambiguous4 a4 = new Ambiguous4("S", "bbbbbb".getBytes());
		INode result = a4.parse();
		
		System.out.println(result);
	}
}
