package com.tesladodger.dodgerlib.structures;

import org.jetbrains.annotations.NotNull;

import java.util.Iterator;


public abstract class AbstractLinearStructure<E> implements Iterable<E> {

    /**
     * Class that holds an element of the list.
     *
     * @param <E> data type param;
     */
    protected static final class Node<E> {
        Node<E> next;
        E data;

        Node (Node<E> next, E data) {
            this.next = next;
            this.data = data;
        }
    }

    /** Entry point to the structure */
    protected Node<E> root;

    /** Number of elements in the structure */
    protected int size;

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
     *
     * @param data to find;
     *
     * @return boolean;
     */
    public boolean contains (E data) {
        Node<E> next = root;
        while (next != null) {
            if (next.data == data) {
                return true;
            }
            next = next.next; // nice.
        }
        return false;
    }

    /**
     * Provide an iterator for the structure.
     *
     * @return LinearStructureIterator for the list;
     */
    @NotNull
    @Override
    public Iterator<E> iterator () {
        return new LinearStructureIterator<>(this);
    }

    /**
     * Iterator for the structure.
     *
     * @param <E>
     */
    protected static class LinearStructureIterator<E> implements Iterator<E> {

        private Node<E> current;

        public LinearStructureIterator (AbstractLinearStructure<E> structure) {
            current = structure.root;
        }

        @Override
        public boolean hasNext () {
            return current != null;
        }

        @Override
        public E next () {
            E data = current.data;
            current = current.next;
            return data;
        }
    }

    public int size () {
        return size;
    }

    public boolean isEmpty () {
        return root == null;
    }

}
