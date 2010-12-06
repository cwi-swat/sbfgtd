package gtd.result;

import gtd.result.struct.Link;
import gtd.util.IndexedStack;

public abstract class AbstractNode{
	
	public AbstractNode(){
		super();
	}
	
	public abstract void addAlternative(Link children);
	
	public abstract boolean isEmpty();
	
	public abstract boolean isSeparator();
	
	protected abstract String print(IndexedStack<AbstractNode> stack, int depth, CycleMark cycleMark);
	
	protected static class CycleMark{
		public int depth = Integer.MAX_VALUE;
		
		public void setMark(int depth){
			if(depth < this.depth){
				this.depth = depth;
			}
		}
		
		public void reset(){
			depth = Integer.MAX_VALUE;
		}
	}
}
