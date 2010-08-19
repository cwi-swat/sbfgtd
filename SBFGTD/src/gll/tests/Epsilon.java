package gll.tests;

import gll.SGLL;
import gll.result.AbstractNode;
import gll.stack.AbstractStackNode;
import gll.stack.EpsilonStackNode;
import gll.stack.LiteralStackNode;
import gll.stack.NonTerminalStackNode;

/*
S ::= AA
A ::= epsilon
*/
public class Epsilon extends SGLL{
	private final static AbstractStackNode NONTERMINAL_A0 = new NonTerminalStackNode(0, "A");
	private final static AbstractStackNode NONTERMINAL_A1 = new NonTerminalStackNode(1, "A");
	private final static AbstractStackNode NONTERMINAL_B2 = new NonTerminalStackNode(2, "B");
	private final static AbstractStackNode EPSILON_3 = new EpsilonStackNode(3);
	private final static AbstractStackNode LITERAL_a4 = new LiteralStackNode(4, new char[]{'a'});
	
	public Epsilon(char[] input){
		super(input);
	}
	
	public void S(){
		expect(NONTERMINAL_A0, NONTERMINAL_A1, NONTERMINAL_B2);
	}
	
	public void A(){
		expect(EPSILON_3);
	}
	
	public void B(){
		expect(LITERAL_a4);
	}
	
	public static void main(String[] args){
		Epsilon e = new Epsilon(new char[]{'a'});
		AbstractNode result = e.parse("S");
		System.out.println(result);
		
		System.out.println("S(A(),A(),B(a)) <- good");
	}
}
