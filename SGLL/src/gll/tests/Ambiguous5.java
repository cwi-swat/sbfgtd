package gll.tests;

import gll.SGLL;
import gll.stack.NonTerminalParseStackNode;
import gll.stack.ParseStackNode;
import gll.stack.LiteralParseStackNode;

/*
S ::= A
A ::= BB
B ::= aa | a
*/
public class Ambiguous5 extends SGLL{
	private final static ParseStackNode NONTERMINAL_A0 = new NonTerminalParseStackNode("A", 0);
	private final static ParseStackNode NONTERMINAL_B1 = new NonTerminalParseStackNode("B", 1);
	private final static ParseStackNode NONTERMINAL_B2 = new NonTerminalParseStackNode("B", 2);
	private final static ParseStackNode LITERAL_a3 = new LiteralParseStackNode("a".toCharArray(), 3);
	private final static ParseStackNode LITERAL_aa4 = new LiteralParseStackNode("aa".toCharArray(), 4);
	
	public Ambiguous5(char[] input){
		super(input);
	}
	
	public void S(){
		expect(NONTERMINAL_A0);
	}
	
	public void A(){
		expect(NONTERMINAL_B1, NONTERMINAL_B2);
	}
	
	public void B(){
		expect(LITERAL_a3);
		
		expect(LITERAL_aa4);
	}
	
	public static void main(String[] args){
		Ambiguous5 a5 = new Ambiguous5("aaa".toCharArray());
		System.out.println(a5.parse("S"));
		
		System.out.println("S([A(B(a),B(aa)),A(B(aa),B(a))]) <- good");
	}
}
