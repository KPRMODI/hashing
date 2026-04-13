import java.util.Iterator;
import java.util.LinkedList;

/**
 * Karan Modi
 * Course: Java I
 * Date: 4/9/2026
 *
 * A program meant to build a HashMap within java and test the results to prove our knowledge.
 */

// NOTE: I used AI for the style guildlines but also double checked everything to make sure it adhered to the guildliens

public class MyHashMap<K, V> {


    /**
     * Inner entry class to store key-value pairs.
     */

    // ── Inner entry class ─────────────────────────────────────────────────
    static class Entry<K, V> {
        K key;
        V value;

        Entry(K key, V value) {
            this.key   = key;
            this.value = value;
        }
    }

    // ── Fields ────────────────────────────────────────────────────────────
    private LinkedList<Entry<K, V>>[] table;
    private int size;
    private static final int DEFAULT_CAPACITY = 11;


    /**
     * Initializes a new MyHashMap with a default capacity.
     */

    // ── Constructor ───────────────────────────────────────────────────────
    @SuppressWarnings("unchecked")
    public MyHashMap() {
        table = new LinkedList[DEFAULT_CAPACITY];
        size  = 0;
    }


    /**
     * Generates a hash index for a given key.
     *
     * @param key the key to hash
     * @return a valid index within the table array
     */

    // ── Hash helper ───────────────────────────────────────────────────────
    private int hash(K key) {
        // Keys must not be null
        return Math.abs(key.hashCode()) % table.length;
    }




    /**
     * Associates the specified value with the specified key in this map.
     * If the map previously contained a mapping for the key, the old value is replaced.
     *
     * @param key   key with which the specified value is to be associated
     * @param value value to be associated with the specified key
     * @return the previous value associated with key, or null if there was no mapping
     */

    // Put compares keys with equals() instead of == because == compares 2 objects that are exactly the same in the location of memory while equals() only compares if the keys are equalavent based on value.
    // Remove uses iterator instead of a for each loop because iterator is more safe to delete values as doing so in a for each loop causes a ConcurrentModificationException error.
    public V put(K key, V value) {

        int index = hash(key);


        if (table[index] == null){
            table[index] = new LinkedList<Entry<K, V>>();
        }


        for (Entry<K,V> entry : table[index]) {
            if (entry.key.equals(key)){
                V oldValue = entry.value;
                entry.value = value;
                return oldValue;
            }
        }

        table[index].addFirst(new Entry<>(key, value));
        size++;
        return null;
    }

    /**
     * Returns the value to which the specified key is mapped.
     *
     * @param key the key whose associated value is to be returned
     * @return the value to which the specified key is mapped, or null if no mapping exists
     */

    // ── get ───────────────────────────────────────────────────────────────
    public V get(K key) {

        int index = hash(key);

        if (table[index] == null) {
            return null;
        }

        for (Entry<K, V> entry : table[index]) {
            if (entry.key.equals(key)){
                return entry.value;
            }
        }

        return null; 
    }

    /**
     * Determines whether the provided key exists in the map.
     *
     * @param key the key to evaluate
     * @return true if the key is present; false otherwise
     */

    // ── containsKey ───────────────────────────────────────────────────────
    public boolean containsKey(K key) {
        int index = hash(key);

        if (table[index] == null) {
            return false;
        }

        for (Entry<K, V> entry : table[index]) {
            if (entry.key.equals(key)){
                return true;
            }
        }

        return false;

    }

    /**
     * Removes the mapping for a key from this map if it is present.
     *
     * @param key key whose mapping is to be removed from the map
     * @return the previous value associated with key, or null if there was no mapping
     */

    // ── remove ────────────────────────────────────────────────────────────
    public V remove(K key) {

        int index = hash(key);

        if (table[index] == null) {
            return null;
        }

        Iterator<Entry<K, V>> it = table[index].iterator();
        while (it.hasNext()) {
            Entry<K, V> entry = it.next();
            if (entry.key.equals(key)) {
                V oldValue = entry.value;
                it.remove();
                size--;
                return oldValue;
            }
        }

        return null;
    }

    /**
     * Returns the number of key-value mappings in this map.
     *
     * @return the number of entries in the map
     */

    // ── size ──────────────────────────────────────────────────────────────
    public int size() {
        return size;
    }

    /**
     * Returns true if this map contains no key-value mappings.
     *
     * @return true if the map is empty; false otherwise
     */

    // ── isEmpty ───────────────────────────────────────────────────────────
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Entry point for the program. Tests the MyHashMap implementation.
     *
     * @param args command-line arguments
     */

    // ── Test driver ───────────────────────────────────────────────────────
    public static void main(String[] args) {
        MyHashMap<String, Integer> map = new MyHashMap<>();

        // put -- basic insertions
        map.put("cat", 1);
        map.put("dog", 2);
        map.put("rat", 3);
        map.put("bat", 4);
        map.put("ant", 5);
        System.out.println("Size after 5 insertions: " + map.size());   // 5

        // get -- existing keys
        System.out.println("get(cat): " + map.get("cat"));              // 1
        System.out.println("get(bat): " + map.get("bat"));              // 4

        // get -- missing key
        System.out.println("get(owl): " + map.get("owl"));              // null

        // put -- duplicate key (update)
        map.put("cat", 99);
        System.out.println("get(cat) after update: " + map.get("cat")); // 99
        System.out.println("Size after update: " + map.size());         // 5

        // containsKey
        System.out.println("containsKey(dog): " + map.containsKey("dog")); // true
        System.out.println("containsKey(elk): " + map.containsKey("elk")); // false

        // remove -- existing key
        map.remove("dog");
        System.out.println("get(dog) after remove: " + map.get("dog")); // null
        System.out.println("Size after remove: " + map.size());         // 4

        // remove -- missing key
        map.remove("owl");
        System.out.println("Size after removing missing key: " + map.size()); // 4

        // null value -- a key can legitimately map to null
        map.put("fox", null);
        System.out.println("get(fox): " + map.get("fox"));              // null
        System.out.println("containsKey(fox): " + map.containsKey("fox")); // true
        System.out.println("Size with null value: " + map.size());      // 5

        // forced collision -- "AaAaAa" and "BBBaaa" have the same hashCode in Java
        MyHashMap<String, Integer> collisionMap = new MyHashMap<>();
        collisionMap.put("AaAaAa", 1);
        collisionMap.put("BBBaaa", 2);
        System.out.println("Collision get(AaAaAa): " + collisionMap.get("AaAaAa")); // 1
        System.out.println("Collision get(BBBaaa): " + collisionMap.get("BBBaaa")); // 2
        collisionMap.remove("AaAaAa");
        System.out.println("After remove, get(BBBaaa): " + collisionMap.get("BBBaaa")); // 2
        System.out.println("After remove, size: " + collisionMap.size()); // 1
    }
}
