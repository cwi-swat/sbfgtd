package gtd.bench;

import gtd.SGTDBF;
import gtd.preprocessing.ExpectBuilder;
import gtd.stack.AbstractStackNode;
import gtd.stack.CharStackNode;
import gtd.stack.ListStackNode;
import gtd.stack.NonTerminalStackNode;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;

public class NonLeftFactoredShared extends SGTDBF{
	private final static AbstractStackNode NONTERMINAL_E0 = new NonTerminalStackNode(0, 0, "E");
	private final static AbstractStackNode NONTERMINAL_E1 = new NonTerminalStackNode(1, 2, "E");
	private final static AbstractStackNode NONTERMINAL_E3 = new NonTerminalStackNode(3, 2, "E");
	private final static AbstractStackNode NONTERMINAL_E5 = new NonTerminalStackNode(5, 2, "E");
	private final static AbstractStackNode NONTERMINAL_E7 = new NonTerminalStackNode(7, 2, "E");
	private final static AbstractStackNode NONTERMINAL_E9 = new NonTerminalStackNode(9, 2, "E");
	private final static AbstractStackNode NONTERMINAL_E11 = new NonTerminalStackNode(11, 2, "E");
	private final static AbstractStackNode NONTERMINAL_E13 = new NonTerminalStackNode(13, 2, "E");
	private final static AbstractStackNode NONTERMINAL_E15 = new NonTerminalStackNode(15, 2, "E");
	private final static AbstractStackNode NONTERMINAL_E17 = new NonTerminalStackNode(17, 2, "E");
	private final static AbstractStackNode NONTERMINAL_E19 = new NonTerminalStackNode(19, 2, "E");
	private final static AbstractStackNode NONTERMINAL_E21 = new NonTerminalStackNode(21, 2, "E");
	private final static AbstractStackNode NONTERMINAL_E23 = new NonTerminalStackNode(23, 2, "E");
	private final static AbstractStackNode NONTERMINAL_E25 = new NonTerminalStackNode(25, 2, "E");
	private final static AbstractStackNode NONTERMINAL_E27 = new NonTerminalStackNode(27, 2, "E");
	private final static AbstractStackNode NONTERMINAL_E29 = new NonTerminalStackNode(29, 2, "E");
	private final static AbstractStackNode NONTERMINAL_E31 = new NonTerminalStackNode(31, 2, "E");
	private final static AbstractStackNode NONTERMINAL_E33 = new NonTerminalStackNode(33, 2, "E");
	private final static AbstractStackNode NONTERMINAL_E35 = new NonTerminalStackNode(35, 2, "E");
	private final static AbstractStackNode NONTERMINAL_E37 = new NonTerminalStackNode(37, 2, "E");
	private final static AbstractStackNode NONTERMINAL_E39 = new NonTerminalStackNode(39, 2, "E");
	private final static AbstractStackNode NONTERMINAL_E41 = new NonTerminalStackNode(41, 2, "E");
	private final static AbstractStackNode NONTERMINAL_E43 = new NonTerminalStackNode(43, 2, "E");
	private final static AbstractStackNode NONTERMINAL_E45 = new NonTerminalStackNode(45, 2, "E");
	private final static AbstractStackNode NONTERMINAL_E47 = new NonTerminalStackNode(47, 2, "E");
	private final static AbstractStackNode NONTERMINAL_E49 = new NonTerminalStackNode(49, 2, "E");
	private final static AbstractStackNode NONTERMINAL_E51 = new NonTerminalStackNode(51, 2, "E");
	private final static AbstractStackNode NONTERMINAL_E53 = new NonTerminalStackNode(53, 2, "E");
	private final static AbstractStackNode NONTERMINAL_E55 = new NonTerminalStackNode(55, 2, "E");
	private final static AbstractStackNode NONTERMINAL_E57 = new NonTerminalStackNode(57, 2, "E");
	private final static AbstractStackNode NONTERMINAL_E59 = new NonTerminalStackNode(59, 2, "E");
	private final static AbstractStackNode LITERAL_0 = new CharStackNode(100, 0, '@');
	private final static AbstractStackNode LITERAL_1 = new CharStackNode(101, 0, '-');
	private final static AbstractStackNode LITERAL_2 = new CharStackNode(102, 0, '_');
	private final static AbstractStackNode LITERAL_3 = new CharStackNode(103, 0, '+');
	private final static AbstractStackNode LITERAL_4 = new CharStackNode(104, 0, '=');
	private final static AbstractStackNode LITERAL_5 = new CharStackNode(105, 0, '[');
	private final static AbstractStackNode LITERAL_6 = new CharStackNode(106, 0, ']');
	private final static AbstractStackNode LITERAL_7 = new CharStackNode(107, 0, '|');
	private final static AbstractStackNode LITERAL_8 = new CharStackNode(108, 0, '\\');
	private final static AbstractStackNode LITERAL_9 = new CharStackNode(109, 0, '\'');
	private final static AbstractStackNode LITERAL_10 = new CharStackNode(110, 0, '\"');
	private final static AbstractStackNode LITERAL_11 = new CharStackNode(111, 0, ';');
	private final static AbstractStackNode LITERAL_12 = new CharStackNode(112, 0, ':');
	private final static AbstractStackNode LITERAL_13 = new CharStackNode(113, 0, '?');
	private final static AbstractStackNode LITERAL_14 = new CharStackNode(114, 0, '/');
	private final static AbstractStackNode LITERAL_15 = new CharStackNode(115, 0, '.');
	private final static AbstractStackNode LITERAL_16 = new CharStackNode(116, 0, '>');
	private final static AbstractStackNode LITERAL_17 = new CharStackNode(117, 0, '<');
	private final static AbstractStackNode LITERAL_18 = new CharStackNode(118, 0, ',');
	private final static AbstractStackNode LITERAL_19 = new CharStackNode(119, 0, '*');
	private final static AbstractStackNode LITERAL_20 = new CharStackNode(120, 0, '`');
	private final static AbstractStackNode LITERAL_21 = new CharStackNode(121, 0, '~');
	private final static AbstractStackNode LITERAL_22 = new CharStackNode(122, 0, '!');
	private final static AbstractStackNode LITERAL_23 = new CharStackNode(123, 0, '(');
	private final static AbstractStackNode LITERAL_24 = new CharStackNode(124, 0, ')');
	private final static AbstractStackNode LITERAL_25 = new CharStackNode(125, 0, '&');
	private final static AbstractStackNode LITERAL_26 = new CharStackNode(126, 0, '^');
	private final static AbstractStackNode LITERAL_27 = new CharStackNode(127, 0, '%');
	private final static AbstractStackNode LITERAL_28 = new CharStackNode(128, 0, '$');
	private final static AbstractStackNode LITERAL_29 = new CharStackNode(129, 0, '#');
	
