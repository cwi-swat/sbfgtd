package gll.tests;

import gll.SGLL;
import gll.stack.NonTerminalListStackNode;
import gll.stack.StackNode;
import gll.stack.LiteralStackNode;

/*
S ::= A+
A ::= a
*/
public class NonTerminalPlusList extends SGLL{
	private final static StackNode NONTERMINAL_LIST0 = new NonTerminalListStackNode(0, "A", "A+", true);
	private final static StackNode LITERAL_a1 = new LiteralStackNode(new char[]{'a'}, 1);
	
	public NonTerminalPlusList(char[] input){
		super(input);
	}
	
	public void S(){
		expect(NONTERMINAL_LIST0);
	}
	
	public void A(){
		expect(LITERAL_a1);
	}
	
	public static void main(String[] args){
		NonTerminalPlusList nrpl = new NonTerminalPlusList("aaa".toCharArray());
		System.out.println(nrpl.parse("S"));
		
		System.out.println("S(A+(A+(A+(A(a)),A(a)),A(a))) <- good");
	}
}
