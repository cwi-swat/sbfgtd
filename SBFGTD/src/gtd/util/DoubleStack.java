package gtd.util;

@SuppressWarnings("unchecked")
public class DoubleStack<E, F>{
	private final static int DEFAULT_SIZE = 8;
	
	private E[] firstData;
	private F[] secondData;
	private int size;
	
	public DoubleStack(){
		super();
		
		firstData = (E[]) new Object[DEFAULT_SIZE];
		secondData = (F[]) new Object[DEFAULT_SIZE];
		size = 0;
	}
	
	public DoubleStack(int initialSize){
		super();
		
		firstData = (E[]) new Object[initialSize];
		secondData = (F[]) new Object[initialSize];
		size = 0;
	}
	
	public void enlarge(){
		E[] oldFirst = firstData;
		firstData = (E[]) new Object[size << 1];
		System.arraycopy(oldFirst, 0, firstData, 0, size);
		
		F[] oldSecond = secondData;
		secondData = (F[]) new Object[size << 1];
		System.arraycopy(oldSecond, 0, secondData, 0, size);
	}
	
	public void push(E first, F second){
		while(size >= firstData.length){
			enlarge();
		}
		
		firstData[size] = first;
		secondData[size++] = second;
	}
	
	public E peekFirst(){
		return firstData[size - 1];
	}
	
	public F peekSecond(){
		return secondData[size - 1];
	}
	
	public E popFirst(){
		E object = firstData[--size];
		firstData[size] = null;
		secondData[size] = null;
		return object;
	}
	
	public F popSecond(){
		F object = secondData[--size];
		firstData[size] = null;
		secondData[size] = null;
		return object;
	}
	
	public void purge(){
		firstData[--size] = null;
		secondData[size] = null;
	}
	
	public boolean isEmpty(){
		return (size == 0);
	}
	
	public void clear(){
		firstData = (E[]) new Object[firstData.length];
		secondData = (F[]) new Object[firstData.length];
		size = 0;
	}
}
