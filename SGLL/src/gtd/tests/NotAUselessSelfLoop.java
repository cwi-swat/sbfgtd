package gtd.tests;

import gtd.SBFGTD;
import gtd.result.AbstractNode;
import gtd.stack.AbstractStackNode;
import gtd.stack.LiteralStackNode;
import gtd.stack.NonTerminalStackNode;

/*
S ::= AA | B
A ::= CC | a
B ::= AA | CC
C ::= AA | a
*/
public class NotAUselessSelfLoop extends SBFGTD{
	private final static AbstractStackNode NONTERMINAL_A0 = new NonTerminalStackNode(0, 0, "A");
	private final static AbstractStackNode NONTERMINAL_A1 = new NonTerminalStackNode(1, 1, "A");
	private final static AbstractStackNode NONTERMINAL_A2 = new NonTerminalStackNode(2, 0, "A");
	private final static AbstractStackNode NONTERMINAL_A3 = new NonTerminalStackNode(3, 1, "A");
	private final static AbstractStackNode NONTERMINAL_A4 = new NonTerminalStackNode(4, 0, "A");
	private final static AbstractStackNode NONTERMINAL_A5 = new NonTerminalStackNode(5, 1, "A");
	private final static AbstractStackNode NONTERMINAL_B6 = new NonTerminalStackNode(6, 0, "B");
	private final static AbstractStackNode NONTERMINAL_C7 = new NonTerminalStackNode(7, 0, "C");
	private final static AbstractStackNode NONTERMINAL_C8 = new NonTerminalStackNode(8, 1, "C");
	private final static AbstractStackNode NONTERMINAL_C9 = new NonTerminalStackNode(9, 0, "C");
	private final static AbstractStackNode NONTERMINAL_C10 = new NonTerminalStackNode(10, 1, "C");
	private final static AbstractStackNode LITERAL_a11 = new LiteralStackNode(11, 0, new char[]{'a'});
	private final static AbstractStackNode LITERAL_a12 = new LiteralStackNode(12, 0, new char[]{'a'});
	
	public NotAUselessSelfLoop(char[] input){
		super(input);
	}
	
	public void S(){
		expect(NONTERMINAL_A0, NONTERMINAL_A1);
		
		expect(NONTERMINAL_B6);
	}
	
	public void A(){
		expect(NONTERMINAL_C7, NONTERMINAL_C8);
		
		expect(LITERAL_a11);
	}
	
	public void B(){
		expect(NONTERMINAL_A2, NONTERMINAL_A3);

		expect(NONTERMINAL_C9, NONTERMINAL_C10);
	}
	
	public void C(){
		expect(NONTERMINAL_A4, NONTERMINAL_A5);
		
		expect(LITERAL_a12);
	}
	
	public static void main(String[] args){
		NotAUselessSelfLoop nausl = new NotAUselessSelfLoop("aaa".toCharArray());
		AbstractNode result = nausl.parse("S");
		System.out.println(result);
		
		System.out.println("[S([B(C(A(a),A(a)),C(a)),B(C(a),C(A(a),A(a))),B(A(a),A(C(a),C(a))),B(A(C(a),C(a)),A(a))]),S(A(a),A(C(a),C(a))),S(A(C(a),C(a)),A(a))] <- good");
	}
}
