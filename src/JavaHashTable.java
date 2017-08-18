import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.Vector;
import java.util.LinkedList;
import java.util.ListIterator;

/**
 * 
 * @author Gideon
 *
 * Self implemented Java Hash Table using Java interfaces
 * @param <K>
 * @param <V>
 */
public class JavaHashTable<K, V> extends Dictionary<K, V> implements Map<K, V>, Cloneable, Serializable {

	private double loadFactor; // load factor of hashtable
	
	private Set<K> keys; // set of keys. Use hashset
	
	/*
	 * buckets is a collection of entries in which the key is an Integer and the value is a linked list of entries.
	 * The entries of the linked list are the actual keys and the value is the value.
	 */
	private ArrayList<LinkedList<Map.Entry<K,V>>> buckets; // use ArrayList for this
	
	//private LinkedList<Map.Entry<K, V>>[] buckets;
	private int capacity; // default 11
	
	/**	DONE
	 * Constructs a new, empty hashtable with a default initial capacity (11) and load factor (0.75).
	 */
	public JavaHashTable() {
		this.loadFactor = .75; // load factor of .75
		this.capacity = 11;
		this.keys = new HashSet<K>(this.capacity); // initializes a set with a capacity of 11
		this.buckets = new ArrayList<LinkedList<Map.Entry<K, V>>>(Collections.nCopies(this.capacity, null));
	}
	
	/**	DONE
	 * Constructs a new, empty hashtable with the specified initial capacity and default load factor (0.75)
	 * 
	 * @param initialCapacity
	 */
	public JavaHashTable(int initialCapacity) {
		if(initialCapacity < 0) throw new IllegalArgumentException();
		this.capacity = initialCapacity;
		this.loadFactor = .75;
		this.keys = new HashSet<K>(this.capacity);
		this.buckets = new ArrayList<LinkedList<Map.Entry<K, V>>>(Collections.nCopies(this.capacity, null));
	}
	
	/** DONE
	 * Constructs a new, empty hashtable with the specified initial capacity and the specified load factor.
	 * 
	 * @param initialCapacity
	 * @param loadFactor
	 */
	public JavaHashTable(int initialCapacity, float loadFactor) {
		if(initialCapacity < 0 || loadFactor < 0) throw new IllegalArgumentException();
		this.capacity = initialCapacity;
		this.loadFactor = loadFactor;
		this.keys = new HashSet<K>(initialCapacity);
		this.buckets = new ArrayList<LinkedList<Map.Entry<K, V>>>(Collections.nCopies(this.capacity, null));
	}
	
	/**	DONE
	 * Constructs a new hashtable with the same mappings as the given Map.
	 * 
	 * @param t
	 */
	public JavaHashTable(Map<? extends K, ? extends V> t) {
		this.loadFactor = .75;
		this.putAll(t);		
	}
	
	/**	DONE
	 * Clears this hashtable so that it contains no keys.
	 */
	@Override
	public void clear() {
		this.keys.clear();
		this.buckets.clear();
	}

	/** DONE
	 * Tests if the specified object is a key in this hashtable.
	 * 
	 * O(number of keys in set)
	 */
	@Override
	public boolean containsKey(Object arg0) {
		if(arg0 == null) throw new NullPointerException();
		return this.keys.contains(arg0);
	}

	/** DONE
	 * Returns true if this hashtable maps one or more keys to this value.
	 * 
	 * O(total number of linked list elements for every bucket)
	 */
	@Override
	public boolean containsValue(Object arg0) {
		if(arg0 == null) throw new NullPointerException();
		
		for(LinkedList<Entry<K, V>> bucket : this.buckets) {
			if(bucket == null) continue;
			else {
				Iterator<Entry<K, V>> linkedListIter = bucket.iterator();
				while(linkedListIter.hasNext()) {
					Entry<K, V> el = linkedListIter.next();
					if(el.getValue().equals(arg0)) return true;
				}
			}
		}		
		return false;
	}

