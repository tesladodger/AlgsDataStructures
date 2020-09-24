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
        counter = 0;
    }


    // ---------------------------------------------------- Insert //
    /**
     * Public method to call the recursive insertion method.
     * If this tree is empty, create the root.
     *
     * @param key new key;
     * @param value new value;
     */
    public void insert (K key, V value) {
        if (isEmpty()) {
            root = new Node(key, value);
            root.parent = null;
        }
        else {
            insertIteratively(key, value);
        }
        counter++;
    }

    /**
     * Traverses the tree until it finds an empty spot for the new node.
     *
     * @param key new key;
     * @param value new value;
     */
    private void insertIteratively (K key, V value) {
        Node current = root;

        while (true) {
            if (key.compareTo(current.key) < 0) {
                if (current.left == null) {
                    current.left = new Node(key, value);
                    current.left.parent = current;
                    break;
                }
                current = current.left;
            }
            else if (key.compareTo(current.key) > 0) {
                if (current.right == null) {
                    current.right = new Node(key, value);
                    current.right.parent = current;
                    break;
                }
                current = current.right;
            }
            else {
                current.value = value;
                counter--;
                break;
            }
        }

    }

    // ---------------------------------------------------- Remove //
    /**
     * Removes a node and returns its data.
     *
     * @param key of the node to delete;
     *
     * @return data;
     */
    public V remove (K key) {
        V result;
        if (isEmpty()) throw new NoSuchElementException("The tree is empty.");
        // Find the node to delete.
        Node D = findIteratively(key);
        // If either left or right are null, replace D with the other, which may be null.
        if (D.left == null) {
            result = D.value;
            replaceNodeInParent(D, D.right);
        }
        else if (D.right == null) {
            result = D.value;
            replaceNodeInParent(D, D.left);
        }
        // D's children are both not null.
        else {
            result = D.value;
            // Find in-order successor.
            Node E = getMin(D.right);
            // If D's successor is it's right child. Wikipedia's algorithm fails in this case.
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
                // Replace E's place in it's parent with E's right subtree, which can be null.
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
        counter--;
        return result;
    }

    /**
     * When removing a node with only one child, only its parent's pointer must be updated.
     *
     * @param D node being deleted;
     * @param replacement node to replace D's place in the parent;
     */
    private void replaceNodeInParent (Node D, Node replacement) {
        if (D.parent != null) {
            if (D.parent.left == D) {
                D.parent.left = replacement;
            }
            else {
                D.parent.right = replacement;
            }
            if (replacement != null) {
                replacement.parent = D.parent;
            }
        }
        // Null parent means D is the root.
        else {
            root = replacement;
            if (replacement != null) {
                replacement.parent = null;
            }
        }
    }

    /**
     * Find the min value and remove it.
     *
     * @return min value;
     */
    public V popMin () {
        if (isEmpty()) throw new NoSuchElementException("The tree is empty.");
        Node min = getMin(root);
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
        Node max = getMax(root);
        V toRet = max.value;
        remove(max.key);
        return toRet;
    }


}
