package gll.tests;

import gll.SGLL;
import gll.nodes.INode;
import gll.stack.NonTerminalParseStackNode;
import gll.stack.ParseStackNode;
import gll.stack.TerminalParseStackNode;

// TODO This is broken (doesn't terminate).

/*
S ::= SSS | SS | a
*/
public class AmbiguousRecursive extends SGLL{
	private final static ParseStackNode NONTERMINAL_S = new NonTerminalParseStackNode("S");
	private final static ParseStackNode TERMINAL_a = new TerminalParseStackNode("a".getBytes());
	
	public AmbiguousRecursive(String start, byte[] input){
		super(start, input);
	}
	
	public void S(){
		expect(NONTERMINAL_S, NONTERMINAL_S, NONTERMINAL_S);
		
		expect(NONTERMINAL_S, NONTERMINAL_S);
		
		expect(TERMINAL_a);
	}
	
	public static void main(String[] args){
		AmbiguousRecursive ar = new AmbiguousRecursive("S", "aaa".getBytes());
		INode result = ar.parse();
		
		System.out.println(result);
	}
}
