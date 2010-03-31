package gll.tests;

import gll.SGLL;
import gll.stack.ParseStackNode;
import gll.stack.TerminalListParseStackNode;
import gll.stack.TerminalParseStackNode;

/*
S ::= [a-z]+
*/
public class TerminalList extends SGLL{
	private final static ParseStackNode TERMINAL_LIST0 = new TerminalListParseStackNode(0, new char[][]{{'a', 'z'}}, new char[]{}, true);
	private final static ParseStackNode TERMINAL_a1 = new TerminalParseStackNode("a".toCharArray(), 1);
	
	public TerminalList(char[] input){
		super(input);
	}
	
	public void S(){
		expect(TERMINAL_LIST0);
	}
	
	public void List0(){
		expect(TERMINAL_a1);
	}
	
	public static void main(String[] args){
		TerminalList tl = new TerminalList("abc".toCharArray());
		System.out.println(tl.parse("S"));
		
		System.out.println("S(List0(a),List0(b),List0(c)) <- good");
	}
}
