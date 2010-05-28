package gll.tests;

import gll.SGLL;
import gll.stack.NonTerminalStackNode;
import gll.stack.AbstractStackNode;
import gll.stack.LiteralStackNode;

/*
S ::= Aab | bab
A ::= B
B ::= b
*/
public class Ambiguous2 extends SGLL{
	private final static AbstractStackNode NONTERMINAL_A0 = new NonTerminalStackNode(0, "A");
	private final static AbstractStackNode NONTERMINAL_B1 = new NonTerminalStackNode(1, "B");
	private final static AbstractStackNode LITERAL_b2 = new LiteralStackNode(2, new char[]{'b'});
	private final static AbstractStackNode LITERALL_ab3 = new LiteralStackNode(3, new char[]{'a','b'});
	private final static AbstractStackNode LITERAL_bab4 = new LiteralStackNode(4, new char[]{'b','a','b'});
	
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
		a2.parse("S");
		System.out.println(a2.getStringResult());
		
		System.out.println("[S(A(B(b)),ab),S(bab)] <- good");
	}
}
