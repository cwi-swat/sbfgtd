package gll.tests;

import gll.SGLL;
import gll.stack.LiteralParseStackNode;
import gll.stack.OptionalParseStackNode;
import gll.stack.ParseStackNode;

public class Optional1 extends SGLL{
	private final static ParseStackNode LITERAL_a0 = new LiteralParseStackNode(new char[]{'a'}, 0);
	private final static ParseStackNode LITERAL_a1 = new LiteralParseStackNode(new char[]{'a'}, 1);
	private final static ParseStackNode OPTIONAL_2 = new OptionalParseStackNode(1, LITERAL_a1, "a?");
	
	public Optional1(char[] input){
		super(input);
	}
	
	public void S(){
		expect(LITERAL_a0, OPTIONAL_2);
	}
	
	public static void main(String[] args){
		Optional1 o1 = new Optional1("aa".toCharArray());
		System.out.println(o1.parse("S"));
		System.out.println("S(a,a?(a)) <- good");
	}
}
