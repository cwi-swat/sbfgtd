package gll.tests;

import gll.SGLL;
import gll.nodes.INode;
import gll.stack.NonTerminalParseStackNode;
import gll.stack.ParseStackNode;
import gll.stack.TerminalParseStackNode;

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
		/*AmbiguousRecursive ar = new AmbiguousRecursive("S", "aaa".getBytes());
		INode result = ar.parse();
		
		System.out.println(result);
		System.out.println("parsetree([S(S(a),S(S(a),S(a))),S(S(a),S(a),S(a)),S(S(S(a),S(a)),S(a))]) <- good");
		*/
		
		long start = System.currentTimeMillis();
		AmbiguousRecursive ar = new AmbiguousRecursive("S", "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaa".getBytes());
		INode result = ar.parse();
		long end = System.currentTimeMillis();
		System.out.println((end - start)+"ms");
	}
}
