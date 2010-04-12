package gll.tests;

import gll.SGLL;
import gll.stack.CharStackNode;
import gll.stack.LiteralStackNode;
import gll.stack.NonTerminalStackNode;
import gll.stack.SeparatedListStackNode;
import gll.stack.StackNode;

public class SeparatedPlusList extends SGLL{
	private final static StackNode NONTERMINAL_A0 = new NonTerminalStackNode("A", 0);
	private final static StackNode CHAR_b1 = new CharStackNode(new char[][]{}, new char[]{'b'}, 1, "b");
	private final static StackNode LIST2 = new SeparatedListStackNode(2, NONTERMINAL_A0, new StackNode[]{CHAR_b1}, "(Ab)+", true);
	private final static StackNode LITERAL_a3 = new LiteralStackNode(new char[]{'a'}, 3);
	
	public SeparatedPlusList(char[] input){
		super(input);
	}
	
	public void S(){
		expect(LIST2);
	}
	
	public void A(){
		expect(LITERAL_a3);
	}
	
	public static void main(String[] args){
		SeparatedPlusList nrpl = new SeparatedPlusList("aa".toCharArray());
		System.out.println(nrpl.parse("S"));
		
		System.out.println("S((Ab)+((Ab)+((Ab)+(A(a)),A(ba)),A(ba))) <- good");
	}
}
