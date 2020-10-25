package com.tesladodger.dodgerlib.structures;

import java.util.NoSuchElementException;


/**
 * Self balancing Red-Black Tree.
 *
 * @param <K> key type;
 * @param <V> value type;
 */
public class RedBlackTree<K extends Comparable<K>, V> extends AbstractTree<K, V>{

    /**
     * Class that represents a node in the Red-Black tree.
     *
     * @param <K>
     * @param <V>
     */
    private static final class RBNode<K, V> extends Node<K, V> {

        /** Enum for the node colors */
        private enum Color {RED, BLACK}

        /** Color of this node */
        Color color;

        /**
         * Constructor.
         *
         * @param key used to organize the tree;
         * @param value stored in the node;
         */
        RBNode (K key, V value) {
            super(key, value);
            // Default color of a new node is red
            this.color = Color.RED;
        }
    }

    /**
     * Constructor.
     */
    public RedBlackTree () {
        root = null;
        size = 0;
    }

    /**
     * Creates a new red node, calls the iterative insertion method.
     *
     * @param key new key;
     * @param value new value;
     *
     * @throws IllegalArgumentException if the key is null;
     */
    public void insert (K key, V value) {
        if (key == null) throw new IllegalArgumentException("New key cannot be null.");

        RBNode<K, V> n = new RBNode<>(key, value);

        if (isEmpty())
            ((RBNode<K, V>) (root = n)).color = RBNode.Color.BLACK;
        else
            insertIteratively(n);

        size++;
    }

    /**
     * Iteratively inserts a node in the same way a binary tree does. If a new node is created,
     * calls the repair method on that node.
     *
     * @param n new node;
     */
    private void insertIteratively (RBNode<K, V> n) {
        RBNode<K, V> current = (RBNode<K, V>) root;
        // Traverse the tree until a null node is found.
        while (true) {
            if (n.key.compareTo(current.key) < 0) {
                if (current.left == null) {
                    current.left = n;
                    n.parent = current;
                    insertRepair(n);
                    break;
                }
                current = (RBNode<K, V>) current.left;
            }
            else if (n.key.compareTo(current.key) > 0) {
                if (current.right == null) {
                    current.right = n;
                    n.parent = current;
                    insertRepair(n);
                    break;
                }
                current = (RBNode<K, V>) current.right;
            }
            else {
                // Key is already present, just update the value.
                current.value = n.value;
                size--;
                break;
            }
        }
    }

    /**
     * Restores balance after the insertion of a node. Can be recursive.
     *
     * @param n newly inserted node or current node.
     */
    private void insertRepair (RBNode<K, V> n) {
        RBNode<K, V> p = parent(n);
        RBNode<K, V> g = grandParent(n);
        while (n != root && p.color == RBNode.Color.RED) {
            // There is no danger of NullPointerException:
            // If p is the root, its color is black, so the loop is never entered.
            // Otherwise, n always has a grandparent.
            // noinspection ConstantConditions
            if (p == g.left) {
                // ^ uncle is on the right (can be null).
                RBNode<K, V> u = uncle(n);
                // Uncle is red.
                if (u != null && (u).color == RBNode.Color.RED) {
                    p.color = RBNode.Color.BLACK;
                    g.color = RBNode.Color.RED;
                    u.color = RBNode.Color.BLACK;
                    insertRepair(g);
                }
                // Uncle is black or null.
                else {
                    // Triangle case.
                    if (n == p.right) {
                        rotateLeft(p);
                    }
                    // Case 2 falls to case 3, line case.
                    rotateRight(g);
                    n.color = RBNode.Color.BLACK;
                    g.color = RBNode.Color.RED;
                    break;
                }
            }
            else if (p == g.right) {
                // ^ uncle is on the left (can be null).
                RBNode<K, V> u = uncle(n);
                // Uncle is red.
                if (u != null && u.color == RBNode.Color.RED) {
                    p.color = RBNode.Color.BLACK;
                    g.color = RBNode.Color.RED;
                    u.color = RBNode.Color.BLACK;
                    insertRepair(g);
                }
                // Uncle is black or null.
                else {
                    // Triangle case.
                    if (n == p.left) {
                        rotateRight(p);
                    }
                    // Case 2 falls to case 3, line case.
                    rotateLeft(g);
                    n.color = RBNode.Color.BLACK;
                    g.color = RBNode.Color.RED;
                    break;
                }
            }
        }
        // Fix the color of the root.
        ((RBNode<K, V>) root).color = RBNode.Color.BLACK;
    }

