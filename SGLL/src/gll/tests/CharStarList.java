package gll.tests;

import gll.SGLL;
import gll.result.INode;
import gll.stack.CharStackNode;
import gll.stack.ListStackNode;
import gll.stack.AbstractStackNode;

/*
S ::= [a-z]*
*/
public class CharStarList extends SGLL{
	private final static AbstractStackNode CHAR0 = new CharStackNode(0, "[a-z]", new char[][]{{'a', 'z'}}, new char[]{});
	private final static AbstractStackNode LIST1 = new ListStackNode(1, CHAR0, "[a-z]*", false);
	
	public CharStarList(char[] input){
		super(input);
	}
	
	public void S(){
		expect(LIST1);
	}
	
	public static void main(String[] args){
		CharStarList csl = new CharStarList("abc".toCharArray());
		INode result = csl.parse("S");
		System.out.println(result);
		
		System.out.println("S([a-z]*([a-z](a),[a-z]*([a-z](b),[a-z]*([a-z](c))))) <- good");
	}
}
