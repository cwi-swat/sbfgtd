package gll.tests;

import gll.SGLL;
import gll.stack.NonTerminalParseStackNode;
import gll.stack.ParseStackNode;
import gll.stack.TerminalParseStackNode;

/*
S ::= AA
A ::= aa | a
*/
public class Ambiguous3 extends SGLL{
	private final static ParseStackNode NONTERMINAL_A0 = new NonTerminalParseStackNode("A", 0);
	private final static ParseStackNode NONTERMINAL_A1 = new NonTerminalParseStackNode("A", 1);
	private final static ParseStackNode TERMINAL_a2 = new TerminalParseStackNode("a".getBytes(), 2);
	private final static ParseStackNode TERMINAL_aa3 = new TerminalParseStackNode("aa".getBytes(), 3);
	
	public Ambiguous3(byte[] input){
		super(input);
	}
	
	public void S(){
		expect(NONTERMINAL_A0, NONTERMINAL_A1);
	}
	
	public void A(){
		expect(TERMINAL_a2);
		
		expect(TERMINAL_aa3);
	}
	
	public static void main(String[] args){
		Ambiguous3 a3 = new Ambiguous3("aaa".getBytes());
		a3.parse("S");
		
		System.out.println("parsetree([S(A(a),A(aa)),S(A(aa),A(a))]) <- good");
	}
}
