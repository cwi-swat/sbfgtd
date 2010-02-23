package gll.tests;

import gll.SGLL;
import gll.nodes.INode;
import gll.stack.NonTerminalParseStackNode;
import gll.stack.ParseStackNode;
import gll.stack.TerminalParseStackNode;

// TODO Broken

/*
S ::= A | E
A ::= B
B ::= C
C ::= D
D ::= E | a
E ::= F
F ::= G
G ::= a
*/
public class Ambiguous6 extends SGLL{
	private final static ParseStackNode NONTERMINAL_A = new NonTerminalParseStackNode("A");
	private final static ParseStackNode NONTERMINAL_B = new NonTerminalParseStackNode("B");
	private final static ParseStackNode NONTERMINAL_C = new NonTerminalParseStackNode("C");
	private final static ParseStackNode NONTERMINAL_D = new NonTerminalParseStackNode("D");
	private final static ParseStackNode NONTERMINAL_E = new NonTerminalParseStackNode("E");
	private final static ParseStackNode NONTERMINAL_F = new NonTerminalParseStackNode("F");
	private final static ParseStackNode NONTERMINAL_G = new NonTerminalParseStackNode("G");
	private final static ParseStackNode TERMINAL_a = new TerminalParseStackNode("a".getBytes());
	
	public Ambiguous6(String start, byte[] input){
		super(start, input);
	}
	
	public void S(){
		expect(NONTERMINAL_A);
		
		expect(NONTERMINAL_E);
	}
	
	public void A(){
		expect(NONTERMINAL_B);
	}
	
	public void B(){
		expect(NONTERMINAL_C);
	}
	
	public void C(){
		expect(NONTERMINAL_D);
	}
	
	public void D(){
		expect(NONTERMINAL_E);
		
		expect(TERMINAL_a);
	}
	
	public void E(){
		expect(NONTERMINAL_F);
	}
	
	public void F(){
		expect(NONTERMINAL_G);
	}
	
	public void G(){
		expect(TERMINAL_a);
	}
	
	public static void main(String[] args){
		Ambiguous6 a6 = new Ambiguous6("S", "a".getBytes());
		INode result = a6.parse();
		
		System.out.println(result);
		System.out.println("parsetree([S(A(B(C([D(E(F(G(a))))),D(a)]))),S(E(F(G(a))))]) <- good");
	}
}
