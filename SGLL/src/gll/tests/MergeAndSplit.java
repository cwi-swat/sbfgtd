package gll.tests;

import gll.SGLL;
import gll.stack.NonTerminalParseStackNode;
import gll.stack.ParseStackNode;
import gll.stack.TerminalParseStackNode;

/*
S ::= aAa
A ::= Ba | aB
B ::= a
*/
public class MergeAndSplit extends SGLL{
	private final static ParseStackNode NONTERMINAL_A0 = new NonTerminalParseStackNode("A", 0);
	private final static ParseStackNode NONTERMINAL_B1 = new NonTerminalParseStackNode("B", 1);
	private final static ParseStackNode NONTERMINAL_B2 = new NonTerminalParseStackNode("B", 2);
	private final static ParseStackNode TERMINAL_a3 = new TerminalParseStackNode("a".toCharArray(), 3);
	private final static ParseStackNode TERMINAL_a4 = new TerminalParseStackNode("a".toCharArray(), 4);
	private final static ParseStackNode TERMINAL_a5 = new TerminalParseStackNode("a".toCharArray(), 5);
	private final static ParseStackNode TERMINAL_a6 = new TerminalParseStackNode("a".toCharArray(), 6);
	private final static ParseStackNode TERMINAL_a7 = new TerminalParseStackNode("a".toCharArray(), 7);
	
	public MergeAndSplit(char[] input){
		super(input);
	}
	
	public void S(){
		expect(TERMINAL_a3, NONTERMINAL_A0, TERMINAL_a4);
	}
	
	public void A(){
		expect(NONTERMINAL_B1, TERMINAL_a5);
		
		expect(TERMINAL_a6, NONTERMINAL_B2);
	}
	
	public void B(){
		expect(TERMINAL_a7);
	}
	
	public static void main(String[] args){
		MergeAndSplit ms = new MergeAndSplit("aaaa".toCharArray());
		System.out.println(ms.parse("S"));
		
		System.out.println("S(a,[A(a,B(a)),A(B(a),a)],a) <- good");
	}
}
