package com.tesladodger.dodgerlib.structures;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;


/**
 * Contains the methods common to all tree com.tesladodger.dodgerlib.structures, mostly non-destructive methods.
 * @param <K>
 * @param <V>
 */
abstract class AbstractTree<K extends Comparable<K>, V> implements Iterable <V> {

    class Node {
        K key;
        V value;

        Node parent = null;
        Node left = null;
        Node right = null;

        Node (K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    Node root;

    /* Counter for number of elements in the tree. */
    int counter;

    @Override
    public Iterator<V> iterator () {
        return new TreeIterator<>(this.traverse());
    }

    // ------------------------------------------------------------------------ Search methods //
    /**
     * Public method that calls the iterative search method.
     * @param key to find;
     * @return value of that key;
     */
    public V find (K key) {
        if (isEmpty()) throw new NoSuchElementException("The tree is empty.");
        return findIteratively(key).value;
    }

    /**
     * Traverses the tree iteratively until it finds the key.
     * Throws an error if the key is not present.
     * @param key to find;
     * @return node to be found;
     */
     Node findIteratively (K key) {
        Node current = root;
        while (current != null) {
            if (key.compareTo(current.key) < 0) {
                current = current.left;
            }
            else if (key.compareTo(current.key) > 0) {
                current = current.right;
            }
            else {
                return current;
            }
        }
        throw new NoSuchElementException("The key " + key + " is not in the tree.");
    }

    /**
     * I'm not proud of this.
     * @param key to your heart;
     * @return 42;
     */
    public boolean containsKey (K key) {
        try { find(key); }
        catch (NoSuchElementException e) { return false; }
        return true;
    }

    /**
     * Returns the minimum (left-most) node in the subtree of current.
     * @param current subtree's root;
     * @return left-most node;
     */
    Node getMin (Node current) {
        while (current.left != null) current = current.left;
        return current;
    }

    /**
     * Returns the maximum (right-most) node in the subtree of current.
     * @param current subtree's root;
     * @return right-most node;
     */
    Node getMax (Node current) {
        while (current.right != null) current = current.right;
        return current;
    }

    public V findMin () {
        if (isEmpty()) throw new NoSuchElementException("The tree is empty.");
        return getMin(root).value;
    }

    public V findMax () {
        if (isEmpty()) throw new NoSuchElementException("The tree is empty.");
        return getMax(root).value;
    }


    // ------------------------------------------------------------------------ Traverse methods //
    /**
     * Calls the recursive traverse method.
     * @return sorted list of values;
     */
    public List<V> traverse () {
        if (isEmpty()) throw new NoSuchElementException("Tree is empty.");
        List<V> sorted = new ArrayList<>();
        sorted = traverseRecursively(root, sorted);
        return sorted;
    }

    /**
     * Recursive traverse.
     * @param current node being evaluated;
     * @param sorted list of values;
     * @return sorted list;
     */
    private List<V> traverseRecursively (Node current, List<V> sorted) {
        if (current != null) {
            traverseRecursively(current.left, sorted);
            sorted.add(current.value);
            traverseRecursively(current.right, sorted);
        }
        return sorted;
    }


    public void clear () {
        root = null;
        counter = 0;
    }

    public boolean isEmpty () {
        return root == null;
    }

    public int size () {
        return counter;
    }

}

class TreeIterator<V> implements Iterator<V> {

    private final List<V> list;
    private int current;

    public TreeIterator (List<V> list) {
        this.list = list;
        current = 0;
    }

    @Override
    public boolean hasNext() {
        return current < list.size();
    }

    @Override
    public V next() {
        V data = list.get(current);
        current++;
        return data;
    }
}
