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
	private final static StackNode NONTERMINAL_A0 = new NonTerminalStackNode("A", 0);
	private final static StackNode NONTERMINAL_A1 = new NonTerminalStackNode("A", 1);
	private final static StackNode LITERAL_a2 = new LiteralStackNode(new char[]{'a'}, 2);
	private final static StackNode LITERAL_a3 = new LiteralStackNode(new char[]{'a'}, 3);
	
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