package gtd.tests;

import gtd.SGTDBF;
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
	
	public void S(){
		expect(NONTERMINAL_A0);

		expect(NONTERMINAL_C4);
	}
	
	public void A(){
		expect(NONTERMINAL_B2, LITERAL_a5);
		
		expect(LITERAL_a6);
	}
	
	public void B(){
		expect(NONTERMINAL_A1, LITERAL_a7);
		
		expect(LITERAL_a8);
	}
	
	public void C(){
		expect(NONTERMINAL_B3);
	}
	
	public static void main(String[] args){
		SplitAndMerge3 sm3 = new SplitAndMerge3("aaa".toCharArray());
		AbstractNode result = sm3.parse("S");
		System.out.println(result);
		
		System.out.println("[S(C(B(A(B(a),a),a))),S(A(B(A(a),a),a))] <- good");
	}
}
