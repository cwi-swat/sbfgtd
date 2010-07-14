package gll.tests;

import gll.SGLL;
import gll.result.INode;
import gll.stack.AbstractStackNode;
import gll.stack.EpsilonStackNode;

/*
S ::= epsilon
*/
public class Epsilon extends SGLL{
	private final static AbstractStackNode EPSILON_1 = new EpsilonStackNode(0);
	
	public Epsilon(char[] input){
		super(input);
	}
	
	public void S(){
		expect(EPSILON_1);
	}
	
	public static void main(String[] args){
		Epsilon e = new Epsilon(new char[]{});
		INode result = e.parse("S");
		System.out.println(result);
		
		System.out.println("S() <- good");
	}
}
