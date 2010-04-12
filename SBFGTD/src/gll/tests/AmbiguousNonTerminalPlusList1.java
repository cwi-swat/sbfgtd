package gll.tests;

import gll.SGLL;
import gll.stack.ListStackNode;
import gll.stack.NonTerminalStackNode;
import gll.stack.StackNode;
import gll.stack.LiteralStackNode;

/*
S ::= aA+ | A+a
A ::= a
*/
public class AmbiguousNonTerminalPlusList1 extends SGLL{
	private final static StackNode NONTERMINAL_A0 = new NonTerminalStackNode(0, "A");
	private final static StackNode NONTERMINAL_A1 = new NonTerminalStackNode(1, "A");
	private final static StackNode LIST2 = new ListStackNode(2, NONTERMINAL_A0, "A+", true);
	private final static StackNode LIST3 = new ListStackNode(3, NONTERMINAL_A1, "A+", true);
	private final static StackNode LITERAL_a4 = new LiteralStackNode(4, new char[]{'a'});
	private final static StackNode LITERAL_a5 = new LiteralStackNode(5, new char[]{'a'});
	private final static StackNode LITERAL_a6 = new LiteralStackNode(6, new char[]{'a'});
	
	public AmbiguousNonTerminalPlusList1(char[] input){
		super(input);
	}
	
	public void S(){
		expect(LITERAL_a4, LIST2);
		
		expect(LIST3, LITERAL_a5);
	}
	
	public void A(){
		expect(LITERAL_a6);
	}
	
	public static void main(String[] args){
		AmbiguousNonTerminalPlusList1 nrpl1 = new AmbiguousNonTerminalPlusList1("aaa".toCharArray());
		System.out.println(nrpl1.parse("S"));
		
		System.out.println("[S(a,A+(A+(A(a)),A(a))),S(A+(A+(A(a)),A(a)),a)] <- good");
	}
}
