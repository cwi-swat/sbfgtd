package gtd.bench;

import gtd.SGTDBF;
import gtd.preprocessing.ExpectBuilder;
import gtd.stack.AbstractStackNode;
import gtd.stack.LiteralStackNode;
import gtd.stack.NonTerminalStackNode;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;

public class LR extends SGTDBF{
	private final static AbstractStackNode LITERAL_0 = new LiteralStackNode(0, 0, "a".toCharArray());
	private final static AbstractStackNode NONTERMINAL_S1 = new NonTerminalStackNode(1, 1, "S");
	private final static AbstractStackNode LITERAL_2 = new LiteralStackNode(2, 2, "b".toCharArray());
	private final static AbstractStackNode LITERAL_3 = new LiteralStackNode(3, 2, "c".toCharArray());
	private final static AbstractStackNode LITERAL_4 = new LiteralStackNode(4, 0, "a".toCharArray());
	
	private LR(char[] input){
		super(input);
	}
	
	private final static AbstractStackNode[] SMatrix;
	static{
		ExpectBuilder eb = new ExpectBuilder();
		
		eb.addAlternative(LITERAL_0, NONTERMINAL_S1, LITERAL_2);
		eb.addAlternative(LITERAL_0, NONTERMINAL_S1, LITERAL_3);
		eb.addAlternative(LITERAL_4);
		
		SMatrix = eb.buildExpectMatrix();
	}
	
	public AbstractStackNode[] S(){
		return SMatrix;
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
			LR lr = new LR(input);
			lr.parse("S");
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
			LR lr = new LR(input);
			lr.parse("S");
		}
		
		// The benchmarks.
		for(int i = 50001; i <= 2000001; i += 50000){
			input = createInput(i);
			runTest(input);
		}
	}
}
