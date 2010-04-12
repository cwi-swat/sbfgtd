package gll.tests;

import gll.SGLL;
import gll.stack.ContextInsensitiveLiteralParseStackNode;
import gll.stack.ParseStackNode;

/*
S ::= ci(bla)
*/
public class CILiteral extends SGLL{
	private final static ParseStackNode LITERAL_a0 = new ContextInsensitiveLiteralParseStackNode(new char[]{'b','l','a'}, 0);
	
	public CILiteral(char[] input){
		super(input);
	}
	
	public void S(){
		expect(LITERAL_a0);
	}
	
	public static void main(String[] args){
		CILiteral cil = new CILiteral("Bla".toCharArray());
		System.out.println(cil.parse("S"));
		
		System.out.println("S(Bla) <- good");
	}
}
