package gll.tests;

import gll.SGLL;
import gll.stack.NonTerminalListParseStackNode;
import gll.stack.ParseStackNode;
import gll.stack.TerminalParseStackNode;

/*
S ::= A+
A ::= a
*/
public class NonTerminalPlusList extends SGLL{
	private final static ParseStackNode NONTERMINAL_LIST0 = new NonTerminalListParseStackNode(0, "A", true);
	private final static ParseStackNode TERMINAL_A1 = new TerminalParseStackNode(new char[]{'a'}, 1);
	
	public NonTerminalPlusList(char[] input){
		super(input);
	}
	
	public void S(){
		expect(NONTERMINAL_LIST0);
	}
	
	public void A(){
		expect(TERMINAL_A1);
	}
	
	public static void main(String[] args){
		NonTerminalPlusList nrpl = new NonTerminalPlusList("aaa".toCharArray());
		System.out.println(nrpl.parse("S"));
		
		System.out.println("S(List0(A(a),A(a),A(a))) <- good");
	}
}
