package gtd.tests;

import gtd.SGTDBF;
import gtd.preprocessing.ExpectBuilder;
import gtd.result.AbstractNode;
import gtd.stack.AbstractStackNode;
import gtd.stack.ListStackNode;
import gtd.stack.LiteralStackNode;
import gtd.stack.NonTerminalStackNode;

/*
S ::= A+
A ::= a
*/
public class NonTerminalStarList extends SGTDBF{
	private final static AbstractStackNode NONTERMINAL_A0 = new NonTerminalStackNode(0, 0, "A");
	private final static AbstractStackNode LIST1 = new ListStackNode(1, 0, NONTERMINAL_A0, "A*", false);
	private final static AbstractStackNode LITERAL_a2 = new LiteralStackNode(2, 0, new char[]{'a'});
	
	public NonTerminalStarList(char[] input){
		super(input);
	}
	
	public void S(){
		ExpectBuilder eb = new ExpectBuilder();
		
		eb.addAlternative(LIST1);
		
		expect(eb.buildExpectMatrix());
	}
	
	public void A(){
		ExpectBuilder eb = new ExpectBuilder();
		
		eb.addAlternative(LITERAL_a2);
		
		expect(eb.buildExpectMatrix());
	}
	
	public static void main(String[] args){
		NonTerminalStarList nrsl = new NonTerminalStarList("aaa".toCharArray());
		AbstractNode result = nrsl.parse("S");
		System.out.println(result);
		
		System.out.println("S(A*(A(a),A(a),A(a))) <- good");
	}
}
