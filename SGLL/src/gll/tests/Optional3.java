package gll.tests;

import gll.SGLL;
import gll.stack.LiteralParseStackNode;
import gll.stack.NonTerminalParseStackNode;
import gll.stack.OptionalParseStackNode;
import gll.stack.ParseStackNode;

/*
S ::= a(A?) | aA
A ::= a
*/
public class Optional3 extends SGLL{
	private final static ParseStackNode LITERAL_a0 = new LiteralParseStackNode(new char[]{'a'}, 0);
	private final static ParseStackNode LITERAL_a1 = new LiteralParseStackNode(new char[]{'a'}, 1);
	private final static ParseStackNode LITERAL_a2 = new LiteralParseStackNode(new char[]{'a'}, 2);
	private final static ParseStackNode NONTERMINAL_A3 = new NonTerminalParseStackNode("A", 3);
	private final static ParseStackNode NONTERMINAL_A4 = new NonTerminalParseStackNode("A", 4);
	private final static ParseStackNode NON_TERMINAL_O5 = new NonTerminalParseStackNode("O", 5);
	private final static ParseStackNode OPTIONAL_6 = new OptionalParseStackNode(6, NON_TERMINAL_O5, "O?");
	
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

