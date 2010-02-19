package gll.tests;

import gll.SGLL;
import gll.nodes.INode;
import gll.stack.NonTerminalParseStackNode;
import gll.stack.ParseStackNode;
import gll.stack.TerminalParseStackNode;

public class NotAUselessSelfLoop extends SGLL{
	private final static ParseStackNode NONTERMINAL_A = new NonTerminalParseStackNode("A");
	private final static ParseStackNode NONTERMINAL_B = new NonTerminalParseStackNode("B");
	private final static ParseStackNode NONTERMINAL_C = new NonTerminalParseStackNode("C");
	private final static ParseStackNode TERMINAL_a = new TerminalParseStackNode("a".getBytes());
	
	public NotAUselessSelfLoop(String start, byte[] input){
		super(start, input);
	}
	
	public void S(){
		expect(NONTERMINAL_A, NONTERMINAL_A);
		
		expect(NONTERMINAL_B);
	}
	
	public void A(){
		expect(NONTERMINAL_C, NONTERMINAL_C);
		
		expect(TERMINAL_a);
	}
	
	public void B(){
		expect(NONTERMINAL_A, NONTERMINAL_A);

		expect(NONTERMINAL_C, NONTERMINAL_C);
	}
	
	public void C(){
		expect(NONTERMINAL_A, NONTERMINAL_A);
		
		expect(TERMINAL_a);
	}
	
	public static void main(String[] args){
		NotAUselessSelfLoop pusl = new NotAUselessSelfLoop("S", "aaa".getBytes());
		INode result = pusl.parse();
		
		System.out.println(result);
	}
}
