package gll.tests;

import gll.SGLL;
import gll.stack.EpsilonStackNode;
import gll.stack.LiteralStackNode;
import gll.stack.NonTerminalStackNode;
import gll.stack.StackNode;

/*
S ::= A
A ::= AA | epsilon | a
*/
public class LeftRecursionEpsilon extends SGLL{
	private final static StackNode NONTERMINAL_A0 = new NonTerminalStackNode(0, "A");
	private final static StackNode NONTERMINAL_A1 = new NonTerminalStackNode(1, "A");
	private final static StackNode NONTERMINAL_A2 = new NonTerminalStackNode(2, "A");
	private final static StackNode LITERAL_a3 = new LiteralStackNode(3, new char[]{'a'});
	private final static StackNode EPSILON_4 = new EpsilonStackNode(4);
	
	public LeftRecursionEpsilon(char[] input){
		super(input);
	}
	
	public void S(){
		expect(NONTERMINAL_A0);
	}
	
	public void A(){
		expect(NONTERMINAL_A1, NONTERMINAL_A2);
		
		expect(LITERAL_a3);
		
		expect(EPSILON_4);
	}
	
	public static void main(String[] args){
		LeftRecursionEpsilon lre = new LeftRecursionEpsilon("a".toCharArray());
		System.out.println(lre.parse("S"));
		
		System.out.println("S([A(A(),A(a)),A(A(a),A())]) <- good (TODO Add cycle)");
	}
}