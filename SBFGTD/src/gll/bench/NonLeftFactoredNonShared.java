package gll.bench;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;

import gll.SGLL;
import gll.stack.AbstractStackNode;
import gll.stack.ListStackNode;
import gll.stack.LiteralStackNode;
import gll.stack.NonTerminalStackNode;

public class NonLeftFactoredNonShared extends SGLL{
	private final static AbstractStackNode NONTERMINAL_E0 = new NonTerminalStackNode(0, 0, "E");
	private final static AbstractStackNode NONTERMINAL_E1 = new NonTerminalStackNode(1, 2, "E");
	private final static AbstractStackNode NONTERMINAL_E2 = new NonTerminalStackNode(2, 0, "E");
	private final static AbstractStackNode NONTERMINAL_E3 = new NonTerminalStackNode(3, 2, "E");
	private final static AbstractStackNode NONTERMINAL_E4 = new NonTerminalStackNode(4, 0, "E");
	private final static AbstractStackNode NONTERMINAL_E5 = new NonTerminalStackNode(5, 2, "E");
	private final static AbstractStackNode NONTERMINAL_E6 = new NonTerminalStackNode(6, 0, "E");
	private final static AbstractStackNode NONTERMINAL_E7 = new NonTerminalStackNode(7, 2, "E");
	private final static AbstractStackNode NONTERMINAL_E8 = new NonTerminalStackNode(8, 0, "E");
	private final static AbstractStackNode NONTERMINAL_E9 = new NonTerminalStackNode(9, 2, "E");
	private final static AbstractStackNode NONTERMINAL_E10 = new NonTerminalStackNode(10, 0, "E");
	private final static AbstractStackNode NONTERMINAL_E11 = new NonTerminalStackNode(11, 2, "E");
	private final static AbstractStackNode NONTERMINAL_E12 = new NonTerminalStackNode(12, 0, "E");
	private final static AbstractStackNode NONTERMINAL_E13 = new NonTerminalStackNode(13, 2, "E");
	private final static AbstractStackNode NONTERMINAL_E14 = new NonTerminalStackNode(14, 0, "E");
	private final static AbstractStackNode NONTERMINAL_E15 = new NonTerminalStackNode(15, 2, "E");
	private final static AbstractStackNode NONTERMINAL_E16 = new NonTerminalStackNode(16, 0, "E");
	private final static AbstractStackNode NONTERMINAL_E17 = new NonTerminalStackNode(17, 2, "E");
	private final static AbstractStackNode NONTERMINAL_E18 = new NonTerminalStackNode(18, 0, "E");
	private final static AbstractStackNode NONTERMINAL_E19 = new NonTerminalStackNode(19, 2, "E");
	private final static AbstractStackNode NONTERMINAL_E20 = new NonTerminalStackNode(20, 0, "E");
	private final static AbstractStackNode NONTERMINAL_E21 = new NonTerminalStackNode(21, 2, "E");
	private final static AbstractStackNode NONTERMINAL_E22 = new NonTerminalStackNode(22, 0, "E");
	private final static AbstractStackNode NONTERMINAL_E23 = new NonTerminalStackNode(23, 2, "E");
	private final static AbstractStackNode NONTERMINAL_E24 = new NonTerminalStackNode(24, 0, "E");
	private final static AbstractStackNode NONTERMINAL_E25 = new NonTerminalStackNode(25, 2, "E");
	private final static AbstractStackNode NONTERMINAL_E26 = new NonTerminalStackNode(26, 0, "E");
	private final static AbstractStackNode NONTERMINAL_E27 = new NonTerminalStackNode(27, 2, "E");
	private final static AbstractStackNode NONTERMINAL_E28 = new NonTerminalStackNode(28, 0, "E");
	private final static AbstractStackNode NONTERMINAL_E29 = new NonTerminalStackNode(29, 2, "E");
	private final static AbstractStackNode NONTERMINAL_E30 = new NonTerminalStackNode(30, 0, "E");
	private final static AbstractStackNode NONTERMINAL_E31 = new NonTerminalStackNode(31, 2, "E");
	private final static AbstractStackNode NONTERMINAL_E32 = new NonTerminalStackNode(32, 0, "E");
	private final static AbstractStackNode NONTERMINAL_E33 = new NonTerminalStackNode(33, 2, "E");
	private final static AbstractStackNode NONTERMINAL_E34 = new NonTerminalStackNode(34, 0, "E");
	private final static AbstractStackNode NONTERMINAL_E35 = new NonTerminalStackNode(35, 2, "E");
	private final static AbstractStackNode NONTERMINAL_E36 = new NonTerminalStackNode(36, 0, "E");
	private final static AbstractStackNode NONTERMINAL_E37 = new NonTerminalStackNode(37, 2, "E");
	private final static AbstractStackNode NONTERMINAL_E38 = new NonTerminalStackNode(38, 0, "E");
	private final static AbstractStackNode NONTERMINAL_E39 = new NonTerminalStackNode(39, 2, "E");
	private final static AbstractStackNode NONTERMINAL_E40 = new NonTerminalStackNode(40, 0, "E");
	private final static AbstractStackNode NONTERMINAL_E41 = new NonTerminalStackNode(41, 2, "E");
	private final static AbstractStackNode NONTERMINAL_E42 = new NonTerminalStackNode(42, 0, "E");
	private final static AbstractStackNode NONTERMINAL_E43 = new NonTerminalStackNode(43, 2, "E");
	private final static AbstractStackNode NONTERMINAL_E44 = new NonTerminalStackNode(44, 0, "E");
	private final static AbstractStackNode NONTERMINAL_E45 = new NonTerminalStackNode(45, 2, "E");
	private final static AbstractStackNode NONTERMINAL_E46 = new NonTerminalStackNode(46, 0, "E");
	private final static AbstractStackNode NONTERMINAL_E47 = new NonTerminalStackNode(47, 2, "E");
	private final static AbstractStackNode NONTERMINAL_E48 = new NonTerminalStackNode(48, 0, "E");
	private final static AbstractStackNode NONTERMINAL_E49 = new NonTerminalStackNode(49, 2, "E");
	private final static AbstractStackNode NONTERMINAL_E50 = new NonTerminalStackNode(50, 0, "E");
	private final static AbstractStackNode NONTERMINAL_E51 = new NonTerminalStackNode(51, 2, "E");
	private final static AbstractStackNode NONTERMINAL_E52 = new NonTerminalStackNode(52, 0, "E");
	private final static AbstractStackNode NONTERMINAL_E53 = new NonTerminalStackNode(53, 2, "E");
	private final static AbstractStackNode NONTERMINAL_E54 = new NonTerminalStackNode(54, 0, "E");
	private final static AbstractStackNode NONTERMINAL_E55 = new NonTerminalStackNode(55, 2, "E");
	private final static AbstractStackNode NONTERMINAL_E56 = new NonTerminalStackNode(56, 0, "E");
	private final static AbstractStackNode NONTERMINAL_E57 = new NonTerminalStackNode(57, 2, "E");
	private final static AbstractStackNode NONTERMINAL_E58 = new NonTerminalStackNode(58, 0, "E");
	private final static AbstractStackNode NONTERMINAL_E59 = new NonTerminalStackNode(59, 2, "E");
	private final static AbstractStackNode NONTERMINAL_E10000 = new NonTerminalStackNode(10000, 0, "E");
	
