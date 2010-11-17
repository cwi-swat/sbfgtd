package gll.bench;

import gll.SGLL;
import gll.stack.AbstractStackNode;
import gll.stack.LiteralStackNode;
import gll.stack.NonTerminalStackNode;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;

public class LL extends SGLL{
	private final static AbstractStackNode LITERAL_0 = new LiteralStackNode(0, 0, "a".toCharArray());
	private final static AbstractStackNode NONTERMINAL_S1 = new NonTerminalStackNode(1, 1, "S");
	private final static AbstractStackNode LITERAL_2 = new LiteralStackNode(2, 2, "b".toCharArray());
	private final static AbstractStackNode LITERAL_4 = new LiteralStackNode(4, 0, "a".toCharArray());
	
	private LL(char[] input){
		super(input);
	}
	
	public void S(){
		expect(LITERAL_0, NONTERMINAL_S1, LITERAL_2);
		expect(LITERAL_4);
	}
	
	private final static int ITERATIONS = 3;
	
	private static char[] createInput(int size){
		char[] input = new char[size];
		for(int i = (size / 2) - 1; i >= 0; --i){
			input[i] = 'a';
		}
		for(int i = size - 1; i >= size / 2; --i){
			input[i] = 'b';
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
			LL ll = new LL(input);
			ll.parse("S");
			long end = tmxb.getCurrentThreadCpuTime();
			
			long time = (end - start) / 1000000;
			total += time;
			lowest = (time < lowest) ? time : lowest;
		}
		System.out.println(input.length+": avg="+(total / ITERATIONS)+"ms, lowest="+lowest+"ms");
	}
	
	public static void main(String[] args) throws Exception{
		// Warmup.
		char[] input = createInput(5);
		
		for(int i = 9999; i >= 0; --i){
			LL ll = new LL(input);
			ll.parse("S");
		}
		
		// The benchmarks.
		for(int i = 50001; i <= 2000001; i += 50000){
			input = createInput(i);
			runTest(input);
		}
	}
}
