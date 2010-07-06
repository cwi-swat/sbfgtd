package gll.tests;

import gll.SGLL;
import gll.result.INode;
import gll.stack.ListStackNode;
import gll.stack.NonTerminalStackNode;
import gll.stack.AbstractStackNode;
import gll.stack.LiteralStackNode;

/*
S ::= A+
A ::= a
*/
public class NonTerminalPlusList extends SGLL{
	private final static AbstractStackNode NONTERMINAL_A0 = new NonTerminalStackNode(0, "A");
	private final static AbstractStackNode LIST1 = new ListStackNode(1, NONTERMINAL_A0, "A+", true);
	private final static AbstractStackNode LITERAL_a2 = new LiteralStackNode(2, new char[]{'a'});
	
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
		INode result = nrpl.parse("S");
		System.out.println(result);
		
		System.out.println("S(A+(A(a),A+(A(a),A+(A(a))))) <- good");
	}
}
