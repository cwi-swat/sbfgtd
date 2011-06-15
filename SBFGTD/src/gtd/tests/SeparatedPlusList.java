package gtd.tests;

import gtd.SGTDBF;
import gtd.preprocessing.ExpectBuilder;
import gtd.result.AbstractNode;
import gtd.stack.AbstractStackNode;
import gtd.stack.LiteralStackNode;
import gtd.stack.NonTerminalStackNode;
import gtd.stack.SeparatedListStackNode;

/*
S ::= sep(A, b)+
A ::= a

sep(X, Y) means, a list of X separated by Y's.
*/
public class SeparatedPlusList extends SGTDBF{
	private final static AbstractStackNode NONTERMINAL_A0 = new NonTerminalStackNode(0, 0, "A");
	private final static AbstractStackNode LITERAL_b1 = new LiteralStackNode(1, 1, new char[]{'b'});
	private final static AbstractStackNode LIST2 = new SeparatedListStackNode(2, 0, NONTERMINAL_A0, new AbstractStackNode[]{LITERAL_b1}, "(Ab)+", true);
	private final static AbstractStackNode LITERAL_a3 = new LiteralStackNode(3, 0, new char[]{'a'});
	
	public SeparatedPlusList(char[] input){
		super(input);
	}
	
	public void S(){
		ExpectBuilder eb = new ExpectBuilder();
		
		eb.addAlternative(LIST2);
		
		expect(eb.buildExpectMatrix());
	}
	
	public void A(){
		ExpectBuilder eb = new ExpectBuilder();
		
		eb.addAlternative(LITERAL_a3);
		
		expect(eb.buildExpectMatrix());
	}
	
	public static void main(String[] args){
		SeparatedPlusList nrpl = new SeparatedPlusList("ababa".toCharArray());
		AbstractNode result = nrpl.parse("S");
		System.out.println(result);
		
		System.out.println("S((Ab)+(A(a),b,A(a),b,A(a))) <- good");
	}
}
