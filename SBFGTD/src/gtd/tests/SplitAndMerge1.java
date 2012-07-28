package gtd.tests;

import gtd.SGTDBF;
import gtd.preprocessing.ExpectBuilder;
import gtd.result.AbstractNode;
import gtd.stack.AbstractStackNode;
import gtd.stack.LiteralStackNode;
import gtd.stack.NonTerminalStackNode;

/*
S ::= aAa
A ::= Ba | aB
B ::= a
*/
public class SplitAndMerge1 extends SGTDBF{
	private final static AbstractStackNode NONTERMINAL_A0 = new NonTerminalStackNode(0, 1, "A");
	private final static AbstractStackNode NONTERMINAL_B1 = new NonTerminalStackNode(1, 0, "B");
	private final static AbstractStackNode NONTERMINAL_B2 = new NonTerminalStackNode(2, 1, "B");
	private final static AbstractStackNode LITERAL_a3 = new LiteralStackNode(3, 0, new char[]{'a'});
	private final static AbstractStackNode LITERAL_a4 = new LiteralStackNode(4, 2, new char[]{'a'});
	private final static AbstractStackNode LITERAL_a5 = new LiteralStackNode(5, 1, new char[]{'a'});
	private final static AbstractStackNode LITERAL_a6 = new LiteralStackNode(6, 0, new char[]{'a'});
	private final static AbstractStackNode LITERAL_a7 = new LiteralStackNode(7, 0, new char[]{'a'});
	
	public SplitAndMerge1(char[] input){
		super(input);
	}
	
	private final static AbstractStackNode[] S_EXPECT;
	static{
		ExpectBuilder eb = new ExpectBuilder();
		eb.addAlternative(LITERAL_a3, NONTERMINAL_A0, LITERAL_a4);
		S_EXPECT = eb.buildExpectMatrix();
	}
	
	public AbstractStackNode[] S(){
		return S_EXPECT;
	}
	
	private final static AbstractStackNode[] A_EXPECT;
	static{
		ExpectBuilder eb = new ExpectBuilder();
		eb.addAlternative(NONTERMINAL_B1, LITERAL_a5);
		eb.addAlternative(LITERAL_a6, NONTERMINAL_B2);
		A_EXPECT = eb.buildExpectMatrix();
	}
	
	public AbstractStackNode[] A(){
		return A_EXPECT;
	}
	
	private final static AbstractStackNode[] B_EXPECT;
	static{
		ExpectBuilder eb = new ExpectBuilder();
		eb.addAlternative(LITERAL_a7);
		B_EXPECT = eb.buildExpectMatrix();
	}
	
	public AbstractStackNode[] B(){
		return B_EXPECT;
	}
	
	public static void main(String[] args){
		SplitAndMerge1 sm1 = new SplitAndMerge1("aaaa".toCharArray());
		AbstractNode result = sm1.parse("S");
		System.out.println(result);
		
		System.out.println("S(a,[A(a,B(a)),A(B(a),a)],a) <- good");
	}
}
