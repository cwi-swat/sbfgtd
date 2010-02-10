package gll.examples;

import gll.SGLL;
import gll.nodes.INode;
import gll.stack.NonTerminalParseStackNode;
import gll.stack.TerminalParseStackNode;

//TODO This is broken

/*
S ::= AA
A ::= BB
B ::= bb | b
*/
public class Ambiguous4 extends SGLL{
	private final String NONTERMINAL_A = "A";
	private final String NONTERMINAL_B = "B";
	private final byte[] TERMINAL_b = "b".getBytes();
	private final byte[] TERMINAL_bb = "bb".getBytes();
	
	public Ambiguous4(String start, byte[] input){
		super(start, input);
	}
	
	public void S(){
		expect(new NonTerminalParseStackNode(NONTERMINAL_A), new NonTerminalParseStackNode(NONTERMINAL_A));
	}
	
	public void A(){
		expect(new NonTerminalParseStackNode(NONTERMINAL_B), new NonTerminalParseStackNode(NONTERMINAL_B));
	}
	
	public void B(){
		expectAlternative(new TerminalParseStackNode(TERMINAL_bb));
		
		expectAlternative(new TerminalParseStackNode(TERMINAL_b));
	}
	
	public void b(){
		reduceTerminal();
	}
	
	public void bb(){
		reduceTerminal();
	}
	
	public static void main(String[] args){
		Ambiguous4 a4 = new Ambiguous4("S", "bbbbbb".getBytes());
		INode result = a4.parse();
		
		System.out.println(result);
	}
}
