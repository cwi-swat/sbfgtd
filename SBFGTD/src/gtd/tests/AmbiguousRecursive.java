package gtd.tests;

import gtd.SGTDBF;
import gtd.preprocessing.ExpectBuilder;
import gtd.result.AbstractNode;
import gtd.stack.AbstractStackNode;
import gtd.stack.LiteralStackNode;
import gtd.stack.NonTerminalStackNode;

/*
S ::= SSS | SS | a
*/
public class AmbiguousRecursive extends SGTDBF{
	private final static AbstractStackNode NONTERMINAL_S0 = new NonTerminalStackNode(0, 0, "S");
	private final static AbstractStackNode NONTERMINAL_S1 = new NonTerminalStackNode(1, 1, "S");
	private final static AbstractStackNode NONTERMINAL_S2 = new NonTerminalStackNode(2, 2, "S");
	private final static AbstractStackNode NONTERMINAL_S3 = new NonTerminalStackNode(3, 0, "S");
	private final static AbstractStackNode NONTERMINAL_S4 = new NonTerminalStackNode(4, 1, "S");
	private final static AbstractStackNode LITERAL_a5 = new LiteralStackNode(5, 0, new char[]{'a'});
	
	public AmbiguousRecursive(char[] input){
		super(input);
	}
	
	private final static AbstractStackNode[] S_EXPECT;
	static{
		ExpectBuilder eb = new ExpectBuilder();
		eb.addAlternative(NONTERMINAL_S0, NONTERMINAL_S1, NONTERMINAL_S2);
		eb.addAlternative(NONTERMINAL_S3, NONTERMINAL_S4);
		eb.addAlternative(LITERAL_a5);
		S_EXPECT = eb.buildExpectMatrix();
	}
	
	public AbstractStackNode[] S(){
		return S_EXPECT;
	}
	
	public static void main(String[] args){
		AmbiguousRecursive ar = new AmbiguousRecursive("aaa".toCharArray());
		AbstractNode result = ar.parse("S");
		System.out.println(result);
		
		System.out.println("[S(S(a),S(S(a),S(a))),S(S(a),S(a),S(a)),S(S(S(a),S(a)),S(a))] <- good"); // Temp
	}
}
