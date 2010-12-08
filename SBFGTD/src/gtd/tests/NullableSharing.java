package gtd.tests;

import gtd.SGTDBF;
import gtd.result.AbstractNode;
import gtd.stack.AbstractStackNode;
import gtd.stack.EpsilonStackNode;
import gtd.stack.NonTerminalStackNode;

/*
* S ::= N N
* N ::= A
* A ::= epsilon
*/
public class NullableSharing extends SGTDBF{
	private final static AbstractStackNode NONTERMINAL_A0 = new NonTerminalStackNode(0, 0, "A");
	private final static AbstractStackNode NONTERMINAL_N1 = new NonTerminalStackNode(1, 0, "N");
	private final static AbstractStackNode NONTERMINAL_N2 = new NonTerminalStackNode(2, 1, "N");
	private final static AbstractStackNode EPSILON3 = new EpsilonStackNode(3, 0);
	
	public NullableSharing(char[] input){
		super(input);
	}
	
	public void S(){
		expect(NONTERMINAL_N1, NONTERMINAL_N2);
	}
	
	public void A(){
		expect(EPSILON3);
	}
	
	public void N(){
		expect(NONTERMINAL_A0);
	}
	
	public static void main(String[] args){
		NullableSharing ns = new NullableSharing("".toCharArray());
		AbstractNode result = ns.parse("S");
		System.out.println(result);
		
		System.out.println("S(N(A()),N(A())) <- good");
	}
}