	private final static AbstractStackNode LITERAL_0 = new LiteralStackNode(100, 1, "@".toCharArray());
	private final static AbstractStackNode LITERAL_1 = new LiteralStackNode(101, 1, "-".toCharArray());
	private final static AbstractStackNode LITERAL_2 = new LiteralStackNode(102, 1, "_".toCharArray());
	private final static AbstractStackNode LITERAL_3 = new LiteralStackNode(103, 1, "+".toCharArray());
	private final static AbstractStackNode LITERAL_4 = new LiteralStackNode(104, 1, "=".toCharArray());
	private final static AbstractStackNode LITERAL_5 = new LiteralStackNode(105, 1, "[".toCharArray());
	private final static AbstractStackNode LITERAL_6 = new LiteralStackNode(106, 1, "]".toCharArray());
	private final static AbstractStackNode LITERAL_7 = new LiteralStackNode(107, 1, "|".toCharArray());
	private final static AbstractStackNode LITERAL_8 = new LiteralStackNode(108, 1, "\\".toCharArray());
	private final static AbstractStackNode LITERAL_9 = new LiteralStackNode(109, 1, "'".toCharArray());
	private final static AbstractStackNode LITERAL_10 = new LiteralStackNode(110, 1, "\"".toCharArray());
	private final static AbstractStackNode LITERAL_11 = new LiteralStackNode(111, 1, ";".toCharArray());
	private final static AbstractStackNode LITERAL_12 = new LiteralStackNode(112, 1, ":".toCharArray());
	private final static AbstractStackNode LITERAL_13 = new LiteralStackNode(113, 1, "?".toCharArray());
	private final static AbstractStackNode LITERAL_14 = new LiteralStackNode(114, 1, "/".toCharArray());
	private final static AbstractStackNode LITERAL_15 = new LiteralStackNode(115, 1, ".".toCharArray());
	private final static AbstractStackNode LITERAL_16 = new LiteralStackNode(116, 1, ">".toCharArray());
	private final static AbstractStackNode LITERAL_17 = new LiteralStackNode(117, 1, "<".toCharArray());
	private final static AbstractStackNode LITERAL_18 = new LiteralStackNode(118, 1, ",".toCharArray());
	private final static AbstractStackNode LITERAL_19 = new LiteralStackNode(119, 1, "*".toCharArray());
	private final static AbstractStackNode LITERAL_20 = new LiteralStackNode(120, 1, "`".toCharArray());
	private final static AbstractStackNode LITERAL_21 = new LiteralStackNode(121, 1, "~".toCharArray());
	private final static AbstractStackNode LITERAL_22 = new LiteralStackNode(122, 1, "!".toCharArray());
	private final static AbstractStackNode LITERAL_23 = new LiteralStackNode(123, 1, "(".toCharArray());
	private final static AbstractStackNode LITERAL_24 = new LiteralStackNode(124, 1, ")".toCharArray());
	private final static AbstractStackNode LITERAL_25 = new LiteralStackNode(125, 1, "&".toCharArray());
	private final static AbstractStackNode LITERAL_26 = new LiteralStackNode(126, 1, "^".toCharArray());
	private final static AbstractStackNode LITERAL_27 = new LiteralStackNode(127, 1, "%".toCharArray());
	private final static AbstractStackNode LITERAL_28 = new LiteralStackNode(128, 1, "$".toCharArray());
	private final static AbstractStackNode LITERAL_29 = new LiteralStackNode(129, 1, "#".toCharArray());
	private final static AbstractStackNode LITERAL_1000 = new LiteralStackNode(1000, 0, "1".toCharArray());
	
