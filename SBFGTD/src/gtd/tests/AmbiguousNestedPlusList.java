package gtd.tests;

import gtd.SGTDBF;
import gtd.preprocessing.ExpectBuilder;
import gtd.result.AbstractNode;
import gtd.stack.AbstractStackNode;
import gtd.stack.CharStackNode;
import gtd.stack.ListStackNode;
import gtd.stack.NonTerminalStackNode;

/*
S ::= A+
A ::= [a]+
*/
public class AmbiguousNestedPlusList extends SGTDBF{
	private final static AbstractStackNode NONTERMINAL_A0 = new NonTerminalStackNode(0, 0, "A");
	private final static AbstractStackNode LIST1 = new ListStackNode(1, 0, NONTERMINAL_A0, "A+", true);
	private final static AbstractStackNode CHAR2 = new CharStackNode(2, 0, 'a');
	private final static AbstractStackNode CHAR_LIST3 = new ListStackNode(3, 0, CHAR2, "[a]+", true);
	
	public AmbiguousNestedPlusList(char[] input){
		super(input);
	}
	
	public void S(){
		ExpectBuilder eb = new ExpectBuilder();
		
		eb.addAlternative(LIST1);
		
		expect(eb.buildExpectMatrix());
	}
	
	public void A(){
		ExpectBuilder eb = new ExpectBuilder();
		
		eb.addAlternative(CHAR_LIST3);
		
		expect(eb.buildExpectMatrix());
	}
	
	public static void main(String[] args){
		AmbiguousNestedPlusList anpl = new AmbiguousNestedPlusList("aa".toCharArray());
		AbstractNode result = anpl.parse("S");
		System.out.println(result);
		
		System.out.println("S([A+(A([a]+([a](a))),A([a]+([a](a)))),A+(A([a]+([a](a),[a](a))))]) <- good");
	}
}
