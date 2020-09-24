package com.tesladodger.dodgerlib.structures;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;


/**
 * Pairing Heap using generic comparable keys.
 * Ties are resolved in LIFO manner, for inserting and popping. This fails on merged lists, since
 * there is no way of knowing the insertion order.
 *
 * @param <K>
 * @param <V>
 */
public class PairingHeap_old<K extends Comparable<K>, V> {

    private final class PNode {
        K key;
        V value;
        short n;

        PNode child;
        PNode right;

        /**
         * Node constructor.
         * @param key generic comparable type for the priority queue;
         * @param value generic type, can be null;
         * @param n insertion number, to solve ties in LIFO;
         */
        PNode (K key, V value, short n) {
            this.key = key;
            this.value = value;
            this.n = n;
        }
    }

    private PNode root;
    public enum Type {MIN, MAX}
    private final Type type;
    private int counter;
    private short n;


    /**
     * Constructor for an empty heap.
     *
     * @param type specify min or max priority queue;
     */
    public PairingHeap_old (Type type) {
        this.type = type;
        root = null;
        counter = 0;
        n = 0;
    }


    /**
     * Find first element and return data without deleting it.
     * @return data of the root node;
     */
    public V peek () {
        if (this.isEmpty()) throw new NoSuchElementException("The heap is empty.");
        return root.value;
    }

    /**
     * Peek the key of the first element.
     * @return best key;
     */
    public K peekKey () {
        if (this.isEmpty()) throw new NoSuchElementException("The heap is empty.");
        return root.key;
    }

    /**
     * Calls the recursive function that clones the heap.
     * @return new heap;
     */
    public PairingHeap_old<K, V> cloneHeap() {
        if (isEmpty()) throw new NullPointerException("The heap is empty.");
        return cloneRecursively(root, new PairingHeap_old<>(this.type));
    }

    /**
     * Traverses the heap recursively and inserts every node in the new heap.
     * @param current node being evaluated;
     * @param newHeap to return;
     * @return new heap;
     */
    private PairingHeap_old<K, V> cloneRecursively (PNode current, PairingHeap_old<K, V> newHeap) {
        if (current != null) {
            cloneRecursively(current.child, newHeap);
            cloneRecursively(current.right, newHeap);
            newHeap.insert(current.key, current.value);
        }
        return newHeap;
    }

    /**
     * Merge two heaps. If either is null, return the other. Otherwise, make the highest priority
     * root the new root, add the worse heap as a child of the new root, make the rest of the best
     * heap the right of the original worse root.
     * The two heaps are cloned before merging, so the original heaps are not affected.
     * @param heap1 first heap;
     * @param heap2 second heap;
     * @return merged heap;
     */
    public static <K extends Comparable<K>, V> PairingHeap_old<K, V>
                merge (PairingHeap_old<K, V> heap1, PairingHeap_old<K, V> heap2) {

        if (heap1.type != heap2.type) throw new IllegalArgumentException("Cannot merge heaps of " +
                "different types.");
        PairingHeap_old<K, V> clone1 = heap1.cloneHeap();
        PairingHeap_old<K, V> clone2 = heap2.cloneHeap();

        // First heap is higher priority.
        if ((clone1.root.key.compareTo(clone2.root.key) > 0 && clone1.type == Type.MAX) ||
                 (clone1.root.key.compareTo(clone2.root.key) < 0 && clone1.type == Type.MIN)) {
            clone2.root.right = clone1.root.child;
            clone1.root.child = clone2.root;
            clone1.counter += clone2.counter;
            return clone1;
        }
        // Second heap is higher or equal priority.
        else {
            clone1.root.right = clone2.root.child;
            clone2.root.child = clone1.root;
            clone2.counter += clone1.counter;
            return clone2;
        }
    }