	/**DONE
	 * Returns a Set view of the mappings contained in this map.
	 */
	@Override
	public Set<Entry<K, V>> entrySet() {
		Set<Entry<K, V>> returnSet = new HashSet<Entry<K, V>>();
		
		for(LinkedList<Entry<K, V>> bucket : this.buckets) {
			if(bucket == null) continue;
			else {
				Iterator<Entry<K, V>> linkedListIter = bucket.iterator();
				while(linkedListIter.hasNext()) {
					Entry<K, V> el = linkedListIter.next();
					returnSet.add(el);
				}
			}
		}
		
		return returnSet;
	}

	/** DONE
	 * Returns a Set view of the keys contained in this map.
	 */
	@Override
	public Set<K> keySet() {
		return this.keys;
	}

	/** DONE
	 * Copies all of the mappings from the specified map to this hashtable.
	 * 
	 * O(number of elements in arg0)
	 * 
	 * we can cast because the elements of arg0 are subclasses of K and V
	 */
	@Override
	public void putAll(Map<? extends K, ? extends V> arg0) {
		if(arg0 == null) throw new NullPointerException();
		for(Entry<? extends K, ? extends V> entry : arg0.entrySet()) {			
			this.put( (K) entry.getKey(), (V) entry.getValue());
		}
		return;
	}

	/** DONE
	 * Returns a Collection view of the values contained in this map.
	 * 
	 * O(total number of entries in every bucket)
	 */
	@Override
	public Collection<V> values() {
		
		ArrayList<V> returnCollection = new ArrayList<V>();
		
		for(LinkedList<Entry<K, V>> bucket : this.buckets) {
			if(bucket == null) continue;
			else {
				Iterator<Entry<K, V>> linkedListIter = bucket.iterator();
				while(linkedListIter.hasNext()) {
					Entry<K, V> el = linkedListIter.next();
					returnCollection.add(el.getValue());
				}
			}
		}
		return returnCollection;
	}

	/** DONE
	 * Returns an enumeration of the values in this hashtable. Use the Enumeration methods on the returned object to fetch the elements sequentially.
	 */
	@Override
	public Enumeration<V> elements() {
		Enumeration<V> retEnum = new JavaHashTableValueEnumeration();
		return retEnum;
	}

	/** DONE
	 * 
	 * Returns the value to which the specified key is mapped, or null if this map contains no mapping for the key. 
	 * More formally, if this map contains a mapping from a key k to a value v such that (key.equals(k)), then this method returns v; 
	 * otherwise it returns null. (There can be at most one such mapping.)
	 */
	@Override
	public V get(Object key) {
		if(key == null || !this.containsKey(key)) return null;
		
		Integer hash = hash(key.hashCode());
		
		LinkedList<Entry<K, V>> ll = this.buckets.get(hash);
		
		Iterator<Entry<K, V>> iter = ll.iterator();
		
		while(iter.hasNext()) {
			Entry<K, V> el = iter.next();
			if(el.getKey().equals(key)) return el.getValue();
		}
		
		return null;
	}

	/**	DONE
	 * hash function to call on hash code
	 * @param hashcode
	 * @return
	 */
	private int hash(int hashcode) {
		int returnHash = hashcode % this.capacity;
		return returnHash;
	}
	
	/** DONE
	 * Tests if this hashtable maps no keys to values.
	 */
	@Override
	public boolean isEmpty() {
		if(this.keys.size() == 0) return true;
		else return false;
	}

	/** DONE
	 * Returns an enumeration of the keys in this hashtable.
	 */
	@Override
	public Enumeration<K> keys() {
		Enumeration<K> retEnumeration = new JavaHashTableKeyEnumeration();
		return retEnumeration;
	}

