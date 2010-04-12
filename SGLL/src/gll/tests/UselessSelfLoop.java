package gll.tests;

import gll.SGLL;
import gll.stack.NonTerminalStackNode;
import gll.stack.StackNode;
import gll.stack.LiteralStackNode;

/*
S ::= A | B
A ::= B | a
B ::= A | a
*/
public class UselessSelfLoop extends SGLL{
	private final static StackNode NONTERMINAL_A0 = new NonTerminalStackNode(0, "A");
	private final static StackNode NONTERMINAL_B1 = new NonTerminalStackNode(1, "B");
	private final static StackNode LITERAL_a2 = new LiteralStackNode(2, new char[]{'a'});
	
	public UselessSelfLoop(char[] input){
		super(input);
	}
	
	public void S(){
		expect(NONTERMINAL_A0);
		
		expect(NONTERMINAL_B1);
	}
	
	public void A(){
		expect(NONTERMINAL_B1);
		
		expect(LITERAL_a2);
	}
	
	public void B(){
		expect(NONTERMINAL_A0);
		
		expect(LITERAL_a2);
	}
	
	public static void main(String[] args){
		UselessSelfLoop usl = new UselessSelfLoop("a".toCharArray());
		System.out.println(usl.parse("S"));
		
		System.out.println("[S([A(B(a)),A(a)]),S([B(A(a)),B(a)])] <- good");
	}
}
