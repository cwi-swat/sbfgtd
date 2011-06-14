package gtd.bench;

import gtd.SGTDBF;
import gtd.stack.AbstractStackNode;
import gtd.stack.CharStackNode;
import gtd.stack.ListStackNode;
import gtd.stack.NonTerminalStackNode;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;

public class LeftFactored extends SGTDBF{
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
	private final static AbstractStackNode LITERAL_10 = new CharStackNode(110, 0, '\'');
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
	private final static AbstractStackNode[] Ep_0E = new AbstractStackNode[]{LITERAL_0, NONTERMINAL_E1};
	private final static AbstractStackNode[] Ep_1E = new AbstractStackNode[]{LITERAL_1, NONTERMINAL_E3};
	private final static AbstractStackNode[] Ep_2E = new AbstractStackNode[]{LITERAL_2, NONTERMINAL_E5};
	private final static AbstractStackNode[] Ep_3E = new AbstractStackNode[]{LITERAL_3, NONTERMINAL_E7};
	private final static AbstractStackNode[] Ep_4E = new AbstractStackNode[]{LITERAL_4, NONTERMINAL_E9};
	private final static AbstractStackNode[] Ep_5E = new AbstractStackNode[]{LITERAL_5, NONTERMINAL_E11};
	private final static AbstractStackNode[] Ep_6E = new AbstractStackNode[]{LITERAL_6, NONTERMINAL_E13};
	private final static AbstractStackNode[] Ep_7E = new AbstractStackNode[]{LITERAL_7, NONTERMINAL_E15};
	private final static AbstractStackNode[] Ep_8E = new AbstractStackNode[]{LITERAL_8, NONTERMINAL_E17};
	private final static AbstractStackNode[] Ep_9E = new AbstractStackNode[]{LITERAL_9, NONTERMINAL_E19};
	private final static AbstractStackNode[] Ep_10E = new AbstractStackNode[]{LITERAL_10, NONTERMINAL_E21};
	private final static AbstractStackNode[] Ep_11E = new AbstractStackNode[]{LITERAL_11, NONTERMINAL_E23};
	private final static AbstractStackNode[] Ep_12E = new AbstractStackNode[]{LITERAL_12, NONTERMINAL_E25};
	private final static AbstractStackNode[] Ep_13E = new AbstractStackNode[]{LITERAL_13, NONTERMINAL_E27};
	private final static AbstractStackNode[] Ep_14E = new AbstractStackNode[]{LITERAL_14, NONTERMINAL_E29};
	private final static AbstractStackNode[] Ep_15E = new AbstractStackNode[]{LITERAL_15, NONTERMINAL_E31};
	private final static AbstractStackNode[] Ep_16E = new AbstractStackNode[]{LITERAL_16, NONTERMINAL_E33};
	private final static AbstractStackNode[] Ep_17E = new AbstractStackNode[]{LITERAL_17, NONTERMINAL_E35};
	private final static AbstractStackNode[] Ep_18E = new AbstractStackNode[]{LITERAL_18, NONTERMINAL_E37};
	private final static AbstractStackNode[] Ep_19E = new AbstractStackNode[]{LITERAL_19, NONTERMINAL_E39};
	private final static AbstractStackNode[] Ep_20E = new AbstractStackNode[]{LITERAL_20, NONTERMINAL_E41};
	private final static AbstractStackNode[] Ep_21E = new AbstractStackNode[]{LITERAL_21, NONTERMINAL_E43};
	private final static AbstractStackNode[] Ep_22E = new AbstractStackNode[]{LITERAL_22, NONTERMINAL_E45};
	private final static AbstractStackNode[] Ep_23E = new AbstractStackNode[]{LITERAL_23, NONTERMINAL_E47};
	private final static AbstractStackNode[] Ep_24E = new AbstractStackNode[]{LITERAL_24, NONTERMINAL_E49};
	private final static AbstractStackNode[] Ep_25E = new AbstractStackNode[]{LITERAL_25, NONTERMINAL_E51};
	private final static AbstractStackNode[] Ep_26E = new AbstractStackNode[]{LITERAL_26, NONTERMINAL_E53};
	private final static AbstractStackNode[] Ep_27E = new AbstractStackNode[]{LITERAL_27, NONTERMINAL_E55};
	private final static AbstractStackNode[] Ep_28E = new AbstractStackNode[]{LITERAL_28, NONTERMINAL_E57};
	private final static AbstractStackNode[] Ep_29E = new AbstractStackNode[]{LITERAL_29, NONTERMINAL_E59};

	private final static AbstractStackNode NONTERMINAL_E0 = new NonTerminalStackNode(0, 0, "E");
	private final static AbstractStackNode NONTERMINAL_Ep100000 = new NonTerminalStackNode(100000, 1, "Ep");
	private final static AbstractStackNode[] E_EEp = new AbstractStackNode[]{NONTERMINAL_E0, NONTERMINAL_Ep100000};

	private final static AbstractStackNode NONTERMINAL_E10000 = new NonTerminalStackNode(10000, 0, "E");
	private final static AbstractStackNode LIST9999 = new ListStackNode(9999, 0, NONTERMINAL_E10000, "E+", true);
	private final static AbstractStackNode[] LST9999 = new AbstractStackNode[]{LIST9999};
	
	private final static AbstractStackNode LITERAL_1000 = new CharStackNode(1000, 0, '1');
	private final static AbstractStackNode[] L1000 = new AbstractStackNode[]{LITERAL_1000};
	
	private LeftFactored(char[] input){
		super(input);
	}
	
	public void S(){
		expect(LST9999);
	}
	
	public void E(){
		expect(E_EEp);
		
		expect(L1000);
	}
	
	public void Ep(){
		expect(Ep_0E);
		expect(Ep_1E);
		expect(Ep_2E);
		expect(Ep_3E);
		expect(Ep_4E);
		expect(Ep_5E);
		expect(Ep_6E);
		expect(Ep_7E);
		expect(Ep_8E);
		expect(Ep_9E);
		expect(Ep_10E);
		expect(Ep_11E);
		expect(Ep_12E);
		expect(Ep_13E);
		expect(Ep_14E);
		expect(Ep_15E);
		expect(Ep_16E);
		expect(Ep_17E);
		expect(Ep_18E);
		expect(Ep_19E);
		expect(Ep_20E);
		expect(Ep_21E);
		expect(Ep_22E);
		expect(Ep_23E);
		expect(Ep_24E);
		expect(Ep_25E);
		expect(Ep_26E);
		expect(Ep_27E);
		expect(Ep_28E);
		expect(Ep_29E);
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
