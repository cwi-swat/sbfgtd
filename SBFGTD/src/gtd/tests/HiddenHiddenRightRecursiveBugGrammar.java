package gtd.tests;

import gtd.SGTDBF;
import gtd.preprocessing.ExpectBuilder;
import gtd.result.AbstractNode;
import gtd.stack.AbstractStackNode;
import gtd.stack.EpsilonStackNode;
import gtd.stack.LiteralStackNode;
import gtd.stack.NonTerminalStackNode;

public class HiddenHiddenRightRecursiveBugGrammar extends SGTDBF{
	private final static AbstractStackNode NONTERMINAL_S0 = new NonTerminalStackNode(0, 0, "S");
	private final static AbstractStackNode NONTERMINAL_S1 = new NonTerminalStackNode(1, 1, "S");
	private final static AbstractStackNode NONTERMINAL_S2 = new NonTerminalStackNode(2, 2, "S");
	private final static AbstractStackNode NONTERMINAL_S3 = new NonTerminalStackNode(3, 3, "S");
	private final static AbstractStackNode LITERAL_a5 = new LiteralStackNode(5, 0, new char[]{'a'});
	private final static AbstractStackNode EP6 = new EpsilonStackNode(6, 0);
	
	public HiddenHiddenRightRecursiveBugGrammar(char[] input){
		super(input);
	}
	
	public void S(){
		ExpectBuilder eb = new ExpectBuilder();
		
		eb.addAlternative(NONTERMINAL_S0, NONTERMINAL_S1, NONTERMINAL_S2, NONTERMINAL_S3);
		eb.addAlternative(LITERAL_a5);
		eb.addAlternative(EP6);
		
		expect(eb.buildExpectMatrix());
	}
	
	public static void main(String[] args){
		HiddenHiddenRightRecursiveBugGrammar hhrrbg = new HiddenHiddenRightRecursiveBugGrammar("a".toCharArray());
		AbstractNode result = hhrrbg.parse("S");
		System.out.println(result);
		
		System.out.println("[S([S(cycle(S,1),cycle(S,1),cycle(S,1),cycle(S,1)),S()],[S(cycle(S,1),cycle(S,1),cycle(S,1),cycle(S,1)),S()],[S(cycle(S,1),cycle(S,1),cycle(S,1),cycle(S,1)),S()],cycle(S,1)),S([S(cycle(S,1),cycle(S,1),cycle(S,1),cycle(S,1)),S()],[S(cycle(S,1),cycle(S,1),cycle(S,1),cycle(S,1)),S()],cycle(S,1),[S(cycle(S,1),cycle(S,1),cycle(S,1),cycle(S,1)),S()]),S([S(cycle(S,1),cycle(S,1),cycle(S,1),cycle(S,1)),S()],cycle(S,1),[S(cycle(S,1),cycle(S,1),cycle(S,1),cycle(S,1)),S()],[S(cycle(S,1),cycle(S,1),cycle(S,1),cycle(S,1)),S()]),S(cycle(S,1),[S(cycle(S,1),cycle(S,1),cycle(S,1),cycle(S,1)),S()],[S(cycle(S,1),cycle(S,1),cycle(S,1),cycle(S,1)),S()],[S(cycle(S,1),cycle(S,1),cycle(S,1),cycle(S,1)),S()]),S(a)] <- good");
	}
}
