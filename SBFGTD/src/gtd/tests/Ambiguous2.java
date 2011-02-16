package gtd.tests;

import gtd.SGTDBF;
import gtd.result.AbstractNode;
import gtd.stack.AbstractStackNode;
import gtd.stack.LiteralStackNode;
import gtd.stack.NonTerminalStackNode;

/*
S ::= Aab | bab
A ::= B
B ::= b
*/
public class Ambiguous2 extends SGTDBF{
	private final static AbstractStackNode NONTERMINAL_A0 = new NonTerminalStackNode(0, 0, "A");
	private final static AbstractStackNode NONTERMINAL_B1 = new NonTerminalStackNode(1, 0, "B");
	private final static AbstractStackNode LITERAL_b2 = new LiteralStackNode(2, 0, new char[]{'b'});
	private final static AbstractStackNode LITERALL_ab3 = new LiteralStackNode(3, 1, new char[]{'a','b'});
	private final static AbstractStackNode LITERAL_bab4 = new LiteralStackNode(4, 0, new char[]{'b','a','b'});
	
	public Ambiguous2(char[] input){
		super(input);
	}
	
	public void S(){
		expect(NONTERMINAL_A0, LITERALL_ab3);
		
		expect(LITERAL_bab4);
	}
	
	public void A(){
		expect(NONTERMINAL_B1);
	}
	
	public void B(){
		expect(LITERAL_b2);
	}
	
	public static void main(String[] args){
		Ambiguous2 a2 = new Ambiguous2("bab".toCharArray());
		AbstractNode result = a2.parse("S");
		System.out.println(result);
		
		System.out.println("[S(bab),S(A(B(b)),ab)] <- good");
	}
}
