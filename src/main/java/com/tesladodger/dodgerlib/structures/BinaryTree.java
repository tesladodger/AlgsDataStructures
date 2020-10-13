package com.tesladodger.dodgerlib.structures;

import java.util.NoSuchElementException;


/**
 * Binary search tree implementation.
 * This is only useful for sorting, since there's no balancing mechanism. When a node is deleted,
 * it's generally replaced with it's in-order successor, meaning the tree will be unbalanced after
 * some deletions.
 * I really don't like deleting elements in this tree, it's way too complex...
 * On the other hand, traversing (which returns a sorted List) is very fast.
 * Traversing is the only recursive method.
 *
 * @param <K>
 * @param <V>
 */
public class BinaryTree<K extends Comparable<K>, V> extends AbstractTree<K, V> {

    public BinaryTree () {
        root = null;
        size = 0;
    }

    /**
     * Public method to call the recursive insertion method.
     * If this tree is empty, create the root.
     *
     * @param key new key;
     * @param value new value;
     */
    public void insert (K key, V value) {
        if (isEmpty()) {
            root = new Node<>(key, value);
            root.parent = null;
        }
        else {
            insertIteratively(key, value);
        }
        size++;
    }

    /**
     * Traverses the tree until it finds an empty spot for the new node.
     *
     * @param key new key;
     * @param value new value;
     */
    private void insertIteratively (K key, V value) {
        Node<K, V> current = root;

        while (true) {
            if (key.compareTo(current.key) < 0) {
                if (current.left == null) {
                    current.left = new Node<>(key, value);
                    current.left.parent = current;
                    break;
                }
                current = current.left;
            }
            else if (key.compareTo(current.key) > 0) {
                if (current.right == null) {
                    current.right = new Node<>(key, value);
                    current.right.parent = current;
                    break;
                }
                current = current.right;
            }
            else {
                current.value = value;
                size--;
                break;
            }
        }

    }

    /**
     * Removes a node and returns its data.
     *
     * @param key of the node to delete;
     *
     * @return data represented by the deleted node;
     */
    public V remove (K key) {
        V result;
        if (isEmpty()) throw new NoSuchElementException("The tree is empty.");
        // Find the node to delete.
        Node<K, V> D = findIteratively(key);
        result = D.value;
        // If either left or right are null, replace D with the other, which may be null.
        if (D.left == null)
            replaceNode(D, D.right);
        else if (D.right == null)
            replaceNode(D, D.left);
        // D's children are both not null.
        else {
            // Find in-order successor.
            Node<K, V> E = getMin(D.right);
            // If D's successor is its right child. Wikipedia's algorithm fails in this case.
            if (E == D.right) {
                // Replace D's data with E's.
                D.value = E.value;
                D.key = E.key;
                // D's right subtree is now E's right subtree, thus deleting D.
                D.right = E.right;
                // Update the parent.
                if (D.right != null) {
                    D.right.parent = D;
                }
            }
            // D's successor is further down in its right subtree.
            else {
                // Replace E's place in its parent with E's right subtree, which can be null.
                E.parent.left = E.right;
                // Update E's right subtree's parent, avoiding null pointer.
                if (E.right != null) {
                    E.right.parent = E.parent;
                }
                // Replace D's values with E's.
                D.value = E.value;
                D.key = E.key;
            }
        }
        size--;
        return result;
    }

    /**
     * Find the min value and remove it.
     *
     * @return min value;
     */
    public V popMin () {
        if (isEmpty()) throw new NoSuchElementException("The tree is empty.");
        Node<K, V> min = getMin(root);
        V toRet = min.value;
        remove(min.key);
        return toRet;
    }

    /**
     * Find the min value and remove it.
     *
     * @return min value;
     */
    public V popMax () {
        if (isEmpty()) throw new NoSuchElementException("The tree is empty.");
        Node<K, V> max = getMax(root);
        V toRet = max.value;
        remove(max.key);
        return toRet;
    }

}
