package gll.tests;

import gll.SGLL;
import gll.nodes.INode;
import gll.stack.NonTerminalParseStackNode;
import gll.stack.TerminalParseStackNode;

// TODO This is broken.

/*
S ::= ABA
A ::= a
B ::= Aa | aA
*/
public class MergeAndSplit extends SGLL{
	private final String NONTERMINAL_A = "A";
	private final String NONTERMINAL_B = "B";
	private final byte[] TERMINAL_a = "a".getBytes();
	
	public MergeAndSplit(String start, byte[] input){
		super(start, input);
	}
	
	public void S(){
		expect(new NonTerminalParseStackNode(NONTERMINAL_A), new NonTerminalParseStackNode(NONTERMINAL_B), new NonTerminalParseStackNode(NONTERMINAL_A));
	}
	
	public void A(){
		expect(new TerminalParseStackNode(TERMINAL_a));
	}
	
	public void B(){
		expectAlternative(new NonTerminalParseStackNode(NONTERMINAL_A), new TerminalParseStackNode(TERMINAL_a));
		
		expectAlternative(new TerminalParseStackNode(TERMINAL_a), new NonTerminalParseStackNode(NONTERMINAL_A));
	}
	
	public static void main(String[] args){
		MergeAndSplit ms = new MergeAndSplit("S", "aaaa".getBytes());
		INode result = ms.parse();
		
		System.out.println(result);
	}
}
