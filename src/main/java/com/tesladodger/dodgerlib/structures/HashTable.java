package com.tesladodger.dodgerlib.structures;


/**
 * Hash table to map keys to values.
 * @param <K>
 * @param <V>
 */
public class HashTable<K, V> {

    /**
     * Each element in the table points to the root of a basic linked list, usually called bucket. In
     * case of a hash collision, the root is replaced and the new node points to it.
     * @param <K>
     * @param <V>
     */
    private static class Node<K, V> {
        K key;
        V value;
        Node<K, V> next;

        Node (K key, V value, Node<K, V> next) {
            this.key = key;
            this.value = value;
            this.next = next;
        }
    }

    /* Size of the array. It's also used as modulo for hashing. */
    private int capacity;

    /* If number of elements exceeds this percentage of the capacity the array is resized. */
    private final float FULLNESS_THRESHOLD;

    /* Number of elements in the table. */
    private int count;

    /* Array of 'buckets' */
    private Node<K, V>[] table;

    /* List of prime numbers */
    private static final int[] primes = {11, 53, 101, 251, 503, 1009, 1499, 2069, 3001, 4001, 5003};

    /**
     * Constructor with default values: capacity of 11 and threshold of 75%.
     */
    public HashTable () {
        this(11, 0.75f);
    }

    /**
     * Constructor with specified capacity and default threshold of 75%.
     * @param capacity specified size of the array;
     */
    public HashTable (int capacity) {
        this(capacity, 0.75f);
    }

    /**
     * Constructor with specified value.
     * @param capacity specified size of the array;
     * @param FULLNESS_THRESHOLD specified threshold;
     */
    public HashTable (int capacity, float FULLNESS_THRESHOLD) {
        if (capacity < 1) capacity = 1;
        this.capacity = capacity;
        this.FULLNESS_THRESHOLD = FULLNESS_THRESHOLD;

        // noinspection unchecked
        table = ( Node<K, V> [] ) new Node[capacity];
    }

    /**
     * Inserts a new element in the table.
     * @param key of the new element;
     * @param value of the new element;
     */
    public void put (K key, V value) {
        if (key == null) throw new NullPointerException("Key cannot be null");

        int i = hash(key);

        // Check for duplicate keys in the bucket.
        Node<K, V> current = table[i];
        while (current != null) {
            if (current.key.equals(key)) {
                throw new IllegalArgumentException("Duplicate keys are not allowed");
            }
            current = current.next;
        }

        table[i] = new Node<>(key, value, table[i]);
        count++;

        if (capacity <= 5003 && (float) count / capacity > FULLNESS_THRESHOLD) {
            resize();
        }
    }

    /**
     * Returns the value of an element in the list.
     * @param key of that element;
     * @return value or null;
     */
    public V get (K key) {
        if (key == null) throw new NullPointerException("Key cannot be null");

        int i = hash(key);

        Node<K, V> current = table[i];
        while (current != null) {
            if (current.key.equals(key)) {
                return current.value;
            }
            current = current.next;
        }
        return null;
    }

    /**
     * Removes an element from the list.
     * @param key of that element;
     * @return value of the element or null;
     */
    public V remove (K key) {
        if (key == null) throw new NullPointerException("Key cannot be null");

        int i = hash(key);

        Node<K, V> current = table[i];
        if (current.key.equals(key)) {
            table[i] = current.next;
            count--;
            return current.value;
        }
        while (current.next != null) {
            if (current.next.key.equals(key)) {
                V toRet = current.next.value;
                current.next = current.next.next;
                count--;
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
        Node<K, V>[] newTable = new Node[capacity];

        // Populate the new table.
        for (Node<K, V> entry : table) {
            if (entry == null) continue;

            Node<K, V> current = entry;
            while (current != null) {
                int i = hash(current.key);
                Node<K, V> temp = current.next;
                current.next = newTable[i];
                newTable[i] = current;
                current = temp;
            }
        }
        table = newTable;
    }

    /**
     * Return whether are any values in the table.
     * @return true if empty;
     */
    public boolean isEmpty () {
        return count == 0;
    }

    /**
     * Return the number of elements in this table.
     * @return number of elements;
     */
    public int size () {
        return count;
    }

    // ------------------------------------------------------------------------ Hashing methods //
    /**
     * Takes a generic key and calls the appropriate hashing methods for the type.
     * @param key to be hashed;
     * @return hash (int);
     */
    private int hash (K key) {
        if (key instanceof String) {
            return hash((String) key);
        }
        else if (key instanceof Integer) {
            return hash((Integer) key);
        }
        else if (key instanceof Double) {
            return hash((Double) key);
        }
        else if (key instanceof Float) {
            return hash((Float) key);
        }
        return 0;
    }

    private int hash (String key) {
        int sum = 0;
        for (int i = 0; i < key.length(); i++) {
            sum += (int) key.charAt(i) * (i+1);
        }
        return sum % capacity;
    }

    private int hash (Integer key) {
        return key % capacity;
    }

    private int hash (Double key) {
        // Four decimal points should be enough, I don't know.
        while (key < 1000) {
            key *= 10;
        }
        return key.intValue() % capacity;
    }

    private int hash (Float key) {
        while (key < 100) {
            key *= 10;
        }
        return key.intValue() % capacity;
    }

}
