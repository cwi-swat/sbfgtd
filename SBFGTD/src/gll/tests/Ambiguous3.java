package gll.tests;

import gll.SGLL;
import gll.nodes.INode;
import gll.stack.NonTerminalParseStackNode;
import gll.stack.TerminalParseStackNode;

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
		expect(new TerminalParseStackNode(TERMINAL_a));
		
		expect(new TerminalParseStackNode(TERMINAL_aa));
	}
	
	public static void main(String[] args){
		Ambiguous3 a3 = new Ambiguous3("S", "aaa".getBytes());
		INode result = a3.parse();
		
		System.out.println(result);
	}
}
