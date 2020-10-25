package com.tesladodger.dodgerlib.structures;

import org.jetbrains.annotations.NotNull;

import java.util.Iterator;


public class DynamicArray<E> implements List<E>, Iterable<E> {

    /** Internal array. */
    private E[] array;

    /** Size of the internal array. */
    private int capacity;

    private static final int DEFAULT_INITIAL_CAPACITY = 16;

    /** Number of elements on the array. */
    private int size;

    /**
     * Default constructor with default initial capacity.
     */
    public DynamicArray () {
        this(DEFAULT_INITIAL_CAPACITY);
    }

    /**
     * Constructor.
     *
     * @param initialCapacity of the structure;
     */
    @SuppressWarnings("unchecked")
    public DynamicArray (int initialCapacity) {
        size = 0;
        capacity = initialCapacity;
        array = (E[]) new Object[capacity];
    }

    /**
     * Add an element to the end of the list.
     *
     * @param data to add;
     */
    public void add (E data) {
        if (size == capacity)
            changeCapacity((int) (capacity * 1.5));
        array[size++] = data;
    }

    /**
     * Get the data in the index.
     *
     * @param index of the the data;
     *
     * @return data in the index;
     *
     * @throws IndexOutOfBoundsException if the index exceeds the bounds of the array;
     */
    public E get (int index) {
        if (index < 0 || index > size - 1)
            throw new IndexOutOfBoundsException("Index " + index + " out of bounds.");
        return array[index];
    }

    /**
     * Remove an element from the array.
     *
     * @param index of the element;
     *
     * @return data of that element;
     *
     * @throws IndexOutOfBoundsException if the index exceeds the bounds of the array;
     */
    public E remove (int index) {
        if (index < 0 || index > size - 1)
            throw new IndexOutOfBoundsException("Index " + index + " out of bounds.");
        E result = array[index];
        if (size - 1 - index >= 0) System.arraycopy(array, index + 1, array, index, size - 1 - index);
        size--;
        if (size < capacity / 3)
            changeCapacity((int) (capacity * .75));
        return result;
    }

    /**
     * Set the data of an index.
     *
     * @param index to set;
     * @param data of the element;
     *
     * @throws IndexOutOfBoundsException if the index exceeds the bounds of the array;
     */
    public void set (int index, E data) {
        if (index < 0 || index > size - 1)
            throw new IndexOutOfBoundsException("Index " + index + " out of bounds.");
        array[index] = data;
    }

    /**
     * Change the size of the internal array.
     *
     * @param newCapacity of the structure;
     */
    @SuppressWarnings("unchecked")
    private void changeCapacity (int newCapacity) {
        E[] newArray = (E[]) new Object[newCapacity];
        System.arraycopy(array, 0, newArray, 0, size);
        capacity = newCapacity;
        array = newArray;
    }

    public boolean isEmpty () {
        return size == 0;
    }

    public int size () {
        return size;
    }

    @SuppressWarnings("unchecked")
    public void clear () {
        array = (E[]) new Object[capacity];
        size = 0;
    }

    /**
     * Provide an iterator for the array.
     *
     * @return DynamicArrayIterator for the array;
     */
    @NotNull
    @Override
    public Iterator<E> iterator () {
        return new DynamicArrayIterator<>(this);
    }

    /**
     * Iterator for the structure.
     *
     * @param <E>
     */
    private static class DynamicArrayIterator<E> implements Iterator<E> {
        private final DynamicArray<E> array;
        private int current;

        public DynamicArrayIterator (DynamicArray<E> array) {
            this.array = array;
            current = 0;
        }

        @Override
        public boolean hasNext () {
            return current < array.size;
        }

        @Override
        public E next () {
            return array.array[current++];
        }
    }

}
