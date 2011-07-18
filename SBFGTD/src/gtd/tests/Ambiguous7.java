package gtd.tests;

import gtd.SGTDBF;
import gtd.preprocessing.ExpectBuilder;
import gtd.result.AbstractNode;
import gtd.stack.AbstractStackNode;
import gtd.stack.LiteralStackNode;
import gtd.stack.NonTerminalStackNode;

/*
S ::= A
A ::= AA | a
*/
public class Ambiguous7 extends SGTDBF{
	private final static AbstractStackNode NONTERMINAL_A0 = new NonTerminalStackNode(0, 0, "A");
	private final static AbstractStackNode NONTERMINAL_A1 = new NonTerminalStackNode(1, 0, "A");
	private final static AbstractStackNode NONTERMINAL_A2 = new NonTerminalStackNode(2, 1, "A");
	private final static AbstractStackNode LITERAL_a3 = new LiteralStackNode(3, 0, new char[]{'a'});
	
	public Ambiguous7(char[] input){
		super(input);
	}
	
	private final static AbstractStackNode[] S_EXPECT;
	static{
		ExpectBuilder eb = new ExpectBuilder();
		eb.addAlternative(NONTERMINAL_A0);
		S_EXPECT = eb.buildExpectMatrix();
	}
	
	public AbstractStackNode[] S(){
		return S_EXPECT;
	}
	
	private final static AbstractStackNode[] A_EXPECT;
	static{
		ExpectBuilder eb = new ExpectBuilder();
		eb.addAlternative(NONTERMINAL_A1, NONTERMINAL_A2);
		eb.addAlternative(LITERAL_a3);
		A_EXPECT = eb.buildExpectMatrix();
	}
	
	public AbstractStackNode[] A(){
		return A_EXPECT;
	}
	
	public static void main(String[] args){
		Ambiguous7 a7 = new Ambiguous7("aaaa".toCharArray());
		AbstractNode result = a7.parse("S");
		System.out.println(result);
		
		System.out.println("S([A(A(A(a),A(a)),A(A(a),A(a))),A(A(a),[A(A(a),A(A(a),A(a))),A(A(A(a),A(a)),A(a))]),A([A(A(a),A(A(a),A(a))),A(A(A(a),A(a)),A(a))],A(a))]) <- good");
	}
}
