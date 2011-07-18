package gtd.tests;

import gtd.SGTDBF;
import gtd.preprocessing.ExpectBuilder;
import gtd.result.AbstractNode;
import gtd.stack.AbstractStackNode;
import gtd.stack.EpsilonStackNode;
import gtd.stack.LiteralStackNode;
import gtd.stack.NonTerminalStackNode;
import gtd.stack.SeparatedListStackNode;

/*
* S ::= sep(A, SEP)+
* A ::= a | epsilon
* SEP ::= epsilon
*/
public class AmbiguousSeparatedEpsilonList extends SGTDBF{
	private final static AbstractStackNode NONTERMINAL_A0 = new NonTerminalStackNode(0, 0, "A");
	private final static AbstractStackNode NONTERMINAL_SEP1 = new NonTerminalStackNode(1, 1, "SEP");
	private final static AbstractStackNode LIST2 = new SeparatedListStackNode(2, 0, NONTERMINAL_A0, new AbstractStackNode[]{NONTERMINAL_SEP1}, "(ASEP)+", true);
	private final static AbstractStackNode LITERAL_a3 = new LiteralStackNode(3, 0, new char[]{'a'});
	private final static AbstractStackNode EPSILON4 = new EpsilonStackNode(4, 0);
	private final static AbstractStackNode EPSILON5 = new EpsilonStackNode(5, 0);
	
	
	public AmbiguousSeparatedEpsilonList(char[] input){
		super(input);
	}
	
	private final static AbstractStackNode[] S_EXPECT;
	static{
		ExpectBuilder eb = new ExpectBuilder();
		eb.addAlternative(LIST2);
		S_EXPECT = eb.buildExpectMatrix();
	}
	
	public AbstractStackNode[] S(){
		return S_EXPECT;
	}
	
	private final static AbstractStackNode[] A_EXPECT;
	static{
		ExpectBuilder eb = new ExpectBuilder();
		eb.addAlternative(LITERAL_a3);
		eb.addAlternative(EPSILON4);
		A_EXPECT = eb.buildExpectMatrix();
	}
	
	public AbstractStackNode[] A(){
		return A_EXPECT;
	}
	
	private final static AbstractStackNode[] SEP_EXPECT;
	static{
		ExpectBuilder eb = new ExpectBuilder();
		eb.addAlternative(EPSILON5);
		SEP_EXPECT = eb.buildExpectMatrix();
	}
	
	public AbstractStackNode[] SEP(){
		return SEP_EXPECT;
	}
	
	public static void main(String[] args){
		AmbiguousSeparatedEpsilonList asel = new AmbiguousSeparatedEpsilonList("a".toCharArray());
		AbstractNode result = asel.parse("S");
		System.out.println(result);
		
		System.out.println("S([(ASEP)+([(ASEP)+(A(a)),(ASEP)+(A(),repeat(SEP(),A()),SEP(),A(a))],SEP(),A(),repeat(SEP(),A())),(ASEP)+(A(),repeat(SEP(),A()),SEP(),A(a)),(ASEP)+(A(a))]) <- good, but not minimal");
		//System.out.println("S([(ASEP)+([(ASEP)+(A(a)),(ASEP)+(A(),repeat(SEP(),A()),SEP(),A(a))],repeat(SEP(),A())),(ASEP)+(A(),repeat(SEP(),A()),SEP(),A(a)),(ASEP)+(A(a))]) <- good");
	}
}
