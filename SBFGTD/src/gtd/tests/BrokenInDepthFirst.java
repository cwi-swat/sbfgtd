package gtd.tests;

import gtd.SGTDBF;
import gtd.preprocessing.ExpectBuilder;
import gtd.result.AbstractNode;
import gtd.stack.AbstractStackNode;
import gtd.stack.LiteralStackNode;
import gtd.stack.NonTerminalStackNode;

/*
S ::= aA | Aa
A ::= BCD
B ::= a | aa
C ::= a
D ::= a | aa
*/
public class BrokenInDepthFirst extends SGTDBF{
	private final static AbstractStackNode LITERAL_a10 = new LiteralStackNode(10, 0, new char[]{'a'});
	private final static AbstractStackNode NONTERMINAL_A0 = new NonTerminalStackNode(0, 1, "A");
	private final static AbstractStackNode LITERAL_a11 = new LiteralStackNode(11, 1, new char[]{'a'});
	private final static AbstractStackNode NONTERMINAL_A1 = new NonTerminalStackNode(1, 0, "A");
	private final static AbstractStackNode NONTERMINAL_B2 = new NonTerminalStackNode(2, 0, "B");
	private final static AbstractStackNode NONTERMINAL_C3 = new NonTerminalStackNode(3, 1, "C");
	private final static AbstractStackNode NONTERMINAL_D4 = new NonTerminalStackNode(4, 2, "D");
	private final static AbstractStackNode LITERAL_a5 = new LiteralStackNode(5, 0, new char[]{'a'});
	private final static AbstractStackNode LITERAL_aa6 = new LiteralStackNode(6, 0, new char[]{'a', 'a'});
	private final static AbstractStackNode LITERAL_a7 = new LiteralStackNode(7, 0, new char[]{'a'});
	private final static AbstractStackNode LITERAL_a8 = new LiteralStackNode(8, 0, new char[]{'a'});
	private final static AbstractStackNode LITERAL_aa9 = new LiteralStackNode(9, 0, new char[]{'a', 'a'});
	
	public BrokenInDepthFirst(char[] input){
		super(input);
	}
	
	public void S(){
		ExpectBuilder eb = new ExpectBuilder();
		
		eb.addAlternative(LITERAL_a10, NONTERMINAL_A0);
		eb.addAlternative(NONTERMINAL_A1, LITERAL_a11);
		
		expect(eb.buildExpectMatrix());
	}
	
	public void A(){
		ExpectBuilder eb = new ExpectBuilder();
		
		eb.addAlternative(NONTERMINAL_B2, NONTERMINAL_C3, NONTERMINAL_D4);
		
		expect(eb.buildExpectMatrix());
	}
	
	public void B(){
		ExpectBuilder eb = new ExpectBuilder();
		
		eb.addAlternative(LITERAL_a5);
		eb.addAlternative(LITERAL_aa6);
		
		expect(eb.buildExpectMatrix());
	}
	
	public void C(){
		ExpectBuilder eb = new ExpectBuilder();
		
		eb.addAlternative(LITERAL_a7);
		
		expect(eb.buildExpectMatrix());
	}
	
	public void D(){
		ExpectBuilder eb = new ExpectBuilder();
		
		eb.addAlternative(LITERAL_a8);
		eb.addAlternative(LITERAL_aa9);
		
		expect(eb.buildExpectMatrix());
	}
	
	public static void main(String[] args){
		BrokenInDepthFirst bidf = new BrokenInDepthFirst("aaaaa".toCharArray());
		AbstractNode result = bidf.parse("S");
		System.out.println(result);
		
		System.out.println("[S(a,[A(B(aa),C(a),D(a)),A(B(a),C(a),D(aa))]),S([A(B(aa),C(a),D(a)),A(B(a),C(a),D(aa))],a)] <- good");
	}
}
