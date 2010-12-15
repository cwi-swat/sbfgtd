package gtd.tests;

import gtd.SGTDBF;
import gtd.result.AbstractNode;
import gtd.stack.AbstractStackNode;
import gtd.stack.CharRangeStackNode;
import gtd.stack.ListStackNode;

/*
S ::= [a-z]+
*/
public class CharPlusList extends SGTDBF{
	private final static AbstractStackNode CHAR0 = new CharRangeStackNode(0, 0, "[a-z]", new char[][]{{'a', 'z'}});
	private final static AbstractStackNode LIST1 = new ListStackNode(1, 0, CHAR0, "[a-z]+", true);
	
	public CharPlusList(char[] input){
		super(input);
	}
	
	public void S(){
		expect(LIST1);
	}
	
	public static void main(String[] args){
		CharPlusList cpl = new CharPlusList("abc".toCharArray());
		AbstractNode result = cpl.parse("S");
		System.out.println(result);
		
		System.out.println("S([a-z]+([a-z](a),[a-z](b),[a-z](c))) <- good");
	}
}
