import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Random;
import java.util.Set;

import org.junit.Test;

public class JavaHashTableJunit {

	Hashtable jtable0 = new Hashtable();
	JavaHashTable table0 = new JavaHashTable();
	
	
	// test JavaHashTable constructors
	@Test
	public void testConstructors() {
		
		JavaHashTable constr0 = new JavaHashTable();
		JavaHashTable constr1 = new JavaHashTable(80);
		JavaHashTable constr2 = new JavaHashTable(80, .5);
		JavaHashTable constr3 = new JavaHashTable(constr0);
		
		
		assertEquals(true, constr0.isEmpty());
		assertEquals(true, constr1.isEmpty());
		assertEquals(true, constr2.isEmpty());
		assertEquals(true, constr3.isEmpty());
		
		// test constructor that takes map as input
		table0.put("a", 1);
		table0.put("b", 2);
		table0.put("c", 3);
		table0.put("d", 4);
		table0.put("e", 5);
		
		JavaHashTable constr4 = new JavaHashTable(table0);
		
		assertEquals(table0.get("a"), constr4.get("a"));
		assertEquals(table0.get("b"), constr4.get("b"));
		assertEquals(table0.get("c"), constr4.get("c"));
		assertEquals(table0.get("d"), constr4.get("d"));
		assertEquals(table0.get("e"), constr4.get("e"));
		assertEquals(table0.get("dummy"), constr4.get("dummy"));
	}

	// test JavaHashTable by putting elements in it
	@Test
	public void testPut() {
		assertEquals(1, table0.put("a", 1));
		assertEquals(2, table0.put("b", 2));
		assertEquals(3, table0.put("c", 3));
		assertEquals(4, table0.put("d", 4));
		assertEquals(5, table0.put("e", 5));
		
		assertEquals(null, table0.put("a", 1));
		assertEquals(null, table0.put("a", 2));
		assertEquals(3, table0.put('a',  3));
		
		jtable0.put("a", 1);
		jtable0.put("b", 2);
		jtable0.put("c", 3);
		jtable0.put("d", 4);
		jtable0.put("e", 5);
		
		// test containsKey and containsValue methods
		assertEquals(true, table0.containsKey("a"));
		assertEquals(true, table0.containsKey("b"));
		assertEquals(true, table0.containsKey("c"));
		assertEquals(true, table0.containsKey("d"));
		assertEquals(true, table0.containsKey("e"));
		
		assertEquals(true, table0.containsValue(1));
		assertEquals(true, table0.containsValue(2));
		assertEquals(true, table0.containsValue(3));
		assertEquals(true, table0.containsValue(4));
		assertEquals(true, table0.containsValue(5));
		
		assertEquals(false, table0.containsKey("f"));
		assertEquals(false, table0.containsValue(6));
		
		// test if both JavaHashTable and Hashtable have same values
		assertEquals(jtable0.get("a"), table0.get("a"));
		assertEquals(jtable0.get("b"), table0.get("b"));
		assertEquals(jtable0.get("c"), table0.get("c"));
		assertEquals(jtable0.get("d"), table0.get("d"));
		assertEquals(jtable0.get("e"), table0.get("e"));

		// test keySet() method
		Set testSet = table0.keySet();
		
		assertEquals(true, testSet.contains("a"));
		assertEquals(true, testSet.contains("b"));
		assertEquals(true, testSet.contains("c"));
		assertEquals(true, testSet.contains("d"));
		assertEquals(true, testSet.contains("e"));
		
		assertEquals(6, table0.values().size());
		assertEquals(6, table0.keySet().size());
		
		// test remove() method
		assertEquals(1, table0.remove("a"));
		assertEquals(2, table0.remove("b"));
		assertEquals(3, table0.remove("c"));
		assertEquals(4, table0.remove("d"));
		assertEquals(5, table0.remove("e"));
		assertEquals(3, table0.remove('a'));
		
		assertEquals(true, table0.isEmpty());
		
		assertEquals(null, table0.get("a"));
		assertEquals(null, table0.get("b"));
		assertEquals(null, table0.get("c"));
		assertEquals(null, table0.get("d"));
		assertEquals(null, table0.get("e"));
		
		assertEquals(0, table0.values().size());
		assertEquals(0, table0.keySet().size());
		
		// test put for large quantities
		for(int i = 0; i < 100; i++) {
			assertEquals(i + 100, table0.put(i, i + 100));
		}
		
		for(int i = 0; i < 100; i++) {
			assertEquals(true, table0.containsKey(i));
			assertEquals(true, table0.containsValue(i + 100));
		}
		
		for(int i = 0; i < 100; i++) {
			assertEquals(i + 100, table0.remove(i));
		}
		
		assertEquals(true, table0.isEmpty());
		
		for(int i = 0; i < 10000; i++) {
			assertEquals(i + 10000, table0.put(i, i + 10000));
		}
		
		for(int i = 0; i < 10000; i++) {
			assertEquals(true, table0.containsKey(i));
			assertEquals(true, table0.containsValue(i + 10000));
		}
		
		for(int i = 0; i < 10000; i++) {
			assertEquals(i + 10000, table0.remove(i));
		}
		
		assertEquals(true, table0.isEmpty());
	}
	
	// test JavaHashTable by creating instance using generic Hashtable
	@Test
	public void testConstructor1() {
		jtable0.put(1, 1);
		jtable0.put(2, 2);
		jtable0.put(3, 3);
		
		JavaHashTable newTable = new JavaHashTable(jtable0);
		
		assertEquals(true, newTable.containsKey(1));
		assertEquals(true, newTable.containsKey(2));
		assertEquals(true, newTable.containsKey(3));
	}
	
	
	// Test the keys() and elements() methods 
	@Test
	public void testEnumerations() {
		
		for(int i = 0; i < 10; i++) table0.put(i, i);
		
		Enumeration keys = table0.keys();
		Enumeration values = table0.elements();
		
	}
}