    /**
     * Remove an element from the tree.
     *
     * @param key of the element to remove;
     *
     * @return the data corresponding to the key;
     *
     * @throws NoSuchElementException if the tree is empty or the node does not exist;
     */
    public V remove (K key) {
        if (isEmpty()) throw new NoSuchElementException("The tree is empty.");

        RBNode<K, V> D = (RBNode<K, V>) findIteratively(key);
        V result = D.value;
        removeNode(D);
        size--;
        return result;
    }

    /**
     * Internal method to delete a node from the tree.
     * If D (the node to be deleted) has two non-null children, replace its value and key with the
     * in-order successor's, like a normal binary tree, and delete the node the values were copied
     * from (which has, at most, one non-null child).
     * Otherwise, the algorithm consists of a series of methods taken straight from the pseudo-code
     * in the wikipedia page.
     *
     * @param D node to delete;
     */
    private void removeNode (RBNode<K, V> D) {
        // If D has two non-null children, replace D with its in-order successor, like a normal
        // binary tree, and delete the replacement (which has, at most, one non-null child).
        if (D.left != null && D.right != null) {
            RBNode<K, V> E = (RBNode<K, V>) getMin(D.right);
            D.value = E.value;
            D.key = E.key;
            removeNode(E);
        } else {
            // child can be null if both right and left are null
            RBNode<K, V> child = (RBNode<K, V>) ((D.right == null) ? D.left : D.right);
            replaceNode(D, child);
            if (D.color == RBNode.Color.BLACK && child != null)
                if (child.color == RBNode.Color.RED)
                    child.color = RBNode.Color.BLACK;
                else
                    deleteCase1(child);
        }
    }

    private void deleteCase1 (RBNode<K, V> n) {
        if (n.parent == null)
            root = n;
        else
            deleteCase2(n);
    }

    private void deleteCase2 (RBNode<K, V> n) {
        RBNode<K, V> s = sibling(n);
        assert s != null;
        if (s.color == RBNode.Color.RED) {
            parent(n).color = RBNode.Color.RED;
            s.color = RBNode.Color.BLACK;
            if (n == parent(n).left)
                rotateLeft(parent(n));
            else
                rotateRight(parent(n));
        }
        deleteCase3(n);
    }

    private void deleteCase3 (RBNode<K, V> n) {
        RBNode<K, V> s = sibling(n);
        assert s != null;
        if (parent(n).color == RBNode.Color.BLACK &&
                s.color == RBNode.Color.BLACK &&
                (s.left == null || ((RBNode<K, V>)s.left).color == RBNode.Color.BLACK) &&
                (s.right == null || ((RBNode<K, V>)s.right).color == RBNode.Color.BLACK)) {
            s.color = RBNode.Color.RED;
            deleteCase1(parent(n));
        } else {
            deleteCase4(n);
        }
    }

    private void deleteCase4 (RBNode<K, V> n) {
        RBNode<K, V> s = sibling(n);
        assert s != null;
        if (parent(n).color == RBNode.Color.RED &&
                s.color == RBNode.Color.BLACK &&
                (s.left == null || ((RBNode<K, V>)s.left).color == RBNode.Color.BLACK) &&
                (s.right == null || ((RBNode<K, V>)s.right).color == RBNode.Color.BLACK)) {
            s.color = RBNode.Color.RED;
            parent(n).color = RBNode.Color.BLACK;
        } else {
            deleteCase5(n);
        }
    }

