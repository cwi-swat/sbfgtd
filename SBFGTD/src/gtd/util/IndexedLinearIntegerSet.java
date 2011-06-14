package gtd.util;

public class IndexedLinearIntegerSet{
	private final static int DEFAULT_SIZE = 8;
	
	private int[] elements;
	
	private int size;
	
	public IndexedLinearIntegerSet(){
		super();
		
		elements = new int[DEFAULT_SIZE];
	}
	
	public IndexedLinearIntegerSet(IndexedLinearIntegerSet original){
		super();
		
		int[] oldElements = original.elements;
		int length = oldElements.length;
		
		size = original.size;
		elements = new int[length];
		System.arraycopy(oldElements, 0, elements, 0, size);
	}
	
	public void enlarge(){
		int[] oldElements = elements;
		elements = new int[size << 1];
		System.arraycopy(oldElements, 0, elements, 0, size);
	}
	
	public void add(int element){
		while(size >= elements.length){
			enlarge();
		}
		
		elements[size++] = element;
	}
	
	public int getElement(int index){
		return elements[index];
	}
	
	public boolean contains(int element){
		for(int i = size - 1; i >= 0; --i){
			if(elements[i] == element){
				return true;
			}
		}
		return false;
	}
	
	public int find(int element){
		for(int i = size - 1; i >= 0; --i){
			if(elements[i] == element){
				return i;
			}
		}
		return -1;
	}
	
	public int findBefore(int element, int index){
		for(int i = index - 1; i >= 0; --i){
			if(elements[i] == element){
				return i;
			}
		}
		return -1;
	}
	
	public int size(){
		return size;
	}
	
	public int capacity(){
		return elements.length;
	}
	
	public void clear(){
		int length = elements.length;
		elements = new int[length];
		size = 0;
	}
	
	public void dirtyClear(){
		size = 0;
	}
}