	private final static AbstractStackNode NONTERMINAL_A999 = new NonTerminalStackNode(999, 0, "A");
	private final static AbstractStackNode LIST9999 = new ListStackNode(9999, 0, NONTERMINAL_A999, "A+", true);
	private final static AbstractStackNode LITERAL_a99999 = new LiteralStackNode(99999, 0, new char[]{'a'});
	
	private NonLeftFactoredNonShared(char[] input){
		super(input);
	}
	
	public void S(){
		expect(LIST9999);
	}
	
	public void A(){
		expect(NONTERMINAL_E10000);
		expect(LITERAL_a99999);
	}
	
	public void E(){
		expect(NONTERMINAL_E0, LITERAL_0, NONTERMINAL_E1);
		expect(NONTERMINAL_E2, LITERAL_1, NONTERMINAL_E3);
		expect(NONTERMINAL_E4, LITERAL_2, NONTERMINAL_E5);
		expect(NONTERMINAL_E6, LITERAL_3, NONTERMINAL_E7);
		expect(NONTERMINAL_E8, LITERAL_4, NONTERMINAL_E9);
		expect(NONTERMINAL_E10, LITERAL_5, NONTERMINAL_E11);
		expect(NONTERMINAL_E12, LITERAL_6, NONTERMINAL_E13);
		expect(NONTERMINAL_E14, LITERAL_7, NONTERMINAL_E15);
		expect(NONTERMINAL_E16, LITERAL_8, NONTERMINAL_E17);
		expect(NONTERMINAL_E18, LITERAL_9, NONTERMINAL_E19);
		expect(NONTERMINAL_E20, LITERAL_10, NONTERMINAL_E21);
		expect(NONTERMINAL_E22, LITERAL_11, NONTERMINAL_E23);
		expect(NONTERMINAL_E24, LITERAL_12, NONTERMINAL_E25);
		expect(NONTERMINAL_E26, LITERAL_13, NONTERMINAL_E27);
		expect(NONTERMINAL_E28, LITERAL_14, NONTERMINAL_E29);
		expect(NONTERMINAL_E30, LITERAL_15, NONTERMINAL_E31);
		expect(NONTERMINAL_E32, LITERAL_16, NONTERMINAL_E33);
		expect(NONTERMINAL_E34, LITERAL_17, NONTERMINAL_E35);
		expect(NONTERMINAL_E36, LITERAL_18, NONTERMINAL_E37);
		expect(NONTERMINAL_E38, LITERAL_19, NONTERMINAL_E39);
		expect(NONTERMINAL_E40, LITERAL_20, NONTERMINAL_E41);
		expect(NONTERMINAL_E42, LITERAL_21, NONTERMINAL_E43);
		expect(NONTERMINAL_E44, LITERAL_22, NONTERMINAL_E45);
		expect(NONTERMINAL_E46, LITERAL_23, NONTERMINAL_E47);
		expect(NONTERMINAL_E48, LITERAL_24, NONTERMINAL_E49);
		expect(NONTERMINAL_E50, LITERAL_25, NONTERMINAL_E51);
		expect(NONTERMINAL_E52, LITERAL_26, NONTERMINAL_E53);
		expect(NONTERMINAL_E54, LITERAL_27, NONTERMINAL_E55);
		expect(NONTERMINAL_E56, LITERAL_28, NONTERMINAL_E57);
		expect(NONTERMINAL_E58, LITERAL_29, NONTERMINAL_E59);
		
		expect(LITERAL_1000);
	}
	
	private final static int ITERATIONS = 3;
	
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
			NonLeftFactoredNonShared nlfns = new NonLeftFactoredNonShared(input);
			nlfns.parse("S");
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
			NonLeftFactoredNonShared nlfns = new NonLeftFactoredNonShared(input);
			nlfns.parse("S");
		}
		
		// The benchmarks.
		for(int i = 50000; i <= 200000; i += 50000){
			input = createInput(i);
			runTest(input);
		}
	}
}
