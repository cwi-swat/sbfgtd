package gtd.util;

import java.util.Iterator;

public class IntegerKeyedHashMap<V>{
	private final static int DEFAULT_BIT_SIZE = 2;
	
	private Entry<V>[] entries;

	private int hashMask;
	private int bitSize;
	
	private int threshold;
	private int load;
	
	public IntegerKeyedHashMap(){
		super();
		
		int nrOfEntries = 1 << (bitSize = DEFAULT_BIT_SIZE);
		
		hashMask = nrOfEntries - 1;
		
		entries = (Entry<V>[]) new Entry[nrOfEntries];
		
		threshold = nrOfEntries;
		load = 0;
	}
	
	public IntegerKeyedHashMap(IntegerKeyedHashMap<V> integerKeyedHashMap){
		super();
		
		hashMask = integerKeyedHashMap.hashMask;
		bitSize = integerKeyedHashMap.bitSize;
		
		entries = (Entry<V>[]) new Entry[hashMask + 1];
		System.arraycopy(integerKeyedHashMap.entries, 0, entries, 0, hashMask + 1);
		
		threshold = hashMask + 1;
		load = integerKeyedHashMap.load;
	}
	
	private void rehash(){
		int nrOfEntries = 1 << (++bitSize);
		int newHashMask = nrOfEntries - 1;
		
		Entry<V>[] oldEntries = entries;
		Entry<V>[] newEntries = (Entry<V>[]) new Entry[nrOfEntries];
		
		Entry<V> currentEntryRoot = new Entry<V>(-1, null, null);
		Entry<V> shiftedEntryRoot = new Entry<V>(-1, null, null);
		
		int newLoad = load;
		int oldSize = oldEntries.length;
		for(int i = oldSize - 1; i >= 0; --i){
			Entry<V> e = oldEntries[i];
			if(e != null){
				Entry<V> lastCurrentEntry = currentEntryRoot;
				Entry<V> lastShiftedEntry = shiftedEntryRoot;
				do{
					int position = e.key & newHashMask;
					
					if(position == i){
						lastCurrentEntry.next = e;
						lastCurrentEntry = e;
					}else{
						lastShiftedEntry.next = e;
						lastShiftedEntry = e;
					}
					
					e = e.next;
				}while(e != null);
				
				lastCurrentEntry.next = null;
				lastShiftedEntry.next = null;
				
				newEntries[i] = currentEntryRoot.next;
				newEntries[i | oldSize] = shiftedEntryRoot.next;
			}
		}
		
		load = newLoad;
		
		threshold <<= 1;
		entries = newEntries;
		hashMask = newHashMask;
	}
	
	private void ensureCapacity(){
		if(load > threshold){
			rehash();
		}
	}
	
	public V put(int key, V value){
		ensureCapacity();
		
		int position = key & hashMask;
		
		Entry<V> currentStartEntry = entries[position];
		if(currentStartEntry != null){
			Entry<V> entry = currentStartEntry;
			do{
				if(entry.key == key){
					V oldValue = entry.value;
					entry.value = value;
					return oldValue;
				}
			}while((entry = entry.next) != null);
		}
		
		entries[position] = new Entry<V>(key, value, currentStartEntry);
		++load;
		
		return null;
	}
	
	public V putUnsafe(int key, V value){
		ensureCapacity();
		
		int position = key & hashMask;
		entries[position] = new Entry<V>(key, value, entries[position]);
		++load;
		
		return null;
	}
	
	public boolean containsKey(int key){
		int position = key & hashMask;
		
		Entry<V> entry = entries[position];
		while(entry != null){
			if(entry.key == key) return true;
			
			entry = entry.next;
		}
		
		return false;
	}
	
	public V get(int key){
		int position = key & hashMask;
		
		Entry<V> entry = entries[position];
		while(entry != null){
			if(entry.key == key) return entry.value;
			
			entry = entry.next;
		}
		
		return null;
	}
	
	public V remove(int key){
		int position = key & hashMask;
		
		Entry<V> previous = null;
		Entry<V> currentStartEntry = entries[position];
		if(currentStartEntry != null){
			Entry<V> entry = currentStartEntry;
			do{
				if(entry.key == key){
					if(previous == null){
						entries[position] = entry.next;
					}else{
						previous.next = entry.next;
					}
					load--;
					return entry.value;
				}
				
				previous = entry;
			}while((entry = entry.next) != null);
		}
		
		return null;
	}
	
	public int size(){
		return load;
	}
	
	public void clear(){
		int nrOfEntries = 1 << (bitSize = DEFAULT_BIT_SIZE);
		
		hashMask = nrOfEntries - 1;
		
		entries = (Entry<V>[]) new Entry[nrOfEntries];
		
		threshold = nrOfEntries;
		load = 0;
	}
	
	public static class Entry<V>{
		public final int key;
		public V value;
		public Entry<V> next;
		
		public Entry(int key, V value, Entry<V> next){
			super();
			
			this.key = key;
			this.value = value;
			this.next = next;
		}
	}
	
	public Iterator<Entry<V>> iterator(){
		return new EntryIterator<V>(this);
	}
	
	private static class EntryIterator<V> implements Iterator<Entry<V>>{
		private final Entry<V>[] data;
		
		private Entry<V> current;
		private int index;
		
		public EntryIterator(IntegerKeyedHashMap<V> integerKeyedHashMap){
			super();
			
			data = integerKeyedHashMap.entries;

			index = data.length - 1;
			current = new Entry<V>(-1, null, data[index]);
			locateNext();
		}
		
		private void locateNext(){
			Entry<V> next = current.next;
			if(next != null){
				current = next;
				return;
			}
			
			for(int i = index - 1; i >= 0 ; i--){
				Entry<V> entry = data[i];
				if(entry != null){
					current = entry;
					index = i;
					return;
				}
			}
			
			current = null;
			index = 0;
		}
		
		public boolean hasNext(){
			return (current != null);
		}
		
		public Entry<V> next(){
			if(!hasNext()) throw new UnsupportedOperationException("There are no more elements in this iterator.");
			
			Entry<V> entry = current;
			locateNext();
			
			return entry;
		}
		
		public void remove(){
			throw new UnsupportedOperationException("This iterator doesn't support removal.");
		}
	}
}
