package gll.tests;

import gll.SGLL;
import gll.stack.NonTerminalListParseStackNode;
import gll.stack.ParseStackNode;
import gll.stack.TerminalParseStackNode;

public class AmbiguousNonTerminalPlusList2 extends SGLL{
	private final static ParseStackNode NONTERMINAL_LIST0 = new NonTerminalListParseStackNode(0, "A", "A+", true);
	private final static ParseStackNode TERMINAL_a1 = new TerminalParseStackNode(new char[]{'a'}, 1);
	private final static ParseStackNode TERMINAL_aa2 = new TerminalParseStackNode(new char[]{'a', 'a'}, 2);
	
	public AmbiguousNonTerminalPlusList2(char[] input){
		super(input);
	}
	
	public void S(){
		expect(NONTERMINAL_LIST0);
	}
	
	public void A(){
		expect(TERMINAL_a1);
		
		expect(TERMINAL_aa2);
	}
	
	public static void main(String[] args){
		AmbiguousNonTerminalPlusList2 nrpl2 = new AmbiguousNonTerminalPlusList2("aaa".toCharArray());
		System.out.println(nrpl2.parse("S"));
		
		System.out.println("S([A+(A(a),A(aa)),A+(A(a),A(a),A(a)),A+(A(aa),A(a))]) <- good");
	}
}
