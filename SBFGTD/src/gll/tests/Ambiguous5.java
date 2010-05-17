package gll.tests;

import gll.SGLL;
import gll.stack.NonTerminalStackNode;
import gll.stack.AbstractStackNode;
import gll.stack.LiteralStackNode;

/*
S ::= A
A ::= BB
B ::= aa | a
*/
public class Ambiguous5 extends SGLL{
	private final static AbstractStackNode NONTERMINAL_A0 = new NonTerminalStackNode(0, "A");
	private final static AbstractStackNode NONTERMINAL_B1 = new NonTerminalStackNode(1, "B");
	private final static AbstractStackNode NONTERMINAL_B2 = new NonTerminalStackNode(2, "B");
	private final static AbstractStackNode LITERAL_a3 = new LiteralStackNode(3, new char[]{'a'});
	private final static AbstractStackNode LITERAL_aa4 = new LiteralStackNode(4, new char[]{'a','a'});
	
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
