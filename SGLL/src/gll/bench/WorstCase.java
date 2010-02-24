package gll.bench;

import gll.SGLL;
import gll.stack.NonTerminalParseStackNode;
import gll.stack.ParseStackNode;
import gll.stack.TerminalParseStackNode;

/*
S ::= SSS | SS | a
*/
public class WorstCase extends SGLL{
	private final static ParseStackNode NONTERMINAL_S = new NonTerminalParseStackNode("S");
	private final static ParseStackNode TERMINAL_a = new TerminalParseStackNode("a".getBytes());
	
	public WorstCase(String start, byte[] input){
		super(start, input);
	}
	
	public void S(){
		expect(NONTERMINAL_S, NONTERMINAL_S, NONTERMINAL_S);
		
		expect(NONTERMINAL_S, NONTERMINAL_S);
		
		expect(TERMINAL_a);
	}
	
	public static void main(String[] args){
		long start = System.currentTimeMillis();
		WorstCase wc = new WorstCase("S", "aaaaaaaaaa".getBytes());
		wc.parse();
		long end = System.currentTimeMillis();
		System.out.println("10 "+(end - start)+"ms");
		
		start = System.currentTimeMillis();
		wc = new WorstCase("S", "aaaaaaaaaaaaaaa".getBytes());
		wc.parse();
		end = System.currentTimeMillis();
		System.out.println("15 "+(end - start)+"ms");
		
		start = System.currentTimeMillis();
		wc = new WorstCase("S", "aaaaaaaaaaaaaaaaaaaa".getBytes());
		wc.parse();
		end = System.currentTimeMillis();
		System.out.println("20 "+(end - start)+"ms");
		
		start = System.currentTimeMillis();
		wc = new WorstCase("S", "aaaaaaaaaaaaaaaaaaaaaaaaa".getBytes());
		wc.parse();
		end = System.currentTimeMillis();
		System.out.println("25 "+(end - start)+"ms");
		
		start = System.currentTimeMillis();
		wc = new WorstCase("S", "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaa".getBytes());
		wc.parse();
		end = System.currentTimeMillis();
		System.out.println("30 "+(end - start)+"ms");
		
		start = System.currentTimeMillis();
		wc = new WorstCase("S", "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa".getBytes());
		wc.parse();
		end = System.currentTimeMillis();
		System.out.println("35 "+(end - start)+"ms");
		
		start = System.currentTimeMillis();
		wc = new WorstCase("S", "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa".getBytes());
		wc.parse();
		end = System.currentTimeMillis();
		System.out.println("40 "+(end - start)+"ms");
		
		start = System.currentTimeMillis();
		wc = new WorstCase("S", "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa".getBytes());
		wc.parse();
		end = System.currentTimeMillis();
		System.out.println("45 "+(end - start)+"ms");
		
		start = System.currentTimeMillis();
		wc = new WorstCase("S", "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa".getBytes());
		wc.parse();
		end = System.currentTimeMillis();
		System.out.println("50 "+(end - start)+"ms");
	}
}
