package gll.result;

import gll.util.IndexedStack;

import java.io.IOException;
import java.io.Writer;

public interface INode{
	void addAlternative(INode[] children);
	
	boolean isEpsilon();
	
	void print(Writer out, IndexedStack<INode> stack, int depth) throws IOException;
}
