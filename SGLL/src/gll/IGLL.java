package gll;

import gll.result.INode;

public interface IGLL{
	public final static int START_SYMBOL_ID = -1; // 0xffffffff
	
	public final static int LIST_LIST_FLAG = 0x80000000;
	public final static int DEFAULT_LIST_EPSILON_ID = -2; // (0xeffffffe | 0x80000000)
	
	
	INode parse(String start);
}
