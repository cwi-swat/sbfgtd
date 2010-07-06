package gll.tests;

import gll.SGLL;
import gll.result.INode;
import gll.stack.NonTerminalStackNode;
import gll.stack.AbstractStackNode;
import gll.stack.LiteralStackNode;

/*
S ::= AB
A ::= a
B ::= b
*/
public class Simple2 extends SGLL{
	private final static AbstractStackNode NONTERMINAL_A0 = new NonTerminalStackNode(0, "A");
	private final static AbstractStackNode NONTERMINAL_B1 = new NonTerminalStackNode(1, "B");
	private final static AbstractStackNode LITERAL_a2 = new LiteralStackNode(2, new char[]{'a'});
	private final static AbstractStackNode LITERAL_b3 = new LiteralStackNode(3, new char[]{'b'});
	
	public Simple2(char[] input){
		super(input);
	}
	
	public void S(){
		expect(NONTERMINAL_A0, NONTERMINAL_B1);
	}
	
	public void A(){
		expect(LITERAL_a2);
	}
	
	public void B(){
		expect(LITERAL_b3);
	}
	
	public static void main(String[] args){
		Simple2 s2 = new Simple2("ab".toCharArray());
		INode result = s2.parse("S");
		System.out.println(result);
		
		System.out.println("S(A(a),B(b)) <- good");
	}
}