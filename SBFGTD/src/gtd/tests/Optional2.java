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
public class Optional2 extends SGTDBF{
	private final static AbstractStackNode LITERAL_a0 = new LiteralStackNode(0, 0, new char[]{'a'});
	private final static AbstractStackNode LITERAL_a1 = new LiteralStackNode(1, 0, new char[]{'a'});
	private final static AbstractStackNode NON_TERMINAL_O2 = new NonTerminalStackNode(2, 0, "O");
	private final static AbstractStackNode OPTIONAL_3 = new OptionalStackNode(3, 1, NON_TERMINAL_O2, "O?");
	
	public Optional2(char[] input){
		super(input);
	}
	
	public AbstractStackNode[] S(){
		ExpectBuilder eb = new ExpectBuilder();
		
		eb.addAlternative(LITERAL_a0, OPTIONAL_3);
		
		return eb.buildExpectMatrix();
	}
	
	public AbstractStackNode[] O(){
		ExpectBuilder eb = new ExpectBuilder();
		
		eb.addAlternative(LITERAL_a1);
		
		return eb.buildExpectMatrix();
	}
	
	public static void main(String[] args){
		Optional2 o2 = new Optional2("a".toCharArray());
		AbstractNode result = o2.parse("S");
		System.out.println(result);
		
		System.out.println("S(a,O?()) <- good");
	}
}
