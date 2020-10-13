package com.tesladodger.dodgerlib.structures;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;


/**
 * Contains the methods common to all tree structures , mostly non-destructive methods.
 *
 * @param <K>
 * @param <V>
 */
abstract class AbstractTree<K extends Comparable<K>, V> implements Iterable <V> {

    /**
     * Class that holds an element of the tree.
     *
     * @param <K> key type;
     * @param <V> value type;
     */
    protected static class Node<K, V> {
        K key;
        V value;

        Node<K, V> parent = null;
        Node<K, V> left = null;
        Node<K, V> right = null;

        protected Node (K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    /** Entry point to the tree. */
    protected Node<K, V> root;

    /** Number of elements in the tree. */
    protected int size;

    /**
     * Public method that calls the iterative search method.
     *
     * @param key to find;
     *
     * @return value of that key;
     */
    public V find (K key) {
        if (isEmpty()) throw new NoSuchElementException("The tree is empty.");
        return findIteratively(key).value;
    }

    /**
     * Traverses the tree iteratively until it finds the key.
     * Throws an error if the key is not present.
     *
     * @param key to find;
     *
     * @return node to be found;
     */
     protected Node<K, V> findIteratively (K key) {
        Node<K, V> current = root;
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
     *
     * @param key to your heart;
     *
     * @return true if the tree contains the key;
     */
    public boolean containsKey (K key) {
        try { find(key); }
        catch (NoSuchElementException e) { return false; }
        return true;
    }

    /**
     * Returns the minimum (left-most) node in the subtree of current.
     *
     * @param current subtree's root;
     *
     * @return left-most node;
     */
    protected Node<K, V> getMin (Node<K, V> current) {
        while (current.left != null) current = current.left;
        return current;
    }

    /**
     * Returns the maximum (right-most) node in the subtree of current.
     *
     * @param current subtree's root;
     *
     * @return right-most node;
     */
    protected Node<K, V> getMax (Node<K, V> current) {
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

    /**
     * Calls the recursive traverse method.
     *
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
     *
     * @param current node being evaluated;
     * @param sorted list of values;
     *
     * @return sorted list;
     */
    private List<V> traverseRecursively (Node<K, V> current, List<V> sorted) {
        if (current != null) {
            traverseRecursively(current.left, sorted);
            sorted.add(current.value);
            traverseRecursively(current.right, sorted);
        }
        return sorted;
    }

    /**
     * Replace a node with its child (can be null).
     *
     * @param n node being replaced;
     * @param child node to replace D's place in the parent;
     */
    protected void replaceNode (Node<K, V> n, Node<K, V> child) {
        if (n.parent != null) {
            if (n.parent.left == n)
                n.parent.left = child;
            else
                n.parent.right = child;
            if (child != null)
                child.parent = n.parent;
        }
        // Null parent means D was the root.
        else {
            root = child;
            if (child != null)
                child.parent = null;
        }
    }

    /**
     * Provide an iterator for the structure.
     *
     * @return TreeIterator;
     */
    @NotNull
    @Override
    public Iterator<V> iterator () {
        return new TreeIterator<>(this.traverse());
    }

    /**
     * Iterator for the tree.
     *
     * @param <V>
     */
    protected static class TreeIterator<V> implements Iterator<V> {

        private final List<V> list;
        private int current;

        public TreeIterator (List<V> list) {
            this.list = list;
            current = 0;
        }

        @Override
        public boolean hasNext () {
            return current < list.size();
        }

        @Override
        public V next () {
            V data = list.get(current);
            current++;
            return data;
        }
    }

    public void clear () {
        root = null;
        size = 0;
    }

    public boolean isEmpty () {
        return root == null;
    }

    public int size () {
        return size;
    }

}
