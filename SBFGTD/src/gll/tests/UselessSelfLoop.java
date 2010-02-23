package gll.tests;

import gll.SGLL;
import gll.nodes.INode;
import gll.stack.NonTerminalParseStackNode;
import gll.stack.ParseStackNode;
import gll.stack.TerminalParseStackNode;

/*
S ::= A | B
A ::= B | a
B ::= A | a
*/
public class UselessSelfLoop extends SGLL{
	private final static ParseStackNode NONTERMINAL_A = new NonTerminalParseStackNode("A");
	private final static ParseStackNode NONTERMINAL_B = new NonTerminalParseStackNode("B");
	private final static ParseStackNode TERMINAL_a = new TerminalParseStackNode("a".getBytes());
	
	public UselessSelfLoop(String start, byte[] input){
		super(start, input);
	}
	
	public void S(){
		expect(NONTERMINAL_A);
		
		expect(NONTERMINAL_B);
	}
	
	public void A(){
		expect(NONTERMINAL_B);
		
		expect(TERMINAL_a);
	}
	
	public void B(){
		expect(NONTERMINAL_A);
		
		expect(TERMINAL_a);
	}
	
	public static void main(String[] args){
		UselessSelfLoop usl = new UselessSelfLoop("S", "a".getBytes());
		INode result = usl.parse();
		
		System.out.println(result);
		System.out.println("[S([A(B(a)),A(a)]),S([B(A(a)),B(a)])] <- good");
	}
}
