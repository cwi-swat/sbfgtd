package gll.examples;

import gll.SGLL;
import gll.stack.NonTerminalParseStackNode;
import gll.stack.TerminalParseStackNode;

//TODO This is broken

/*
S ::= A
A ::= Aa | a
*/
public class LeftRecursion extends SGLL{
	private final String NONTERMINAL_A = "A";
	private final byte[] TERMINAL_a = "a".getBytes();
	
	public LeftRecursion(String start, byte[] input){
		super(start, input);
	}
	
	public void S(){
		expectAlternative(new NonTerminalParseStackNode(NONTERMINAL_A), new TerminalParseStackNode(TERMINAL_a));
		
		expectAlternative(new TerminalParseStackNode(TERMINAL_a));
	}
	
	public void A(){
		expect(new TerminalParseStackNode(TERMINAL_a));
	}
	
	public void a(){
		reduceTerminal();
	}
	
	public static void main(String[] args){
		LeftRecursion lr = new LeftRecursion("S", "aa".getBytes());
		lr.parse();
	}
}