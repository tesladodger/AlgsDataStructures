package com.tesladodger.dodgerlib.structures;


/**
 * Interface for lists of elements.
 *
 * @param <E> type of the data stored;
 */
public interface List<E> {

    /**
     * Add an element to the list.
     *
     * @param data to add;
     */
    void add (E data);

    /**
     * Get data from the element in the index.
     *
     * @param index of the element;
     *
     * @return data in that index;
     */
    E get (int index);

    /**
     * Delete an element from the list.
     *
     * @param index of the element;
     *
     * @return data of the deleted element;
     */
    E remove (int index);

    /**
     * Set the data of an index.
     *
     * @param index to set;
     * @param data of the element;
     */
    void set (int index, E data);

    /**
     * Return whether the list is empty.
     *
     * @return true if list is empty;
     */
    boolean isEmpty ();

    /**
     * Number of elements on the list.
     *
     * @return number of element;
     */
    int size ();

    /**
     * Remove all elements from the list.
     */
    void clear ();
}
