package gll.tests;

import gll.SGLL;
import gll.stack.NonTerminalParseStackNode;
import gll.stack.ParseStackNode;
import gll.stack.TerminalParseStackNode;

/*
S ::= SSS | SS | a
*/
public class AmbiguousRecursive extends SGLL{
	private final static ParseStackNode NONTERMINAL_S0 = new NonTerminalParseStackNode("S", 0);
	private final static ParseStackNode NONTERMINAL_S1 = new NonTerminalParseStackNode("S", 1);
	private final static ParseStackNode NONTERMINAL_S2 = new NonTerminalParseStackNode("S", 2);
	private final static ParseStackNode NONTERMINAL_S3 = new NonTerminalParseStackNode("S", 3);
	private final static ParseStackNode NONTERMINAL_S4 = new NonTerminalParseStackNode("S", 4);
	private final static ParseStackNode TERMINAL_a5 = new TerminalParseStackNode("a".getBytes(), 5);
	
	public AmbiguousRecursive(byte[] input){
		super(input);
	}
	
	public void S(){
		expect(NONTERMINAL_S0, NONTERMINAL_S1, NONTERMINAL_S2);
		
		expect(NONTERMINAL_S3, NONTERMINAL_S4);
		
		expect(TERMINAL_a5);
	}
	
	public static void main(String[] args){
		AmbiguousRecursive ar = new AmbiguousRecursive("aaa".getBytes());
		ar.parse("S");
		
		System.out.println("parsetree([S(S(a),S(a),S(a)),S(S(S(a),S(a)),S(a)),S(S(a),S(S(a),S(a)))]) <- good");
	}
}
