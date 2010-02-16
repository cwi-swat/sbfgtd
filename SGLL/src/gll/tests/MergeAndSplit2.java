package gll.tests;

import gll.SGLL;
import gll.nodes.INode;
import gll.stack.NonTerminalParseStackNode;
import gll.stack.ParseStackNode;
import gll.stack.TerminalParseStackNode;

/*
S ::= D | Da
D ::= C
C ::= Baa | Ba
B ::= A
A ::= a
*/
public class MergeAndSplit2 extends SGLL{
	private final ParseStackNode NONTERMINAL_A = new NonTerminalParseStackNode("A");
	private final ParseStackNode NONTERMINAL_B = new NonTerminalParseStackNode("B");
	private final ParseStackNode NONTERMINAL_C = new NonTerminalParseStackNode("C");
	private final ParseStackNode NONTERMINAL_D = new NonTerminalParseStackNode("D");
	private final ParseStackNode TERMINAL_a = new TerminalParseStackNode("a".getBytes());
	private final ParseStackNode TERMINAL_aa = new TerminalParseStackNode("aa".getBytes());
	
	public MergeAndSplit2(String start, byte[] input){
		super(start, input);
	}
	
	public void S(){
		expect(NONTERMINAL_D);
		expect(NONTERMINAL_D, TERMINAL_a);
	}
	
	public void A(){
		expect(TERMINAL_a);
	}
	
	public void B(){
		expect(NONTERMINAL_A);
	}
	
	public void C(){
		expect(NONTERMINAL_B, TERMINAL_a);
		
		expect(NONTERMINAL_B, TERMINAL_aa);
	}
	
	public void D(){
		expect(NONTERMINAL_C);
	}
	
	public static void main(String[] args){
		MergeAndSplit2 ms2 = new MergeAndSplit2("S", "aaa".getBytes());
		INode result = ms2.parse();
		
		System.out.println(result);
	}
}
