import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.LinkedList;

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
	
	private Collection<Map.Entry<Integer, LinkedList<V>>> buckets; // use ArrayList for this
	/**
	 * Constructs a new, empty hashtable with a default initial capacity (11) and load factor (0.75).
	 */
	public JavaHashTable() {
		this.loadFactor = .75; // load factor of .75
		this.keys = new HashSet<K>(11); // initializes a set with a capacity of 11
		this.buckets = new ArrayList<Map.Entry<Integer, LinkedList<V>>>();
	}
	
	/**
	 * Constructs a new, empty hashtable with the specified initial capacity and default load factor (0.75)
	 * 
	 * @param initialCapacity
	 */
	public JavaHashTable(int initialCapacity) {
		
	}
	
	/**
	 * Constructs a new, empty hashtable with the specified initial capacity and the specified load factor.
	 * 
	 * @param initialCapacity
	 * @param loadFactor
	 */
	public JavaHashTable(int initialCapacity, float loadFactor) {
		
	}
	
	/**
	 * Constructs a new hashtable with the same mappings as the given Map.
	 * 
	 * @param t
	 */
	public JavaHashTable(Map<? extends K, ? extends V> t) {
		
	}
	
	/**
	 * Clears this hashtable so that it contains no keys.
	 */
	@Override
	public void clear() {
		// TODO Auto-generated method stub
		
	}

	/**
	 * Tests if the specified object is a key in this hashtable.
	 */
	@Override
	public boolean containsKey(Object arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * Returns true if this hashtable maps one or more keys to this value.
	 */
	@Override
	public boolean containsValue(Object arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * Returns a Set view of the mappings contained in this map.
	 */
	@Override
	public Set<java.util.Map.Entry<K, V>> entrySet() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Returns a Set view of the keys contained in this map.
	 */
	@Override
	public Set<K> keySet() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Copies all of the mappings from the specified map to this hashtable.
	 */
	@Override
	public void putAll(Map<? extends K, ? extends V> arg0) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * Returns a Collection view of the values contained in this map.
	 */
	@Override
	public Collection<V> values() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Returns an enumeration of the values in this hashtable. Use the Enumeration methods on the returned object to fetch the elements sequentially.
	 */
	@Override
	public Enumeration<V> elements() {
		// TODO Auto-generated method stub
		return null;
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

	/**
	 * Tests if this hashtable maps no keys to values.
	 */
	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return false;
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

}
