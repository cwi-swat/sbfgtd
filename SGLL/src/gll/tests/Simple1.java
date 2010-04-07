package gll.tests;

import gll.SGLL;
import gll.stack.NonTerminalParseStackNode;
import gll.stack.ParseStackNode;
import gll.stack.LiteralParseStackNode;

/*
S ::= Ab
A ::= aa
*/
public class Simple1 extends SGLL{
	private final static ParseStackNode NONTERMINAL_A0 = new NonTerminalParseStackNode("A", 0);
	private final static ParseStackNode LITERAL_aa1 = new LiteralParseStackNode("aa".toCharArray(), 1);
	private final static ParseStackNode LITERAL_b2 = new LiteralParseStackNode("b".toCharArray(), 2);
	
	public Simple1(char[] input){
		super(input);
	}
	
	public void S(){
		expect(NONTERMINAL_A0, LITERAL_b2);
	}
	
	public void A(){
		expect(LITERAL_aa1);
	}
	
	public static void main(String[] args){
		Simple1 s1 = new Simple1("aab".toCharArray());
		System.out.println(s1.parse("S"));
		
		System.out.println("S(A(aa),b) <- good");
	}
}
