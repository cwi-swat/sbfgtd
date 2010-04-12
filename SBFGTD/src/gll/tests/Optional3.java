package gll.tests;

import gll.SGLL;
import gll.stack.LiteralStackNode;
import gll.stack.NonTerminalStackNode;
import gll.stack.OptionalStackNode;
import gll.stack.StackNode;

/*
S ::= a(A?) | aA
A ::= a
*/
public class Optional3 extends SGLL{
	private final static StackNode LITERAL_a0 = new LiteralStackNode(new char[]{'a'}, 0);
	private final static StackNode LITERAL_a1 = new LiteralStackNode(new char[]{'a'}, 1);
	private final static StackNode LITERAL_a2 = new LiteralStackNode(new char[]{'a'}, 2);
	private final static StackNode NONTERMINAL_A3 = new NonTerminalStackNode("A", 3);
	private final static StackNode NONTERMINAL_A4 = new NonTerminalStackNode("A", 4);
	private final static StackNode NON_TERMINAL_O5 = new NonTerminalStackNode("O", 5);
	private final static StackNode OPTIONAL_6 = new OptionalStackNode(6, NON_TERMINAL_O5, "O?");
	
	public Optional3(char[] input){
		super(input);
	}
	
	public void S(){
		expect(LITERAL_a0, OPTIONAL_6);
		
		expect(LITERAL_a1, NONTERMINAL_A3);
	}
	
	public void A(){
		expect(LITERAL_a2);
	}
	
	public void O(){
		expect(NONTERMINAL_A4);
	}
	
	public static void main(String[] args){
		Optional3 o3 = new Optional3("aa".toCharArray());
		System.out.println(o3.parse("S"));
		System.out.println("[S(a,O?(O(A(a)))),S(a,A(a))] <- good");
	}
}

