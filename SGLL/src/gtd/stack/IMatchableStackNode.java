package gtd.stack;

public interface IMatchableStackNode{
	boolean match(char[] input);
	
	int getLength();
}
