package com.tesladodger.dodgerlib.structures;


/**
 * Hash table to map keys to values.
 *
 * @param <K>
 * @param <V>
 */
public class HashTable<K, V> {

    /**
     * Each element in the table points to the root of a basic linked list, usually called bucket. In
     * case of a hash collision, the root is replaced and the new node points to it.
     *
     * @param <K>
     * @param <V>
     */
    private static class Node<K, V> {
        K key;
        V value;
        Node<K, V> next;

        private Node (K key, V value, Node<K, V> next) {
            this.key = key;
            this.value = value;
            this.next = next;
        }
    }

    /** Size of the array. It's also used as modulo for hashing. */
    private int capacity;

    /** Default initial capacity. */
    private static final int DEFAULT_INITIAL_CAPACITY = 11;

    /** If number of elements exceeds this percentage of the capacity the array is resized. */
    private final float FULLNESS_THRESHOLD;

    private static final float DEFAULT_FULLNESS_THRESHOLD = .75f;

    /** Number of elements in the table. */
    private int size;

    /** Array of 'buckets'. */
    private Node<K, V>[] table;

    /** List of prime numbers. */
    private static final int[] primes = {11, 53, 101, 251, 503, 1009, 1499, 2069, 3001, 4001, 5003};

    /**
     * Constructor with default values: capacity of 11 and threshold of 75%.
     */
    public HashTable () {
        this(DEFAULT_INITIAL_CAPACITY, DEFAULT_FULLNESS_THRESHOLD);
    }

    /**
     * Constructor with specified capacity and default threshold of 75%.
     *
     * @param capacity specified size of the array;
     */
    public HashTable (int capacity) {
        this(capacity, DEFAULT_FULLNESS_THRESHOLD);
    }

    /**
     * Constructor with specified value.
     *
     * @param capacity specified size of the array;
     * @param threshold specified threshold;
     */
    @SuppressWarnings("unchecked")
    public HashTable (int capacity, float threshold) {
        if (capacity < 1) capacity = 1;
        this.capacity = capacity;
        this.FULLNESS_THRESHOLD = threshold;
        table = ( Node<K, V>[] ) new Node[capacity];
    }

    /**
     * Inserts a new element in the table. When an existing key is provided, the corresponding
     * value is updated.
     *
     * @param key of the new element;
     * @param value of the new element;
     *
     * @throws IllegalArgumentException if the provided key is null;
     */
    public void put (K key, V value) {
        if (key == null) throw new IllegalArgumentException("Key cannot be null");

        int i = hash(key, capacity);

        // Check for duplicate keys in the bucket.
        Node<K, V> current = table[i];
        while (current != null) {
            if (current.key.equals(key)) {
                current.value = value;
                return;
            }
            current = current.next;
        }

        table[i] = new Node<>(key, value, table[i]);
        size++;

        if (capacity <= 5003 && (float) size / capacity > FULLNESS_THRESHOLD)
            resize();
    }

    /**
     * Returns the value of an element in the list.
     *
     * @param key of that element;
     *
     * @return value or null if the element is not present;
     *
     * @throws IllegalArgumentException if the provided key is null;
     */
    public V get (K key) {
        if (key == null) throw new IllegalArgumentException("Key cannot be null");

        int i = hash(key, capacity);

        Node<K, V> current = table[i];
        while (current != null) {
            if (current.key.equals(key))
                return current.value;
            current = current.next;
        }
        return null;
    }

    /**
     * Removes an element from the list.
     *
     * @param key of that element;
     *
     * @return value of the element or null, if the element is not present;
     *
     * @throws IllegalArgumentException if the provided key is null;
     */
    public V remove (K key) {
        if (key == null) throw new IllegalArgumentException("Key cannot be null");

        int i = hash(key, capacity);

        Node<K, V> current = table[i];
        if (current.key.equals(key)) {
            table[i] = current.next;
            size--;
            return current.value;
        }
        while (current.next != null) {
            if (current.next.key.equals(key)) {
                V toRet = current.next.value;
                current.next = current.next.next;
                size--;
                return toRet;
            }
            current = current.next;
        }
        return null;
    }

    /**
     * Method to resize the internal array when the 'fullness' surpasses the threshold.
     */
    private void resize () {
        // Select the next prime on the list.
        for (int prime : primes) {
            if (prime > capacity) {
                capacity = prime;
                break;
            }
        }

        // noinspection unchecked
        Node<K, V>[] newTable = (Node<K, V>[]) new Node[capacity];

        // Populate the new table with new hashes.
        for (Node<K, V> entry : table) {
            if (entry != null) {
                Node<K, V> current = entry;
                while (current != null) {
                    int i = hash(current.key, capacity);
                    Node<K, V> temp = current.next;
                    current.next = newTable[i];
                    newTable[i] = current;
                    current = temp;
                }
            }
        }
        table = newTable;
    }

    /**
     * {@link LinkedList} with the keys present in the table.
     *
     * @return list of the keys in the table;
     */
    public LinkedList<K> keys () {
        LinkedList<K> result = new LinkedList<>();
        for (Node<K, V> n : table) {
            if (n != null) {
                Node<K, V> c = n;
                do {
                    result.add(c.key);
                } while ((c = c.next) != null);
            }
        }
        return result;
    }

    /**
     * {@link LinkedList} with the values present in the table.
     *
     * @return list of values in the table;
     */
    public LinkedList<V> values () {
        LinkedList<V> result = new LinkedList<>();
        for (Node<K, V> n : table) {
            if (n != null) {
                Node<K, V> c = n;
                do {
                    result.add(c.value);
                } while ((c = c.next) != null);
            }
        }
        return result;
    }

    /**
     * Clear the table.
     */
    @SuppressWarnings("unchecked")
    public void clear () {
        table = (Node<K, V> []) new Node[capacity];
        size = 0;
    }

    /**
     * Return whether are any values in the table.
     *
     * @return true if empty;
     */
    public boolean isEmpty () {
        return size == 0;
    }

    /**
     * Return the number of elements in this table.
     *
     * @return number of elements;
     */
    public int size () {
        return size;
    }

    /**
     * Returns the hash of a key modulus the capacity of the table, which will be its index.
     *
     * @param <K> type of the key;
     * @param key to be hashed;
     * @param capacity of the table;
     *
     * @return hash (int);
     */
    private static <K> int hash (K key, int capacity) {
        int hash = Math.abs(key.hashCode());
        return hash % capacity;
    }

}
