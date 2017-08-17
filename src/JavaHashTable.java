import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
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
	private Collection<Map.Entry<Integer, LinkedList<Map.Entry<K,V>>>> buckets; // use ArrayList for this
	
	/**	DONE
	 * Constructs a new, empty hashtable with a default initial capacity (11) and load factor (0.75).
	 */
	public JavaHashTable() {
		this.loadFactor = .75; // load factor of .75
		this.keys = new HashSet<K>(11); // initializes a set with a capacity of 11
		this.buckets = new ArrayList<Map.Entry<Integer, LinkedList<Map.Entry<K, V>>>>(11);
	}
	
	/**	DONE
	 * Constructs a new, empty hashtable with the specified initial capacity and default load factor (0.75)
	 * 
	 * @param initialCapacity
	 */
	public JavaHashTable(int initialCapacity) {
		if(initialCapacity < 0) throw new IllegalArgumentException();
		this.loadFactor = .75;
		this.keys = new HashSet<K>(initialCapacity);
		this.buckets = new ArrayList<Map.Entry<Integer, LinkedList<Map.Entry<K, V>>>>(initialCapacity);
	}
	
	/** DONE
	 * Constructs a new, empty hashtable with the specified initial capacity and the specified load factor.
	 * 
	 * @param initialCapacity
	 * @param loadFactor
	 */
	public JavaHashTable(int initialCapacity, float loadFactor) {
		if(initialCapacity < 0 || loadFactor < 0) throw new IllegalArgumentException();
		this.loadFactor = loadFactor;
		this.keys = new HashSet<K>(initialCapacity);
		this.buckets = new ArrayList<Map.Entry<Integer, LinkedList<Map.Entry<K, V>>>>(initialCapacity);
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
		for(Map.Entry<
				Integer, LinkedList<
				Map.Entry<K, V>>> entry : this.buckets
				) { // entry is element of buckets(ArrayList)
			LinkedList<Map.Entry<K, V>> ll = 
					entry.getValue(); // ll is linked list

			// I really want to use iterator here...
			for(int i = 0; i < ll.size(); i++) { // iterate through linked list of entries. 
				if (ll.get(i).getValue().equals(arg0)) return true; // return true if the entry's value matches
			}
		}
		return false;
	}

	/**DONE
	 * Returns a Set view of the mappings contained in this map.
	 */
	@Override
	public Set<java.util.Map.Entry<K, V>> entrySet() {
		Set<Map.Entry<K, V>> returnSet = new HashSet<Map.Entry<K, V>>();
		
		for(Map.Entry<
				Integer, LinkedList<
				Map.Entry<K, V>>> entry : this.buckets
				) { // entry is element of buckets(ArrayList)
			
			returnSet.addAll((Collection<? extends Map.Entry<K, V>>) entry.getValue());	
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
		Set mappingsToPut = arg0.entrySet();
		for(Map.Entry entry : arg0.entrySet()) {			
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
		
		for(Map.Entry<
				Integer, LinkedList<
				Map.Entry<K, V>>> entry : this.buckets
				) { // entry is element of buckets(ArrayList)
			LinkedList<Map.Entry<K, V>> ll = 
					entry.getValue(); // ll is linked list

			// I really want to use iterator here...
			for(int i = 0; i < ll.size(); i++) { // iterate through linked list of entries. 
				returnCollection.add(ll.get(i).getValue());
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

	/**
	 * Returns the value to which the specified key is mapped, or null if this map contains no mapping for the key. 
	 * More formally, if this map contains a mapping from a key k to a value v such that (key.equals(k)), then this method returns v; 
	 * otherwise it returns null. (There can be at most one such mapping.)
	 */
	@Override
	public V get(Object key) {
		// TODO Auto-generated method stub
		return null;
	}

	/** DONE
	 * Tests if this hashtable maps no keys to values.
	 */
	@Override
	public boolean isEmpty() {
		if(this.keys.size() == 0) return true;
		else return false;
	}

	/**
	 * Returns an enumeration of the keys in this hashtable.
	 */
	@Override
	public Enumeration<K> keys() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Maps the specified key to the specified value in this hashtable.
	 */
	@Override
	public V put(K key, V value) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Removes the key (and its corresponding value) from this hashtable.
	 */
	@Override
	public V remove(Object key) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Returns the number of keys in this hashtable.
	 */
	@Override
	public int size() {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
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
