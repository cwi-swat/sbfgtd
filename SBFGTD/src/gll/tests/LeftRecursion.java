package gll.tests;

import gll.SGLL;
import gll.stack.NonTerminalStackNode;
import gll.stack.StackNode;
import gll.stack.LiteralStackNode;

/*
S ::= A
A ::= Aa | a
*/
public class LeftRecursion extends SGLL{
	private final static StackNode NONTERMINAL_A0 = new NonTerminalStackNode(0, "A");
	private final static StackNode NONTERMINAL_A1 = new NonTerminalStackNode(1, "A");
	private final static StackNode LITERAL_a2 = new LiteralStackNode(2, new char[]{'a'});
	private final static StackNode LITERAL_a3 = new LiteralStackNode(3, new char[]{'a'});
	
	public LeftRecursion(char[] input){
		super(input);
	}
	
	public void S(){
		expect(NONTERMINAL_A0);
	}
	
	public void A(){
		expect(NONTERMINAL_A1, LITERAL_a2);
		
		expect(LITERAL_a3);
	}
	
	public static void main(String[] args){
		LeftRecursion lr = new LeftRecursion("aaa".toCharArray());
		System.out.println(lr.parse("S"));
		
		System.out.println("S(A(A(A(a),a),a)) <- good");
	}
}