package gll.tests;

import gll.SGLL;
import gll.stack.CharStackNode;
import gll.stack.ListStackNode;
import gll.stack.NonTerminalStackNode;
import gll.stack.AbstractStackNode;

/*
S ::= A+
A ::= [a]+
*/
public class AmbiguousNestedPlusList extends SGLL{
	private final static AbstractStackNode NONTERMINAL_A0 = new NonTerminalStackNode(0, "A");
	private final static AbstractStackNode LIST1 = new ListStackNode(1, NONTERMINAL_A0, "A+", true);
	private final static AbstractStackNode CHAR2 = new CharStackNode(2, "[a]", new char[][]{}, new char[]{'a'});
	private final static AbstractStackNode CHAR_LIST3 = new ListStackNode(3, CHAR2, "[a]+", true);
	
	public AmbiguousNestedPlusList(char[] input){
		super(input);
	}
	
	public void S(){
		expect(LIST1);
	}
	
	public void A(){
		expect(CHAR_LIST3);
	}
	
	public static void main(String[] args){
		AmbiguousNestedPlusList anpl = new AmbiguousNestedPlusList("aa".toCharArray());
		anpl.parse("S");
		System.out.println(anpl.getStringResult());
		
		System.out.println("S([A+(A([a]+([a](a))),A+(A([a]+([a](a))))),A+(A([a]+([a](a),[a]+([a](a)))))]) <- good");
	}
}