	private final static AbstractStackNode LITERAL_1000 = new CharStackNode(1000, 0, '1');
	
	private final static AbstractStackNode NONTERMINAL_E10000 = new NonTerminalStackNode(10000, 0, "E");
	private final static AbstractStackNode LIST9999 = new ListStackNode(9999, 0, NONTERMINAL_E10000, "E+", true);
	
	private NonLeftFactoredShared(char[] input){
		super(input);
	}
	
	private final static AbstractStackNode[] SMatrix;
	static{
		ExpectBuilder eb = new ExpectBuilder();
		
		eb.addAlternative(LIST9999);
		
		SMatrix = eb.buildExpectMatrix();
	}
	
	public AbstractStackNode[] S(){
		return SMatrix;
	}
	
	private final static AbstractStackNode[] EMatrix;
	static{
		ExpectBuilder eb = new ExpectBuilder();
		
		eb.addAlternative(NONTERMINAL_E0, LITERAL_0, NONTERMINAL_E1);
		eb.addAlternative(NONTERMINAL_E0, LITERAL_1, NONTERMINAL_E3);
		eb.addAlternative(NONTERMINAL_E0, LITERAL_2, NONTERMINAL_E5);
		eb.addAlternative(NONTERMINAL_E0, LITERAL_3, NONTERMINAL_E7);
		eb.addAlternative(NONTERMINAL_E0, LITERAL_4, NONTERMINAL_E9);
		eb.addAlternative(NONTERMINAL_E0, LITERAL_5, NONTERMINAL_E11);
		eb.addAlternative(NONTERMINAL_E0, LITERAL_6, NONTERMINAL_E13);
		eb.addAlternative(NONTERMINAL_E0, LITERAL_7, NONTERMINAL_E15);
		eb.addAlternative(NONTERMINAL_E0, LITERAL_8, NONTERMINAL_E17);
		eb.addAlternative(NONTERMINAL_E0, LITERAL_9, NONTERMINAL_E19);
		eb.addAlternative(NONTERMINAL_E0, LITERAL_10, NONTERMINAL_E21);
		eb.addAlternative(NONTERMINAL_E0, LITERAL_11, NONTERMINAL_E23);
		eb.addAlternative(NONTERMINAL_E0, LITERAL_12, NONTERMINAL_E25);
		eb.addAlternative(NONTERMINAL_E0, LITERAL_13, NONTERMINAL_E27);
		eb.addAlternative(NONTERMINAL_E0, LITERAL_14, NONTERMINAL_E29);
		eb.addAlternative(NONTERMINAL_E0, LITERAL_15, NONTERMINAL_E31);
		eb.addAlternative(NONTERMINAL_E0, LITERAL_16, NONTERMINAL_E33);
		eb.addAlternative(NONTERMINAL_E0, LITERAL_17, NONTERMINAL_E35);
		eb.addAlternative(NONTERMINAL_E0, LITERAL_18, NONTERMINAL_E37);
		eb.addAlternative(NONTERMINAL_E0, LITERAL_19, NONTERMINAL_E39);
		eb.addAlternative(NONTERMINAL_E0, LITERAL_20, NONTERMINAL_E41);
		eb.addAlternative(NONTERMINAL_E0, LITERAL_21, NONTERMINAL_E43);
		eb.addAlternative(NONTERMINAL_E0, LITERAL_22, NONTERMINAL_E45);
		eb.addAlternative(NONTERMINAL_E0, LITERAL_23, NONTERMINAL_E47);
		eb.addAlternative(NONTERMINAL_E0, LITERAL_24, NONTERMINAL_E49);
		eb.addAlternative(NONTERMINAL_E0, LITERAL_25, NONTERMINAL_E51);
		eb.addAlternative(NONTERMINAL_E0, LITERAL_26, NONTERMINAL_E53);
		eb.addAlternative(NONTERMINAL_E0, LITERAL_27, NONTERMINAL_E55);
		eb.addAlternative(NONTERMINAL_E0, LITERAL_28, NONTERMINAL_E57);
		eb.addAlternative(NONTERMINAL_E0, LITERAL_29, NONTERMINAL_E59);
		eb.addAlternative(LITERAL_1000);
		
		EMatrix = eb.buildExpectMatrix();
	}
	
	public AbstractStackNode[] E(){
		return EMatrix;
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
			NonLeftFactoredShared nlfs = new NonLeftFactoredShared(input);
			nlfs.parse("S");
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
			NonLeftFactoredShared nlfs = new NonLeftFactoredShared(input);
			nlfs.parse("S");
		}
		
		// The benchmarks.
		for(int i = 50000; i <= 200000; i += 50000){
			input = createInput(i);
			runTest(input);
		}
	}
}
