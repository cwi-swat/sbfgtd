package gll.tests;

import gll.SGLL;
import gll.stack.NonTerminalStackNode;
import gll.stack.StackNode;
import gll.stack.LiteralStackNode;

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
	private final static StackNode NONTERMINAL_A0 = new NonTerminalStackNode("A", 0);
	private final static StackNode NONTERMINAL_B1 = new NonTerminalStackNode("B", 1);
	private final static StackNode NONTERMINAL_C2 = new NonTerminalStackNode("C", 2);
	private final static StackNode NONTERMINAL_D3 = new NonTerminalStackNode("D", 3);
	private final static StackNode NONTERMINAL_E4 = new NonTerminalStackNode("E", 4);
	private final static StackNode NONTERMINAL_F5 = new NonTerminalStackNode("F", 5);
	private final static StackNode NONTERMINAL_G6 = new NonTerminalStackNode("G", 6);
	private final static StackNode LITERAL_a7 = new LiteralStackNode(new char[]{'a'}, 7);
	
	public Ambiguous6(char[] input){
		super(input);
	}
	
	public void S(){
		expect(NONTERMINAL_A0);
		
		expect(NONTERMINAL_E4);
	}
	
	public void A(){
		expect(NONTERMINAL_B1);
	}
	
	public void B(){
		expect(NONTERMINAL_C2);
	}
	
	public void C(){
		expect(NONTERMINAL_D3);
	}
	
	public void D(){
		expect(NONTERMINAL_E4);
		
		expect(LITERAL_a7);
	}
	
	public void E(){
		expect(NONTERMINAL_F5);
	}
	
	public void F(){
		expect(NONTERMINAL_G6);
	}
	
	public void G(){
		expect(LITERAL_a7);
	}
	
	public static void main(String[] args){
		Ambiguous6 a6 = new Ambiguous6("a".toCharArray());
		System.out.println(a6.parse("S"));
		
		System.out.println("[S(E(F(G(a)))),S(A(B(C([D(E(F(G(a)))),D(a)]))))] <- good");
	}
}
