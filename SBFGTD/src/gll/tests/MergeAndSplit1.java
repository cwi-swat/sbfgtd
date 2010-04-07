package gll.tests;

import gll.SGLL;
import gll.stack.NonTerminalParseStackNode;
import gll.stack.ParseStackNode;
import gll.stack.LiteralParseStackNode;

/*
S ::= aAa
A ::= Ba | aB
B ::= a
*/
public class MergeAndSplit1 extends SGLL{
	private final static ParseStackNode NONTERMINAL_A0 = new NonTerminalParseStackNode("A", 0);
	private final static ParseStackNode NONTERMINAL_B1 = new NonTerminalParseStackNode("B", 1);
	private final static ParseStackNode NONTERMINAL_B2 = new NonTerminalParseStackNode("B", 2);
	private final static ParseStackNode LITERAL_a3 = new LiteralParseStackNode("a".toCharArray(), 3);
	private final static ParseStackNode LITERAL_a4 = new LiteralParseStackNode("a".toCharArray(), 4);
	private final static ParseStackNode LITERAL_a5 = new LiteralParseStackNode("a".toCharArray(), 5);
	private final static ParseStackNode LITERAL_a6 = new LiteralParseStackNode("a".toCharArray(), 6);
	private final static ParseStackNode LITERAL_a7 = new LiteralParseStackNode("a".toCharArray(), 7);
	
	public MergeAndSplit1(char[] input){
		super(input);
	}
	
	public void S(){
		expect(LITERAL_a3, NONTERMINAL_A0, LITERAL_a4);
	}
	
	public void A(){
		expect(NONTERMINAL_B1, LITERAL_a5);
		
		expect(LITERAL_a6, NONTERMINAL_B2);
	}
	
	public void B(){
		expect(LITERAL_a7);
	}
	
	public static void main(String[] args){
		MergeAndSplit1 ms1 = new MergeAndSplit1("aaaa".toCharArray());
		System.out.println(ms1.parse("S"));
		
		System.out.println("S(a,[A(a,B(a)),A(B(a),a)],a) <- good");
	}
}
