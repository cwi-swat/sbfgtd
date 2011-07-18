package gtd.tests;

import gtd.SGTDBF;
import gtd.preprocessing.ExpectBuilder;
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
	
	private final static AbstractStackNode[] S_EXPECT;
	static{
		ExpectBuilder eb = new ExpectBuilder();
		eb.addAlternative(NONTERMINAL_N1, NONTERMINAL_N2);
		S_EXPECT = eb.buildExpectMatrix();
	}
	
	public AbstractStackNode[] S(){
		return S_EXPECT;
	}
	
	private final static AbstractStackNode[] A_EXPECT;
	static{
		ExpectBuilder eb = new ExpectBuilder();
		eb.addAlternative(EPSILON3);
		A_EXPECT = eb.buildExpectMatrix();
	}
	
	public AbstractStackNode[] A(){
		return A_EXPECT;
	}
	
	private final static AbstractStackNode[] N_EXPECT;
	static{
		ExpectBuilder eb = new ExpectBuilder();
		eb.addAlternative(NONTERMINAL_A0);
		N_EXPECT = eb.buildExpectMatrix();
	}
	
	public AbstractStackNode[] N(){
		return N_EXPECT;
	}
	
	public static void main(String[] args){
		NullableSharing ns = new NullableSharing("".toCharArray());
		AbstractNode result = ns.parse("S");
		System.out.println(result);
		
		System.out.println("S(N(A()),N(A())) <- good");
	}
}
