package gtd.tests;

import gtd.SGTDBF;
import gtd.preprocessing.ExpectBuilder;
import gtd.result.AbstractNode;
import gtd.stack.AbstractStackNode;
import gtd.stack.ListStackNode;
import gtd.stack.LiteralStackNode;
import gtd.stack.NonTerminalStackNode;

/*
* S ::= A*B*A*
* A ::= a
* B ::= b
*/
public class ListOverlap extends SGTDBF{
	private final static AbstractStackNode NONTERMINAL_A0 = new NonTerminalStackNode(0, 0, "A");
	private final static AbstractStackNode NONTERMINAL_B1 = new NonTerminalStackNode(1, 0, "B");
	private final static AbstractStackNode NONTERMINAL_A2 = new NonTerminalStackNode(2, 0, "A");
	private final static AbstractStackNode LIST3 = new ListStackNode(3, 0, NONTERMINAL_A0, "A*", false);
	private final static AbstractStackNode LIST4 = new ListStackNode(4, 1, NONTERMINAL_B1, "B*", false);
	private final static AbstractStackNode LIST5 = new ListStackNode(5, 2, NONTERMINAL_A2, "A*", false);
	private final static AbstractStackNode LITERAL_a6 = new LiteralStackNode(6, 0, new char[]{'a'});
	private final static AbstractStackNode LITERAL_b7 = new LiteralStackNode(7, 0, new char[]{'b'});
	
	public ListOverlap(char[] input){
		super(input);
	}
	
	public AbstractStackNode[] S(){
		ExpectBuilder eb = new ExpectBuilder();
		
		eb.addAlternative(LIST3, LIST4, LIST5);
		
		return eb.buildExpectMatrix();
	}
	
	public AbstractStackNode[] A(){
		ExpectBuilder eb = new ExpectBuilder();
		
		eb.addAlternative(LITERAL_a6);
		
		return eb.buildExpectMatrix();
	}
	
	public AbstractStackNode[] B(){
		ExpectBuilder eb = new ExpectBuilder();
		
		eb.addAlternative(LITERAL_b7);
		
		return eb.buildExpectMatrix();
	}
	
	public static void main(String[] args){
		ListOverlap lo = new ListOverlap("aab".toCharArray());
		AbstractNode result = lo.parse("S");
		System.out.println(result);
		
		System.out.println("S(A*(A(a),A(a)),B*(B(b)),A*()) <- good");
	}
}
