package gtd.tests;

import gtd.SGTDBF;
import gtd.preprocessing.ExpectBuilder;
import gtd.result.AbstractNode;
import gtd.stack.AbstractStackNode;
import gtd.stack.CharStackNode;
import gtd.stack.EpsilonStackNode;
import gtd.stack.ListStackNode;
import gtd.stack.NonTerminalStackNode;

/*
S ::= A+
A ::= a | epsilon
*/
public class AmbiguousEpsilonList extends SGTDBF{
	private final static AbstractStackNode NONTERMINAL_A0 = new NonTerminalStackNode(0, 0, "A");
	private final static AbstractStackNode LIST1 = new ListStackNode(1, 0, NONTERMINAL_A0, "A+", true);
	private final static AbstractStackNode CHAR2 = new CharStackNode(2, 0, 'a');
	private final static AbstractStackNode EPSILON3 = new EpsilonStackNode(3, 0);
	
	public AmbiguousEpsilonList(char[] input){
		super(input);
	}
	
	private final static AbstractStackNode[] S_EXPECT;
	static{
		ExpectBuilder eb = new ExpectBuilder();
		eb.addAlternative(LIST1);
		S_EXPECT = eb.buildExpectMatrix();
	}
	
	public AbstractStackNode[] S(){
		return S_EXPECT;
	}
	
	private final static AbstractStackNode[] A_EXPECT;
	static{
		ExpectBuilder eb = new ExpectBuilder();
		eb.addAlternative(CHAR2);
		eb.addAlternative(EPSILON3);
		A_EXPECT = eb.buildExpectMatrix();
	}
	
	public AbstractStackNode[] A(){
		return A_EXPECT;
	}
	
	public static void main(String[] args){
		AmbiguousEpsilonList ael = new AmbiguousEpsilonList("a".toCharArray());
		AbstractNode result = ael.parse("S");
		System.out.println(result);
		
		System.out.println("S([A+([A+(A([a](a))),A+(repeat(A()),A([a](a)))],repeat(A())),A+(repeat(A()),A([a](a))),A+(A([a](a)))]) <- good");
	}
}
