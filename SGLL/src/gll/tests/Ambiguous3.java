package gll.tests;

import gll.SGLL;
import gll.stack.NonTerminalStackNode;
import gll.stack.StackNode;
import gll.stack.LiteralStackNode;

/*
S ::= AA
A ::= aa | a
*/
public class Ambiguous3 extends SGLL{
	private final static StackNode NONTERMINAL_A0 = new NonTerminalStackNode("A", 0);
	private final static StackNode NONTERMINAL_A1 = new NonTerminalStackNode("A", 1);
	private final static StackNode LITERAL_a2 = new LiteralStackNode(new char[]{'a'}, 2);
	private final static StackNode LITERAL_aa3 = new LiteralStackNode(new char[]{'a','a'}, 3);
	
	public Ambiguous3(char[] input){
		super(input);
	}
	
	public void S(){
		expect(NONTERMINAL_A0, NONTERMINAL_A1);
	}
	
	public void A(){
		expect(LITERAL_a2);
		
		expect(LITERAL_aa3);
	}
	
	public static void main(String[] args){
		Ambiguous3 a3 = new Ambiguous3("aaa".toCharArray());
		System.out.println(a3.parse("S"));
		
		System.out.println("[S(A(a),A(aa)),S(A(aa),A(a))] <- good");
	}
}