	/** DONE
	 * Maps the specified key to the specified value in this hashtable.
	 */
	@Override
	public V put(K key, V value) {
		if(key == null || value == null) throw new NullPointerException();
		if(this.keys.contains(key)) return null;
				
		int hashcode = hash(key.hashCode());
		
		Entry<K, V> entryToAdd = new JavaHashTableEntry(key, value);
		
		this.putHelper(entryToAdd, hashcode);
		
		this.keys.add(key);
		if( (double) this.keys.size() / (double) this.capacity > this.loadFactor) this.resize();
		return value;	
	}

	
	/**
	 * helper method for adding
	 */
	private void putHelper(Entry<K, V> inputEntry, int hashcode) {
		
		LinkedList<Entry<K,V>> bucket = this.buckets.get(hashcode);

		if(bucket == null) { // new item
			LinkedList<Entry<K,V>> toAdd = new LinkedList<Entry<K, V>>();
			toAdd.add(inputEntry);
			this.buckets.set(hashcode, toAdd);
		} else {	// linked list already exists so we append to the end of the linked list
			bucket.add(inputEntry);
		}
		return;	
	}
	
	/**
	 * Function to resize map when load factor is exceeded
	 */
	private void resize() {
		this.capacity = this.capacity * 2;
		ArrayList<LinkedList<Entry<K,V>>> newBuckets = new ArrayList<LinkedList<Map.Entry<K, V>>>(
				Collections.nCopies(this.capacity, null)); // new buckets collection with a new capacity
		
		@SuppressWarnings("unchecked")
		Entry<K, V>[] entryArray  = (
				Entry<K, V>[]) this.entrySet().toArray(); // entrySet() returns a Set object of Entry<K, V>
		
		this.buckets = newBuckets; // set this.buckets to newBuckets here because putHelper operates on this.buckets
		
		for(Entry<K, V> entry : entryArray) { // 
			int hashcode = hash(entry.getKey().hashCode());
			putHelper(entry, hashcode);
		}

		return;
	}
	/**
	 * Removes the key (and its corresponding value) from this hashtable.
	 */
	@Override
	public V remove(Object key) {
		// TODO Auto-generated method stub
		return null;
	}

	/**	DONE
	 * Returns the number of keys in this hashtable.
	 */
	@Override
	public int size() {
		return this.keys.size();
	}

	/**	DONE
	 * I am implementing this enumeration implementation using an iterator.
	 * I know its not a good idea, but I'm not sure how else to implement the 
	 * nextElement method.
	 * 
	 * @author Gideon
	 *
	 */
	private class JavaHashTableKeyEnumeration implements Enumeration<K>{

		Iterator<K> iter;
		
		public JavaHashTableKeyEnumeration() {
			Iterator<K> iter = JavaHashTable.this.keys.iterator();
		}
		
		@Override
		public boolean hasMoreElements() {		
			if(this.iter.hasNext()) return true;
			else return false;
		}

		@Override
		public K nextElement() {
			if(this.hasMoreElements()) return iter.next();
			else throw new NoSuchElementException();
		}
		
		
	}
	
	
	private class JavaHashTableEntry implements Map.Entry<K, V> {

		private K key;
		
		private V value;
		
		public JavaHashTableEntry(K key, V value) {
			if(key == null || value == null) throw new NullPointerException();
			this.key = key;
			this.value = value;
		}
		
		@Override
		public K getKey() {
			return this.key;
		}

		@Override
		public V getValue() {
			return this.value;
		}

		@Override
		public V setValue(V value) {
			this.value = value;
			return value;
		}
		
		
	}
	/**
	 * Calls the values() method
	 * 
	 * @author Gideon
	 *
	 */
	private class JavaHashTableValueEnumeration implements Enumeration<V> {

		Map.Entry[] values;
		int counter;
		
		protected JavaHashTableValueEnumeration() {
			this.values = (Map.Entry[]) JavaHashTable.this.values().toArray();
			this.counter = 0;
		}
		
		@Override
		public boolean hasMoreElements() {
			if(this.counter < this.values.length) return true;
			else return false;
		}

		@Override
		public V nextElement() {
			if(this.hasMoreElements()) {
				int current = counter;
				counter++;
				return (V) this.values[current].getValue();
			} else throw new NoSuchElementException();
		}
		
	}
}
