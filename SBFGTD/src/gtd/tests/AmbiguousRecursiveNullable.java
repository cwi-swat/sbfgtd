package gtd.tests;

import gtd.SGTDBF;
import gtd.preprocessing.ExpectBuilder;
import gtd.result.AbstractNode;
import gtd.stack.AbstractStackNode;
import gtd.stack.EpsilonStackNode;
import gtd.stack.LiteralStackNode;
import gtd.stack.NonTerminalStackNode;

/*
S ::= SSS | SS | a | epsilon
*/
public class AmbiguousRecursiveNullable extends SGTDBF{
	private final static AbstractStackNode NONTERMINAL_S0 = new NonTerminalStackNode(0, 0, "S");
	private final static AbstractStackNode NONTERMINAL_S1 = new NonTerminalStackNode(1, 1, "S");
	private final static AbstractStackNode NONTERMINAL_S2 = new NonTerminalStackNode(2, 2, "S");
	private final static AbstractStackNode NONTERMINAL_S3 = new NonTerminalStackNode(3, 0, "S");
	private final static AbstractStackNode NONTERMINAL_S4 = new NonTerminalStackNode(4, 1, "S");
	private final static AbstractStackNode LITERAL_a5 = new LiteralStackNode(5, 0, new char[]{'a'});
	private final static AbstractStackNode EP6 = new EpsilonStackNode(6, 0);
	
	public AmbiguousRecursiveNullable(char[] input){
		super(input);
	}
	
	public AbstractStackNode[] S(){
		ExpectBuilder eb = new ExpectBuilder();
		
		eb.addAlternative(NONTERMINAL_S0, NONTERMINAL_S1, NONTERMINAL_S2);
		eb.addAlternative(NONTERMINAL_S3, NONTERMINAL_S4);
		eb.addAlternative(LITERAL_a5);
		eb.addAlternative(EP6);
		
		return eb.buildExpectMatrix();
	}
	
