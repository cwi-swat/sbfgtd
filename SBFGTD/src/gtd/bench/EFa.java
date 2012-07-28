package gtd.bench;

import gtd.SGTDBF;
import gtd.preprocessing.ExpectBuilder;
import gtd.stack.AbstractStackNode;
import gtd.stack.CharStackNode;
import gtd.stack.NonTerminalStackNode;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;

/*
S ::= E
E ::= E + F | F
F ::= a | ( E )
*/
public class EFa extends SGTDBF{
	private final static AbstractStackNode NONTERMINAL_E0 = new NonTerminalStackNode(0, 0, "E");
	
	private final static AbstractStackNode NONTERMINAL_E1 = new NonTerminalStackNode(1, 0, "E");
	private final static AbstractStackNode LITERAL_2 = new CharStackNode(2, 1, '+');
	private final static AbstractStackNode NONTERMINAL_F3 = new NonTerminalStackNode(3, 2, "F");
	private final static AbstractStackNode NONTERMINAL_F4 = new NonTerminalStackNode(4, 0, "F");
	
	private final static AbstractStackNode LITERAL_5 = new CharStackNode(5, 0, 'a');
	private final static AbstractStackNode LITERAL_6 = new CharStackNode(6, 0, '(');
	private final static AbstractStackNode NONTERMINAL_E7 = new NonTerminalStackNode(7, 1, "E");
	private final static AbstractStackNode LITERAL_8 = new CharStackNode(8, 2, ')');
	
	private EFa(char[] input){
		super(input);
	}
	
	private final static AbstractStackNode[] SMatrix;
	static{
		ExpectBuilder eb = new ExpectBuilder();
		
		eb.addAlternative(NONTERMINAL_E0);
		
		SMatrix = eb.buildExpectMatrix();
	}
	
	public AbstractStackNode[] S(){
		return SMatrix;
	}
	
	private final static AbstractStackNode[] EMatrix;
	static{
		ExpectBuilder eb = new ExpectBuilder();
		
		eb.addAlternative(NONTERMINAL_E1, LITERAL_2, NONTERMINAL_F3);
		eb.addAlternative(NONTERMINAL_F4);
		
		EMatrix = eb.buildExpectMatrix();
	}
	
	public AbstractStackNode[] E(){
		return EMatrix;
	}
	
	private final static AbstractStackNode[] FMatrix;
	static{
		ExpectBuilder eb = new ExpectBuilder();
		
		eb.addAlternative(LITERAL_5);
		eb.addAlternative(LITERAL_6, NONTERMINAL_E7, LITERAL_8);
		
		FMatrix = eb.buildExpectMatrix();
	}
	
	public AbstractStackNode[] F(){
		return FMatrix;
	}
	
	private final static int ITERATIONS = 3;
	
	private static char[] createInput(int size){
		int depth = (size - 3) / 4;
		
		StringBuilder sb = new StringBuilder();
		sb.append('a');
		sb.append('+');
		addInput(sb, depth);
		
		return sb.toString().toCharArray();
	}
	
	private static void addInput(StringBuilder sb, int counter){
		sb.append('(');
		sb.append('a');
		sb.append('+');
		if(counter != 0) addInput(sb, counter - 1);
		else sb.append('a');
		sb.append(')');
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
			EFa eFa = new EFa(input);
			eFa.parse("S");
			long end = tmxb.getCurrentThreadCpuTime();
			
			long time = (end - start) / 1000000;
			total += time;
			lowest = (time < lowest) ? time : lowest;
		}
		System.out.println(input.length+": avg="+(total / ITERATIONS)+"ms, lowest="+lowest+"ms");
	}
	
	public static void main(String[] args) throws Exception{
		char[] input = createInput(5);
		
		EFa testOut = new EFa(input);
		if(testOut.parse("S") != null) System.out.println("WARNING: Running in parser instead of recognizer mode.");
		
		// Warmup.
		for(int i = 9999; i >= 0; --i){
			EFa eFa = new EFa(input);
			eFa.parse("S");
		}
		
		for(int i = 200001; i <= 1000001; i += 200000){
			input = createInput(i);
			EFa eFa = new EFa(input);
			eFa.parse("S");
		}
		
		// The benchmarks.
		for(int i = 200001; i <= 1000001; i += 200000){
			input = createInput(i);
			runTest(input);
		}
	}
}
