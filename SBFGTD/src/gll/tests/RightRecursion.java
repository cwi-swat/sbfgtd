package gll.tests;

import gll.SGLL;
import gll.stack.AbstractStackNode;
import gll.stack.LiteralStackNode;
import gll.stack.NonTerminalStackNode;

/*
S ::= A
A ::= aA | a
*/
public class RightRecursion extends SGLL{
	private final static AbstractStackNode NONTERMINAL_A0 = new NonTerminalStackNode(0, "A");
	private final static AbstractStackNode NONTERMINAL_A1 = new NonTerminalStackNode(1, "A");
	private final static AbstractStackNode LITERAL_a2 = new LiteralStackNode(2, new char[]{'a'});
	private final static AbstractStackNode LITERAL_a3 = new LiteralStackNode(3, new char[]{'a'});
	
	public RightRecursion(char[] input){
		super(input);
	}
	
	public void S(){
		expect(NONTERMINAL_A0);
	}
	
	public void A(){
		expect(LITERAL_a2, NONTERMINAL_A1);
		
		expect(LITERAL_a3);
	}
	
	public static void main(String[] args){
		RightRecursion rr = new RightRecursion("aaa".toCharArray());
		rr.parse("S");
		System.out.println(rr.getStringResult());
		
		System.out.println("S(A(a,A(a,A(a)))) <- good");
	}
}
