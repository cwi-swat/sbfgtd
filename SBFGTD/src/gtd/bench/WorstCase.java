package gtd.bench;

import gtd.SGTDBF;
import gtd.stack.AbstractStackNode;
import gtd.stack.LiteralStackNode;
import gtd.stack.NonTerminalStackNode;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;

/*
S ::= SSS | SS | a
*/
public class WorstCase extends SGTDBF{
	private final static AbstractStackNode NONTERMINAL_S0 = new NonTerminalStackNode(0, 0, "S");
	private final static AbstractStackNode NONTERMINAL_S1 = new NonTerminalStackNode(1, 1, "S");
	private final static AbstractStackNode NONTERMINAL_S2 = new NonTerminalStackNode(2, 2, "S");
	private final static AbstractStackNode TERMINAL_a5 = new LiteralStackNode(5, 0, new char[]{'a'});
	
	private final static AbstractStackNode[] SS = new AbstractStackNode[]{NONTERMINAL_S0, NONTERMINAL_S1};
	private final static AbstractStackNode[] SSS = new AbstractStackNode[]{NONTERMINAL_S0, NONTERMINAL_S1, NONTERMINAL_S2};
	private final static AbstractStackNode[] a = new AbstractStackNode[]{TERMINAL_a5};
	
	public WorstCase(char[] input){
		super(input);
	}
	
	public void S(){
		expect(SS);
		
		expect(SSS);
		
		expect(a);
	}
	
	private final static int ITERATIONS = 5;
	
	private static char[] createInput(int size){
		char[] input = new char[size];
		for(int i = size - 1; i >= 0; --i){
			input[i] = 'a';
		}
		return input;
	}
	
	private static void cleanup() throws Exception{
		System.gc();
		System.gc();
		System.gc();
		System.gc();
		System.gc();
		Thread.sleep(1000);
	}
	
	private static void runTest(char[] input) throws Exception{
		ThreadMXBean tmxb = ManagementFactory.getThreadMXBean();
		
		long total = 0;
		long lowest = Long.MAX_VALUE;
		for(int i = ITERATIONS - 1; i >= 0; --i){
			cleanup();
			
			long start = tmxb.getCurrentThreadCpuTime();
			WorstCase wc = new WorstCase(input);
			wc.parse("S");
			long end = tmxb.getCurrentThreadCpuTime();
			
			long time = (end - start) / 1000000;
			total += time;
			lowest = (time < lowest) ? time : lowest;
			
			//System.out.println(input.length+": intermediate time: "+time+"ms"); // Temp
		}
		System.out.println(input.length+": avg="+(total / ITERATIONS)+"ms, lowest="+lowest+"ms");
	}
	
	public static void main(String[] args) throws Exception{
		// Warmup.
		char[] input = createInput(5);
		for(int i = 9999; i >= 0; --i){
			WorstCase wc = new WorstCase(input);
			wc.parse("S");
		}
		
		// The benchmarks.
		/*for(int i = 5; i < 50; i += 5){
			input = createInput(i);
			runTest(input);
		}*/
		
		for(int i = 50; i <= 500; i += 50){
			input = createInput(i);
			runTest(input);
		}
	}
}
