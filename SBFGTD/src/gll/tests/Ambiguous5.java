package gll.tests;

import gll.SGLL;
import gll.nodes.INode;
import gll.stack.NonTerminalParseStackNode;
import gll.stack.TerminalParseStackNode;

// TODO This is broken.

/*
S ::= A
A ::= BB
B ::= aa | a
*/
public class Ambiguous5 extends SGLL{
	private final String NONTERMINAL_A = "A";
	private final String NONTERMINAL_B = "B";
	private final byte[] TERMINAL_a = "a".getBytes();
	private final byte[] TERMINAL_aa = "aa".getBytes();
	
	public Ambiguous5(String start, byte[] input){
		super(start, input);
	}
	
	public void S(){
		expect(new NonTerminalParseStackNode(NONTERMINAL_A), new NonTerminalParseStackNode(NONTERMINAL_A));
	}
	
	public void A(){
		expect(new NonTerminalParseStackNode(NONTERMINAL_B));
	}
	
	public void B(){
		expectAlternative(new TerminalParseStackNode(TERMINAL_a));
		
		expectAlternative(new TerminalParseStackNode(TERMINAL_aa));
	}
	
	public static void main(String[] args){
		Ambiguous5 a5 = new Ambiguous5("S", "aaa".getBytes());
		INode result = a5.parse();
		
		System.out.println(result);
	}
}
