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
	private final static StackNode NONTERMINAL_A0 = new NonTerminalStackNode(0, "A");
	private final static StackNode NONTERMINAL_B1 = new NonTerminalStackNode(1, "B");
	private final static StackNode NONTERMINAL_C2 = new NonTerminalStackNode(2, "C");
	private final static StackNode NONTERMINAL_D3 = new NonTerminalStackNode(3, "D");
	private final static StackNode NONTERMINAL_E4 = new NonTerminalStackNode(4, "E");
	private final static StackNode NONTERMINAL_F5 = new NonTerminalStackNode(5, "F");
	private final static StackNode NONTERMINAL_G6 = new NonTerminalStackNode(6, "G");
	private final static StackNode LITERAL_a7 = new LiteralStackNode(7, new char[]{'a'});
	
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
