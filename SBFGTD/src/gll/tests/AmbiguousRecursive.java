package gll.tests;

import gll.SGLL;
import gll.stack.NonTerminalStackNode;
import gll.stack.AbstractStackNode;
import gll.stack.LiteralStackNode;

/*
S ::= SSS | SS | a
*/
public class AmbiguousRecursive extends SGLL{
	private final static AbstractStackNode NONTERMINAL_S0 = new NonTerminalStackNode(0, "S");
	private final static AbstractStackNode NONTERMINAL_S1 = new NonTerminalStackNode(1, "S");
	private final static AbstractStackNode NONTERMINAL_S2 = new NonTerminalStackNode(2, "S");
	private final static AbstractStackNode NONTERMINAL_S3 = new NonTerminalStackNode(3, "S");
	private final static AbstractStackNode NONTERMINAL_S4 = new NonTerminalStackNode(4, "S");
	private final static AbstractStackNode LITERAL_a5 = new LiteralStackNode(5, new char[]{'a'});
	
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
