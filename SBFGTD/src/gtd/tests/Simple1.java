package gtd.tests;

import gtd.SGTDBF;
import gtd.preprocessing.ExpectBuilder;
import gtd.result.AbstractNode;
import gtd.stack.AbstractStackNode;
import gtd.stack.LiteralStackNode;
import gtd.stack.NonTerminalStackNode;

/*
S ::= Ab
A ::= aa
*/
public class Simple1 extends SGTDBF{
	private final static AbstractStackNode NONTERMINAL_A0 = new NonTerminalStackNode(0, 0, "A");
	private final static AbstractStackNode LITERAL_aa1 = new LiteralStackNode(1, 0, new char[]{'a','a'});
	private final static AbstractStackNode LITERAL_b2 = new LiteralStackNode(2, 1, new char[]{'b'});
	
	public Simple1(char[] input){
		super(input);
	}
	
	public AbstractStackNode[] S(){
		ExpectBuilder eb = new ExpectBuilder();
		
		eb.addAlternative(NONTERMINAL_A0, LITERAL_b2);
		
		return eb.buildExpectMatrix();
	}
	
	public AbstractStackNode[] A(){
		ExpectBuilder eb = new ExpectBuilder();
		
		eb.addAlternative(LITERAL_aa1);
		
		return eb.buildExpectMatrix();
	}
	
	public static void main(String[] args){
		Simple1 s1 = new Simple1("aab".toCharArray());
		AbstractNode result = s1.parse("S");
		System.out.println(result);
		
		System.out.println("S(A(aa),b) <- good");
	}
}
