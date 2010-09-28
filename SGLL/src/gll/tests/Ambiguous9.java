package gll.tests;

import gll.SGLL;
import gll.result.AbstractNode;
import gll.stack.AbstractStackNode;
import gll.stack.LiteralStackNode;
import gll.stack.NonTerminalStackNode;

/*
* S ::= E
* E ::= E + E | E * E | 1
* 
* NOTE: This test, tests prefix sharing.
*/
public class Ambiguous9 extends SGLL{
	private final static AbstractStackNode NONTERMINAL_E0 = new NonTerminalStackNode(0, "E");
	private final static AbstractStackNode NONTERMINAL_E1 = new NonTerminalStackNode(1, "E");
	private final static AbstractStackNode NONTERMINAL_E2 = new NonTerminalStackNode(2, "E");
	private final static AbstractStackNode NONTERMINAL_E3 = new NonTerminalStackNode(3, "E");
	private final static AbstractStackNode LITERAL_4 = new LiteralStackNode(4, "+".toCharArray());
	private final static AbstractStackNode LITERAL_5 = new LiteralStackNode(5, "*".toCharArray());
	private final static AbstractStackNode LITERAL_6 = new LiteralStackNode(6, "1".toCharArray());
	
	
	public Ambiguous9(char[] input){
		super(input);
	}
	
	public void S(){
		expect(NONTERMINAL_E0);
	}
	
	public void E(){
		expect(NONTERMINAL_E1, LITERAL_4, NONTERMINAL_E2);
		expect(NONTERMINAL_E1, LITERAL_5, NONTERMINAL_E3);
		
		expect(LITERAL_6);
	}
	
	public static void main(String[] args){
		Ambiguous9 a9 = new Ambiguous9("1+1+1".toCharArray());
		AbstractNode result = a9.parse("S");
		System.out.println(result);
		
		System.out.println("S([E(E(1),+,E(E(1),+,E(1))),E(E(E(1),+,E(1)),+,E(1))]) <- good");
	}
}
