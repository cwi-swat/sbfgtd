package gll.result;

import gll.result.struct.Link;
import gll.util.IndexedStack;

public interface INode{
	void addAlternative(Link children);
	
	String toString(IndexedStack<INode> stack, int depth);
}
