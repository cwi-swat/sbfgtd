package gll.result;

import gll.result.struct.Link;
import gll.util.IndexedStack;

public abstract class AbstractNode{
	
	public AbstractNode(){
		super();
	}
	
	public boolean isContainer(){
		return (this instanceof ContainerNode);
	}
	
	public abstract void addAlternative(Link children);
	
	public abstract String print(IndexedStack<AbstractNode> stack, int depth);
}
