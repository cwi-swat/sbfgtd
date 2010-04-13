package gll.result;

public interface INode{
	void addAlternative(INode[] children);
	
	boolean isEpsilon();
}
