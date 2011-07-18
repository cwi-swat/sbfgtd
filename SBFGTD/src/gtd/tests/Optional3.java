package gtd.tests;

import gtd.SGTDBF;
import gtd.preprocessing.ExpectBuilder;
import gtd.result.AbstractNode;
import gtd.stack.AbstractStackNode;
import gtd.stack.LiteralStackNode;
import gtd.stack.NonTerminalStackNode;
import gtd.stack.OptionalStackNode;

/*
S ::= aO? | aA
O ::= A
A ::= a
*/
public class Optional3 extends SGTDBF{
	private final static AbstractStackNode LITERAL_a0 = new LiteralStackNode(0, 0, new char[]{'a'});
	private final static AbstractStackNode LITERAL_a1 = new LiteralStackNode(1, 0, new char[]{'a'});
	private final static AbstractStackNode LITERAL_a2 = new LiteralStackNode(2, 0, new char[]{'a'});
	private final static AbstractStackNode NONTERMINAL_A3 = new NonTerminalStackNode(3, 1, "A");
	private final static AbstractStackNode NONTERMINAL_A4 = new NonTerminalStackNode(4, 0, "A");
	private final static AbstractStackNode NON_TERMINAL_O5 = new NonTerminalStackNode(5, 0, "O");
	private final static AbstractStackNode OPTIONAL_6 = new OptionalStackNode(6, 1, NON_TERMINAL_O5, "O?");
	
	public Optional3(char[] input){
		super(input);
	}
	
	private final static AbstractStackNode[] S_EXPECT;
	static{
		ExpectBuilder eb = new ExpectBuilder();
		eb.addAlternative(LITERAL_a0, OPTIONAL_6);
		eb.addAlternative(LITERAL_a1, NONTERMINAL_A3);
		S_EXPECT = eb.buildExpectMatrix();
	}
	
	public AbstractStackNode[] S(){
		return S_EXPECT;
	}
	
	private final static AbstractStackNode[] A_EXPECT;
	static{
		ExpectBuilder eb = new ExpectBuilder();
		eb.addAlternative(LITERAL_a2);
		A_EXPECT = eb.buildExpectMatrix();
	}
	
	public AbstractStackNode[] A(){
		return A_EXPECT;
	}
	
	private final static AbstractStackNode[] O_EXPECT;
	static{
		ExpectBuilder eb = new ExpectBuilder();
		eb.addAlternative(NONTERMINAL_A4);
		O_EXPECT = eb.buildExpectMatrix();
	}
	
	public AbstractStackNode[] O(){
		return O_EXPECT;
	}
	
	public static void main(String[] args){
		Optional3 o3 = new Optional3("aa".toCharArray());
		AbstractNode result = o3.parse("S");
		System.out.println(result);
		
		System.out.println("[S(a,O?(O(A(a)))),S(a,A(a))] <- good");
	}
}

