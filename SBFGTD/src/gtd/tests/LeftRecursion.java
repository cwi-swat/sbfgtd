package gtd.tests;

import gtd.SGTDBF;
import gtd.preprocessing.ExpectBuilder;
import gtd.result.AbstractNode;
import gtd.stack.AbstractStackNode;
import gtd.stack.LiteralStackNode;
import gtd.stack.NonTerminalStackNode;

/*
S ::= A
A ::= Aa | a
*/
public class LeftRecursion extends SGTDBF{
	private final static AbstractStackNode NONTERMINAL_A0 = new NonTerminalStackNode(0, 0, "A");
	private final static AbstractStackNode NONTERMINAL_A1 = new NonTerminalStackNode(1, 0, "A");
	private final static AbstractStackNode LITERAL_a2 = new LiteralStackNode(2, 1, new char[]{'a'});
	private final static AbstractStackNode LITERAL_a3 = new LiteralStackNode(3, 0, new char[]{'a'});
	
	public LeftRecursion(char[] input){
		super(input);
	}
	
	public void S(){
		ExpectBuilder eb = new ExpectBuilder();
		
		eb.addAlternative(NONTERMINAL_A0);
		
		expect(eb.buildExpectMatrix());
	}
	
	public void A(){
		ExpectBuilder eb = new ExpectBuilder();
		
		eb.addAlternative(NONTERMINAL_A1, LITERAL_a2);
		eb.addAlternative(LITERAL_a3);
		
		expect(eb.buildExpectMatrix());
	}
	
	public static void main(String[] args){
		LeftRecursion lr = new LeftRecursion("aaa".toCharArray());
		AbstractNode result = lr.parse("S");
		System.out.println(result);
		
		System.out.println("S(A(A(A(a),a),a)) <- good");
	}
}
