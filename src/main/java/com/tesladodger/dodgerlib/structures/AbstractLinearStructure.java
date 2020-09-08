package com.tesladodger.dodgerlib.structures;


public abstract class AbstractLinearStructure<E> {

    class Node {
        Node next;
        E data;

        Node (Node next, E data) {
            this.next = next;
            this.data = data;
        }
    }

    Node root;
    int size;


    public E peek () {
        return root.data;
    }

    /**
     * Iteratively search for the data.
     * @param data to find;
     * @return boolean;
     */
    public boolean contains (E data) {
        Node next = root;
        while (next != null) {
            if (next.data == data) {
                return true;
            }
            next = next.next; // nice.
        }
        return false;
    }

    public int size () {
        return size;
    }

    public boolean isEmpty () {
        return root == null;
    }

}
