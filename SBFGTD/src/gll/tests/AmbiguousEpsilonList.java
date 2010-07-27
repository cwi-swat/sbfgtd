package gll.tests;

import gll.SGLL;
import gll.result.INode;
import gll.stack.AbstractStackNode;
import gll.stack.CharStackNode;
import gll.stack.EpsilonStackNode;
import gll.stack.ListStackNode;
import gll.stack.NonTerminalStackNode;

/*
S ::= A+
A ::= a | epsilon
*/
public class AmbiguousEpsilonList extends SGLL{
	private final static AbstractStackNode NONTERMINAL_A0 = new NonTerminalStackNode(0, "A");
	private final static AbstractStackNode LIST1 = new ListStackNode(1, NONTERMINAL_A0, "A+", true);
	private final static AbstractStackNode CHAR2 = new CharStackNode(2, "[a]", new char[][]{}, new char[]{'a'});
	private final static AbstractStackNode EPSILON3 = new EpsilonStackNode(3);
	
	public AmbiguousEpsilonList(char[] input){
		super(input);
	}
	
	public void S(){
		expect(LIST1);
	}
	
	public void A(){
		expect(CHAR2);
		
		expect(EPSILON3);
	}
	
	public static void main(String[] args){
		AmbiguousEpsilonList ael = new AmbiguousEpsilonList("a".toCharArray());
		INode result = ael.parse("S");
		System.out.println(result);
		
		System.out.println("S([A+(A([a](a)),A()),A+(A([a](a)),repeat(1),A()),A+(A(),A([a](a)),repeat(1),A()),A+(repeat(1),A(),A([a](a)),repeat(1),A()),A+(A([a](a))),A+(A(),A([a](a))),A+(repeat(1),A(),A([a](a))),A+(repeat(1),A(),A([a](a)),A())]) <- good");
	}
}
