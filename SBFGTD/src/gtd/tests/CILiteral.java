package gtd.tests;

import gtd.SGTDBF;
import gtd.preprocessing.ExpectBuilder;
import gtd.result.AbstractNode;
import gtd.stack.AbstractStackNode;
import gtd.stack.CaseInsensitiveLiteralStackNode;

/*
S ::= ci(bla)

NOTE: ci(*) means whatever * represents is Case Insensitive.
*/
public class CILiteral extends SGTDBF{
	private final static AbstractStackNode LITERAL_bla0 = new CaseInsensitiveLiteralStackNode(0, 0, new char[]{'b','l','a'});
	
	public CILiteral(char[] input){
		super(input);
	}
	
	public void S(){
		ExpectBuilder eb = new ExpectBuilder();
		
		eb.addAlternative(LITERAL_bla0);
		
		expect(eb.buildExpectMatrix());
	}
	
	public static void main(String[] args){
		CILiteral cil = new CILiteral("Bla".toCharArray());
		AbstractNode result = cil.parse("S");
		System.out.println(result);
		
		System.out.println("S(Bla) <- good");
	}
}
