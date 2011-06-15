package gtd.tests;

import gtd.SGTDBF;
import gtd.preprocessing.ExpectBuilder;
import gtd.result.AbstractNode;
import gtd.stack.AbstractStackNode;
import gtd.stack.EpsilonStackNode;
import gtd.stack.NonTerminalStackNode;

/*
S ::= S T U | epsilon
T ::= T U S | epsilon
U ::= U S T | epsilon
*/
public class HiddenLeftRecursionEmpty extends SGTDBF{
	private final static AbstractStackNode NONTERMINAL_S0 = new NonTerminalStackNode(0, 0, "S");
	private final static AbstractStackNode NONTERMINAL_T1 = new NonTerminalStackNode(1, 1, "T");
	private final static AbstractStackNode NONTERMINAL_U2 = new NonTerminalStackNode(2, 2, "U");
	
	private final static AbstractStackNode NONTERMINAL_T3 = new NonTerminalStackNode(3, 0, "T");
	private final static AbstractStackNode NONTERMINAL_U4 = new NonTerminalStackNode(4, 1, "U");
	private final static AbstractStackNode NONTERMINAL_S5 = new NonTerminalStackNode(5, 2, "S");

	private final static AbstractStackNode NONTERMINAL_U6 = new NonTerminalStackNode(6, 0, "U");
	private final static AbstractStackNode NONTERMINAL_S7 = new NonTerminalStackNode(7, 1, "S");
	private final static AbstractStackNode NONTERMINAL_T8 = new NonTerminalStackNode(8, 2, "T");
	
	private final static AbstractStackNode EPSILON_10 = new EpsilonStackNode(10, 0);
	private final static AbstractStackNode EPSILON_11 = new EpsilonStackNode(11, 0);
	private final static AbstractStackNode EPSILON_12 = new EpsilonStackNode(12, 0);
	
	public HiddenLeftRecursionEmpty(char[] input){
		super(input);
	}
	
	public void S(){
		ExpectBuilder eb = new ExpectBuilder();
		
		eb.addAlternative(NONTERMINAL_S0, NONTERMINAL_T1, NONTERMINAL_U2);
		eb.addAlternative(EPSILON_10);
		
		expect(eb.buildExpectMatrix());
	}
	
	public void T(){
		ExpectBuilder eb = new ExpectBuilder();
		
		eb.addAlternative(NONTERMINAL_T3, NONTERMINAL_U4, NONTERMINAL_S5);
		eb.addAlternative(EPSILON_11);
		
		expect(eb.buildExpectMatrix());
	}
	
	public void U(){
		ExpectBuilder eb = new ExpectBuilder();
		
		eb.addAlternative(NONTERMINAL_U6, NONTERMINAL_S7, NONTERMINAL_T8);
		eb.addAlternative(EPSILON_12);
		
		expect(eb.buildExpectMatrix());
	}
	
	public static void main(String[] args){
		HiddenLeftRecursionEmpty hlre = new HiddenLeftRecursionEmpty("".toCharArray());
		AbstractNode result = hlre.parse("S");
		System.out.println(result);
		
		System.out.println("[S(cycle(S,1),[T(cycle(T,1),[U(cycle(U,1),cycle(S,3),cycle(T,2)),U()],cycle(S,2)),T()],[U(cycle(U,1),cycle(S,2),[T(cycle(T,1),cycle(U,2),cycle(S,3)),T()]),U()]),S()] <- good");
	}
}
