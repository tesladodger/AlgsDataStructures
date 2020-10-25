package com.tesladodger.dodgerlib.structures;

import java.util.NoSuchElementException;


/**
 * Basic implementation of a linked list.
 *
 * @param <E>
 */
public class LinkedList<E> extends AbstractLinearStructure<E> implements List<E> {

    /**
     * Default constructor.
     */
    public LinkedList () {
        root = null;
        size = 0;
    }

    /**
     * Adds a new element to the end of the list.
     *
     * @param data to add;
     */
    public void add (E data) {
        Node<E> current = root;
        if (current == null) {
            root = new Node<>(null, data);
        } else {
            while (current.next != null)
                current = current.next;
            current.next = new Node<>(null, data);
        }
        size++;
    }

    /**
     * Add a new element to the start of the list.
     *
     * @param data to add;
     */
    public void addFirst (E data) {
        root = new Node<>(root, data);
        size++;
    }

    /**
     * Returns the value of the element at the specified index.
     *
     * @param index wanted;
     *
     * @return the value at that index;
     *
     * @throws IndexOutOfBoundsException if the index exceeds the bounds of the list;
     */
    public E get (int index) {
        if (index < 0 || index >= size) throw new IndexOutOfBoundsException();
        Node<E> current = root;
        for (int i = 0; i < index; i++)
            current = current.next;
        return current.data;
    }

    /**
     * Removes the node at the specified index.
     *
     * @param index to remove;
     *
     * @return the value of the removed index;
     *
     * @throws IndexOutOfBoundsException if the index exceeds the bounds of the list;
     */
    public E remove (int index) {
        if (index < 0 || index >= size) throw new IndexOutOfBoundsException();
        size--;

        E result;

        if (index == 0) {
            result = root.data;
            root = root.next;
        } else {
            Node<E> parent = root;
            for (int i = 0; i < index - 1; i++)
                parent = parent.next;
            result = parent.next.data;
            parent.next = parent.next.next;
        }

        return result;
    }

    /**
     * Set the element in a given index.
     *
     * @param index to set;
     * @param data of the element;
     *
     * @throws NoSuchElementException if the list is empty;
     * @throws IndexOutOfBoundsException if the index exceeds the bounds of the list;
     */
    public void set (int index, E data) {
        if (isEmpty()) throw new NoSuchElementException("The list is empty.");
        if (index < 0 || index > size - 1) throw new IndexOutOfBoundsException();
        Node<E> current = root;
        while (index-- > 0)
            current = current.next;
        current.data = data;
    }

    /**
     * Clears the list.
     */
    public void clear () {
        root = null;
        size = 0;
    }

}
