package gtd.util;

@SuppressWarnings("unchecked")
public class SortedIntegerObjectList<V>{
	private final static int DEFAULT_SIZE = 8;
	
	private int[] keys;
	private V[] values;
	
	private int size;
	
	public SortedIntegerObjectList(){
		super();
		
		keys = new int[DEFAULT_SIZE];
		values = (V[]) new Object[DEFAULT_SIZE];
	}
	
	public void enlarge(){
		int[] oldKeys = keys;
		keys = new int[size << 1];
		System.arraycopy(oldKeys, 0, keys, 0, size);

		V[] oldValues = values;
		values = (V[]) new Object[size << 1];
		System.arraycopy(oldValues, 0, values, 0, size);
	}
	
	public void add(int key, V value){
		while(size >= keys.length){
			enlarge();
		}
		
		if(size == 0 || keys[size - 1] < key){
			keys[size] = key;
			values[size++] = value;
			return;
		}
		
		for(int i = size - 1; i >= 0; --i){
			if(keys[i] < key){
				System.arraycopy(keys, i + 1, keys, i + 2, size - i - 1);
				System.arraycopy(values, i + 1, values, i + 2, size++ - i -1);
				
				keys[i + 1] = key;
				values[i + 1] = value;
				
				return;
			}
		}
		
		System.arraycopy(keys, 0, keys, 1, size);
		System.arraycopy(values, 0, values, 1, size++);
		
		keys[0] = key;
		values[0] = value;
	}
	
	public int getKey(int index){
		return keys[index];
	}
	
	public V getValue(int index){
		return values[index];
	}
	
	public int findKey(int key){
		for(int i = size - 1; i >= 0; --i){
			if(keys[i] == key){
				return i;
			}
		}
		return -1;
	}
	
	public V findValue(int key){
		for(int i = size - 1; i >= 0; --i){
			if(keys[i] == key){
				return values[i];
			}
		}
		return null;
	}
	
	public int size(){
		return size;
	}
	
	public int capacity(){
		return keys.length;
	}
	
	public void clear(){
		int length = keys.length;
		keys = new int[length];
		values = (V[]) new Object[length];
		size = 0;
	}
	
	public void dirtyClear(){
		size = 0;
	}
}
