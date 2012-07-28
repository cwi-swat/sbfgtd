package gtd.tests;

import gtd.SGTDBF;
import gtd.preprocessing.ExpectBuilder;
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
public class EmptyRightRecursion extends SGTDBF{
	private final static AbstractStackNode NONTERMINAL_A0 = new NonTerminalStackNode(0, 0, "A");
	private final static AbstractStackNode NONTERMINAL_B1 = new NonTerminalStackNode(1, 1, "B");
	private final static AbstractStackNode NONTERMINAL_B2 = new NonTerminalStackNode(2, 0, "B");
	private final static AbstractStackNode LITERAL_a3 = new LiteralStackNode(3, 0, new char[]{'a'});
	private final static AbstractStackNode EPSILON_4 = new EpsilonStackNode(4, 0);
	
	public EmptyRightRecursion(char[] input){
		super(input);
	}
	
	private final static AbstractStackNode[] S_EXPECT;
	static{
		ExpectBuilder eb = new ExpectBuilder();
		eb.addAlternative(NONTERMINAL_A0, NONTERMINAL_B1);
		S_EXPECT = eb.buildExpectMatrix();
	}
	
	public AbstractStackNode[] S(){
		return S_EXPECT;
	}
	
	private final static AbstractStackNode[] A_EXPECT;
	static{
		ExpectBuilder eb = new ExpectBuilder();
		eb.addAlternative(LITERAL_a3);
		A_EXPECT = eb.buildExpectMatrix();
	}
	
	public AbstractStackNode[] A(){
		return A_EXPECT;
	}
	
	private final static AbstractStackNode[] B_EXPECT;
	static{
		ExpectBuilder eb = new ExpectBuilder();
		eb.addAlternative(NONTERMINAL_B2);
		eb.addAlternative(EPSILON_4);
		B_EXPECT = eb.buildExpectMatrix();
	}
	
	public AbstractStackNode[] B(){
		return B_EXPECT;
	}
	
	public static void main(String[] args){
		EmptyRightRecursion erre = new EmptyRightRecursion("a".toCharArray());
		AbstractNode result = erre.parse("S");
		System.out.println(result);
		
		System.out.println("S(A(a),[B(cycle(B,1)),B()]) <- good");
	}
}
