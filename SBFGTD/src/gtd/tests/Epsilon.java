package gtd.tests;

import gtd.SGTDBF;
import gtd.preprocessing.ExpectBuilder;
import gtd.result.AbstractNode;
import gtd.stack.AbstractStackNode;
import gtd.stack.EpsilonStackNode;
import gtd.stack.LiteralStackNode;
import gtd.stack.NonTerminalStackNode;

/*
S ::= AAB
A ::= epsilon
B ::= a
*/
public class Epsilon extends SGTDBF{
	private final static AbstractStackNode NONTERMINAL_A0 = new NonTerminalStackNode(0, 0, "A");
	private final static AbstractStackNode NONTERMINAL_A1 = new NonTerminalStackNode(1, 1, "A");
	private final static AbstractStackNode NONTERMINAL_B2 = new NonTerminalStackNode(2, 2, "B");
	private final static AbstractStackNode EPSILON_3 = new EpsilonStackNode(3, 0);
	private final static AbstractStackNode LITERAL_a4 = new LiteralStackNode(4, 0, new char[]{'a'});
	
	public Epsilon(char[] input){
		super(input);
	}
	
	public void S(){
		ExpectBuilder eb = new ExpectBuilder();
		
		eb.addAlternative(NONTERMINAL_A0, NONTERMINAL_A1, NONTERMINAL_B2);
		
		expect(eb.buildExpectMatrix());
	}
	
	public void A(){
		ExpectBuilder eb = new ExpectBuilder();
		
		eb.addAlternative(EPSILON_3);
		
		expect(eb.buildExpectMatrix());
	}
	
	public void B(){
		ExpectBuilder eb = new ExpectBuilder();
		
		eb.addAlternative(LITERAL_a4);
		
		expect(eb.buildExpectMatrix());
	}
	
	public static void main(String[] args){
		Epsilon e = new Epsilon(new char[]{'a'});
		AbstractNode result = e.parse("S");
		System.out.println(result);
		
		System.out.println("S(A(),A(),B(a)) <- good");
	}
}
