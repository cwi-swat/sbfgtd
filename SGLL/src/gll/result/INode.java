package gll.result;

import gll.util.Stack;

import java.io.IOException;
import java.io.Writer;

public interface INode{
	void addAlternative(INode[] children);
	
	boolean isEpsilon();
	
	void print(Writer out, Stack<INode> stack) throws IOException;
}