    private void deleteCase5 (RBNode<K, V> n) {
        RBNode<K, V> s = sibling(n);
        assert s != null;
        if (s.color == RBNode.Color.BLACK) {
            if (n == parent(n).left &&
                    (s.right == null || ((RBNode<K, V>)s.right).color == RBNode.Color.BLACK) &&
                    ((RBNode<K, V>)s.left).color == RBNode.Color.RED) {
                s.color = RBNode.Color.RED;
                ((RBNode<K, V>) s.left).color = RBNode.Color.RED;
                rotateRight(s);
            } else if (n == parent(n).right &&
                    (s.left == null || ((RBNode<K, V>)s.left).color == RBNode.Color.BLACK) &&
                    ((RBNode<K, V>)s.right).color == RBNode.Color.RED) {
                s.color = RBNode.Color.RED;
                ((RBNode<K, V>) s.right).color = RBNode.Color.BLACK;
                rotateLeft(s);
            }
        }
        deleteCase6(n);
    }

    private void deleteCase6 (RBNode<K, V> n) {
        RBNode<K, V> s = sibling(n);
        assert s != null;
        s.color = parent(n).color;
        parent(n).color = RBNode.Color.BLACK;
        if (n == parent(n).left) {
            ((RBNode<K, V>)s.right).color = RBNode.Color.BLACK;
            rotateLeft(parent(n));
        } else {
            ((RBNode<K, V>)s.left).color = RBNode.Color.BLACK;
            rotateRight(parent(n));
        }
    }

    private RBNode<K, V> parent (RBNode<K, V> n) {
        return (RBNode<K, V>) n.parent;
    }

    private RBNode<K, V> grandParent (RBNode<K, V> n) {
        RBNode<K, V> p = parent(n);
        return p == null ? null : (RBNode<K, V>) p.parent;
    }

    private RBNode<K, V> sibling (RBNode<K, V> n) {
        RBNode<K, V> p = parent(n);
        return p == null ? null : (RBNode<K, V>) (p.right == n ? p.left : p.right);
    }

    private RBNode<K, V> uncle (RBNode<K, V> n) {
        RBNode<K, V> p = parent(n);
        RBNode<K, V> g = grandParent(n);
        return g == null ? null : sibling(p);
    }

    private void rotateLeft (RBNode<K, V> n) {
        RBNode<K, V> p = parent(n);
        // Update n's parent to point to n's right (or the root).
        // Update n's right's parent.
        if (p != null) {
            if (n == n.parent.left) {
                p.left = n.right;
                p.left.parent = p;
            }
            else {
                p.right = n.right;
                p.right.parent = p;
            }
        }
        else {
            root = n.right;
            n.right.parent = null;
        }

        RBNode<K, V> rightLeftSubtree = (RBNode<K, V>) n.right.left;

        // n's new parent is n.right
        n.parent = n.right;
        n.parent.left = n;

        // n.right now points to what was on the left of n.right.
        n.right = rightLeftSubtree;
        if (n.right != null) n.right.parent = n;
    }

    private void rotateRight (RBNode<K, V> n) {
        RBNode<K, V> p = parent(n);
        // Update n's parent to point to n's left (or the root).
        // Update n's left's parent.
        if (p != null) {
            if (n == n.parent.left) {
                p.left = n.left;
                p.left.parent = p;
            }
            else {
                p.right = n.left;
                p.right.parent = p;
            }
        }
        else {
            root = n.left;
            n.left.parent = null;
        }

        RBNode<K, V> leftRightSubtree = (RBNode<K, V>) n.left.right;

        // n's new parent is n.left
        n.parent = n.left;
        n.parent.right = n;

        // n.left now points to what was on the right of n.left.
        n.left = leftRightSubtree;
        if (n.left != null) n.left.parent = n;
    }

    /**
     * Used for debugging, to assert if a tree is properly balanced.
     *
     * @return true if the tree obeys the red black properties;
     */
    public final boolean isBalanced () {
        // The root must be black
        if (((RBNode<K, V>) root).color == RBNode.Color.RED)
            return false;

        // The children of a red node must be black
        //todo

        // Every path from a given node to its descendant null nodes goes through the same number
        // of black nodes.




        return true;
    }

}
