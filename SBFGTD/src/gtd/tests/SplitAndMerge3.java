package gtd.tests;

import gtd.SGTDBF;
import gtd.preprocessing.ExpectBuilder;
import gtd.result.AbstractNode;
import gtd.stack.AbstractStackNode;
import gtd.stack.LiteralStackNode;
import gtd.stack.NonTerminalStackNode;

/*
S ::= A | C
A ::= Ba | a
B ::= Aa | a
C ::= B
*/
public class SplitAndMerge3 extends SGTDBF{
	private final static AbstractStackNode NONTERMINAL_A0 = new NonTerminalStackNode(0, 0, "A");
	private final static AbstractStackNode NONTERMINAL_A1 = new NonTerminalStackNode(1, 0, "A");
	private final static AbstractStackNode NONTERMINAL_B2 = new NonTerminalStackNode(2, 0, "B");
	private final static AbstractStackNode NONTERMINAL_B3 = new NonTerminalStackNode(3, 0, "B");
	private final static AbstractStackNode NONTERMINAL_C4 = new NonTerminalStackNode(4, 0, "C");
	private final static AbstractStackNode LITERAL_a5 = new LiteralStackNode(5, 1, new char[]{'a'});
	private final static AbstractStackNode LITERAL_a6 = new LiteralStackNode(6, 0, new char[]{'a'});
	private final static AbstractStackNode LITERAL_a7 = new LiteralStackNode(7, 1, new char[]{'a'});
	private final static AbstractStackNode LITERAL_a8 = new LiteralStackNode(8, 0, new char[]{'a'});
	
	public SplitAndMerge3(char[] input){
		super(input);
	}
	
	private final static AbstractStackNode[] S_EXPECT;
	static{
		ExpectBuilder eb = new ExpectBuilder();
		eb.addAlternative(NONTERMINAL_A0);
		eb.addAlternative(NONTERMINAL_C4);
		S_EXPECT = eb.buildExpectMatrix();
	}
	
	public AbstractStackNode[] S(){
		return S_EXPECT;
	}
	
	private final static AbstractStackNode[] A_EXPECT;
	static{
		ExpectBuilder eb = new ExpectBuilder();
		eb.addAlternative(NONTERMINAL_B2, LITERAL_a5);
		eb.addAlternative(LITERAL_a6);
		A_EXPECT = eb.buildExpectMatrix();
	}
	
	public AbstractStackNode[] A(){
		return A_EXPECT;
	}
	
	private final static AbstractStackNode[] B_EXPECT;
	static{
		ExpectBuilder eb = new ExpectBuilder();
		eb.addAlternative(NONTERMINAL_A1, LITERAL_a7);
		eb.addAlternative(LITERAL_a8);
		B_EXPECT = eb.buildExpectMatrix();
	}
	
	public AbstractStackNode[] B(){
		return B_EXPECT;
	}
	
	private final static AbstractStackNode[] C_EXPECT;
	static{
		ExpectBuilder eb = new ExpectBuilder();
		eb.addAlternative(NONTERMINAL_B3);
		C_EXPECT = eb.buildExpectMatrix();
	}
	
	public AbstractStackNode[] C(){
		return C_EXPECT;
	}
	
	public static void main(String[] args){
		SplitAndMerge3 sm3 = new SplitAndMerge3("aaa".toCharArray());
		AbstractNode result = sm3.parse("S");
		System.out.println(result);
		
		System.out.println("[S(A(B(A(a),a),a)),S(C(B(A(B(a),a),a)))] <- good");
	}
}
