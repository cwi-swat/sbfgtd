package gll.tests;

import gll.SGLL;
import gll.stack.LiteralStackNode;
import gll.stack.NonTerminalStackNode;
import gll.stack.SeparatedListStackNode;
import gll.stack.AbstractStackNode;

/*
S ::= sep(A, b)*
A ::= a

sep(X, Y) means, a list of X separated by Y's.
*/
public class SeparatedStarList extends SGLL{
	private final static AbstractStackNode NONTERMINAL_A0 = new NonTerminalStackNode(0, "A");
	private final static AbstractStackNode LITERAL_b1 = new LiteralStackNode(1, new char[]{'b'});
	private final static AbstractStackNode LIST2 = new SeparatedListStackNode(2, NONTERMINAL_A0, new AbstractStackNode[]{LITERAL_b1}, "(Ab)*", false);
	private final static AbstractStackNode LITERAL_a3 = new LiteralStackNode(3, new char[]{'a'});
	
	public SeparatedStarList(char[] input){
		super(input);
	}
	
	public void S(){
		expect(LIST2);
	}
	
	public void A(){
		expect(LITERAL_a3);
	}
	
	public static void main(String[] args){
		SeparatedStarList nrsl = new SeparatedStarList("ababa".toCharArray());
		nrsl.parse("S");
		System.out.println(nrsl.getStringResult());
		
		System.out.println("S((Ab)*((Ab)*((Ab)*(A(a)),b,A(a)),b,A(a))) <- good");
	}
}
