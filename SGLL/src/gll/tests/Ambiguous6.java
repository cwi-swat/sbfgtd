package gll.tests;

import gll.SGLL;
import gll.nodes.INode;
import gll.stack.NonTerminalParseStackNode;
import gll.stack.TerminalParseStackNode;

// TODO This is broken (doesn't terminate).

/*
S ::= SSS | SS | a
*/
public class Ambiguous6 extends SGLL{
	private final String NONTERMINAL_S = "S";
	private final byte[] TERMINAL_a = "a".getBytes();
	
	public Ambiguous6(String start, byte[] input){
		super(start, input);
	}
	
	public void S(){
		expectAlternative(new NonTerminalParseStackNode(NONTERMINAL_S), new NonTerminalParseStackNode(NONTERMINAL_S), new NonTerminalParseStackNode(NONTERMINAL_S));
		
		expectAlternative(new NonTerminalParseStackNode(NONTERMINAL_S), new NonTerminalParseStackNode(NONTERMINAL_S));
		
		expectAlternative(new TerminalParseStackNode(TERMINAL_a));
	}
	
	public void a(){
		reduceTerminal();
	}
	
	public static void main(String[] args){
		Ambiguous6 a6 = new Ambiguous6("S", "aaa".getBytes());
		INode result = a6.parse();
		
		System.out.println(result);
	}
}
