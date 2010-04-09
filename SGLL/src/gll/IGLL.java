package gll;

import gll.result.INode;

public interface IGLL{
	public final static int START_SYMBOL_ID = -1;
	public final static int LIST_CHILD_NOT_FOUND_ID = -2;
	
	public final static int LIST_CHILD_FLAG = 0x80000000;
	public final static int LIST_NEXT_FLAG = 0x40000000;
	public final static int LIST_LIST_FLAG = 0x20000000;
	
	INode parse(String start);
}
