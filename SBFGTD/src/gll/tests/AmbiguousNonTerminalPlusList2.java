package gll.tests;

import gll.SGLL;
import gll.stack.NonTerminalListStackNode;
import gll.stack.StackNode;
import gll.stack.LiteralStackNode;
/*
S ::= A+
A ::= a | aa
*/
public class AmbiguousNonTerminalPlusList2 extends SGLL{
	private final static StackNode NONTERMINAL_LIST0 = new NonTerminalListStackNode(0, "A", "A+", true);
	private final static StackNode LITERAL_a1 = new LiteralStackNode(new char[]{'a'}, 1);
	private final static StackNode LITERAL_aa2 = new LiteralStackNode(new char[]{'a', 'a'}, 2);
	
	public AmbiguousNonTerminalPlusList2(char[] input){
		super(input);
	}
	
	public void S(){
		expect(NONTERMINAL_LIST0);
	}
	
	public void A(){
		expect(LITERAL_a1);
		
		expect(LITERAL_aa2);
	}
	
	public static void main(String[] args){
		AmbiguousNonTerminalPlusList2 nrpl2 = new AmbiguousNonTerminalPlusList2("aaa".toCharArray());
		System.out.println(nrpl2.parse("S"));
		
		System.out.println("S([A+(A+(A(a)),A(aa)),A+([A+(A(aa)),A+(A+(A(a)),A(a))],A(a))]) <- good");
	}
}
