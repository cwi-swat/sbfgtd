package gll.tests;

import gll.SGLL;
import gll.nodes.INode;
import gll.stack.NonTerminalParseStackNode;
import gll.stack.TerminalParseStackNode;

// TODO This is broken (doesn't terminate).

/*
S ::= SSS | SS | a
*/
public class AmbiguousRecursive extends SGLL{
	private final String NONTERMINAL_S = "S";
	private final byte[] TERMINAL_a = "a".getBytes();
	
	public AmbiguousRecursive(String start, byte[] input){
		super(start, input);
	}
	
	public void S(){
		expectAlternative(new NonTerminalParseStackNode(NONTERMINAL_S), new NonTerminalParseStackNode(NONTERMINAL_S), new NonTerminalParseStackNode(NONTERMINAL_S));
		
		expectAlternative(new NonTerminalParseStackNode(NONTERMINAL_S), new NonTerminalParseStackNode(NONTERMINAL_S));
		
		expectAlternative(new TerminalParseStackNode(TERMINAL_a));
	}
	
	public static void main(String[] args){
		AmbiguousRecursive ar = new AmbiguousRecursive("S", "aaa".getBytes());
		INode result = ar.parse();
		
		System.out.println(result);
	}
}
