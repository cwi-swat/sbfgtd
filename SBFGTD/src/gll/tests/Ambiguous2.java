package gll.tests;

import gll.SGLL;
import gll.nodes.INode;
import gll.stack.NonTerminalParseStackNode;
import gll.stack.TerminalParseStackNode;

/*
S ::= Aab | bab
A ::= B
B ::= b
*/
public class Ambiguous2 extends SGLL{
	private final String NONTERMINAL_A = "A";
	private final String NONTERMINAL_B = "B";
	private final byte[] TERMINAL_a = "a".getBytes();
	private final byte[] TERMINAL_b = "b".getBytes();
	
	public Ambiguous2(String start, byte[] input){
		super(start, input);
	}
	
	public void S(){
		expect(new NonTerminalParseStackNode(NONTERMINAL_A), new TerminalParseStackNode(TERMINAL_a), new NonTerminalParseStackNode(NONTERMINAL_B));
		
		expect(new TerminalParseStackNode(TERMINAL_b), new TerminalParseStackNode(TERMINAL_a), new NonTerminalParseStackNode(NONTERMINAL_B));
	}
	
	public void A(){
		expect(new NonTerminalParseStackNode(NONTERMINAL_B));
	}
	
	public void B(){
		expect(new TerminalParseStackNode(TERMINAL_b));
	}
	
	public static void main(String[] args){
		Ambiguous2 a2 = new Ambiguous2("S", "bab".getBytes());
		INode result = a2.parse();
		
		System.out.println(result);
	}
}
