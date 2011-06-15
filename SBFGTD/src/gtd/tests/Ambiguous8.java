package gtd.tests;

import gtd.SGTDBF;
import gtd.preprocessing.ExpectBuilder;
import gtd.result.AbstractNode;
import gtd.stack.AbstractStackNode;
import gtd.stack.LiteralStackNode;
import gtd.stack.NonTerminalStackNode;

/*
S ::= AB | AC
A ::= a
B ::= a
C ::= a

NOTE: This test, tests prefix sharing.
*/
public class Ambiguous8 extends SGTDBF{
	private final static AbstractStackNode NONTERMINAL_A0 = new NonTerminalStackNode(0, 0, "A");
	private final static AbstractStackNode NONTERMINAL_B1 = new NonTerminalStackNode(1, 1, "B");
	private final static AbstractStackNode NONTERMINAL_C2 = new NonTerminalStackNode(2, 1, "C");
	private final static AbstractStackNode LITERAL_a3 = new LiteralStackNode(3, 0, new char[]{'a'});
	private final static AbstractStackNode LITERAL_a4 = new LiteralStackNode(4, 0, new char[]{'a'});
	private final static AbstractStackNode LITERAL_a5 = new LiteralStackNode(5, 0, new char[]{'a'});
	
	public Ambiguous8(char[] input){
		super(input);
	}
	
	public void S(){
		ExpectBuilder eb = new ExpectBuilder();
		
		eb.addAlternative(NONTERMINAL_A0, NONTERMINAL_B1);
		eb.addAlternative(NONTERMINAL_A0, NONTERMINAL_C2);
		
		expect(eb.buildExpectMatrix());
	}
	
	public void A(){
		ExpectBuilder eb = new ExpectBuilder();
		
		eb.addAlternative(LITERAL_a3);
		
		expect(eb.buildExpectMatrix());
	}
	
	public void B(){
		ExpectBuilder eb = new ExpectBuilder();
		
		eb.addAlternative(LITERAL_a4);
		
		expect(eb.buildExpectMatrix());
	}
	
	public void C(){
		ExpectBuilder eb = new ExpectBuilder();
		
		eb.addAlternative(LITERAL_a5);
		
		expect(eb.buildExpectMatrix());
	}
	
	public static void main(String[] args){
		Ambiguous8 a8 = new Ambiguous8("aa".toCharArray());
		AbstractNode result = a8.parse("S");
		System.out.println(result);
		
		System.out.println("[S(A(a),C(a)),S(A(a),B(a))] <- good");
	}
}
