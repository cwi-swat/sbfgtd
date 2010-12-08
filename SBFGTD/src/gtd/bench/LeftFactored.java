package gtd.bench;

import gtd.SGTDBF;
import gtd.stack.AbstractStackNode;
import gtd.stack.ListStackNode;
import gtd.stack.LiteralStackNode;
import gtd.stack.NonTerminalStackNode;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;

public class LeftFactored extends SGTDBF{
	private final static AbstractStackNode NONTERMINAL_E0 = new NonTerminalStackNode(0, 0, "E");
	private final static AbstractStackNode NONTERMINAL_E1 = new NonTerminalStackNode(1, 1, "E");
	private final static AbstractStackNode NONTERMINAL_E3 = new NonTerminalStackNode(3, 1, "E");
	private final static AbstractStackNode NONTERMINAL_E5 = new NonTerminalStackNode(5, 1, "E");
	private final static AbstractStackNode NONTERMINAL_E7 = new NonTerminalStackNode(7, 1, "E");
	private final static AbstractStackNode NONTERMINAL_E9 = new NonTerminalStackNode(9, 1, "E");
	private final static AbstractStackNode NONTERMINAL_E11 = new NonTerminalStackNode(11, 1, "E");
	private final static AbstractStackNode NONTERMINAL_E13 = new NonTerminalStackNode(13, 1, "E");
	private final static AbstractStackNode NONTERMINAL_E15 = new NonTerminalStackNode(15, 1, "E");
	private final static AbstractStackNode NONTERMINAL_E17 = new NonTerminalStackNode(17, 1, "E");
	private final static AbstractStackNode NONTERMINAL_E19 = new NonTerminalStackNode(19, 1, "E");
	private final static AbstractStackNode NONTERMINAL_E21 = new NonTerminalStackNode(21, 1, "E");
	private final static AbstractStackNode NONTERMINAL_E23 = new NonTerminalStackNode(23, 1, "E");
	private final static AbstractStackNode NONTERMINAL_E25 = new NonTerminalStackNode(25, 1, "E");
	private final static AbstractStackNode NONTERMINAL_E27 = new NonTerminalStackNode(27, 1, "E");
	private final static AbstractStackNode NONTERMINAL_E29 = new NonTerminalStackNode(29, 1, "E");
	private final static AbstractStackNode NONTERMINAL_E31 = new NonTerminalStackNode(31, 1, "E");
	private final static AbstractStackNode NONTERMINAL_E33 = new NonTerminalStackNode(33, 1, "E");
	private final static AbstractStackNode NONTERMINAL_E35 = new NonTerminalStackNode(35, 1, "E");
	private final static AbstractStackNode NONTERMINAL_E37 = new NonTerminalStackNode(37, 1, "E");
	private final static AbstractStackNode NONTERMINAL_E39 = new NonTerminalStackNode(39, 1, "E");
	private final static AbstractStackNode NONTERMINAL_E41 = new NonTerminalStackNode(41, 1, "E");
	private final static AbstractStackNode NONTERMINAL_E43 = new NonTerminalStackNode(43, 1, "E");
	private final static AbstractStackNode NONTERMINAL_E45 = new NonTerminalStackNode(45, 1, "E");
	private final static AbstractStackNode NONTERMINAL_E47 = new NonTerminalStackNode(47, 1, "E");
	private final static AbstractStackNode NONTERMINAL_E49 = new NonTerminalStackNode(49, 1, "E");
	private final static AbstractStackNode NONTERMINAL_E51 = new NonTerminalStackNode(51, 1, "E");
	private final static AbstractStackNode NONTERMINAL_E53 = new NonTerminalStackNode(53, 1, "E");
	private final static AbstractStackNode NONTERMINAL_E55 = new NonTerminalStackNode(55, 1, "E");
	private final static AbstractStackNode NONTERMINAL_E57 = new NonTerminalStackNode(57, 1, "E");
	private final static AbstractStackNode NONTERMINAL_E59 = new NonTerminalStackNode(59, 1, "E");
	private final static AbstractStackNode NONTERMINAL_E10000 = new NonTerminalStackNode(10000, 0, "E");
	private final static AbstractStackNode NONTERMINAL_Ep100000 = new NonTerminalStackNode(100000, 1, "Ep");
	
