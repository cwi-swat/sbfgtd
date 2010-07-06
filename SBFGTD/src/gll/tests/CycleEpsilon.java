package gll.tests;

import gll.SGLL;
import gll.result.INode;
import gll.stack.EpsilonStackNode;
import gll.stack.LiteralStackNode;
import gll.stack.NonTerminalStackNode;
import gll.stack.AbstractStackNode;

/*
S ::= A
A ::= AA | epsilon | a
*/
public class CycleEpsilon extends SGLL{
	private final static AbstractStackNode NONTERMINAL_A0 = new NonTerminalStackNode(0, "A");
	private final static AbstractStackNode NONTERMINAL_A1 = new NonTerminalStackNode(1, "A");
	private final static AbstractStackNode NONTERMINAL_A2 = new NonTerminalStackNode(2, "A");
	private final static AbstractStackNode LITERAL_a3 = new LiteralStackNode(3, new char[]{'a'});
	private final static AbstractStackNode EPSILON_4 = new EpsilonStackNode(4);
	
	public CycleEpsilon(char[] input){
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
		CycleEpsilon ce = new CycleEpsilon("a".toCharArray());
		INode result = ce.parse("S");
		System.out.println(result);
		
		System.out.println("S([A([A(cycle(A,1),cycle(A,1)),A()],cycle(A,1)),A(cycle(A,1),[A(cycle(A,1),cycle(A,1)),A()]),A(a)]) <- good");
	}
}