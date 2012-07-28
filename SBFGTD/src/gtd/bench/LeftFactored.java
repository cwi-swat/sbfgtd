package gtd.bench;

import gtd.SGTDBF;
import gtd.preprocessing.ExpectBuilder;
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
	
	private final static AbstractStackNode NONTERMINAL_Epp100000 = new NonTerminalStackNode(100000, 0, "Epp");
	private final static AbstractStackNode NONTERMINAL_Ep1000000 = new NonTerminalStackNode(1000000, 1, "Ep");
	
	private final static AbstractStackNode NONTERMINAL_E10000 = new NonTerminalStackNode(10000, 0, "E");
	private final static AbstractStackNode LIST9999 = new ListStackNode(9999, 0, NONTERMINAL_E10000, "E+", true);
	
	private final static AbstractStackNode LITERAL_1000 = new CharStackNode(1000, 0, '1');
	private final static AbstractStackNode LITERAL_10000000 = new CharStackNode(10000000, 0, '1');
	
	private LeftFactored(char[] input){
		super(input);
	}
	
	private final static AbstractStackNode[] S1Matrix;
	static{
		ExpectBuilder eb = new ExpectBuilder();
		
		eb.addAlternative(LIST9999);
		
		S1Matrix = eb.buildExpectMatrix();
	}
	
	public AbstractStackNode[] S(){
		if(location + 1 <= input.length && input[location] == '1') return S1Matrix;
		return null;
	}
	
	private final static AbstractStackNode[] E1Matrix;
	static{
		ExpectBuilder eb = new ExpectBuilder();
		
		eb.addAlternative(NONTERMINAL_Epp100000, NONTERMINAL_Ep1000000);
		eb.addAlternative(LITERAL_10000000);
		
		E1Matrix = eb.buildExpectMatrix();
	}
	
	public AbstractStackNode[] E(){
		if(location + 1 <= input.length){
			if(input[location] == '1') return E1Matrix;
		}
		return null;
	}
	
	private final static AbstractStackNode[] Ep0Matrix;
	private final static AbstractStackNode[] Ep1Matrix;
	private final static AbstractStackNode[] Ep2Matrix;
	private final static AbstractStackNode[] Ep3Matrix;
	private final static AbstractStackNode[] Ep4Matrix;
	private final static AbstractStackNode[] Ep5Matrix;
	private final static AbstractStackNode[] Ep6Matrix;
	private final static AbstractStackNode[] Ep7Matrix;
	private final static AbstractStackNode[] Ep8Matrix;
	private final static AbstractStackNode[] Ep9Matrix;
	private final static AbstractStackNode[] Ep10Matrix;
	private final static AbstractStackNode[] Ep11Matrix;
	private final static AbstractStackNode[] Ep12Matrix;
	private final static AbstractStackNode[] Ep13Matrix;
	private final static AbstractStackNode[] Ep14Matrix;
	private final static AbstractStackNode[] Ep15Matrix;
	private final static AbstractStackNode[] Ep16Matrix;
	private final static AbstractStackNode[] Ep17Matrix;
	private final static AbstractStackNode[] Ep18Matrix;
	private final static AbstractStackNode[] Ep19Matrix;
	private final static AbstractStackNode[] Ep20Matrix;
	private final static AbstractStackNode[] Ep21Matrix;
	private final static AbstractStackNode[] Ep22Matrix;
	private final static AbstractStackNode[] Ep23Matrix;
	private final static AbstractStackNode[] Ep24Matrix;
	private final static AbstractStackNode[] Ep25Matrix;
	private final static AbstractStackNode[] Ep26Matrix;
	private final static AbstractStackNode[] Ep27Matrix;
	private final static AbstractStackNode[] Ep28Matrix;
	private final static AbstractStackNode[] Ep29Matrix;
	static{
		ExpectBuilder eb0 = new ExpectBuilder();
		eb0.addAlternative(LITERAL_0, NONTERMINAL_E1);
		Ep0Matrix = eb0.buildExpectMatrix();

		ExpectBuilder eb1 = new ExpectBuilder();
		eb1.addAlternative(LITERAL_1, NONTERMINAL_E3);
		Ep1Matrix = eb1.buildExpectMatrix();

		ExpectBuilder eb2 = new ExpectBuilder();
		eb2.addAlternative(LITERAL_2, NONTERMINAL_E5);
		Ep2Matrix = eb2.buildExpectMatrix();

		ExpectBuilder eb3 = new ExpectBuilder();
		eb3.addAlternative(LITERAL_3, NONTERMINAL_E7);
		Ep3Matrix = eb3.buildExpectMatrix();

		ExpectBuilder eb4 = new ExpectBuilder();
		eb4.addAlternative(LITERAL_4, NONTERMINAL_E9);
		Ep4Matrix = eb4.buildExpectMatrix();

		ExpectBuilder eb5 = new ExpectBuilder();
		eb5.addAlternative(LITERAL_5, NONTERMINAL_E11);
		Ep5Matrix = eb5.buildExpectMatrix();

		ExpectBuilder eb6 = new ExpectBuilder();
		eb6.addAlternative(LITERAL_6, NONTERMINAL_E13);
		Ep6Matrix = eb6.buildExpectMatrix();

		ExpectBuilder eb7 = new ExpectBuilder();
		eb7.addAlternative(LITERAL_7, NONTERMINAL_E15);
		Ep7Matrix = eb7.buildExpectMatrix();

		ExpectBuilder eb8 = new ExpectBuilder();
		eb8.addAlternative(LITERAL_8, NONTERMINAL_E17);
		Ep8Matrix = eb8.buildExpectMatrix();

		ExpectBuilder eb9 = new ExpectBuilder();
		eb9.addAlternative(LITERAL_9, NONTERMINAL_E19);
		Ep9Matrix = eb9.buildExpectMatrix();

		ExpectBuilder eb10 = new ExpectBuilder();
		eb10.addAlternative(LITERAL_10, NONTERMINAL_E21);
		Ep10Matrix = eb10.buildExpectMatrix();

		ExpectBuilder eb11 = new ExpectBuilder();
		eb11.addAlternative(LITERAL_11, NONTERMINAL_E23);
		Ep11Matrix = eb11.buildExpectMatrix();

		ExpectBuilder eb12 = new ExpectBuilder();
		eb12.addAlternative(LITERAL_12, NONTERMINAL_E25);
		Ep12Matrix = eb12.buildExpectMatrix();

		ExpectBuilder eb13 = new ExpectBuilder();
		eb13.addAlternative(LITERAL_13, NONTERMINAL_E27);
		Ep13Matrix = eb13.buildExpectMatrix();

		ExpectBuilder eb14 = new ExpectBuilder();
		eb14.addAlternative(LITERAL_14, NONTERMINAL_E29);
		Ep14Matrix = eb14.buildExpectMatrix();

		ExpectBuilder eb15 = new ExpectBuilder();
		eb15.addAlternative(LITERAL_15, NONTERMINAL_E31);
		Ep15Matrix = eb15.buildExpectMatrix();

		ExpectBuilder eb16 = new ExpectBuilder();
		eb16.addAlternative(LITERAL_16, NONTERMINAL_E33);
		Ep16Matrix = eb16.buildExpectMatrix();

		ExpectBuilder eb17 = new ExpectBuilder();
		eb17.addAlternative(LITERAL_17, NONTERMINAL_E35);
		Ep17Matrix = eb17.buildExpectMatrix();

		ExpectBuilder eb18 = new ExpectBuilder();
		eb18.addAlternative(LITERAL_18, NONTERMINAL_E37);
		Ep18Matrix = eb18.buildExpectMatrix();

		ExpectBuilder eb19 = new ExpectBuilder();
		eb19.addAlternative(LITERAL_19, NONTERMINAL_E39);
		Ep19Matrix = eb19.buildExpectMatrix();

		ExpectBuilder eb20 = new ExpectBuilder();
		eb20.addAlternative(LITERAL_20, NONTERMINAL_E41);
		Ep20Matrix = eb20.buildExpectMatrix();

		ExpectBuilder eb21 = new ExpectBuilder();
		eb21.addAlternative(LITERAL_21, NONTERMINAL_E43);
		Ep21Matrix = eb21.buildExpectMatrix();

		ExpectBuilder eb22 = new ExpectBuilder();
		eb22.addAlternative(LITERAL_22, NONTERMINAL_E45);
		Ep22Matrix = eb22.buildExpectMatrix();

		ExpectBuilder eb23 = new ExpectBuilder();
		eb23.addAlternative(LITERAL_23, NONTERMINAL_E47);
		Ep23Matrix = eb23.buildExpectMatrix();

		ExpectBuilder eb24 = new ExpectBuilder();
		eb24.addAlternative(LITERAL_24, NONTERMINAL_E49);
		Ep24Matrix = eb24.buildExpectMatrix();

		ExpectBuilder eb25 = new ExpectBuilder();
		eb25.addAlternative(LITERAL_25, NONTERMINAL_E51);
		Ep25Matrix = eb25.buildExpectMatrix();

		ExpectBuilder eb26 = new ExpectBuilder();
		eb26.addAlternative(LITERAL_26, NONTERMINAL_E53);
		Ep26Matrix = eb26.buildExpectMatrix();

		ExpectBuilder eb27 = new ExpectBuilder();
		eb27.addAlternative(LITERAL_27, NONTERMINAL_E55);
		Ep27Matrix = eb27.buildExpectMatrix();

		ExpectBuilder eb28 = new ExpectBuilder();
		eb28.addAlternative(LITERAL_28, NONTERMINAL_E57);
		Ep28Matrix = eb8.buildExpectMatrix();

		ExpectBuilder eb29 = new ExpectBuilder();
		eb29.addAlternative(LITERAL_29, NONTERMINAL_E59);
		Ep29Matrix = eb29.buildExpectMatrix();
	}
	
	public AbstractStackNode[] Ep(){
		if(location + 1 <= input.length){
			if(input[location] == '@') return Ep0Matrix;
			else if(input[location] == '-') return Ep1Matrix;
			else if(input[location] == '_') return Ep2Matrix;
			else if(input[location] == '+') return Ep3Matrix;
			else if(input[location] == '=') return Ep4Matrix;
			else if(input[location] == '[') return Ep5Matrix;
			else if(input[location] == ']') return Ep6Matrix;
			else if(input[location] == '|') return Ep7Matrix;
			else if(input[location] == '\\') return Ep8Matrix;
			else if(input[location] == '\'') return Ep9Matrix;
			else if(input[location] == '\"') return Ep10Matrix;
			else if(input[location] == ';') return Ep11Matrix;
			else if(input[location] == ':') return Ep12Matrix;
			else if(input[location] == '?') return Ep13Matrix;
			else if(input[location] == '/') return Ep14Matrix;
			else if(input[location] == '.') return Ep15Matrix;
			else if(input[location] == '>') return Ep16Matrix;
			else if(input[location] == '<') return Ep17Matrix;
			else if(input[location] == ',') return Ep18Matrix;
			else if(input[location] == '*') return Ep19Matrix;
			else if(input[location] == '`') return Ep20Matrix;
			else if(input[location] == '~') return Ep21Matrix;
			else if(input[location] == '!') return Ep22Matrix;
			else if(input[location] == '(') return Ep23Matrix;
			else if(input[location] == ')') return Ep24Matrix;
			else if(input[location] == '&') return Ep25Matrix;
			else if(input[location] == '^') return Ep26Matrix;
			else if(input[location] == '%') return Ep27Matrix;
			else if(input[location] == '$') return Ep28Matrix;
			else if(input[location] == '#') return Ep29Matrix;
		}
		return null;
	}
	
	private final static AbstractStackNode[] Epp1Matrix;
	static{
		ExpectBuilder eb = new ExpectBuilder();
		
		eb.addAlternative(LITERAL_1000);
		
		Epp1Matrix = eb.buildExpectMatrix();
	}
	
	public AbstractStackNode[] Epp(){
		if(location + 1 <= input.length){
			if(input[location] == '1') return Epp1Matrix;
		}
		return null;
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
