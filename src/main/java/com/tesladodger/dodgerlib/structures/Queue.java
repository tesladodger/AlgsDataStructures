package com.tesladodger.dodgerlib.structures;

import java.util.NoSuchElementException;


/**
 * Singly linked fifo queue of generic elements.
 *
 * @param <E>
 */
public class Queue<E> extends AbstractLinearStructure<E> {

    /** Reference to the last node. */
    private Node<E> tail;

    /**
     * Constructor.
     */
    public Queue () {
        size = 0;
        root = null;
        tail = null;
    }

    /**
     * Append a new element to the end of the queue.
     *
     * @param data to add;
     */
    public void enqueue (E data) {
        Node<E> n = new Node<>(null, data);
        if (isEmpty())
            root = n;
        else
            tail.next = n;
        tail = n;
        size++;
    }

    /**
     * Remove the first element in the queue.
     *
     * @return the value of that element;
     */
    public E dequeue () {
        if (isEmpty()) throw new NoSuchElementException("The queue is empty.");
        E toRet = root.data;
        root = root.next;
        size--;
        return toRet;
    }

    /**
     * Clear the queue by nullifying the references to the tips.
     */
    public void clear () {
        tail = null;
        root = null;
        size = 0;
    }

}
