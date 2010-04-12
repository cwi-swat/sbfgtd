package gll.bench;

import gll.SGLL;
import gll.stack.NonTerminalStackNode;
import gll.stack.StackNode;
import gll.stack.LiteralStackNode;

/*
S ::= SSS | SS | a
*/
public class WorstCase extends SGLL{
	private final static StackNode NONTERMINAL_S0 = new NonTerminalStackNode(0, "S");
	private final static StackNode NONTERMINAL_S1 = new NonTerminalStackNode(1, "S");
	private final static StackNode NONTERMINAL_S2 = new NonTerminalStackNode(2, "S");
	private final static StackNode NONTERMINAL_S3 = new NonTerminalStackNode(3, "S");
	private final static StackNode NONTERMINAL_S4 = new NonTerminalStackNode(4, "S");
	private final static StackNode TERMINAL_a5 = new LiteralStackNode(5, new char[]{'a'});
	
	public WorstCase(char[] input){
		super(input);
	}
	
	public void S(){
		expect(NONTERMINAL_S0, NONTERMINAL_S1, NONTERMINAL_S2);
		
		expect(NONTERMINAL_S3, NONTERMINAL_S4);
		
		expect(TERMINAL_a5);
	}
	
	public static void main(String[] args){
		// Warmup.
		for(int i = 9999; i >= 0; i--){
			WorstCase wc = new WorstCase("aaaaaaaaaa".toCharArray());
			wc.parse("S");
		}
		
		// The benchmarks.
		long start = System.currentTimeMillis();
		WorstCase wc = new WorstCase("aaaaaaaaaa".toCharArray());
		wc.parse("S");
		long end = System.currentTimeMillis();
		System.out.println("10 "+(end - start)+"ms");
		
		start = System.currentTimeMillis();
		wc = new WorstCase("aaaaaaaaaaaaaaa".toCharArray());
		wc.parse("S");
		end = System.currentTimeMillis();
		System.out.println("15 "+(end - start)+"ms");
		
		start = System.currentTimeMillis();
		wc = new WorstCase("aaaaaaaaaaaaaaaaaaaa".toCharArray());
		wc.parse("S");
		end = System.currentTimeMillis();
		System.out.println("20 "+(end - start)+"ms");
		
		start = System.currentTimeMillis();
		wc = new WorstCase("aaaaaaaaaaaaaaaaaaaaaaaaa".toCharArray());
		wc.parse("S");
		end = System.currentTimeMillis();
		System.out.println("25 "+(end - start)+"ms");
		
		start = System.currentTimeMillis();
		wc = new WorstCase("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaa".toCharArray());
		wc.parse("S");
		end = System.currentTimeMillis();
		System.out.println("30 "+(end - start)+"ms");
		
		start = System.currentTimeMillis();
		wc = new WorstCase("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa".toCharArray());
		wc.parse("S");
		end = System.currentTimeMillis();
		System.out.println("35 "+(end - start)+"ms");
		
		start = System.currentTimeMillis();
		wc = new WorstCase("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa".toCharArray());
		wc.parse("S");
		end = System.currentTimeMillis();
		System.out.println("40 "+(end - start)+"ms");
		
		start = System.currentTimeMillis();
		wc = new WorstCase("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa".toCharArray());
		wc.parse("S");
		end = System.currentTimeMillis();
		System.out.println("45 "+(end - start)+"ms");
		
		start = System.currentTimeMillis();
		wc = new WorstCase("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa".toCharArray());
		wc.parse("S");
		end = System.currentTimeMillis();
		System.out.println("50 "+(end - start)+"ms");
		
		start = System.currentTimeMillis();
		wc = new WorstCase("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa".toCharArray());
		wc.parse("S");
		end = System.currentTimeMillis();
		System.out.println("100 "+(end - start)+"ms");
		
		start = System.currentTimeMillis();
		wc = new WorstCase("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa".toCharArray());
		wc.parse("S");
		end = System.currentTimeMillis();
		System.out.println("200 "+(end - start)+"ms");
		
		start = System.currentTimeMillis();
		wc = new WorstCase("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa".toCharArray());
		wc.parse("S");
		end = System.currentTimeMillis();
		System.out.println("300 "+(end - start)+"ms");
		
		/*start = System.currentTimeMillis();
		wc = new WorstCase("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa".toCharArray());
		wc.parse("S");
		end = System.currentTimeMillis();
		System.out.println("500 "+(end - start)+"ms");*/
	}
}