    /**
     * Inserts a new node in the heap.
     * @param key of the new node;
     * @param data of the new node, can be null;
     */
    public void insert (K key, V data) {
        if (key == null) throw new IllegalArgumentException("Key cannot be null.");
        PNode newNode = new PNode(key, data, n);
        n++;
        if (isEmpty()) {
            root = newNode;
        }
        // New node has lower priority.
        else if ((newNode.key.compareTo(root.key) < 0 && type == Type.MAX) ||
                 (newNode.key.compareTo(root.key) > 0 && type == Type.MIN)) {
            newNode.right = root.child;
            root.child = newNode;
        }
        // New node has higher or equal priority.
        else {
            newNode.child = root;
            root = newNode;
        }
        counter++;
    }

    /**
     * Pops first element;
     * @return data of the element;
     */
    public V pop () {
        if (isEmpty()) throw new NoSuchElementException("Heap is empty.");

        V toRet = root.value;

        if (root.child == null) {
            root = null;
            counter = 0;
            return toRet;
        }
        else {
            // Make a list with children of root.
            List<PNode> list = new ArrayList<>();
            list.add(root.child);
            PNode temp = root.child;
            while (temp.right != null) {
                list.add(temp.right);
                temp = temp.right;
            }

            // Multi pass: merge first two, put them in the end. Repeat until only one left.
            while (list.size() > 1) {
                // If first on the list is better.
                if ((list.get(0).key.compareTo(list.get(1).key) > 0 && type == Type.MAX) ||
                        (list.get(0).key.compareTo(list.get(1).key) < 0 && type == Type.MIN) ||
                        (list.get(0).key == list.get(1).key && list.get(0).n > list.get(1).n)) {
                    // Make it the root.
                    root = list.remove(0);
                }
                else {
                    root = list.remove(1);
                }
                // Remove right link.
                root.right = null;

                // Insert the second best in the heap (first on the list after removing the best).
                list.get(0).right = root.child;
                root.child = list.remove(0);

                // Add the new heap to the end of the list.
                list.add(root);
            }
            root = list.remove(0);
        }

        counter--;
        return toRet;
    }

    /**
     * Calls the recursive method to search for keys.
     * @param key to find;
     * @return boolean;
     */
    public boolean containsKey (K key) {
        if (key == null) throw new IllegalArgumentException("Key cannot be null");
        return searchKeyRecursively(root, key) != null;
    }

    /**
     * Traverses the tree recursively, taking advantage of its properties: no need to go further
     * if the current key is worse priority than the one being searched for.
     * @param current node being checked;
     * @param key to find;
     * @return node with that key or null;
     */
    private PNode searchKeyRecursively (PNode current, K key) {
        if (current != null) {
            if (current.key.compareTo(key) == 0) return current;
            if ( (type == Type.MAX && current.key.compareTo(key) > 0) || (type == Type.MIN && current.key.compareTo(key) < 0) ) {
                searchKeyRecursively(current.child, key);
            }
            searchKeyRecursively(current.right, key);
        }
        return null;
    }

    /**
     * Calls the recursive method to search for a value. A value can be present multiple times, if
     * any one is found the method returns true.
     * @param value to search;
     * @return true if the value was found;
     */
    public boolean containsValue (V value) {
        if (value == null) throw new IllegalArgumentException("Cannot search for a null value.");
        return searchValueRecursively(root, value) != null;
    }

    /**
     * Traverses the tree recursively until the value being searched for is found or all nodes are
     * visited.
     * @param current being visited;
     * @param value to search;
     * @return first node found to contain that value;
     */
    private PNode searchValueRecursively (PNode current, V value) {
        if (current != null) {
            if (current.value.equals(value)) return current;
            // Search the right first, a node with a better key is more likely to be searched.
            // (I think, I have no idea)
            searchValueRecursively(current.right, value);
            searchValueRecursively(current.child, value);
        }
        return null;
    }

    /**
     * Logically clear the heap by nullifying its root.
     */
    public void clear () {
        root = null;
        counter = 0;
        n = 0;
    }

    /**
     * Check if this heap is empty.
     * @return boolean true if empty;
     */
    public boolean isEmpty () {
        return root == null;
    }

    public int size () {
        return counter;
    }

}
