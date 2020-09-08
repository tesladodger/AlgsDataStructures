package com.tesladodger.dodgerlib.structures;

import java.util.NoSuchElementException;


/**
 * Since java is deprecating the stack, I made my own.
 * @param <E>
 */
public class Stack<E> extends AbstractLinearStructure<E> {

    /**
     * Constructor.
     */
    public Stack () {
        size = 0;
        root = null;
    }


    /**
     * Insert new element.
     * @param data of the element;
     */
    public void push (E data) {
        root = new Node(root, data);
        size++;
    }

    /**
     * Remove the root.
     * @return the value of the root;
     */
    public E pop () {
        if (root == null) throw new NoSuchElementException("Stack is empty.");
        E data = root.data;
        root = root.next;
        size--;
        return data;
    }

    /**
     * Clear the stack by nullifying the root.
     */
    public void clear () {
        root = null;
        size = 0;
    }

}
