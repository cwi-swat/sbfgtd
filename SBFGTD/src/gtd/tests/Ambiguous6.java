package gtd.tests;

import gtd.SGTDBF;
import gtd.preprocessing.ExpectBuilder;
import gtd.result.AbstractNode;
import gtd.stack.AbstractStackNode;
import gtd.stack.LiteralStackNode;
import gtd.stack.NonTerminalStackNode;

/*
S ::= A | E
A ::= B
B ::= C
C ::= D
D ::= E | a
E ::= F
F ::= G
G ::= a
*/
public class Ambiguous6 extends SGTDBF{
	private final static AbstractStackNode NONTERMINAL_A0 = new NonTerminalStackNode(0, 0, "A");
	private final static AbstractStackNode NONTERMINAL_B1 = new NonTerminalStackNode(1, 0, "B");
	private final static AbstractStackNode NONTERMINAL_C2 = new NonTerminalStackNode(2, 0, "C");
	private final static AbstractStackNode NONTERMINAL_D3 = new NonTerminalStackNode(3, 0, "D");
	private final static AbstractStackNode NONTERMINAL_E4 = new NonTerminalStackNode(4, 0, "E");
	private final static AbstractStackNode NONTERMINAL_E5 = new NonTerminalStackNode(5, 0, "E");
	private final static AbstractStackNode NONTERMINAL_F6 = new NonTerminalStackNode(6, 0, "F");
	private final static AbstractStackNode NONTERMINAL_G7 = new NonTerminalStackNode(7, 0, "G");
	private final static AbstractStackNode LITERAL_a8 = new LiteralStackNode(8, 0, new char[]{'a'});
	private final static AbstractStackNode LITERAL_a9 = new LiteralStackNode(9, 0, new char[]{'a'});
	
	public Ambiguous6(char[] input){
		super(input);
	}
	
	private final static AbstractStackNode[] S_EXPECT;
	static{
		ExpectBuilder eb = new ExpectBuilder();
		eb.addAlternative(NONTERMINAL_A0);
		eb.addAlternative(NONTERMINAL_E4);
		S_EXPECT = eb.buildExpectMatrix();
	}
	
	public AbstractStackNode[] S(){
		return S_EXPECT;
	}
	
	private final static AbstractStackNode[] A_EXPECT;
	static{
		ExpectBuilder eb = new ExpectBuilder();
		eb.addAlternative(NONTERMINAL_B1);
		A_EXPECT = eb.buildExpectMatrix();
	}
	
	public AbstractStackNode[] A(){
		return A_EXPECT;
	}
	
	private final static AbstractStackNode[] B_EXPECT;
	static{
		ExpectBuilder eb = new ExpectBuilder();
		eb.addAlternative(NONTERMINAL_C2);
		B_EXPECT = eb.buildExpectMatrix();
	}
	
	public AbstractStackNode[] B(){
		return B_EXPECT;
	}
	
	private final static AbstractStackNode[] C_EXPECT;
	static{
		ExpectBuilder eb = new ExpectBuilder();
		eb.addAlternative(NONTERMINAL_D3);
		C_EXPECT = eb.buildExpectMatrix();
	}
	
	public AbstractStackNode[] C(){
		return C_EXPECT;
	}
	
	private final static AbstractStackNode[] D_EXPECT;
	static{
		ExpectBuilder eb = new ExpectBuilder();
		eb.addAlternative(NONTERMINAL_E5);
		eb.addAlternative(LITERAL_a8);
		D_EXPECT = eb.buildExpectMatrix();
	}
	
	public AbstractStackNode[] D(){
		return D_EXPECT;
	}
	
	private final static AbstractStackNode[] E_EXPECT;
	static{
		ExpectBuilder eb = new ExpectBuilder();
		eb.addAlternative(NONTERMINAL_F6);
		E_EXPECT = eb.buildExpectMatrix();
	}
	
	public AbstractStackNode[] E(){
		return E_EXPECT;
	}
	
	private final static AbstractStackNode[] F_EXPECT;
	static{
		ExpectBuilder eb = new ExpectBuilder();
		eb.addAlternative(NONTERMINAL_G7);
		F_EXPECT = eb.buildExpectMatrix();
	}
	
	public AbstractStackNode[] F(){
		return F_EXPECT;
	}
	
	private final static AbstractStackNode[] G_EXPECT;
	static{
		ExpectBuilder eb = new ExpectBuilder();
		eb.addAlternative(LITERAL_a9);
		G_EXPECT = eb.buildExpectMatrix();
	}
	
	public AbstractStackNode[] G(){
		return G_EXPECT;
	}
	
	public static void main(String[] args){
		Ambiguous6 a6 = new Ambiguous6("a".toCharArray());
		AbstractNode result = a6.parse("S");
		System.out.println(result);
		
		System.out.println("[S(A(B(C([D(E(F(G(a)))),D(a)])))),S(E(F(G(a))))] <- good");
	}
}
