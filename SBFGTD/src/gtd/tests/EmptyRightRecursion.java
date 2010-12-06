package gtd.tests;

import gtd.SBFGTD;
import gtd.result.AbstractNode;
import gtd.stack.AbstractStackNode;
import gtd.stack.EpsilonStackNode;
import gtd.stack.LiteralStackNode;
import gtd.stack.NonTerminalStackNode;

/*
S ::= AB
A ::= a
B ::= B | epsilon
*/
public class EmptyRightRecursion extends SBFGTD{
	private final static AbstractStackNode NONTERMINAL_A0 = new NonTerminalStackNode(0, 0, "A");
	private final static AbstractStackNode NONTERMINAL_B1 = new NonTerminalStackNode(1, 1, "B");
	private final static AbstractStackNode NONTERMINAL_B2 = new NonTerminalStackNode(2, 0, "B");
	private final static AbstractStackNode LITERAL_a3 = new LiteralStackNode(3, 0, new char[]{'a'});
	private final static AbstractStackNode EPSILON_4 = new EpsilonStackNode(4, 0);
	
	public EmptyRightRecursion(char[] input){
		super(input);
	}
	
	public void S(){
		expect(NONTERMINAL_A0, NONTERMINAL_B1);
	}
	
	public void A(){
		expect(LITERAL_a3);
	}
	
	public void B(){
		expect(NONTERMINAL_B2);
		
		expect(EPSILON_4);
	}
	
	public static void main(String[] args){
		EmptyRightRecursion erre = new EmptyRightRecursion("a".toCharArray());
		AbstractNode result = erre.parse("S");
		System.out.println(result);
		
		System.out.println("S(A(a),[B(cycle(B,1)),B()]) <- good");
	}
}
