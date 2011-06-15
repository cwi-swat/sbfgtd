package gtd.tests;

import gtd.SGTDBF;
import gtd.preprocessing.ExpectBuilder;
import gtd.result.AbstractNode;
import gtd.stack.AbstractStackNode;
import gtd.stack.LiteralStackNode;
import gtd.stack.NonTerminalStackNode;

/*
S ::= A
A ::= aA | a
*/
public class RightRecursion extends SGTDBF{
	private final static AbstractStackNode NONTERMINAL_A0 = new NonTerminalStackNode(0, 0, "A");
	private final static AbstractStackNode NONTERMINAL_A1 = new NonTerminalStackNode(1, 1, "A");
	private final static AbstractStackNode LITERAL_a2 = new LiteralStackNode(2, 0, new char[]{'a'});
	private final static AbstractStackNode LITERAL_a3 = new LiteralStackNode(3, 0, new char[]{'a'});
	
	public RightRecursion(char[] input){
		super(input);
	}
	
	public void S(){
		ExpectBuilder eb = new ExpectBuilder();
		
		eb.addAlternative(NONTERMINAL_A0);
		
		expect(eb.buildExpectMatrix());
	}
	
	public void A(){
		ExpectBuilder eb = new ExpectBuilder();
		
		eb.addAlternative(LITERAL_a2, NONTERMINAL_A1);
		eb.addAlternative(LITERAL_a3);
		
		expect(eb.buildExpectMatrix());
	}
	
	public static void main(String[] args){
		RightRecursion rr = new RightRecursion("aaa".toCharArray());
		AbstractNode result = rr.parse("S");
		System.out.println(result);
		
		System.out.println("S(A(a,A(a,A(a)))) <- good");
	}
}
