package gll.tests;

import gll.SGLL;
import gll.stack.NonTerminalStackNode;
import gll.stack.StackNode;
import gll.stack.LiteralStackNode;

/*
S ::= Ab
A ::= aa
*/
public class Simple1 extends SGLL{
	private final static StackNode NONTERMINAL_A0 = new NonTerminalStackNode("A", 0);
	private final static StackNode LITERAL_aa1 = new LiteralStackNode(new char[]{'a','a'}, 1);
	private final static StackNode LITERAL_b2 = new LiteralStackNode(new char[]{'b'}, 2);
	
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
