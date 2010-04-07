package gll.tests;

import gll.SGLL;
import gll.stack.LiteralParseStackNode;
import gll.stack.OptionalParseStackNode;
import gll.stack.ParseStackNode;

public class Optional2 extends SGLL{
	private final static ParseStackNode LITERAL_a0 = new LiteralParseStackNode(new char[]{'a'}, 0);
	private final static ParseStackNode LITERAL_a1 = new LiteralParseStackNode(new char[]{'a'}, 1);
	private final static ParseStackNode OPTIONAL_2 = new OptionalParseStackNode(1, LITERAL_a1, "a?");
	
	public Optional2(char[] input){
		super(input);
	}
	
	public void S(){
		expect(LITERAL_a0, OPTIONAL_2);
	}
	
	public static void main(String[] args){
		Optional2 o2 = new Optional2("a".toCharArray());
		System.out.println(o2.parse("S"));
		System.out.println("S(a,a?()) <- good");
	}
}
