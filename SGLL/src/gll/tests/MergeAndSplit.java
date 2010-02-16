package gll.tests;

import gll.SGLL;
import gll.nodes.INode;
import gll.stack.NonTerminalParseStackNode;
import gll.stack.ParseStackNode;
import gll.stack.TerminalParseStackNode;

// TODO This is broken.

/*
S ::= aAa
A ::= Aa | aA
B ::= a
*/
public class MergeAndSplit extends SGLL{
	private final ParseStackNode NONTERMINAL_A = new NonTerminalParseStackNode("A");
	private final ParseStackNode NONTERMINAL_B = new NonTerminalParseStackNode("B");
	private final ParseStackNode TERMINAL_a = new TerminalParseStackNode("a".getBytes());
	
	public MergeAndSplit(String start, byte[] input){
		super(start, input);
	}
	
	public void S(){
		expect(TERMINAL_a, NONTERMINAL_A, TERMINAL_a);
	}
	
	public void A(){
		expect(NONTERMINAL_B, TERMINAL_a);
		
		expect(TERMINAL_a, NONTERMINAL_B);
	}
	
	public void B(){
		expect(TERMINAL_a);
	}
	
	public static void main(String[] args){
		MergeAndSplit ms = new MergeAndSplit("S", "aaaa".getBytes());
		INode result = ms.parse();
		
		System.out.println(result);
	}
}
