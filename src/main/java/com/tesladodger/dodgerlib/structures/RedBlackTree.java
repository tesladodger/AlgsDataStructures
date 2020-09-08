package com.tesladodger.dodgerlib.structures;

import java.util.NoSuchElementException;

// todo deletion

/**
 * Self balancing Red-Black Tree.
 * @param <K> key;
 * @param <V> value;
 */
public class RedBlackTree<K extends Comparable<K>, V> extends AbstractTree<K, V>{

    private enum Color {RED, BLACK}

    private class RBNode extends Node {
        Color color;

        RBNode (K key, V value) {
            super(key, value);
            this.color = Color.RED;
        }
    }

    public RedBlackTree () {
        root = null;
        counter = 0;
    }


    // ---------------------------------------------------- Insertion //
    /**
     * Creates a new red node, calls the iterative insertion method.
     * @param key new key;
     * @param value new value;
     */
    public void insert (K key, V value) {
        if (key == null) throw new IllegalArgumentException("New key cannot be null.");

        // Create new node and color it red.
        RBNode n = new RBNode(key, value);

        // If new node is the root, make it so.
        if (isEmpty()) {
            root = n;
            ((RBNode)root).color = Color.BLACK;
        }
        // Otherwise, insert it and repair the tree.
        else {
            insertIteratively(n);
        }

        counter++;
    }

    /**
     * Iteratively inserts a node in the same way a binary tree does. If a new node is created,
     * calls the repair method on that node.
     * @param n new node;
     */
    private void insertIteratively (RBNode n) {
        RBNode current = (RBNode) root;
        // Traverse the tree until a null node is found.
        while (true) {
            if (n.key.compareTo(current.key) < 0) {
                if (current.left == null) {
                    current.left = n;
                    n.parent = current;
                    insertRepair(n);
                    break;
                }
                current = (RBNode) current.left;
            }
            else if (n.key.compareTo(current.key) > 0) {
                if (current.right == null) {
                    current.right = n;
                    n.parent = current;
                    insertRepair(n);
                    break;
                }
                current = (RBNode) current.right;
            }
            else {
                // Key is already present, just update the value.
                current.value = n.value;
                counter--;
                break;
            }
        }
    }

    /**
     * Restores balance after the insertion of a node. Can be recursive.
     * @param n newly inserted node or current node.
     */
    private void insertRepair (RBNode n) {
        RBNode p = (RBNode) parent(n);
        RBNode g = (RBNode) grandParent(n);
        while (n != root && p.color == Color.RED) {
            // There's no danger of NullPointerException:
            // If p is the root, its color is black, so the loop is never entered.
            // Otherwise, n always has a grandparent.
            // noinspection ConstantConditions
            if (p == g.left) {
                // ^ uncle is on the right (can be null).
                RBNode u = (RBNode) uncle(n);
                // Uncle is red.
                if (u != null && (u).color == Color.RED) {
                    p.color = Color.BLACK;
                    g.color = Color.RED;
                    u.color = Color.BLACK;
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
                    n.color = Color.BLACK;
                    g.color = Color.RED;
                    break;
                }
            }
            else if (p == g.right) {
                // ^ uncle is on the left (can be null).
                RBNode u = (RBNode) uncle(n);
                // Uncle is red.
                if (u != null && u.color == Color.RED) {
                    p.color = Color.BLACK;
                    g.color = Color.RED;
                    u.color = Color.BLACK;
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
                    n.color = Color.BLACK;
                    g.color = Color.RED;
                    break;
                }
            }
        }
        // Fix the color of the root.
        ((RBNode)root).color = Color.BLACK;
    }


    // ---------------------------------------------------- Removal //
    /*public V remove (K key) {
        if (isEmpty()) throw new NoSuchElementException("The tree is empty.");
        // Find the node to delete.
        Node D = findIteratively(key);
        // Save the value.
        V toRet = D.value;
        // Delete it.
        removeNode(D);
        counter--;
        return toRet;
    }

    private void removeNode (Node D) {
        // If D has two non-null children, replace D with its in-order successor, like a normal
        // binary tree, and call this function on the replacement.
        if (D.left != null && D.right != null) {
            Node E = getMin(D.right);
            D.value = E.value;
            D.key = E.key;
            removeNode(E);
            return;
        }

        Node C = (D.left == null) ? D.right : D.left;
        // A red node can only have two null children. In this case we can just delete it.
        if (D.color == Color.RED) {
            // Just replace D with C. No need to repaint, C is either null or black.
            replaceNodeInParent(D, C);
        }
        // Cases when D is black.
        else {
            // Simple case when C is red: replace D with it and paint it black.
            if (C != null && C.color == Color.RED) {
                replaceNodeInParent(D, C);
                C.color = Color.BLACK;
                return;
            }
            // Where the shitshow begins, black node with black children.
            else {
                deleteCase1(D, C);
            }

            if (S.color == Color.RED) {  // Case 2 (don't return, go to case 3 anyway).
                D.parent.color = Color.RED;
                S.color = Color.BLACK;
                if (D == D.parent.left) {
                    replaceNodeInParent(D, C);
                    rotateLeft(D.parent);
                }
                else {
                    replaceNodeInParent(D, C);
                    rotateRight(D.parent);
                }
            }
            if (D.parent.color == Color.BLACK &&
                    S.color == Color.BLACK &&
                    (S.left == null || S.left.color == Color.BLACK) &&
                    (S.right == null || S.right.color == Color.BLACK)) {
                S.color = Color.RED;

            }
        }
    }

    private void deleteCase1 (Node D, Node C) {
        if (D == root) replaceNodeInParent(D, C);
        else deleteCase2(D, C);
    }

    private void deleteCase2 (Node D, Node C) {
        Node S = sibling(D);
        if (S != null && S.color == Color.RED) {
            D.parent.color = Color.RED;
            S.color = Color.BLACK;
        }
    }*/


    // ---------------------------------------------------- Helper methods //
    private Node parent (Node n) {
        return n.parent;
    }

    private Node grandParent (Node n) {
        Node p = parent(n);
        if (p == null) {
            return null;
        }
        return p.parent;
    }

    private Node sibling (Node n) {
        Node p = parent(n);
        if (p == null) return null;
        if (p.right == n) return p.left;
        else return p.right;
    }

    private Node uncle (Node n) {
        Node p = parent(n);
        Node g = grandParent(n);
        if (g == null) return null;
        return sibling(p);
    }

    private void rotateLeft (Node n) {
        Node p = parent(n);
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

        Node rightLeftSubtree = n.right.left;

        // n's new parent is n.right
        n.parent = n.right;
        n.parent.left = n;

        // n.right now points to what was on the left of n.right.
        n.right = rightLeftSubtree;
        if (n.right != null) n.right.parent = n;
    }

    private void rotateRight (Node n) {
        Node p = parent(n);
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

        Node leftRightSubtree = n.left.right;

        // n's new parent is n.left
        n.parent = n.left;
        n.parent.right = n;

        // n.left now points to what was on the right of n.left.
        n.left = leftRightSubtree;
        if (n.left != null) n.left.parent = n;
    }

    /**
     * When removing a node with only one child, only its parent's pointer must be updated.
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
        // Null parent means D was the root.
        else {
            root = replacement;
            if (replacement != null) {
                replacement.parent = null;
            }
        }
    }

}
