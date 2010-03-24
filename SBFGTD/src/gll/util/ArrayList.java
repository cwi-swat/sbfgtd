package gll.util;

public class ArrayList<E>{
	private final static int DEFAULT_SIZE = 8;
	
	private E[] data;
	private int size;
	
	public ArrayList(){
		super();
		
		data = (E[]) new Object[DEFAULT_SIZE];
		size = 0;
	}
	
	public ArrayList(int initialSize){
		super();
		
		data = (E[]) new Object[initialSize];
		size = 0;
	}
	
	public void enlarge(){
		E[] oldData = data;
		data = (E[]) new Object[size << 1];
		System.arraycopy(oldData, 0, data, 0, size);
	}
	
	public void add(E object){
		int index = size;
		if(++size == data.length){
			enlarge();
		}
		
		data[index] = object;
	}
	
	public E get(int index){
		return data[index];
	}
	
	public boolean contains(E object){
		for(int i = 0; i < size; i++){
			if(data[i].equals(object)) return true;
		}
		return false;
	}
	
	public E remove(int index){
		E object = data[index];
		if(index != --size){
			System.arraycopy(data, index + 1, data, index, size - index);
		}
		data[size] = null;
		
		return object;
	}
	
	public E remove(E object){
		for(int i = 0; i < size; i++){
			if(data[i].equals(object)) return remove(i);
		}
		return null;
	}
	
	public int size(){
		return size;
	}
}
