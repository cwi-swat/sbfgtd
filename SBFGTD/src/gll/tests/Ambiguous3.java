package gll.tests;

import gll.SGLL;
import gll.nodes.INode;
import gll.stack.NonTerminalParseStackNode;
import gll.stack.ParseStackNode;
import gll.stack.TerminalParseStackNode;

/*
S ::= AA
A ::= aa | a
*/
public class Ambiguous3 extends SGLL{
	private final static ParseStackNode NONTERMINAL_A = new NonTerminalParseStackNode("A");
	private final static ParseStackNode TERMINAL_a = new TerminalParseStackNode("a".getBytes());
	private final static ParseStackNode TERMINAL_aa = new TerminalParseStackNode("aa".getBytes());
	
	public Ambiguous3(String start, byte[] input){
		super(start, input);
	}
	
	public void S(){
		expect(NONTERMINAL_A, NONTERMINAL_A);
	}
	
	public void A(){
		expect(TERMINAL_a);
		
		expect(TERMINAL_aa);
	}
	
	public static void main(String[] args){
		Ambiguous3 a3 = new Ambiguous3("S", "aaa".getBytes());
		INode result = a3.parse();
		
		System.out.println(result);
		System.out.println("parsetree([S(A(aa),A(a)),S(A(a),A(aa))]) <- good");
	}
}
