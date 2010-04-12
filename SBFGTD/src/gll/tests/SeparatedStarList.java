package gll.tests;

import gll.SGLL;
import gll.stack.LiteralStackNode;
import gll.stack.NonTerminalStackNode;
import gll.stack.SeparatedListStackNode;
import gll.stack.StackNode;

public class SeparatedStarList extends SGLL{
	private final static StackNode NONTERMINAL_A0 = new NonTerminalStackNode("A", 0);
	private final static StackNode LITERAL_b1 = new LiteralStackNode(new char[]{'b'}, 1);
	private final static StackNode LIST2 = new SeparatedListStackNode(2, NONTERMINAL_A0, new StackNode[]{LITERAL_b1}, "(Ab)*", false);
	private final static StackNode LITERAL_a3 = new LiteralStackNode(new char[]{'a'}, 3);
	
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
		SeparatedStarList nrpl = new SeparatedStarList("ababa".toCharArray());
		System.out.println(nrpl.parse("S"));
		
		System.out.println("S((Ab)*((Ab)*((Ab)*(A(a)),b,A(a)),b,A(a))) <- good");
	}
}
