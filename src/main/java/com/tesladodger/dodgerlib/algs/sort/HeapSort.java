package com.tesladodger.dodgerlib.algs.sort;

import com.tesladodger.dodgerlib.structures.PairingHeap_old;

public class HeapSort {

    /**
     * Suppress constructor.
     */
    private HeapSort () {}

    /**
     * Sort an array of ints.
     * @param array to sort;
     */
    public static void sort (int[] array) {
        if (array == null || array.length == 1) return;
        heapSort(array);
    }

    private static void heapSort (int[] array) {
        PairingHeap_old<Integer, Object> heap = new PairingHeap_old<>(PairingHeap_old.Type.MIN);
        for (int x : array)
            heap.insert(x, null);
        int i = 0;
        while (!heap.isEmpty()) {
            System.out.println("here");
            array[i++] = (int) heap.pop();
        }
    }
}
