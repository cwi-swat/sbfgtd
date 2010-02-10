package gll.stack;

public class ParseStack{
	private ParseStackFrame currentTop;
	private int location;
	
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
	
	public ParseStackFrame getTop(){
		return currentTop;
	}
	
	public void setTop(ParseStackFrame top){
		this.currentTop = top;
	}
	
	public int getCurrentLocation(){
		return location;
	}
	
	public void moveLocation(int bytes){
		location += bytes;
	}
}
