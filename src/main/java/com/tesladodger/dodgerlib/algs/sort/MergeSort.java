package com.tesladodger.dodgerlib.algs.sort;


/**
 * Merge sort with a helper array.
 */
public class MergeSort implements SortingAlgorithm {

    /**
     * Suppress constructor.
     */
    private MergeSort () {}

    /**
     * Helper array to hold values.
     */
    private static int[] helper;

    /**
     * Sorts an array of ints.
     *
     * @param array to be sorted;
     */
    public static void sort (int[] array) {
        if (array == null || array.length <= 1) return;

        helper = new int[array.length];
        mergeSort(array, 0, array.length - 1);
    }

    /**
     * Recursive Merge Sort method.
     *
     * @param array array being sorted;
     * @param low index of the sub-array;
     * @param high index of the sub-array;
     */
    private static void mergeSort (int[] array, int low, int high) {
        if (low < high) {
            int middle = low + (high - low) / 2;
            mergeSort(array, low, middle);
            mergeSort(array, middle+1, high);
            merge(array, low, middle, high);
        }
    }

    /**
     * Merging part of the algorithm.
     *
     * @param array being sorted;
     * @param low index;
     * @param middle index;
     * @param high index;
     */
    private static void merge (int[] array, int low, int middle, int high) {
        if (high + 1 - low >= 0) System.arraycopy(array, low, helper, low, high + 1 - low);

        int i = low;
        int j = middle + 1;
        int k = low;

        while (i <= middle && j <= high) {
            if (helper[i] <= (helper[j]))
                array[k++] = helper[i++];
            else
                array[k++] = helper[j++];
        }

        while (i <= middle)
            array[k++] = helper[i++];
    }

}