	private final static AbstractStackNode LITERAL_0 = new LiteralStackNode(100, 0, "@".toCharArray());
	private final static AbstractStackNode LITERAL_1 = new LiteralStackNode(101, 0, "-".toCharArray());
	private final static AbstractStackNode LITERAL_2 = new LiteralStackNode(102, 0, "_".toCharArray());
	private final static AbstractStackNode LITERAL_3 = new LiteralStackNode(103, 0, "+".toCharArray());
	private final static AbstractStackNode LITERAL_4 = new LiteralStackNode(104, 0, "=".toCharArray());
	private final static AbstractStackNode LITERAL_5 = new LiteralStackNode(105, 0, "[".toCharArray());
	private final static AbstractStackNode LITERAL_6 = new LiteralStackNode(106, 0, "]".toCharArray());
	private final static AbstractStackNode LITERAL_7 = new LiteralStackNode(107, 0, "|".toCharArray());
	private final static AbstractStackNode LITERAL_8 = new LiteralStackNode(108, 0, "\\".toCharArray());
	private final static AbstractStackNode LITERAL_9 = new LiteralStackNode(109, 0, "'".toCharArray());
	private final static AbstractStackNode LITERAL_10 = new LiteralStackNode(110, 0, "\"".toCharArray());
	private final static AbstractStackNode LITERAL_11 = new LiteralStackNode(111, 0, ";".toCharArray());
	private final static AbstractStackNode LITERAL_12 = new LiteralStackNode(112, 0, ":".toCharArray());
	private final static AbstractStackNode LITERAL_13 = new LiteralStackNode(113, 0, "?".toCharArray());
	private final static AbstractStackNode LITERAL_14 = new LiteralStackNode(114, 0, "/".toCharArray());
	private final static AbstractStackNode LITERAL_15 = new LiteralStackNode(115, 0, ".".toCharArray());
	private final static AbstractStackNode LITERAL_16 = new LiteralStackNode(116, 0, ">".toCharArray());
	private final static AbstractStackNode LITERAL_17 = new LiteralStackNode(117, 0, "<".toCharArray());
	private final static AbstractStackNode LITERAL_18 = new LiteralStackNode(118, 0, ",".toCharArray());
	private final static AbstractStackNode LITERAL_19 = new LiteralStackNode(119, 0, "*".toCharArray());
	private final static AbstractStackNode LITERAL_20 = new LiteralStackNode(120, 0, "`".toCharArray());
	private final static AbstractStackNode LITERAL_21 = new LiteralStackNode(121, 0, "~".toCharArray());
	private final static AbstractStackNode LITERAL_22 = new LiteralStackNode(122, 0, "!".toCharArray());
	private final static AbstractStackNode LITERAL_23 = new LiteralStackNode(123, 0, "(".toCharArray());
	private final static AbstractStackNode LITERAL_24 = new LiteralStackNode(124, 0, ")".toCharArray());
	private final static AbstractStackNode LITERAL_25 = new LiteralStackNode(125, 0, "&".toCharArray());
	private final static AbstractStackNode LITERAL_26 = new LiteralStackNode(126, 0, "^".toCharArray());
	private final static AbstractStackNode LITERAL_27 = new LiteralStackNode(127, 0, "%".toCharArray());
	private final static AbstractStackNode LITERAL_28 = new LiteralStackNode(128, 0, "$".toCharArray());
	private final static AbstractStackNode LITERAL_29 = new LiteralStackNode(129, 0, "#".toCharArray());
	private final static AbstractStackNode LITERAL_1000 = new LiteralStackNode(1000, 0, "1".toCharArray());
	
	private final static AbstractStackNode LIST9999 = new ListStackNode(9999, 0, NONTERMINAL_E10000, "E+", true);
	
	private LeftFactored(char[] input){
		super(input);
	}
	
	public void S(){
		expect(LIST9999);
	}
	
	public void E(){
		expect(NONTERMINAL_E0, NONTERMINAL_Ep100000);
		
		expect(LITERAL_1000);
	}
	
	public void Ep(){
		expect(LITERAL_0, NONTERMINAL_E1);
		expect(LITERAL_1, NONTERMINAL_E3);
		expect(LITERAL_2, NONTERMINAL_E5);
		expect(LITERAL_3, NONTERMINAL_E7);
		expect(LITERAL_4, NONTERMINAL_E9);
		expect(LITERAL_5, NONTERMINAL_E11);
		expect(LITERAL_6, NONTERMINAL_E13);
		expect(LITERAL_7, NONTERMINAL_E15);
		expect(LITERAL_8, NONTERMINAL_E17);
		expect(LITERAL_9, NONTERMINAL_E19);
		expect(LITERAL_10, NONTERMINAL_E21);
		expect(LITERAL_11, NONTERMINAL_E23);
		expect(LITERAL_12, NONTERMINAL_E25);
		expect(LITERAL_13, NONTERMINAL_E27);
		expect(LITERAL_14, NONTERMINAL_E29);
		expect(LITERAL_15, NONTERMINAL_E31);
		expect(LITERAL_16, NONTERMINAL_E33);
		expect(LITERAL_17, NONTERMINAL_E35);
		expect(LITERAL_18, NONTERMINAL_E37);
		expect(LITERAL_19, NONTERMINAL_E39);
		expect(LITERAL_20, NONTERMINAL_E41);
		expect(LITERAL_21, NONTERMINAL_E43);
		expect(LITERAL_22, NONTERMINAL_E45);
		expect(LITERAL_23, NONTERMINAL_E47);
		expect(LITERAL_24, NONTERMINAL_E49);
		expect(LITERAL_25, NONTERMINAL_E51);
		expect(LITERAL_26, NONTERMINAL_E53);
		expect(LITERAL_27, NONTERMINAL_E55);
		expect(LITERAL_28, NONTERMINAL_E57);
		expect(LITERAL_29, NONTERMINAL_E59);
	}
	
	private final static int ITERATIONS = 3;
	
	private static char[] createInput(int size){
		char[] input = new char[size];
		for(int i = size - 1; i >= 0; --i){
			input[i] = '1';
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
			LeftFactored lf = new LeftFactored(input);
			lf.parse("S");
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
			LeftFactored lf = new LeftFactored(input);
			lf.parse("S");
		}
		
		// The benchmarks.
		for(int i = 50000; i <= 200000; i += 50000){
			input = createInput(i);
			runTest(input);
		}
	}
}
