package gll.stack;

public class ParseStack{
	public ParseStackNode currentTop;
	public int location;
	
	public ParseStack(ParseStackNode root){
		super();
		
		currentTop = root;
		location = 0;
	}
	
	public ParseStack(ParseStack stack){
		super();
		
		this.currentTop = stack.currentTop;
		this.location = stack.location;
	}
	
	public ParseStack(ParseStackNode node, int location){
		super();
		
		this.currentTop = node;
		this.location = location;
	}
}
