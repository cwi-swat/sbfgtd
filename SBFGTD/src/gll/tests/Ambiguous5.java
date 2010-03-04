package gll.tests;

import gll.SGLL;
import gll.nodes.INode;
import gll.stack.NonTerminalParseStackNode;
import gll.stack.ParseStackNode;
import gll.stack.TerminalParseStackNode;

/*
S ::= A
A ::= BB
B ::= aa | a
*/
public class Ambiguous5 extends SGLL{
	private final static ParseStackNode NONTERMINAL_A = new NonTerminalParseStackNode("A");
	private final static ParseStackNode NONTERMINAL_B = new NonTerminalParseStackNode("B");
	private final static ParseStackNode TERMINAL_a = new TerminalParseStackNode("a".getBytes());
	private final static ParseStackNode TERMINAL_aa = new TerminalParseStackNode("aa".getBytes());
	
	public Ambiguous5(byte[] input){
		super(input);
	}
	
	public void S(){
		expect(NONTERMINAL_A);
	}
	
	public void A(){
		expect(NONTERMINAL_B, NONTERMINAL_B);
	}
	
	public void B(){
		expect(TERMINAL_a);
		
		expect(TERMINAL_aa);
	}
	
	public static void main(String[] args){
		Ambiguous5 a5 = new Ambiguous5("aaa".getBytes());
		INode result = a5.parse("S");
		
		System.out.println(result);
		System.out.println("parsetree(S([A(B(a),B(aa)),A(B(aa),B(a))])) <- good");
	}
}
