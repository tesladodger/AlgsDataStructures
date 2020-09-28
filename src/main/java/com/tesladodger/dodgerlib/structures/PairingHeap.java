package com.tesladodger.dodgerlib.structures;

import java.util.NoSuchElementException;


/**
 * Pairing Heap using generic comparable keys.
 *
 * @param <K> key type;
 * @param <V> data type;
 */
public class PairingHeap<K extends Comparable<K>, V> {

    private static final class Node<K, V> {
        K key;
        V value;

        Node<K, V> child;
        Node<K, V> right;

        Node (K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    private Node<K, V> root;
    public enum Type {MIN, MAX}
    private final Type type;
    private int size;


    /**
     * Constructor.
     *
     * @param type specify min or max priority;
     */
    public PairingHeap (Type type) {
        this.type = type;
        root = null;
        size = 0;
    }

    /**
     * Default constructor, min priority.
     */
    public PairingHeap () {
        this(Type.MIN);
    }


    /**
     * Return the value of the element with most priority (which is the root),
     * without deleting it.
     *
     * @return value of the root;
     */
    public V peek () {
        if (isEmpty()) throw new NoSuchElementException("The Heap is empty.");
        return root.value;
    }

    /**
     * Merges two sub-heaps by priority, assuming the first and second nodes are roots.
     * In case of merging after deleting, the caller is responsible for establishing the new root
     * and deleting the right references.
     *
     * @param first root node;
     * @param second root node;
     *
     * @return new root;
     */
    private Node<K, V> merge (Node<K, V> first, Node<K, V> second) {
        if (second == null) return first;
        if (first == null) return second;
        int comp = first.key.compareTo(second.key);
        if ((type == Type.MAX && comp > 0) || (type == Type.MIN && comp < 0)) {
            if (first.child != null)
                second.right = first.child;
            first.child = second;
            return first;
        } else {
            if (second.child != null)
                first.right = second.child;
            second.child = first;
            return second;
        }
    }

    /**
     * Merge the children of a node.
     *
     * @param parent whose children are to be merged;
     *
     * @return highest priority child;
     */
    private Node<K, V> mergeChildPairs (Node<K, V> parent) {
        if (parent.child == null)
            return null;

        // Make a list with the children of parent
        LinkedList<Node<K, V>> list = new LinkedList<>();
        Node<K, V> current = parent.child;
        do {
            list.add(current);
        } while ((current = current.right) != null);

        // Merge first two of the list and put them on the end. Do until only one element left.
        while (list.size() > 1) {
            list.get(0).right = null;
            list.get(1).right = null;
            list.add(merge(list.get(0), list.get(1)));
            list.remove(0);
            list.remove(0);
        }

        return list.get(0);
    }

    /**
     * Inserts a new node into the heap.
     *
     * @param key of the new node;
     * @param value of the new node (can be null);
     */
    public void insert (K key, V value) {
        if (key == null) throw new IllegalArgumentException("Key cannot be null.");
        root = merge(root, new Node<>(key, value));
        size++;
    }

    /**
     * Removes the root of the heap and returns its value.
     *
     * @return value of the root;
     */
    public V pop () {
        if (isEmpty()) throw new NoSuchElementException("The heap is empty");

        V result = root.value;
        root = mergeChildPairs(root);
        size--;

        return result;
    }

    /**
     * Static method to clone a PairingHeap.
     *
     * @param heap to clone;
     * @param <K> key type;
     * @param <V> value type;
     *
     * @return new heap with same keys and values as 'heap' param;
     */
    public static <K extends Comparable<K>, V> PairingHeap<K, V> clone (PairingHeap<K, V> heap) {
        PairingHeap<K, V> result = new PairingHeap<>(heap.type);
        return cloneRecursive(heap.root, result);
    }

    /**
     * Recursively traverses a heap and copies the keys and data to another heap.
     *
     * @param current node of the heap that's being cloned;
     * @param clone resulting heap;
     * @param <K> key type;
     * @param <V> value type;
     *
     * @return clone heap;
     */
    private static <K extends Comparable<K>, V> PairingHeap<K, V> cloneRecursive (Node<K, V> current, PairingHeap<K, V> clone) {
        if (current != null) {
            cloneRecursive(current.child, clone);
            cloneRecursive(current.right, clone);
            clone.insert(current.key, current.value);
        }
        return clone;
    }

    /**
     * A logically empty heap is one with a null root.
     *
     * @return true if empty;
     */
    public boolean isEmpty () {
        return root == null;
    }

    /**
     * Logically clear the heap by nullifying its root. The garbage collection takes care of the
     * rest.
     */
    public void clear () {
        size = 0;
        root = null;
    }

    /**
     * Number of elements in the heap.
     *
     * @return size of the heap;
     */
    public int size () {
        return size;
    }

}
