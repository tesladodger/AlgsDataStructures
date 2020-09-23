package com.tesladodger.dodgerlib.structures;

import java.util.Iterator;


public abstract class AbstractLinearStructure<E> implements Iterable<E> {

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

    /**
     * Provide an iterator for the structure.
     *
     * @return LinearStructureIterator for the list;
     */
    @Override
    public Iterator<E> iterator () {
        return new LinearStructureIterator<>(this);
    }

    /**
     * Data from the root of the structure.
     *
     * @return root data;
     */
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

class LinearStructureIterator<E> implements Iterator<E> {

    private AbstractLinearStructure<E>.Node current;

    public LinearStructureIterator (AbstractLinearStructure<E> structure) {
        current = structure.root;
    }

    @Override
    public boolean hasNext() {
        return current != null;
    }

    @Override
    public E next() {
        E data = current.data;
        current = current.next;
        return data;
    }
}
