package gll.tests;

import gll.SGLL;
import gll.stack.NonTerminalParseStackNode;
import gll.stack.ParseStackNode;
import gll.stack.TerminalParseStackNode;

/*
S ::= Ab
A ::= aa
*/
public class Simple1 extends SGLL{
	private final static ParseStackNode NONTERMINAL_A0 = new NonTerminalParseStackNode("A", 0);
	private final static ParseStackNode TERMINAL_aa1 = new TerminalParseStackNode("aa".toCharArray(), 1);
	private final static ParseStackNode TERMINAL_b2 = new TerminalParseStackNode("b".toCharArray(), 2);
	
	public Simple1(char[] input){
		super(input);
	}
	
	public void S(){
		expect(NONTERMINAL_A0, TERMINAL_b2);
	}
	
	public void A(){
		expect(TERMINAL_aa1);
	}
	
	public static void main(String[] args){
		Simple1 s = new Simple1("aab".toCharArray());
		System.out.println(s.parse("S"));
		
		System.out.println("S(A(aa),b) <- good");
	}
}
