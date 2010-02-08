package gll.examples;

import gll.SGLL;
import gll.stack.NonTerminalParseStackNode;
import gll.stack.TerminalParseStackNode;

/*
S ::= Ab
A ::= aa
*/
public class Simple extends SGLL{
	private final String NONTERMINAL_A = "A";
	private final byte[] TERMINAL_aa = "aa".getBytes();
	private final byte[] TERMINAL_b = "b".getBytes();
	
	public Simple(String start, byte[] input){
		super(start, input);
	}
	
	public void S(){
		expect(new NonTerminalParseStackNode(NONTERMINAL_A), new TerminalParseStackNode(TERMINAL_b));
	}
	
	public void A(){
		expect(new TerminalParseStackNode(TERMINAL_aa));
	}
	
	public void aa(){
		reduceTerminal();
	}
	
	public void b(){
		reduceTerminal();
	}
	
	public static void main(String[] args){
		Simple s = new Simple("S", "aab".getBytes());
		s.parse();
	}
}
