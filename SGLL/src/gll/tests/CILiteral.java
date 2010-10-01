package gll.tests;

import gll.SGLL;
import gll.result.AbstractNode;
import gll.stack.CaseInsensitiveLiteralStackNode;
import gll.stack.AbstractStackNode;

/*
S ::= ci(bla)

NOTE: ci(*) means whatever * represents is Case Insensitive.
*/
public class CILiteral extends SGLL{
	private final static AbstractStackNode LITERAL_bla0 = new CaseInsensitiveLiteralStackNode(0, 0, new char[]{'b','l','a'});
	
	public CILiteral(char[] input){
		super(input);
	}
	
	public void S(){
		expect(LITERAL_bla0);
	}
	
	public static void main(String[] args){
		CILiteral cil = new CILiteral("Bla".toCharArray());
		AbstractNode result = cil.parse("S");
		System.out.println(result);
		
		System.out.println("S(Bla) <- good");
	}
}
