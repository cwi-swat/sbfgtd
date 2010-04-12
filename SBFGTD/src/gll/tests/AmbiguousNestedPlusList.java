package gll.tests;

import gll.SGLL;
import gll.stack.CharStackNode;
import gll.stack.ListStackNode;
import gll.stack.NonTerminalStackNode;
import gll.stack.StackNode;

/*
S ::= A+
A ::= [a]+
*/
public class AmbiguousNestedPlusList extends SGLL{
	private final static StackNode NONTERMINAL_A0 = new NonTerminalStackNode(0, "A");
	private final static StackNode LIST1 = new ListStackNode(1, NONTERMINAL_A0, "A+", true);
	private final static StackNode CHAR2 = new CharStackNode(2, new char[][]{}, new char[]{'a'}, "[a]");
	private final static StackNode CHAR_LIST3 = new ListStackNode(3, CHAR2, "[a]+", true);
	
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
		System.out.println(anpl.parse("S"));
		
		System.out.println("S([A+(A+(A([a]+([a](a)))),A([a]+([a](a)))),A+(A([a]+([a]+([a](a)),[a](a))))]) <- good");
	}
}
