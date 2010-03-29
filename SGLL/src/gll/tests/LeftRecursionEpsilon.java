package gll.tests;

import gll.SGLL;
import gll.stack.NonTerminalParseStackNode;
import gll.stack.ParseStackNode;
import gll.stack.TerminalParseStackNode;

/*
S ::= A
A ::= AA | epsilon | a
*/
public class LeftRecursionEpsilon extends SGLL{
	private final static ParseStackNode NONTERMINAL_A0 = new NonTerminalParseStackNode("A", 0);
	private final static ParseStackNode NONTERMINAL_A1 = new NonTerminalParseStackNode("A", 1);
	private final static ParseStackNode NONTERMINAL_A2 = new NonTerminalParseStackNode("A", 2);
	private final static ParseStackNode TERMINAL_a3 = new TerminalParseStackNode("a".toCharArray(), 3);
	private final static ParseStackNode EPSILON_4 = new TerminalParseStackNode("".toCharArray(), 4);
	
	public LeftRecursionEpsilon(char[] input){
		super(input);
	}
	
	public void S(){
		expect(NONTERMINAL_A0);
	}
	
	public void A(){
		expect(NONTERMINAL_A1, NONTERMINAL_A2);
		
		expect(TERMINAL_a3);
		
		expect(EPSILON_4);
	}
	
	public static void main(String[] args){
		LeftRecursionEpsilon lre = new LeftRecursionEpsilon("a".toCharArray());
		System.out.println(lre.parse("S"));
		
		System.out.println("S([A(A(),A(a)),A(A(a),A())]) <- good (TODO Add cycle)");
	}
}