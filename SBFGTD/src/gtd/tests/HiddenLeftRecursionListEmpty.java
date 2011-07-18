package gtd.tests;

import gtd.SGTDBF;
import gtd.preprocessing.ExpectBuilder;
import gtd.result.AbstractNode;
import gtd.stack.AbstractStackNode;
import gtd.stack.ListStackNode;
import gtd.stack.NonTerminalStackNode;

/*
S ::= S* T* U*
T ::= T* U* S*
U ::= U* S* T*
*/
public class HiddenLeftRecursionListEmpty extends SGTDBF{
	private final static AbstractStackNode NONTERMINAL_S0 = new NonTerminalStackNode(0, 0, "S");
	private final static AbstractStackNode LISTS0 = new ListStackNode(100, 0, NONTERMINAL_S0, "S*", false);
	private final static AbstractStackNode NONTERMINAL_T1 = new NonTerminalStackNode(1, 0, "T");
	private final static AbstractStackNode LISTT1 = new ListStackNode(101, 1, NONTERMINAL_T1, "T*", false);
	private final static AbstractStackNode NONTERMINAL_U2 = new NonTerminalStackNode(2, 0, "U");
	private final static AbstractStackNode LISTU2 = new ListStackNode(102, 2, NONTERMINAL_U2, "U*", false);
	
	private final static AbstractStackNode NONTERMINAL_T3 = new NonTerminalStackNode(3, 0, "T");
	private final static AbstractStackNode LISTT3 = new ListStackNode(103, 0, NONTERMINAL_T3, "T*", false);
	private final static AbstractStackNode NONTERMINAL_U4 = new NonTerminalStackNode(4, 0, "U");
	private final static AbstractStackNode LISTU4 = new ListStackNode(104, 1, NONTERMINAL_U4, "U*", false);
	private final static AbstractStackNode NONTERMINAL_S5 = new NonTerminalStackNode(5, 0, "S");
	private final static AbstractStackNode LISTS5 = new ListStackNode(105, 2, NONTERMINAL_S5, "S*", false);

	private final static AbstractStackNode NONTERMINAL_U6 = new NonTerminalStackNode(6, 0, "U");
	private final static AbstractStackNode LISTU6 = new ListStackNode(106, 0, NONTERMINAL_U6, "U*", false);
	private final static AbstractStackNode NONTERMINAL_S7 = new NonTerminalStackNode(7, 0, "S");
	private final static AbstractStackNode LISTS7 = new ListStackNode(107, 1, NONTERMINAL_S7, "S*", false);
	private final static AbstractStackNode NONTERMINAL_T8 = new NonTerminalStackNode(8, 0, "T");
	private final static AbstractStackNode LISTT8 = new ListStackNode(108, 2, NONTERMINAL_T8, "T*", false);
	
	public HiddenLeftRecursionListEmpty(char[] input){
		super(input);
	}
	
	public AbstractStackNode[] S(){
		ExpectBuilder eb = new ExpectBuilder();
		
		eb.addAlternative(LISTS0, LISTT1, LISTU2);
		
		return eb.buildExpectMatrix();
	}
	
	public AbstractStackNode[] T(){
		ExpectBuilder eb = new ExpectBuilder();
		
		eb.addAlternative(LISTT3, LISTU4, LISTS5);
		
		return eb.buildExpectMatrix();
	}
	
	public AbstractStackNode[] U(){
		ExpectBuilder eb = new ExpectBuilder();
		
		eb.addAlternative(LISTU6, LISTS7, LISTT8);
		
		return eb.buildExpectMatrix();
	}
	
	public static void main(String[] args){
		HiddenLeftRecursionListEmpty hlrle = new HiddenLeftRecursionListEmpty("".toCharArray());
		AbstractNode result = hlrle.parse("S");
		System.out.println(result);
		
		System.out.println("S([S*(repeat(cycle(S,2))),S*()],[T*(repeat(T([T*(repeat(cycle(T,2))),T*()],[U*(repeat(U([U*(repeat(cycle(U,2))),U*()],[S*(repeat(cycle(S,6))),S*()],[T*(repeat(cycle(T,4))),T*()]))),U*()],[S*(repeat(cycle(S,4))),S*()]))),T*()],[U*(repeat(U([U*(repeat(cycle(U,2))),U*()],[S*(repeat(cycle(S,4))),S*()],[T*(repeat(T([T*(repeat(cycle(T,2))),T*()],[U*(repeat(cycle(U,4))),U*()],[S*(repeat(cycle(S,6))),S*()]))),T*()]))),U*()]) <- good, but not minimal");
		//System.out.println("? <- good");
	}
}
