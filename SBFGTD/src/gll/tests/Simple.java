package gll.tests;

import gll.SGLL;
import gll.nodes.INode;
import gll.stack.NonTerminalParseStackNode;
import gll.stack.ParseStackNode;
import gll.stack.TerminalParseStackNode;

/*
S ::= Ab
A ::= aa
*/
public class Simple extends SGLL{
	private final static ParseStackNode NONTERMINAL_A = new NonTerminalParseStackNode("A");
	private final static ParseStackNode TERMINAL_aa = new TerminalParseStackNode("aa".getBytes());
	private final static ParseStackNode TERMINAL_b = new TerminalParseStackNode("b".getBytes());
	
	public Simple(String start, byte[] input){
		super(start, input);
	}
	
	public void S(){
		expect(NONTERMINAL_A, TERMINAL_b);
	}
	
	public void A(){
		expect(TERMINAL_aa);
	}
	
	public static void main(String[] args){
		Simple s = new Simple("S", "aab".getBytes());
		INode result = s.parse();
		
		System.out.println(result);
	}
}
