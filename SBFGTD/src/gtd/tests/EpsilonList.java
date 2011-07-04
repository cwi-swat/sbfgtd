package gtd.tests;

import gtd.SGTDBF;
import gtd.preprocessing.ExpectBuilder;
import gtd.result.AbstractNode;
import gtd.stack.AbstractStackNode;
import gtd.stack.EpsilonStackNode;
import gtd.stack.ListStackNode;
import gtd.stack.NonTerminalStackNode;

/*
S ::= A+
A ::= epsilon
*/
public class EpsilonList extends SGTDBF{
	private final static AbstractStackNode NONTERMINAL_A0 = new NonTerminalStackNode(0, 0, "A");
	private final static AbstractStackNode LIST1 = new ListStackNode(1, 0, NONTERMINAL_A0, "A+", true);
	private final static AbstractStackNode EPSILON2 = new EpsilonStackNode(3, 0);
	
	public EpsilonList(char[] input){
		super(input);
	}
	
	public AbstractStackNode[] S(){
		ExpectBuilder eb = new ExpectBuilder();
		
		eb.addAlternative(LIST1);
		
		return eb.buildExpectMatrix();
	}
	
	public AbstractStackNode[] A(){
		ExpectBuilder eb = new ExpectBuilder();
		
		eb.addAlternative(EPSILON2);
		
		return eb.buildExpectMatrix();
	}
	
	public static void main(String[] args){
		EpsilonList el = new EpsilonList("".toCharArray());
		AbstractNode result = el.parse("S");
		System.out.println(result);
		
		System.out.println("S(A+(repeat(A()))) <- good");
	}
}
