package gll.bench;

import gll.SGLL;
import gll.stack.NonTerminalStackNode;
import gll.stack.AbstractStackNode;
import gll.stack.LiteralStackNode;

/*
S ::= SSS | SS | a
*/
public class WorstCase extends SGLL{
	private final static AbstractStackNode NONTERMINAL_S0 = new NonTerminalStackNode(0, "S");
	private final static AbstractStackNode NONTERMINAL_S1 = new NonTerminalStackNode(1, "S");
	private final static AbstractStackNode NONTERMINAL_S2 = new NonTerminalStackNode(2, "S");
	private final static AbstractStackNode NONTERMINAL_S3 = new NonTerminalStackNode(3, "S");
	private final static AbstractStackNode NONTERMINAL_S4 = new NonTerminalStackNode(4, "S");
	private final static AbstractStackNode TERMINAL_a5 = new LiteralStackNode(5, new char[]{'a'});
	
	public WorstCase(char[] input){
		super(input);
	}
	
	public void S(){
		expect(NONTERMINAL_S0, NONTERMINAL_S1, NONTERMINAL_S2);
		
		expect(NONTERMINAL_S3, NONTERMINAL_S4);
		
		expect(TERMINAL_a5);
	}
	
	private final static int ITERATIONS = 5;
	
	private static char[] createInput(int size){
		char[] input = new char[size];
		for(int i = size - 1; i >= 0; i--){
			input[i] = 'a';
		}
		return input;
	}
	
	private static void runTest(char[] input){
		long total = 0;
		long lowest = Long.MAX_VALUE;
		for(int i = ITERATIONS - 1; i >= 0; i--){
			long start = System.currentTimeMillis();
			WorstCase wc = new WorstCase(input);
			wc.parse("S");
			long end = System.currentTimeMillis();
			
			long time = end - start;
			total += time;
			lowest = (time < lowest) ? time : lowest;
			
			//System.out.println(input.length+": intermediate time: "+time+"ms");
		}
		System.out.println(input.length+": avg="+(total / ITERATIONS)+"ms, lowest="+lowest+"ms");
	}
	
	public static void main(String[] args){
		// Warmup.
		char[] input = createInput(5);
		for(int i = 9999; i >= 0; i--){
			WorstCase wc = new WorstCase(input);
			wc.parse("S");
		}
		
		// The benchmarks.
		for(int i = 5; i < 50; i += 5){
			input = createInput(i);
			runTest(input);
		}
		
		for(int i = 50; i <= 500; i += 50){
			input = createInput(i);
			runTest(input);
		}
	}
}
