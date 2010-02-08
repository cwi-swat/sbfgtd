package gll.stack;

public class ParseStack{
	public ParseStackFrame currentTop;
	public int location;
	
	public ParseStack(ParseStackFrame root){
		super();
		
		currentTop = root;
		location = 0;
	}
	
	public ParseStack(ParseStack stack){
		super();
		
		this.currentTop = stack.currentTop;
		this.location = stack.location;
	}
	
	public ParseStack(ParseStackFrame frame, int location){
		super();
		
		this.currentTop = frame;
		this.location = location;
	}
}
