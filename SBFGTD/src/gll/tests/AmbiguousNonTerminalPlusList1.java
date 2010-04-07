package gll.tests;

import gll.SGLL;
import gll.stack.NonTerminalListParseStackNode;
import gll.stack.ParseStackNode;
import gll.stack.TerminalParseStackNode;

public class AmbiguousNonTerminalPlusList1 extends SGLL{
	private final static ParseStackNode NONTERMINAL_LIST0 = new NonTerminalListParseStackNode(0, "A", "A+", true);
	private final static ParseStackNode TERMINAL_a1 = new TerminalParseStackNode(new char[]{'a'}, 1);
	private final static ParseStackNode TERMINAL_a2 = new TerminalParseStackNode(new char[]{'a'}, 2);
	private final static ParseStackNode TERMINAL_a3 = new TerminalParseStackNode(new char[]{'a'}, 3);
	
	public AmbiguousNonTerminalPlusList1(char[] input){
		super(input);
	}
	
	public void S(){
		expect(TERMINAL_a1, NONTERMINAL_LIST0);
		
		expect(NONTERMINAL_LIST0, TERMINAL_a2);
	}
	
	public void A(){
		expect(TERMINAL_a3);
	}
	
	public static void main(String[] args){
		AmbiguousNonTerminalPlusList1 nrpl1 = new AmbiguousNonTerminalPlusList1("aaa".toCharArray());
		System.out.println(nrpl1.parse("S"));
		
		System.out.println("[S(a,A+(A(a),A(a))),S(A+(A(a),A(a)),a)] <- good");
	}
}
