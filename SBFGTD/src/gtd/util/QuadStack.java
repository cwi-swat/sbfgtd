package gtd.util;

public class QuadStack<E, F, G, H>{
	private final static int DEFAULT_SIZE = 8;
	
	private E[] firstData;
	private F[] secondData;
	private G[] thirdData;
	private H[] fourthData;
	private int size;
	
	public QuadStack(){
		super();
		
		firstData = (E[]) new Object[DEFAULT_SIZE];
		secondData = (F[]) new Object[DEFAULT_SIZE];
		thirdData = (G[]) new Object[DEFAULT_SIZE];
		fourthData = (H[]) new Object[DEFAULT_SIZE];
		size = 0;
	}
	
	public QuadStack(int initialSize){
		super();
		
		firstData = (E[]) new Object[initialSize];
		secondData = (F[]) new Object[initialSize];
		thirdData = (G[]) new Object[initialSize];
		fourthData = (H[]) new Object[initialSize];
		size = 0;
	}
	
	public void enlarge(){
		E[] oldFirst = firstData;
		firstData = (E[]) new Object[size << 1];
		System.arraycopy(oldFirst, 0, firstData, 0, size);
		
		F[] oldSecond = secondData;
		secondData = (F[]) new Object[size << 1];
		System.arraycopy(oldSecond, 0, secondData, 0, size);
		
		G[] oldThird = thirdData;
		thirdData = (G[]) new Object[size << 1];
		System.arraycopy(oldThird, 0, thirdData, 0, size);
		
		H[] oldFourthData = fourthData;
		fourthData = (H[]) new Object[size << 1];
		System.arraycopy(oldFourthData, 0, fourthData, 0, size);
	}
	
	public void push(E first, F second, G third, H fourth){
		while(size >= firstData.length){
			enlarge();
		}
		
		firstData[size] = first;
		secondData[size] = second;
		thirdData[size] = third;
		fourthData[size++] = fourth;
	}
	
	public E peekFirst(){
		return firstData[size - 1];
	}
	
	public F peekSecond(){
		return secondData[size - 1];
	}
	
	public G peekThird(){
		return thirdData[size - 1];
	}
	
	public H peekFourth(){
		return fourthData[size - 1];
	}
	
	public E popFirst(){
		E object = firstData[--size];
		firstData[size] = null;
		secondData[size] = null;
		thirdData[size] = null;
		fourthData[size] = null;
		return object;
	}
	
	public F popSecond(){
		F object = secondData[--size];
		firstData[size] = null;
		secondData[size] = null;
		thirdData[size] = null;
		fourthData[size] = null;
		return object;
	}
	
	public G popThird(){
		G object = thirdData[--size];
		firstData[size] = null;
		secondData[size] = null;
		thirdData[size] = null;
		fourthData[size] = null;
		return object;
	}
	
	public H popFourth(){
		H object = fourthData[--size];
		firstData[size] = null;
		secondData[size] = null;
		thirdData[size] = null;
		fourthData[size] = null;
		return object;
	}
	
	public void purge(){
		firstData[--size] = null;
		secondData[size] = null;
		thirdData[size] = null;
		fourthData[size] = null;
	}
	
	public boolean isEmpty(){
		return (size == 0);
	}
	
	public void clear(){
		firstData = (E[]) new Object[firstData.length];
		secondData = (F[]) new Object[firstData.length];
		thirdData = (G[]) new Object[firstData.length];
		fourthData = (H[]) new Object[firstData.length];
		size = 0;
	}
}
