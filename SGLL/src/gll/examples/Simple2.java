package gll.examples;

import gll.SGLL;
import gll.nodes.INode;
import gll.stack.NonTerminalParseStackNode;
import gll.stack.TerminalParseStackNode;

// TODO This is broken

/*
S ::= AB
A ::= a
B ::= b
*/
public class Simple2 extends SGLL{
	private final String NONTERMINAL_A = "A";
	private final String NONTERMINAL_B = "B";
	private final byte[] TERMINAL_a = "a".getBytes();
	private final byte[] TERMINAL_b = "b".getBytes();
	
	public Simple2(String start, byte[] input){
		super(start, input);
	}
	
	public void S(){
		expect(new NonTerminalParseStackNode(NONTERMINAL_A), new NonTerminalParseStackNode(NONTERMINAL_B));
	}
	
	public void A(){
		expect(new TerminalParseStackNode(TERMINAL_a));
	}
	
	public void B(){
		expect(new TerminalParseStackNode(TERMINAL_b));
	}
	
	public void a(){
		reduceTerminal();
	}
	
	public void b(){
		reduceTerminal();
	}
	
	public static void main(String[] args){
		Simple2 s2 = new Simple2("S", "ab".getBytes());
		INode result = s2.parse();
		
		System.out.println(result);
	}
}