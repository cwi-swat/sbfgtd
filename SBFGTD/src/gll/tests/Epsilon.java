package gll.tests;

import gll.SGLL;
import gll.stack.NonTerminalParseStackNode;
import gll.stack.TerminalParseStackNode;

/*
S ::= Ae
A ::= a
*/
public class Epsilon extends SGLL{
	private final static NonTerminalParseStackNode A0 = new NonTerminalParseStackNode("A", 0);
	private final static TerminalParseStackNode epsilon1 = new TerminalParseStackNode("".getBytes(), 1);
	private final static TerminalParseStackNode a2 = new TerminalParseStackNode("a".getBytes(), 2);
	
	public Epsilon(byte[] input){
		super(input);
	}
	
	public void S(){
		expect(A0, epsilon1);
	}
	
	public void A(){
		expect(a2);
	}
	
	public static void main(String[] args){
		Epsilon e = new Epsilon("a".getBytes());
		System.out.println(e.parse("S"));
		
		System.out.println("S(A(a),) <- good");
	}
}