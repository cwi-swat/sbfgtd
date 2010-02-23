package gll.tests;

import gll.SGLL;
import gll.nodes.INode;
import gll.stack.NonTerminalParseStackNode;
import gll.stack.ParseStackNode;
import gll.stack.TerminalParseStackNode;

/*
S ::= AB
A ::= a
B ::= b
*/
public class Simple2 extends SGLL{
	private final static ParseStackNode NONTERMINAL_A = new NonTerminalParseStackNode("A");
	private final static ParseStackNode NONTERMINAL_B = new NonTerminalParseStackNode("B");
	private final static ParseStackNode TERMINAL_a = new TerminalParseStackNode("a".getBytes());
	private final static ParseStackNode TERMINAL_b = new TerminalParseStackNode("b".getBytes());
	
	public Simple2(String start, byte[] input){
		super(start, input);
	}
	
	public void S(){
		expect(NONTERMINAL_A, NONTERMINAL_B);
	}
	
	public void A(){
		expect(TERMINAL_a);
	}
	
	public void B(){
		expect(TERMINAL_b);
	}
	
	public static void main(String[] args){
		Simple2 s2 = new Simple2("S", "ab".getBytes());
		INode result = s2.parse();
		
		System.out.println(result);
		System.out.println("parsetree(S(A(a),B(b))) <- good");
	}
}