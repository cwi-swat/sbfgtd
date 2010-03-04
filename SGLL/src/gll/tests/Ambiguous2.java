package gll.tests;

import gll.SGLL;
import gll.nodes.INode;
import gll.stack.NonTerminalParseStackNode;
import gll.stack.ParseStackNode;
import gll.stack.TerminalParseStackNode;

/*
S ::= Aab | bab
A ::= B
B ::= b
*/
public class Ambiguous2 extends SGLL{
	private final static ParseStackNode NONTERMINAL_A = new NonTerminalParseStackNode("A");
	private final static ParseStackNode NONTERMINAL_B = new NonTerminalParseStackNode("B");
	private final static ParseStackNode TERMINAL_b = new TerminalParseStackNode("b".getBytes());
	private final static ParseStackNode TERMINAL_ab = new TerminalParseStackNode("ab".getBytes());
	private final static ParseStackNode TERMINAL_bab = new TerminalParseStackNode("bab".getBytes());
	
	public Ambiguous2(byte[] input){
		super(input);
	}
	
	public void S(){
		expect(NONTERMINAL_A, TERMINAL_ab);
		
		expect(TERMINAL_bab);
	}
	
	public void A(){
		expect(NONTERMINAL_B);
	}
	
	public void B(){
		expect(TERMINAL_b);
	}
	
	public static void main(String[] args){
		Ambiguous2 a2 = new Ambiguous2("bab".getBytes());
		INode result = a2.parse("S");
		
		System.out.println(result);
		System.out.println("parsetree([S(A(B(b)),ab),S(bab)]) <- good");
	}
}
