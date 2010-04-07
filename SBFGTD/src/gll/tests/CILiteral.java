package gll.tests;

import gll.SGLL;
import gll.stack.ContextInsensitiveLiteralParseStackNode;
import gll.stack.ParseStackNode;

public class CILiteral extends SGLL{
	private final static ParseStackNode LITERAL_a0 = new ContextInsensitiveLiteralParseStackNode(new char[][]{{'A','a'}}, 0);
	
	public CILiteral(char[] input){
		super(input);
	}
	
	public void S(){
		expect(LITERAL_a0);
	}
	
	public static void main(String[] args){
		CILiteral cil = new CILiteral("a".toCharArray());
		System.out.println(cil.parse("S"));
		
		System.out.println("S(a) <- good");
	}
}
