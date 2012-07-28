package gtd.tests;

import gtd.SGTDBF;
import gtd.preprocessing.ExpectBuilder;
import gtd.result.AbstractNode;
import gtd.stack.AbstractStackNode;
import gtd.stack.LiteralStackNode;
import gtd.stack.NonTerminalStackNode;

/*
S ::= AA
A ::= BB
B ::= bb | b
*/
public class Ambiguous4 extends SGTDBF{
	private final static AbstractStackNode NONTERMINAL_A0 = new NonTerminalStackNode(0, 0, "A");
	private final static AbstractStackNode NONTERMINAL_A1 = new NonTerminalStackNode(1, 1, "A");
	private final static AbstractStackNode NONTERMINAL_B2 = new NonTerminalStackNode(2, 0, "B");
	private final static AbstractStackNode NONTERMINAL_B3 = new NonTerminalStackNode(3, 1, "B");
	private final static AbstractStackNode LITERAL_b4 = new LiteralStackNode(4, 0, new char[]{'b'});
	private final static AbstractStackNode LITERAL_bb5 = new LiteralStackNode(5, 0, new char[]{'b','b'});
	
	public Ambiguous4(char[] input){
		super(input);
	}
	
	private final static AbstractStackNode[] S_EXPECT;
	static{
		ExpectBuilder eb = new ExpectBuilder();
		eb.addAlternative(NONTERMINAL_A0, NONTERMINAL_A1);
		S_EXPECT = eb.buildExpectMatrix();
	}
	
	public AbstractStackNode[] S(){
		return S_EXPECT;
	}
	
	private final static AbstractStackNode[] A_EXPECT;
	static{
		ExpectBuilder eb = new ExpectBuilder();
		eb.addAlternative(NONTERMINAL_B2, NONTERMINAL_B3);
		A_EXPECT = eb.buildExpectMatrix();
	}
	
	public AbstractStackNode[] A(){
		return A_EXPECT;
	}
	
	private final static AbstractStackNode[] B_EXPECT;
	static{
		ExpectBuilder eb = new ExpectBuilder();
		eb.addAlternative(LITERAL_b4);
		eb.addAlternative(LITERAL_bb5);
		B_EXPECT = eb.buildExpectMatrix();
	}
	
	public AbstractStackNode[] B(){
		return B_EXPECT;
	}
	
	public static void main(String[] args){
		Ambiguous4 a4 = new Ambiguous4("bbbbbb".toCharArray());
		AbstractNode result = a4.parse("S");
		System.out.println(result);
		
		System.out.println("[S([A(B(bb),B(b)),A(B(b),B(bb))],[A(B(bb),B(b)),A(B(b),B(bb))]),S(A(B(bb),B(bb)),A(B(b),B(b))),S(A(B(b),B(b)),A(B(bb),B(bb)))] <- good");
	}
}
