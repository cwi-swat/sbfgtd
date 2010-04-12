package gll.tests;

import gll.SGLL;
import gll.stack.NonTerminalStackNode;
import gll.stack.StackNode;
import gll.stack.LiteralStackNode;

/*
S ::= Aab | bab
A ::= B
B ::= b
*/
public class Ambiguous2 extends SGLL{
	private final static StackNode NONTERMINAL_A0 = new NonTerminalStackNode("A", 0);
	private final static StackNode NONTERMINAL_B1 = new NonTerminalStackNode("B", 1);
	private final static StackNode LITERAL_b2 = new LiteralStackNode(new char[]{'b'}, 2);
	private final static StackNode LITERALL_ab3 = new LiteralStackNode(new char[]{'a','b'}, 3);
	private final static StackNode LITERAL_bab4 = new LiteralStackNode(new char[]{'b','a','b'}, 4);
	
	public Ambiguous2(char[] input){
		super(input);
	}
	
	public void S(){
		expect(NONTERMINAL_A0, LITERALL_ab3);
		
		expect(LITERAL_bab4);
	}
	
	public void A(){
		expect(NONTERMINAL_B1);
	}
	
	public void B(){
		expect(LITERAL_b2);
	}
	
	public static void main(String[] args){
		Ambiguous2 a2 = new Ambiguous2("bab".toCharArray());
		System.out.println(a2.parse("S"));
		
		System.out.println("[S(A(B(b)),ab),S(bab)] <- good");
	}
}
