package gll.tests;

import gll.SGLL;
import gll.result.AbstractNode;
import gll.stack.AbstractStackNode;
import gll.stack.EpsilonStackNode;
import gll.stack.ListStackNode;
import gll.stack.NonTerminalStackNode;

/*
S ::= A+
A ::= epsilon
*/
public class EpsilonList extends SGLL{
	private final static AbstractStackNode NONTERMINAL_A0 = new NonTerminalStackNode(0, "A");
	private final static AbstractStackNode LIST1 = new ListStackNode(1, NONTERMINAL_A0, "A+", true);
	private final static AbstractStackNode EPSILON2 = new EpsilonStackNode(3);
	
	public EpsilonList(char[] input){
		super(input);
	}
	
	public void S(){
		expect(LIST1);
	}
	
	public void A(){
		expect(EPSILON2);
	}
	
	public static void main(String[] args){
		EpsilonList el = new EpsilonList("".toCharArray());
		AbstractNode result = el.parse("S");
		System.out.println(result);
		
		System.out.println("S([A+(A()),A+(repeat(A())))]) <- good");
	}
}
