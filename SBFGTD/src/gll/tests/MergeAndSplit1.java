package gll.tests;

import gll.SGLL;
import gll.stack.NonTerminalStackNode;
import gll.stack.StackNode;
import gll.stack.LiteralStackNode;

/*
S ::= aAa
A ::= Ba | aB
B ::= a
*/
public class MergeAndSplit1 extends SGLL{
	private final static StackNode NONTERMINAL_A0 = new NonTerminalStackNode("A", 0);
	private final static StackNode NONTERMINAL_B1 = new NonTerminalStackNode("B", 1);
	private final static StackNode NONTERMINAL_B2 = new NonTerminalStackNode("B", 2);
	private final static StackNode LITERAL_a3 = new LiteralStackNode(new char[]{'a'}, 3);
	private final static StackNode LITERAL_a4 = new LiteralStackNode(new char[]{'a'}, 4);
	private final static StackNode LITERAL_a5 = new LiteralStackNode(new char[]{'a'}, 5);
	private final static StackNode LITERAL_a6 = new LiteralStackNode(new char[]{'a'}, 6);
	private final static StackNode LITERAL_a7 = new LiteralStackNode(new char[]{'a'}, 7);
	
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
