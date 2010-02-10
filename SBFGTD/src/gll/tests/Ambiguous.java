package gll.tests;

import gll.SGLL;
import gll.nodes.INode;
import gll.stack.NonTerminalParseStackNode;
import gll.stack.TerminalParseStackNode;

/*
S ::= A | a
A ::= a
*/
public class Ambiguous extends SGLL{
	private final String NONTERMINAL_A = "A";
	private final byte[] TERMINAL_a = "a".getBytes();
	
	public Ambiguous(String start, byte[] input){
		super(start, input);
	}
	
	public void S(){
		expectAlternative(new NonTerminalParseStackNode(NONTERMINAL_A));
		
		expectAlternative(new TerminalParseStackNode(TERMINAL_a));
	}
	
	public void A(){
		expect(new TerminalParseStackNode(TERMINAL_a));
	}
	
	public void a(){
		reduceTerminal();
	}
	
	public static void main(String[] args){
		Ambiguous a = new Ambiguous("S", "a".getBytes());
		INode result = a.parse();
		
		System.out.println(result);
	}
}
