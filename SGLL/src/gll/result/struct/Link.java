package gll.result.struct;

import gll.result.AbstractNode;
import gll.util.ArrayList;

public class Link{
	public final ArrayList<Link> prefixes;
	public final AbstractNode node;
	
	public Link(ArrayList<Link> prefixes, AbstractNode node){
		super();
		
		this.prefixes = prefixes;
		this.node = node;
	}
}
