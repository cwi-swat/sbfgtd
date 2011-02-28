package gtd.tests;

import gtd.SGTDBF;
import gtd.result.AbstractNode;
import gtd.stack.AbstractStackNode;
import gtd.stack.EpsilonStackNode;
import gtd.stack.LiteralStackNode;
import gtd.stack.NonTerminalStackNode;

/*
S ::= S T U | epsilon | a
T ::= T U S | epsilon
U ::= U S T | epsilon
*/
public class HiddenLeftRecursionNonEmpty extends SGTDBF{
	private final static AbstractStackNode NONTERMINAL_S0 = new NonTerminalStackNode(0, 0, "S");
	private final static AbstractStackNode NONTERMINAL_T1 = new NonTerminalStackNode(1, 1, "T");
	private final static AbstractStackNode NONTERMINAL_U2 = new NonTerminalStackNode(2, 2, "U");
	
	private final static AbstractStackNode NONTERMINAL_T3 = new NonTerminalStackNode(3, 0, "T");
	private final static AbstractStackNode NONTERMINAL_U4 = new NonTerminalStackNode(4, 1, "U");
	private final static AbstractStackNode NONTERMINAL_S5 = new NonTerminalStackNode(5, 2, "S");

	private final static AbstractStackNode NONTERMINAL_U6 = new NonTerminalStackNode(6, 0, "U");
	private final static AbstractStackNode NONTERMINAL_S7 = new NonTerminalStackNode(7, 1, "S");
	private final static AbstractStackNode NONTERMINAL_T8 = new NonTerminalStackNode(8, 2, "T");
	
	private final static AbstractStackNode LITERAL_a9 = new LiteralStackNode(9, 0, new char[]{'a'});
	private final static AbstractStackNode EPSILON_10 = new EpsilonStackNode(10, 0);
	private final static AbstractStackNode EPSILON_11 = new EpsilonStackNode(11, 0);
	private final static AbstractStackNode EPSILON_12 = new EpsilonStackNode(12, 0);
	
	public HiddenLeftRecursionNonEmpty(char[] input){
		super(input);
	}
	
	public void S(){
		expect(NONTERMINAL_S0, NONTERMINAL_T1, NONTERMINAL_U2);
		
		expect(LITERAL_a9);
		
		expect(EPSILON_10);
	}
	
	public void T(){
		expect(NONTERMINAL_T3, NONTERMINAL_U4, NONTERMINAL_S5);
		
		expect(EPSILON_11);
	}
	
	public void U(){
		expect(NONTERMINAL_U6, NONTERMINAL_S7, NONTERMINAL_T8);
		
		expect(EPSILON_12);
	}
	
