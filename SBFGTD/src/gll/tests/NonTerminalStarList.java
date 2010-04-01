package gll.tests;

import gll.SGLL;
import gll.stack.NonTerminalListParseStackNode;
import gll.stack.ParseStackNode;
import gll.stack.TerminalParseStackNode;

/*
S ::= A+
A ::= a
*/
public class NonTerminalStarList extends SGLL{
	private final static ParseStackNode NONTERMINAL_LIST0 = new NonTerminalListParseStackNode(0, "A", false);
	private final static ParseStackNode TERMINAL_A1 = new TerminalParseStackNode(new char[]{'a'}, 1);
	
	public NonTerminalStarList(char[] input){
		super(input);
	}
	
	public void S(){
		expect(NONTERMINAL_LIST0);
	}
	
	public void A(){
		expect(TERMINAL_A1);
	}
	
	public static void main(String[] args){
		NonTerminalStarList nrsl = new NonTerminalStarList("aaa".toCharArray());
		System.out.println(nrsl.parse("S"));
		
		System.out.println("S(A*(A(a),A(a),A(a))) <- good");
	}
}
