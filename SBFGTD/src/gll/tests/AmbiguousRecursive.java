package gll.tests;

import gll.SGLL;
import gll.nodes.INode;
import gll.stack.NonTerminalParseStackNode;
import gll.stack.ParseStackNode;
import gll.stack.TerminalParseStackNode;

/*
S ::= SSS | SS | a
*/
public class AmbiguousRecursive extends SGLL{
	private final static ParseStackNode NONTERMINAL_S = new NonTerminalParseStackNode("S");
	private final static ParseStackNode TERMINAL_a = new TerminalParseStackNode("a".getBytes());
	
	public AmbiguousRecursive(byte[] input){
		super(input);
	}
	
	public void S(){
		expect(NONTERMINAL_S, NONTERMINAL_S, NONTERMINAL_S);
		
		expect(NONTERMINAL_S, NONTERMINAL_S);
		
		expect(TERMINAL_a);
	}
	
	public static void main(String[] args){
		AmbiguousRecursive ar = new AmbiguousRecursive("aaa".getBytes());
		INode result = ar.parse("S");
		
		System.out.println(result);
		System.out.println("parsetree([S(S(a),S(S(a),S(a))),S(S(a),S(a),S(a)),S(S(S(a),S(a)),S(a))]) <- good");
	}
}
