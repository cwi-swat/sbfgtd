package gll.tests;

import gll.SGLL;
import gll.stack.NonTerminalStackNode;
import gll.stack.StackNode;
import gll.stack.LiteralStackNode;

/*
S ::= SSS | SS | a
*/
public class AmbiguousRecursive extends SGLL{
	private final static StackNode NONTERMINAL_S0 = new NonTerminalStackNode("S", 0);
	private final static StackNode NONTERMINAL_S1 = new NonTerminalStackNode("S", 1);
	private final static StackNode NONTERMINAL_S2 = new NonTerminalStackNode("S", 2);
	private final static StackNode NONTERMINAL_S3 = new NonTerminalStackNode("S", 3);
	private final static StackNode NONTERMINAL_S4 = new NonTerminalStackNode("S", 4);
	private final static StackNode LITERAL_a5 = new LiteralStackNode(new char[]{'a'}, 5);
	
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
