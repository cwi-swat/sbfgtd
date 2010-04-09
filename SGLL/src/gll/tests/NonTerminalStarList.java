package gll.tests;

import gll.SGLL;
import gll.stack.NonTerminalListParseStackNode;
import gll.stack.ParseStackNode;
import gll.stack.LiteralParseStackNode;

/*
S ::= A+
A ::= a
*/
public class NonTerminalStarList extends SGLL{
	private final static ParseStackNode NONTERMINAL_LIST0 = new NonTerminalListParseStackNode(0, "A", "A*", false);
	private final static ParseStackNode LITERAL_a1 = new LiteralParseStackNode(new char[]{'a'}, 1);
	
	public NonTerminalStarList(char[] input){
		super(input);
	}
	
	public void S(){
		expect(NONTERMINAL_LIST0);
	}
	
	public void A(){
		expect(LITERAL_a1);
	}
	
	public static void main(String[] args){
		NonTerminalStarList nrsl = new NonTerminalStarList("aaa".toCharArray());
		System.out.println(nrsl.parse("S"));
		
		System.out.println("S(A*(A*(A*(A(a)),A(a)),A(a))) <- good");
	}
}
