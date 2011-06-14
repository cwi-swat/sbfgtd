package gtd.stack;

import gtd.result.AbstractNode;

public interface IMatchableStackNode{
	AbstractNode match(char[] input, int location);
	
	int getLength();
}
