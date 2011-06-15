package gtd.tests;

import gtd.SGTDBF;
import gtd.preprocessing.ExpectBuilder;
import gtd.result.AbstractNode;
import gtd.stack.AbstractStackNode;
import gtd.stack.LiteralStackNode;
import gtd.stack.NonTerminalStackNode;
import gtd.stack.OptionalStackNode;

/*
S ::= aO?
O ::= a
*/
public class Optional1 extends SGTDBF{
	private final static AbstractStackNode LITERAL_a0 = new LiteralStackNode(0, 0, new char[]{'a'});
	private final static AbstractStackNode LITERAL_a1 = new LiteralStackNode(1, 0, new char[]{'a'});
	private final static AbstractStackNode NON_TERMINAL_O2 = new NonTerminalStackNode(2, 0, "O");
	private final static AbstractStackNode OPTIONAL_3 = new OptionalStackNode(3, 1, NON_TERMINAL_O2, "O?");
	
	public Optional1(char[] input){
		super(input);
	}
	
	public void S(){
		ExpectBuilder eb = new ExpectBuilder();
		
		eb.addAlternative(LITERAL_a0, OPTIONAL_3);
		
		expect(eb.buildExpectMatrix());
	}
	
	public void O(){
		ExpectBuilder eb = new ExpectBuilder();
		
		eb.addAlternative(LITERAL_a1);
		
		expect(eb.buildExpectMatrix());
	}
	
	public static void main(String[] args){
		Optional1 o1 = new Optional1("aa".toCharArray());
		AbstractNode result = o1.parse("S");
		System.out.println(result);
		
		System.out.println("S(a,O?(O(a))) <- good");
	}
}
