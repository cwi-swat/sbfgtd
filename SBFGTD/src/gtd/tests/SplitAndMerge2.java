package gtd.tests;

import gtd.SGTDBF;
import gtd.preprocessing.ExpectBuilder;
import gtd.result.AbstractNode;
import gtd.stack.AbstractStackNode;
import gtd.stack.LiteralStackNode;
import gtd.stack.NonTerminalStackNode;

/*
S ::= D | Da
D ::= C
C ::= Baa | Ba
B ::= A
A ::= a
*/
public class SplitAndMerge2 extends SGTDBF{
	private final static AbstractStackNode NONTERMINAL_A0 = new NonTerminalStackNode(0, 0, "A");
	private final static AbstractStackNode NONTERMINAL_B1 = new NonTerminalStackNode(1, 0, "B");
	private final static AbstractStackNode NONTERMINAL_B2 = new NonTerminalStackNode(2, 0, "B");
	private final static AbstractStackNode NONTERMINAL_C3 = new NonTerminalStackNode(3, 0, "C");
	private final static AbstractStackNode NONTERMINAL_D4 = new NonTerminalStackNode(4, 0, "D");
	private final static AbstractStackNode NONTERMINAL_D5 = new NonTerminalStackNode(5, 0, "D");
	private final static AbstractStackNode LITERAL_a6 = new LiteralStackNode(6, 1, new char[]{'a'});
	private final static AbstractStackNode LITERAL_a7 = new LiteralStackNode(7, 0, new char[]{'a'});
	private final static AbstractStackNode LITERAL_a8 = new LiteralStackNode(8, 1, new char[]{'a'});
	private final static AbstractStackNode LITERAL_aa9 = new LiteralStackNode(9, 1, new char[]{'a','a'});
	
	public SplitAndMerge2(char[] input){
		super(input);
	}
	
	private final static AbstractStackNode[] S_EXPECT;
	static{
		ExpectBuilder eb = new ExpectBuilder();
		eb.addAlternative(NONTERMINAL_D4);
		eb.addAlternative(NONTERMINAL_D5, LITERAL_a6);
		S_EXPECT = eb.buildExpectMatrix();
	}
	
	public AbstractStackNode[] S(){
		return S_EXPECT;
	}
	
	private final static AbstractStackNode[] A_EXPECT;
	static{
		ExpectBuilder eb = new ExpectBuilder();
		eb.addAlternative(LITERAL_a7);
		A_EXPECT = eb.buildExpectMatrix();
	}
	
	public AbstractStackNode[] A(){
		return A_EXPECT;
	}
	
	private final static AbstractStackNode[] B_EXPECT;
	static{
		ExpectBuilder eb = new ExpectBuilder();
		eb.addAlternative(NONTERMINAL_A0);
		B_EXPECT = eb.buildExpectMatrix();
	}
	
	public AbstractStackNode[] B(){
		return B_EXPECT;
	}
	
	private final static AbstractStackNode[] C_EXPECT;
	static{
		ExpectBuilder eb = new ExpectBuilder();
		eb.addAlternative(NONTERMINAL_B1, LITERAL_a8);
		eb.addAlternative(NONTERMINAL_B2, LITERAL_aa9);
		C_EXPECT = eb.buildExpectMatrix();
	}
	
	public AbstractStackNode[] C(){
		return C_EXPECT;
	}
	
	private final static AbstractStackNode[] D_EXPECT;
	static{
		ExpectBuilder eb = new ExpectBuilder();
		eb.addAlternative(NONTERMINAL_C3);
		D_EXPECT = eb.buildExpectMatrix();
	}
	
	public AbstractStackNode[] D(){
		return D_EXPECT;
	}
	
	public static void main(String[] args){
		SplitAndMerge2 sm2 = new SplitAndMerge2("aaa".toCharArray());
		AbstractNode result = sm2.parse("S");
		System.out.println(result);
		
		System.out.println("[S(D(C(B(A(a)),aa))),S(D(C(B(A(a)),a)),a)] <- good");
	}
}
