package gtd.bench;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;

import gtd.SGTDBF;
import gtd.stack.AbstractStackNode;
import gtd.stack.LiteralStackNode;
import gtd.stack.NonTerminalStackNode;

/*
S ::= E
E ::= E + F | F
F ::= a | ( E )
*/
public class EFa extends SGTDBF{
	private final static AbstractStackNode NONTERMINAL_E0 = new NonTerminalStackNode(0, 0, "E");
	private final static AbstractStackNode[] S_E = new AbstractStackNode[]{NONTERMINAL_E0};
	
	private final static AbstractStackNode NONTERMINAL_E1 = new NonTerminalStackNode(1, 0, "E");
	private final static AbstractStackNode LITERAL_2 = new LiteralStackNode(2, 1, "+".toCharArray());
	private final static AbstractStackNode NONTERMINAL_F3 = new NonTerminalStackNode(3, 2, "F");
	private final static AbstractStackNode[] E_EF = new AbstractStackNode[]{NONTERMINAL_E1, LITERAL_2, NONTERMINAL_F3};
	private final static AbstractStackNode NONTERMINAL_F4 = new NonTerminalStackNode(4, 0, "F");
	private final static AbstractStackNode[] E_F = new AbstractStackNode[]{NONTERMINAL_F4};
	
	private final static AbstractStackNode LITERAL_5 = new LiteralStackNode(5, 0, "a".toCharArray());
	private final static AbstractStackNode[] F_a = new AbstractStackNode[]{LITERAL_5};
	private final static AbstractStackNode LITERAL_6 = new LiteralStackNode(6, 0, "(".toCharArray());
	private final static AbstractStackNode NONTERMINAL_E7 = new NonTerminalStackNode(7, 1, "E");
	private final static AbstractStackNode LITERAL_8 = new LiteralStackNode(8, 2, ")".toCharArray());
	private final static AbstractStackNode[] F_E = new AbstractStackNode[]{LITERAL_6, NONTERMINAL_E7, LITERAL_8};
	
	private EFa(char[] input){
		super(input);
	}
	
	public void S(){
		expect(S_E);
	}
	
	public void E(){
		expect(E_EF);
		
		expect(E_F);
	}
	
	public void F(){
		expect(F_a);
		
		expect(F_E);
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
		// Warmup.
		char[] input = createInput(5);
		
		for(int i = 9999; i >= 0; --i){
			EFa eFa = new EFa(input);
			eFa.parse("S");
		}
		
		// The benchmarks.
		for(int i = 50001; i <= 2000001; i += 50000){
			input = createInput(i);
			runTest(input);
		}
	}
}
