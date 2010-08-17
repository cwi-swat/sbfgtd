package gll.tests;

import gll.SGLL;
import gll.result.AbstractNode;
import gll.stack.AbstractStackNode;
import gll.stack.LiteralStackNode;
import gll.stack.NonTerminalStackNode;

/*
S ::= A | B
A ::= B | a
B ::= A | a
*/
public class UselessSelfLoop extends SGLL{
	private final static AbstractStackNode NONTERMINAL_A0 = new NonTerminalStackNode(0, "A");
	private final static AbstractStackNode NONTERMINAL_B1 = new NonTerminalStackNode(1, "B");
	private final static AbstractStackNode NONTERMINAL_A2 = new NonTerminalStackNode(2, "A");
	private final static AbstractStackNode NONTERMINAL_B3 = new NonTerminalStackNode(3, "B");
	private final static AbstractStackNode LITERAL_a4 = new LiteralStackNode(4, new char[]{'a'});
	private final static AbstractStackNode LITERAL_a5 = new LiteralStackNode(5, new char[]{'a'});
	
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
		
		System.out.println("[S([B([A(cycle(B,2)),A(a)]),B(a)]),S([A([B(cycle(A,2)),B(a)]),A(a)])] <- good");
	}
}
