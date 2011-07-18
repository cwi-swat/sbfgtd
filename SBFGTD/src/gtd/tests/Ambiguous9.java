package gtd.tests;

import gtd.SGTDBF;
import gtd.preprocessing.ExpectBuilder;
import gtd.result.AbstractNode;
import gtd.stack.AbstractStackNode;
import gtd.stack.LiteralStackNode;
import gtd.stack.NonTerminalStackNode;

/*
S ::= E
E ::= E + E | E * E | 1

NOTE: This test, tests prefix sharing.
*/
public class Ambiguous9 extends SGTDBF{
	private final static AbstractStackNode NONTERMINAL_E0 = new NonTerminalStackNode(0, 0, "E");
	private final static AbstractStackNode NONTERMINAL_E1 = new NonTerminalStackNode(1, 0, "E");
	private final static AbstractStackNode NONTERMINAL_E2 = new NonTerminalStackNode(2, 2, "E");
	private final static AbstractStackNode NONTERMINAL_E3 = new NonTerminalStackNode(3, 2, "E");
	private final static AbstractStackNode LITERAL_4 = new LiteralStackNode(4, 1, "+".toCharArray());
	private final static AbstractStackNode LITERAL_5 = new LiteralStackNode(5, 1, "*".toCharArray());
	private final static AbstractStackNode LITERAL_6 = new LiteralStackNode(6, 0, "1".toCharArray());
	
	
	public Ambiguous9(char[] input){
		super(input);
	}
	
	private final static AbstractStackNode[] S_EXPECT;
	static{
		ExpectBuilder eb = new ExpectBuilder();
		eb.addAlternative(NONTERMINAL_E0);
		S_EXPECT = eb.buildExpectMatrix();
	}
	
	public AbstractStackNode[] S(){
		return S_EXPECT;
	}
	
	private final static AbstractStackNode[] E_EXPECT;
	static{
		ExpectBuilder eb = new ExpectBuilder();
		eb.addAlternative(NONTERMINAL_E1, LITERAL_4, NONTERMINAL_E2);
		eb.addAlternative(NONTERMINAL_E1, LITERAL_5, NONTERMINAL_E3);
		eb.addAlternative(LITERAL_6);
		E_EXPECT = eb.buildExpectMatrix();
	}
	
	public AbstractStackNode[] E(){
		return E_EXPECT;
	}
	
	public static void main(String[] args){
		Ambiguous9 a9 = new Ambiguous9("1+1+1".toCharArray());
		AbstractNode result = a9.parse("S");
		System.out.println(result);
		
		System.out.println("S([E(E(1),+,E(E(1),+,E(1))),E(E(E(1),+,E(1)),+,E(1))]) <- good");
	}
}
