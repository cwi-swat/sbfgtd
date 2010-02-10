package gll.examples;

import gll.SGLL;
import gll.nodes.INode;
import gll.stack.NonTerminalParseStackNode;
import gll.stack.TerminalParseStackNode;

// TODO This is broken

/*
S ::= AA
A ::= aa | a
*/
public class Ambiguous3 extends SGLL{
	private final String NONTERMINAL_A = "A";
	private final byte[] TERMINAL_a = "a".getBytes();
	private final byte[] TERMINAL_aa = "aa".getBytes();
	
	public Ambiguous3(String start, byte[] input){
		super(start, input);
	}
	
	public void S(){
		expect(new NonTerminalParseStackNode(NONTERMINAL_A), new NonTerminalParseStackNode(NONTERMINAL_A));
	}
	
	public void A(){
		expectAlternative(new TerminalParseStackNode(TERMINAL_a));
		
		expectAlternative(new TerminalParseStackNode(TERMINAL_aa));
	}
	
	public void a(){
		reduceTerminal();
	}
	
	public void aa(){
		reduceTerminal();
	}
	
	public static void main(String[] args){
		Ambiguous3 a3 = new Ambiguous3("S", "aaa".getBytes());
		INode result = a3.parse();
		
		System.out.println(result);
	}
}
