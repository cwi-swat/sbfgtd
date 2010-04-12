package gll.tests;

import gll.SGLL;
import gll.stack.NonTerminalStackNode;
import gll.stack.StackNode;
import gll.stack.LiteralStackNode;

/*
S ::= A
A ::= BB
B ::= aa | a
*/
public class Ambiguous5 extends SGLL{
	private final static StackNode NONTERMINAL_A0 = new NonTerminalStackNode("A", 0);
	private final static StackNode NONTERMINAL_B1 = new NonTerminalStackNode("B", 1);
	private final static StackNode NONTERMINAL_B2 = new NonTerminalStackNode("B", 2);
	private final static StackNode LITERAL_a3 = new LiteralStackNode(new char[]{'a'}, 3);
	private final static StackNode LITERAL_aa4 = new LiteralStackNode(new char[]{'a','a'}, 4);
	
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
