package gll;

import gll.result.INode;

public interface IGLL{
	public final static int START_SYMBOL_ID = -1;
	
	public final static int TERMINAL_LIST_CHILD_ID = -2;
	public final static int TERMINAL_LIST_CHILD_NOT_FOUND_ID = -3;
	
	INode parse(String start);
}
