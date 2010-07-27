package gll;

import gll.result.AbstractNode;

public interface IGLL{
	public final static int START_SYMBOL_ID = -1; // 0xffffffff
	
	public final static int DEFAULT_LIST_EPSILON_ID = -2; // (0xeffffffe | 0x80000000)
	
	
	AbstractNode parse(String start);
}
