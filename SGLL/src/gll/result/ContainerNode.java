package gll.result;

import gll.result.struct.Link;
import gll.util.ArrayList;

public abstract class ContainerNode extends AbstractNode{
	protected final String name;
	protected Link firstAlternative;
	protected ArrayList<Link> alternatives;
	
	protected final boolean isNullable;
	protected final boolean isSeparator;
	
	public ContainerNode(String name, boolean isNullable, boolean isSeparator){
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
	
	protected boolean isNullable(){
		return isNullable;
	}
	
	protected boolean isSeparator(){
		return isSeparator;
	}
}
