package gll.tests;

import gll.SGLL;
import gll.stack.ListStackNode;
import gll.stack.NonTerminalStackNode;
import gll.stack.AbstractStackNode;
import gll.stack.LiteralStackNode;
/*
S ::= A+
A ::= a | aa
*/
public class AmbiguousNonTerminalPlusList2 extends SGLL{
	private final static AbstractStackNode NONTERMINAL_A0 = new NonTerminalStackNode(0, "A");
	private final static AbstractStackNode LIST1 = new ListStackNode(1, NONTERMINAL_A0, "A+", true);
	private final static AbstractStackNode LITERAL_a2 = new LiteralStackNode(2, new char[]{'a'});
	private final static AbstractStackNode LITERAL_aa3 = new LiteralStackNode(3, new char[]{'a', 'a'});
	
	public AmbiguousNonTerminalPlusList2(char[] input){
		super(input);
	}
	
	public void S(){
		expect(LIST1);
	}
	
	public void A(){
		expect(LITERAL_a2);
		
		expect(LITERAL_aa3);
	}
	
	public static void main(String[] args){
		AmbiguousNonTerminalPlusList2 nrpl2 = new AmbiguousNonTerminalPlusList2("aaa".toCharArray());
		nrpl2.parse("S");
		System.out.println(nrpl2.getStringResult());
		
		System.out.println("S([A+(A(a),[A+(A(aa)),A+(A(a),A+(A(a)))]),A+(A(aa),A+(A(a)))]) <- good");
	}
}
