package com.tesladodger.dodgerlib.structures;


/**
 * Basic methods of a linked list.
 * @param <E>
 */
public class LinkedList<E> extends AbstractLinearStructure<E> {

    /**
     * Default constructor.
     */
    public LinkedList () {
        root = null;
        size = 0;
    }


    /**
     * Adds a new element to the end of the list.
     * @param data to add;
     */
    public void add (E data) {
        Node current = root;
        if (current == null) {
            root = new Node(null, data);
        } else {
            while (current.next != null) {
                current = current.next;
            }
            current.next = new Node(null, data);
        }
        size++;
    }

    /**
     * Add a new element to the start of the list.
     * @param data to add;
     */
    public void addFirst (E data) {
        root = new Node(root, data);
        size++;
    }

    /**
     * Returns the value of the element at the specified index.
     * @param index wanted;
     * @return the value at that index;
     */
    public E get (int index) {
        if (index < 0 || index >= size) throw new IndexOutOfBoundsException();
        Node current = root;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current.data;
    }

    /**
     * Removes the node at the specified index.
     * @param index to remove;
     * @return the value of the removed index;
     */
    public E remove (int index) {
        if (index < 0 || index >= size) throw new IndexOutOfBoundsException();
        size--;

        E result;

        if (index == 0) {
            result = root.data;
            root = root.next;
        } else {
            Node parent = root;
            for (int i = 0; i < index - 1; i++) {
                parent = parent.next;
            }
            result = parent.next.data;
            parent.next = parent.next.next;
        }

        return result;
    }

    /**
     * Clears the list.
     */
    public void clear () {
        root = null;
        size = 0;
    }

}