	public static void main(String[] args){
		HiddenLeftRecursionNonEmpty hlrne = new HiddenLeftRecursionNonEmpty("a".toCharArray());
		AbstractNode result = hlrne.parse("S");
		System.out.println(result);
		
		System.out.println("[S([S(cycle(S,1),[T(cycle(T,1),[U(cycle(U,1),cycle(S,3),cycle(T,2)),U()],cycle(S,2)),T()],[U(cycle(U,1),cycle(S,2),[T(cycle(T,1),cycle(U,2),cycle(S,3)),T()]),U()]),S()],[T(cycle(T,1),[U(cycle(U,1),[S(cycle(S,1),cycle(T,3),cycle(U,2)),S()],cycle(T,2)),U()],[S(cycle(S,1),cycle(T,2),[U(cycle(U,1),cycle(S,2),cycle(T,3)),U()]),S()]),T()],[U([U(cycle(U,1),[S(cycle(S,1),[T(cycle(T,1),cycle(U,3),cycle(S,2)),T()],cycle(U,2)),S()],[T(cycle(T,1),cycle(U,2),[S(cycle(S,1),cycle(T,2),cycle(U,3)),S()]),T()]),U()],cycle(S,2),[T(cycle(T,1),U(),S()),T()]),U(cycle(U,1),S(),[T(cycle(T,1),U(),S()),T()]),U([U(cycle(U,1),[S(cycle(S,1),[T(cycle(T,1),cycle(U,3),cycle(S,2)),T()],cycle(U,2)),S()],[T(cycle(T,1),cycle(U,2),[S(cycle(S,1),cycle(T,2),cycle(U,3)),S()]),T()]),U()],[S(cycle(S,1),[T(cycle(T,1),[U(cycle(U,1),cycle(S,3),cycle(T,2)),U()],cycle(S,2)),T()],[U(cycle(U,1),cycle(S,2),[T(cycle(T,1),cycle(U,2),cycle(S,3)),T()]),U()]),S()],[T([T(cycle(T,1),[U(cycle(U,1),[S(cycle(S,1),cycle(T,3),cycle(U,2)),S()],cycle(T,2)),U()],[S(cycle(S,1),cycle(T,2),[U(cycle(U,1),cycle(S,2),cycle(T,3)),U()]),S()]),T()],cycle(U,2),S()),T(cycle(T,1),U(),S()),T([T(cycle(T,1),[U(cycle(U,1),[S(cycle(S,1),cycle(T,3),cycle(U,2)),S()],cycle(T,2)),U()],[S(cycle(S,1),cycle(T,2),[U(cycle(U,1),cycle(S,2),cycle(T,3)),U()]),S()]),T()],[U(cycle(U,1),[S(cycle(S,1),[T(cycle(T,1),cycle(U,3),cycle(S,2)),T()],cycle(U,2)),S()],[T(cycle(T,1),cycle(U,2),[S(cycle(S,1),cycle(T,2),cycle(U,3)),S()]),T()]),U()],cycle(S,3))])]),S([S(cycle(S,1),[T(cycle(T,1),[U(cycle(U,1),cycle(S,3),cycle(T,2)),U()],cycle(S,2)),T()],[U(cycle(U,1),cycle(S,2),[T(cycle(T,1),cycle(U,2),cycle(S,3)),T()]),U()]),S()],[T([T(cycle(T,1),[U(cycle(U,1),[S(cycle(S,1),cycle(T,3),cycle(U,2)),S()],cycle(T,2)),U()],[S(cycle(S,1),cycle(T,2),[U(cycle(U,1),cycle(S,2),cycle(T,3)),U()]),S()]),T()],[U([U(cycle(U,1),[S(cycle(S,1),[T(cycle(T,1),cycle(U,3),cycle(S,2)),T()],cycle(U,2)),S()],[T(cycle(T,1),cycle(U,2),[S(cycle(S,1),cycle(T,2),cycle(U,3)),S()]),T()]),U()],cycle(S,3),[T(cycle(T,1),U(),S()),T()]),U(cycle(U,1),S(),[T(cycle(T,1),U(),S()),T()]),U([U(cycle(U,1),[S(cycle(S,1),[T(cycle(T,1),cycle(U,3),cycle(S,2)),T()],cycle(U,2)),S()],[T(cycle(T,1),cycle(U,2),[S(cycle(S,1),cycle(T,2),cycle(U,3)),S()]),T()]),U()],[S(cycle(S,1),[T(cycle(T,1),[U(cycle(U,1),cycle(S,3),cycle(T,2)),U()],cycle(S,2)),T()],[U(cycle(U,1),cycle(S,2),[T(cycle(T,1),cycle(U,2),cycle(S,3)),T()]),U()]),S()],cycle(T,2))],S()),T(cycle(T,1),U(),S()),T([T(cycle(T,1),[U(cycle(U,1),[S(cycle(S,1),cycle(T,3),cycle(U,2)),S()],cycle(T,2)),U()],[S(cycle(S,1),cycle(T,2),[U(cycle(U,1),cycle(S,2),cycle(T,3)),U()]),S()]),T()],[U(cycle(U,1),[S(cycle(S,1),[T(cycle(T,1),cycle(U,3),cycle(S,2)),T()],cycle(U,2)),S()],[T(cycle(T,1),cycle(U,2),[S(cycle(S,1),cycle(T,2),cycle(U,3)),S()]),T()]),U()],cycle(S,2))],U()),S(cycle(S,1),[T(cycle(T,1),U(),S()),T()],U()),S(a)] <- good");
	}
}
