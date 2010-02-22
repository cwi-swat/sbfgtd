package gll.tests;

import gll.SGLL;
import gll.nodes.INode;
import gll.stack.NonTerminalParseStackNode;
import gll.stack.ParseStackNode;
import gll.stack.TerminalParseStackNode;

// TODO Broken.

/*
S ::= A | C
A ::= Ba | a
B ::= Aa | a
C ::= B
*/
public class MergeAndSplit3 extends SGLL{
	private final static ParseStackNode NONTERMINAL_A = new NonTerminalParseStackNode("A");
	private final static ParseStackNode NONTERMINAL_B = new NonTerminalParseStackNode("B");
	private final static ParseStackNode NONTERMINAL_C = new NonTerminalParseStackNode("C");
	private final static ParseStackNode TERMINAL_a = new TerminalParseStackNode("a".getBytes());
	
	public MergeAndSplit3(String start, byte[] input){
		super(start, input);
	}
	
	public void S(){
		expect(NONTERMINAL_A);

		expect(NONTERMINAL_C);
	}
	
	public void A(){
		expect(NONTERMINAL_B, TERMINAL_a);
		
		expect(TERMINAL_a);
	}
	
	public void B(){
		expect(NONTERMINAL_A, TERMINAL_a);
		
		expect(TERMINAL_a);
	}
	
	public void C(){
		expect(NONTERMINAL_B);
	}
	
	public static void main(String[] args){
		MergeAndSplit3 ms3 = new MergeAndSplit3("S", "aaa".getBytes());
		INode result = ms3.parse();
		
		System.out.println(result);
	}
}
