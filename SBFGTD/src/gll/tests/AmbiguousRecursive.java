package gll.tests;

import gll.SGLL;
import gll.stack.NonTerminalParseStackNode;
import gll.stack.ParseStackNode;
import gll.stack.LiteralParseStackNode;

/*
S ::= SSS | SS | a
*/
public class AmbiguousRecursive extends SGLL{
	private final static ParseStackNode NONTERMINAL_S0 = new NonTerminalParseStackNode("S", 0);
	private final static ParseStackNode NONTERMINAL_S1 = new NonTerminalParseStackNode("S", 1);
	private final static ParseStackNode NONTERMINAL_S2 = new NonTerminalParseStackNode("S", 2);
	private final static ParseStackNode NONTERMINAL_S3 = new NonTerminalParseStackNode("S", 3);
	private final static ParseStackNode NONTERMINAL_S4 = new NonTerminalParseStackNode("S", 4);
	private final static ParseStackNode LITERAL_a5 = new LiteralParseStackNode(new char[]{'a'}, 5);
	
	public AmbiguousRecursive(char[] input){
		super(input);
	}
	
	public void S(){
		expect(NONTERMINAL_S0, NONTERMINAL_S1, NONTERMINAL_S2);
		
		expect(NONTERMINAL_S3, NONTERMINAL_S4);
		
		expect(LITERAL_a5);
	}
	
	public static void main(String[] args){
		AmbiguousRecursive ar = new AmbiguousRecursive("aaa".toCharArray());
		System.out.println(ar.parse("S"));
		
		System.out.println("[S(S(a),S(S(a),S(a))),S(S(S(a),S(a)),S(a)),S(S(a),S(a),S(a))] <- good");
	}
}
