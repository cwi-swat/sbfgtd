package gll.result;

import gll.util.ArrayList;

import java.io.IOException;
import java.io.Writer;

public interface INode{
	void addAlternative(INode[] children);
	
	boolean isEpsilon();
	
	// TODO Use a real stack.
	void print(Writer out, ArrayList<INode> stack) throws IOException;
}