	public static void main(String[] args){
		AmbiguousRecursiveNullable arn = new AmbiguousRecursiveNullable("aa".toCharArray());
		AbstractNode result = arn.parse("S");
		System.out.println(result);
		
		System.out.println("[S([S(cycle(S,1),cycle(S,1)),S(cycle(S,1),cycle(S,1),cycle(S,1)),S()],cycle(S,1)),S([S(cycle(S,1),cycle(S,1)),S(cycle(S,1),cycle(S,1),cycle(S,1)),S()],[S(cycle(S,1),cycle(S,1)),S(cycle(S,1),cycle(S,1),cycle(S,1)),S()],cycle(S,1)),S([S(cycle(S,1),cycle(S,1)),S(cycle(S,1),cycle(S,1),cycle(S,1)),S()],[S([S(cycle(S,1),cycle(S,1)),S(cycle(S,1),cycle(S,1),cycle(S,1)),S()],cycle(S,1)),S([S(cycle(S,1),cycle(S,1)),S(cycle(S,1),cycle(S,1),cycle(S,1)),S()],[S(cycle(S,1),cycle(S,1)),S(cycle(S,1),cycle(S,1),cycle(S,1)),S()],cycle(S,1)),S(cycle(S,1),[S(cycle(S,1),cycle(S,1),cycle(S,1)),S(cycle(S,1),cycle(S,1)),S()]),S([S(cycle(S,1),cycle(S,1)),S(cycle(S,1),cycle(S,1),cycle(S,1)),S()],cycle(S,1),[S(cycle(S,1),cycle(S,1),cycle(S,1)),S(cycle(S,1),cycle(S,1)),S()]),S(cycle(S,1),[S(cycle(S,1),cycle(S,1),cycle(S,1)),S(cycle(S,1),cycle(S,1)),S()],[S(cycle(S,1),cycle(S,1),cycle(S,1)),S(cycle(S,1),cycle(S,1)),S()]),S(a)],[S([S(cycle(S,1),cycle(S,1),cycle(S,1)),S(cycle(S,1),cycle(S,1)),S()],cycle(S,1)),S([S(cycle(S,1),cycle(S,1),cycle(S,1)),S(cycle(S,1),cycle(S,1)),S()],[S(cycle(S,1),cycle(S,1),cycle(S,1)),S(cycle(S,1),cycle(S,1)),S()],cycle(S,1)),S(cycle(S,1),[S(cycle(S,1),cycle(S,1),cycle(S,1)),S(cycle(S,1),cycle(S,1)),S()]),S([S(cycle(S,1),cycle(S,1),cycle(S,1)),S(cycle(S,1),cycle(S,1)),S()],cycle(S,1),[S(cycle(S,1),cycle(S,1),cycle(S,1)),S(cycle(S,1),cycle(S,1)),S()]),S(cycle(S,1),[S(cycle(S,1),cycle(S,1),cycle(S,1)),S(cycle(S,1),cycle(S,1)),S()],[S(cycle(S,1),cycle(S,1),cycle(S,1)),S(cycle(S,1),cycle(S,1)),S()]),S(a)]),S([S([S(cycle(S,1),cycle(S,1)),S(cycle(S,1),cycle(S,1),cycle(S,1)),S()],cycle(S,1)),S([S(cycle(S,1),cycle(S,1)),S(cycle(S,1),cycle(S,1),cycle(S,1)),S()],[S(cycle(S,1),cycle(S,1)),S(cycle(S,1),cycle(S,1),cycle(S,1)),S()],cycle(S,1)),S(cycle(S,1),[S(cycle(S,1),cycle(S,1),cycle(S,1)),S(cycle(S,1),cycle(S,1)),S()]),S([S(cycle(S,1),cycle(S,1)),S(cycle(S,1),cycle(S,1),cycle(S,1)),S()],cycle(S,1),[S(cycle(S,1),cycle(S,1),cycle(S,1)),S(cycle(S,1),cycle(S,1)),S()]),S(cycle(S,1),[S(cycle(S,1),cycle(S,1),cycle(S,1)),S(cycle(S,1),cycle(S,1)),S()],[S(cycle(S,1),cycle(S,1),cycle(S,1)),S(cycle(S,1),cycle(S,1)),S()]),S(a)],[S(cycle(S,1),cycle(S,1),cycle(S,1)),S(cycle(S,1),cycle(S,1)),S()],[S([S(cycle(S,1),cycle(S,1),cycle(S,1)),S(cycle(S,1),cycle(S,1)),S()],cycle(S,1)),S([S(cycle(S,1),cycle(S,1),cycle(S,1)),S(cycle(S,1),cycle(S,1)),S()],[S(cycle(S,1),cycle(S,1),cycle(S,1)),S(cycle(S,1),cycle(S,1)),S()],cycle(S,1)),S(cycle(S,1),[S(cycle(S,1),cycle(S,1),cycle(S,1)),S(cycle(S,1),cycle(S,1)),S()]),S([S(cycle(S,1),cycle(S,1),cycle(S,1)),S(cycle(S,1),cycle(S,1)),S()],cycle(S,1),[S(cycle(S,1),cycle(S,1),cycle(S,1)),S(cycle(S,1),cycle(S,1)),S()]),S(cycle(S,1),[S(cycle(S,1),cycle(S,1),cycle(S,1)),S(cycle(S,1),cycle(S,1)),S()],[S(cycle(S,1),cycle(S,1),cycle(S,1)),S(cycle(S,1),cycle(S,1)),S()]),S(a)]),S(cycle(S,1),[S(cycle(S,1),cycle(S,1),cycle(S,1)),S(cycle(S,1),cycle(S,1)),S()]),S([S(cycle(S,1),cycle(S,1)),S(cycle(S,1),cycle(S,1),cycle(S,1)),S()],cycle(S,1),[S(cycle(S,1),cycle(S,1),cycle(S,1)),S(cycle(S,1),cycle(S,1)),S()]),S([S([S(cycle(S,1),cycle(S,1)),S(cycle(S,1),cycle(S,1),cycle(S,1)),S()],cycle(S,1)),S([S(cycle(S,1),cycle(S,1)),S(cycle(S,1),cycle(S,1),cycle(S,1)),S()],[S(cycle(S,1),cycle(S,1)),S(cycle(S,1),cycle(S,1),cycle(S,1)),S()],cycle(S,1)),S(cycle(S,1),[S(cycle(S,1),cycle(S,1),cycle(S,1)),S(cycle(S,1),cycle(S,1)),S()]),S([S(cycle(S,1),cycle(S,1)),S(cycle(S,1),cycle(S,1),cycle(S,1)),S()],cycle(S,1),[S(cycle(S,1),cycle(S,1),cycle(S,1)),S(cycle(S,1),cycle(S,1)),S()]),S(cycle(S,1),[S(cycle(S,1),cycle(S,1),cycle(S,1)),S(cycle(S,1),cycle(S,1)),S()],[S(cycle(S,1),cycle(S,1),cycle(S,1)),S(cycle(S,1),cycle(S,1)),S()]),S(a)],[S([S(cycle(S,1),cycle(S,1),cycle(S,1)),S(cycle(S,1),cycle(S,1)),S()],cycle(S,1)),S([S(cycle(S,1),cycle(S,1),cycle(S,1)),S(cycle(S,1),cycle(S,1)),S()],[S(cycle(S,1),cycle(S,1),cycle(S,1)),S(cycle(S,1),cycle(S,1)),S()],cycle(S,1)),S(cycle(S,1),[S(cycle(S,1),cycle(S,1),cycle(S,1)),S(cycle(S,1),cycle(S,1)),S()]),S([S(cycle(S,1),cycle(S,1),cycle(S,1)),S(cycle(S,1),cycle(S,1)),S()],cycle(S,1),[S(cycle(S,1),cycle(S,1),cycle(S,1)),S(cycle(S,1),cycle(S,1)),S()]),S(cycle(S,1),[S(cycle(S,1),cycle(S,1),cycle(S,1)),S(cycle(S,1),cycle(S,1)),S()],[S(cycle(S,1),cycle(S,1),cycle(S,1)),S(cycle(S,1),cycle(S,1)),S()]),S(a)],[S(cycle(S,1),cycle(S,1),cycle(S,1)),S(cycle(S,1),cycle(S,1)),S()]),S(cycle(S,1),[S(cycle(S,1),cycle(S,1),cycle(S,1)),S(cycle(S,1),cycle(S,1)),S()],[S(cycle(S,1),cycle(S,1),cycle(S,1)),S(cycle(S,1),cycle(S,1)),S()]),S([S([S(cycle(S,1),cycle(S,1)),S(cycle(S,1),cycle(S,1),cycle(S,1)),S()],cycle(S,1)),S([S(cycle(S,1),cycle(S,1)),S(cycle(S,1),cycle(S,1),cycle(S,1)),S()],[S(cycle(S,1),cycle(S,1)),S(cycle(S,1),cycle(S,1),cycle(S,1)),S()],cycle(S,1)),S(cycle(S,1),[S(cycle(S,1),cycle(S,1),cycle(S,1)),S(cycle(S,1),cycle(S,1)),S()]),S([S(cycle(S,1),cycle(S,1)),S(cycle(S,1),cycle(S,1),cycle(S,1)),S()],cycle(S,1),[S(cycle(S,1),cycle(S,1),cycle(S,1)),S(cycle(S,1),cycle(S,1)),S()]),S(cycle(S,1),[S(cycle(S,1),cycle(S,1),cycle(S,1)),S(cycle(S,1),cycle(S,1)),S()],[S(cycle(S,1),cycle(S,1),cycle(S,1)),S(cycle(S,1),cycle(S,1)),S()]),S(a)],[S([S(cycle(S,1),cycle(S,1),cycle(S,1)),S(cycle(S,1),cycle(S,1)),S()],cycle(S,1)),S([S(cycle(S,1),cycle(S,1),cycle(S,1)),S(cycle(S,1),cycle(S,1)),S()],[S(cycle(S,1),cycle(S,1),cycle(S,1)),S(cycle(S,1),cycle(S,1)),S()],cycle(S,1)),S(cycle(S,1),[S(cycle(S,1),cycle(S,1),cycle(S,1)),S(cycle(S,1),cycle(S,1)),S()]),S([S(cycle(S,1),cycle(S,1),cycle(S,1)),S(cycle(S,1),cycle(S,1)),S()],cycle(S,1),[S(cycle(S,1),cycle(S,1),cycle(S,1)),S(cycle(S,1),cycle(S,1)),S()]),S(cycle(S,1),[S(cycle(S,1),cycle(S,1),cycle(S,1)),S(cycle(S,1),cycle(S,1)),S()],[S(cycle(S,1),cycle(S,1),cycle(S,1)),S(cycle(S,1),cycle(S,1)),S()]),S(a)])] <- good");
	}
}
