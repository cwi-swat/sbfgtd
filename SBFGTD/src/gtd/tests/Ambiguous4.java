package gtd.tests;

import gtd.SBFGTD;
import gtd.result.AbstractNode;
import gtd.stack.AbstractStackNode;
import gtd.stack.LiteralStackNode;
import gtd.stack.NonTerminalStackNode;

/*
S ::= AA
A ::= BB
B ::= bb | b
*/
public class Ambiguous4 extends SBFGTD{
	private final static AbstractStackNode NONTERMINAL_A0 = new NonTerminalStackNode(0, 0, "A");
	private final static AbstractStackNode NONTERMINAL_A1 = new NonTerminalStackNode(1, 1, "A");
	private final static AbstractStackNode NONTERMINAL_B2 = new NonTerminalStackNode(2, 0, "B");
	private final static AbstractStackNode NONTERMINAL_B3 = new NonTerminalStackNode(3, 1, "B");
	private final static AbstractStackNode LITERAL_b4 = new LiteralStackNode(4, 0, new char[]{'b'});
	private final static AbstractStackNode LITERAL_bb5 = new LiteralStackNode(5, 0, new char[]{'b','b'});
	
	public Ambiguous4(char[] input){
		super(input);
	}
	
	public void S(){
		expect(NONTERMINAL_A0, NONTERMINAL_A1);
	}
	
	public void A(){
		expect(NONTERMINAL_B2, NONTERMINAL_B3);
	}
	
	public void B(){
		expect(LITERAL_b4);
		
		expect(LITERAL_bb5);
	}
	
	public static void main(String[] args){
		Ambiguous4 a4 = new Ambiguous4("bbbbbb".toCharArray());
		AbstractNode result = a4.parse("S");
		System.out.println(result);
		
		System.out.println("[S(A(B(b),B(b)),A(B(bb),B(bb))),S(A(B(bb),B(bb)),A(B(b),B(b))),S([A(B(bb),B(b)),A(B(b),B(bb))],[A(B(bb),B(b)),A(B(b),B(bb))])] <- good");
	}
}
