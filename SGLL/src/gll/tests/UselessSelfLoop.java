package gll.tests;

import gll.SGLL;
import gll.stack.NonTerminalParseStackNode;
import gll.stack.ParseStackNode;
import gll.stack.TerminalParseStackNode;

/*
S ::= A | B
A ::= B | a
B ::= A | a
*/
public class UselessSelfLoop extends SGLL{
	private final static ParseStackNode NONTERMINAL_A0 = new NonTerminalParseStackNode("A", 0);
	private final static ParseStackNode NONTERMINAL_B1 = new NonTerminalParseStackNode("B", 1);
	private final static ParseStackNode TERMINAL_a2 = new TerminalParseStackNode("a".toCharArray(), 2);
	
	public UselessSelfLoop(char[] input){
		super(input);
	}
	
	public void S(){
		expect(NONTERMINAL_A0);
		
		expect(NONTERMINAL_B1);
	}
	
	public void A(){
		expect(NONTERMINAL_B1);
		
		expect(TERMINAL_a2);
	}
	
	public void B(){
		expect(NONTERMINAL_A0);
		
		expect(TERMINAL_a2);
	}
	
	public static void main(String[] args){
		UselessSelfLoop usl = new UselessSelfLoop("a".toCharArray());
		System.out.println(usl.parse("S"));
		
		System.out.println("[S([A(B(a)),A(a)]),S([B(A(a)),B(a)])] <- good");
	}
}
