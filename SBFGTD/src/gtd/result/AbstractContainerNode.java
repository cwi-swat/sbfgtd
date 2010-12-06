package gtd.result;

import gtd.result.struct.Link;
import gtd.util.ArrayList;

public abstract class AbstractContainerNode extends AbstractNode{
	protected final String name;
	protected Link firstAlternative;
	protected ArrayList<Link> alternatives;
	
	protected final boolean isNullable;
	protected final boolean isSeparator;
	
	public AbstractContainerNode(String name, boolean isNullable, boolean isSeparator){
		super();
		
		this.name = name;
		this.isNullable = isNullable;
		this.isSeparator = isSeparator;
	}
	
	public void addAlternative(Link children){
		if(firstAlternative == null){
			firstAlternative = children;
		}else{
			if(alternatives == null) alternatives = new ArrayList<Link>(1);
			alternatives.add(children);
		}
	}
	
	public boolean isEmpty(){
		return isNullable;
	}
	
	public boolean isSeparator(){
		return isSeparator;
	}
}
