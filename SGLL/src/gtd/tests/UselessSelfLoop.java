package gtd.tests;

import gtd.SBFGTD;
import gtd.result.AbstractNode;
import gtd.stack.AbstractStackNode;
import gtd.stack.LiteralStackNode;
import gtd.stack.NonTerminalStackNode;

/*
S ::= A | B
A ::= B | a
B ::= A | a
*/
public class UselessSelfLoop extends SBFGTD{
	private final static AbstractStackNode NONTERMINAL_A0 = new NonTerminalStackNode(0, 0, "A");
	private final static AbstractStackNode NONTERMINAL_B1 = new NonTerminalStackNode(1, 0, "B");
	private final static AbstractStackNode NONTERMINAL_A2 = new NonTerminalStackNode(2, 0, "A");
	private final static AbstractStackNode NONTERMINAL_B3 = new NonTerminalStackNode(3, 0, "B");
	private final static AbstractStackNode LITERAL_a4 = new LiteralStackNode(4, 0, new char[]{'a'});
	private final static AbstractStackNode LITERAL_a5 = new LiteralStackNode(5, 0, new char[]{'a'});
	
	public UselessSelfLoop(char[] input){
		super(input);
	}
	
	public void S(){
		expect(NONTERMINAL_A0);
		
		expect(NONTERMINAL_B1);
	}
	
	public void A(){
		expect(NONTERMINAL_B3);
		
		expect(LITERAL_a4);
	}
	
	public void B(){
		expect(NONTERMINAL_A2);
		
		expect(LITERAL_a5);
	}
	
	public static void main(String[] args){
		UselessSelfLoop usl = new UselessSelfLoop("a".toCharArray());
		AbstractNode result = usl.parse("S");
		System.out.println(result);
		
		System.out.println("[S([A([B(cycle(A,2)),B(a)]),A(a)]),S([B([A(cycle(B,2)),A(a)]),B(a)])] <- good");
	}
}
