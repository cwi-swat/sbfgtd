package gll.tests;

import gll.SGLL;
import gll.stack.ListStackNode;
import gll.stack.NonTerminalStackNode;
import gll.stack.StackNode;
import gll.stack.LiteralStackNode;

/*
S ::= A+
A ::= a
*/
public class NonTerminalPlusList extends SGLL{
	private final static StackNode NONTERMINAL_A0 = new NonTerminalStackNode("A", 0);
	private final static StackNode LIST1 = new ListStackNode(1, NONTERMINAL_A0, "A+", true);
	private final static StackNode LITERAL_a2 = new LiteralStackNode(new char[]{'a'}, 2);
	
	public NonTerminalPlusList(char[] input){
		super(input);
	}
	
	public void S(){
		expect(LIST1);
	}
	
	public void A(){
		expect(LITERAL_a2);
	}
	
	public static void main(String[] args){
		NonTerminalPlusList nrpl = new NonTerminalPlusList("aaa".toCharArray());
		System.out.println(nrpl.parse("S"));
		
		System.out.println("S(A+(A+(A+(A(a)),A(a)),A(a))) <- good");
	}
}
