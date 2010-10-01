package gll.tests;

import gll.SGLL;
import gll.result.AbstractNode;
import gll.stack.AbstractStackNode;
import gll.stack.LiteralStackNode;
import gll.stack.NonTerminalStackNode;

/*
S ::= SSS | SS | a

NOTE: This test, tests prefix sharing.
*/
public class AmbiguousRecursivePrefixShared extends SGLL{
	private final static AbstractStackNode NONTERMINAL_S0 = new NonTerminalStackNode(0, 0, "S");
	private final static AbstractStackNode NONTERMINAL_S1 = new NonTerminalStackNode(1, 1, "S");
	private final static AbstractStackNode NONTERMINAL_S2 = new NonTerminalStackNode(2, 2, "S");
	private final static AbstractStackNode LITERAL_a5 = new LiteralStackNode(5, 0, new char[]{'a'});
	
	public AmbiguousRecursivePrefixShared(char[] input){
		super(input);
	}
	
	public void S(){
		expect(NONTERMINAL_S0, NONTERMINAL_S1, NONTERMINAL_S2);
		
		expect(NONTERMINAL_S0, NONTERMINAL_S1);
		
		expect(LITERAL_a5);
	}
	
	public static void main(String[] args){
		AmbiguousRecursivePrefixShared arps = new AmbiguousRecursivePrefixShared("aaa".toCharArray());
		AbstractNode result = arps.parse("S");
		System.out.println(result);
		
		System.out.println("[S(S(a),S(a),S(a)),S(S(a),S(S(a),S(a))),S(S(S(a),S(a)),S(a))] <- good");
	}
}
