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
A ::= a | aa
*/
public class AmbiguousNonTerminalPlusList2 extends SGTDBF{
	private final static AbstractStackNode NONTERMINAL_A0 = new NonTerminalStackNode(0, 0, "A");
	private final static AbstractStackNode LIST1 = new ListStackNode(1, 0, NONTERMINAL_A0, "A+", true);
	private final static AbstractStackNode LITERAL_a2 = new LiteralStackNode(2, 0, new char[]{'a'});
	private final static AbstractStackNode LITERAL_aa3 = new LiteralStackNode(3, 0, new char[]{'a', 'a'});
	
	public AmbiguousNonTerminalPlusList2(char[] input){
		super(input);
	}
	
	private final static AbstractStackNode[] S_EXPECT;
	static{
		ExpectBuilder eb = new ExpectBuilder();
		eb.addAlternative(LIST1);
		S_EXPECT = eb.buildExpectMatrix();
	}
	
	public AbstractStackNode[] S(){
		return S_EXPECT;
	}
	
	private final static AbstractStackNode[] A_EXPECT;
	static{
		ExpectBuilder eb = new ExpectBuilder();
		eb.addAlternative(LITERAL_a2);
		eb.addAlternative(LITERAL_aa3);
		A_EXPECT = eb.buildExpectMatrix();
	}
	
	public AbstractStackNode[] A(){
		return A_EXPECT;
	}
	
	public static void main(String[] args){
		AmbiguousNonTerminalPlusList2 nrpl2 = new AmbiguousNonTerminalPlusList2("aaa".toCharArray());
		AbstractNode result = nrpl2.parse("S");
		System.out.println(result);
		
		System.out.println("S([A+([A+(A(a),A(a)),A+(A(aa))],A(a)),A+(A(a),A(aa))]) <- good");
	}
}
