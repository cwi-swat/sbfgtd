package gll.result;

import gll.result.struct.Link;
import gll.util.IndexedStack;

public abstract class AbstractNode{
	
	public AbstractNode(){
		super();
	}
	
	public boolean isContainerNode(){
		return (this instanceof ContainerNode);
	}
	
	public abstract void addAlternative(Link children);
	
	public abstract String toString(IndexedStack<AbstractNode> stack, int depth);
}
