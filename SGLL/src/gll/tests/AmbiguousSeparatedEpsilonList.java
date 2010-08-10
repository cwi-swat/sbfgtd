package gll.tests;

import gll.SGLL;
import gll.result.AbstractNode;
import gll.stack.AbstractStackNode;
import gll.stack.EpsilonStackNode;
import gll.stack.LiteralStackNode;
import gll.stack.NonTerminalStackNode;
import gll.stack.SeparatedListStackNode;

/*
* S ::= sep(A, SEP)+
* A ::= a | epsilon
* SEP ::= epsilon
*/
public class AmbiguousSeparatedEpsilonList extends SGLL{
	private final static AbstractStackNode NONTERMINAL_A0 = new NonTerminalStackNode(0, "A");
	private final static AbstractStackNode NONTERMINAL_SEP1 = new NonTerminalStackNode(1, "SEP");
	private final static AbstractStackNode LIST2 = new SeparatedListStackNode(2, NONTERMINAL_A0, new AbstractStackNode[]{NONTERMINAL_SEP1}, "(ASEP)+", true);
	private final static AbstractStackNode LITERAL_a3 = new LiteralStackNode(3, new char[]{'a'});
	private final static AbstractStackNode EPSILON4 = new EpsilonStackNode(4);
	private final static AbstractStackNode EPSILON5 = new EpsilonStackNode(5);
	
	
	public AmbiguousSeparatedEpsilonList(char[] input){
		super(input);
	}
	
	public void S(){
		expect(LIST2);
	}
	
	public void A(){
		expect(LITERAL_a3);
		
		expect(EPSILON4);
	}
	
	public void SEP(){
		expect(EPSILON5);
	}
	
	public static void main(String[] args){
		AmbiguousSeparatedEpsilonList asel = new AmbiguousSeparatedEpsilonList("aa".toCharArray());
		AbstractNode result = asel.parse("S");
		System.out.println(result);
		
		System.out.println("S([(ASEP)+(A(a),SEP(),A(a)),(ASEP)+(A(a),SEP(),repeat(A(),SEP()),A(a)),(ASEP)+(A(a),SEP(),A(a),repeat(SEP(),A())),(ASEP)+(repeat(A(),SEP()),A(a),SEP(),A(a)),(ASEP)+(A(a),SEP(),repeat(A(),SEP()),A(a),repeat(SEP(),A())),(ASEP)+(repeat(A(),SEP()),A(a),SEP(),repeat(A(),SEP()),A(a))]) <- good");
	}
}
