package gll.tests;

import gll.SGLL;
import gll.stack.NonTerminalListStackNode;
import gll.stack.StackNode;
import gll.stack.LiteralStackNode;

/*
S ::= aA+ | A+a
A ::= a
*/
public class AmbiguousNonTerminalPlusList1 extends SGLL{
	private final static StackNode NONTERMINAL_LIST0 = new NonTerminalListStackNode(0, "A", "A+", true);
	private final static StackNode LITERAL_a1 = new LiteralStackNode(new char[]{'a'}, 1);
	private final static StackNode LITERAL_a2 = new LiteralStackNode(new char[]{'a'}, 2);
	private final static StackNode LITERAL_a3 = new LiteralStackNode(new char[]{'a'}, 3);
	
	public AmbiguousNonTerminalPlusList1(char[] input){
		super(input);
	}
	
	public void S(){
		expect(LITERAL_a1, NONTERMINAL_LIST0);
		
		expect(NONTERMINAL_LIST0, LITERAL_a2);
	}
	
	public void A(){
		expect(LITERAL_a3);
	}
	
	public static void main(String[] args){
		AmbiguousNonTerminalPlusList1 nrpl1 = new AmbiguousNonTerminalPlusList1("aaa".toCharArray());
		System.out.println(nrpl1.parse("S"));
		
		System.out.println("[S(a,A+(A+(A(a)),A(a))),S(A+(A+(A(a)),A(a)),a)] <- good");
	}
}
