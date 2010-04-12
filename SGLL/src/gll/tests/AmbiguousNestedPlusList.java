package gll.tests;

import gll.SGLL;
import gll.stack.CharListStackNode;
import gll.stack.NonTerminalListStackNode;
import gll.stack.StackNode;

/*
S ::= A+
A ::= [a]+
*/
public class AmbiguousNestedPlusList extends SGLL{
	private final static StackNode NONTERMINAL_LIST0 = new NonTerminalListStackNode(0, "A", "A+", true);
	private final static StackNode CHAR_LIST1 = new CharListStackNode(1, new char[][]{}, new char[]{'a'}, "[a]", "[a]+", true);
	
	public AmbiguousNestedPlusList(char[] input){
		super(input);
	}
	
	public void S(){
		expect(NONTERMINAL_LIST0);
	}
	
	public void A(){
		expect(CHAR_LIST1);
	}
	
	public static void main(String[] args){
		AmbiguousNestedPlusList anpl = new AmbiguousNestedPlusList("aa".toCharArray());
		System.out.println(anpl.parse("S"));
		
		System.out.println("S([A+(A+(A([a]+([a](a)))),A([a]+([a](a)))),A+(A([a]+([a]+([a](a)),[a](a))))]) <- good");
	}
}
