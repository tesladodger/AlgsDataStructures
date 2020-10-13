package com.tesladodger.dodgerlib.algs.sort;


/**
 * QuickSort in-place, with median of three on the middle pivot, and insertion sort below a certain
 * threshold.
 */
public class QuickSort implements SortingAlgorithm {

    /**
     * Suppress constructor.
     */
    private QuickSort () {}

    /* Maximum array length at which insertion sort is performed. */
    @SuppressWarnings("FieldCanBeLocal")
    private static final int INSERTION_SORT_THRESHOLD = 17;

    /**
     * Sorts an array of ints.
     * @param array to be sorted;
     */
    public static void sort (int[] array) {
        if (array == null || array.length <= 1) return;

        quickSort(array, 0, array.length - 1, true);
    }

    /**
     * Recursive QuickSort method.
     * @param array being sorted;
     * @param low index;
     * @param high index;
     */
    private static void quickSort (int[] array, int low, int high, boolean onTheLeft) {
        if ((high - low) < INSERTION_SORT_THRESHOLD) {
            if (onTheLeft) insertionSort(array, low, high);
            else pairInsertionSort(array, low, high);
            return;
        }

        // Since median of three is applied, low and high are already sorted.
        int i = low+1;
        int j = high-1;
        int mid = (low + high) >>> 1;

        medianOfThree(array, low, mid, high);

        int pivot = array[mid];

        // Partition.
        while (i < j) {
            // Find a value in the left list larger or equal to the pivot.
            while (array[i] < pivot) i++;

            // Find a value in the right list smaller or equal to the pivot.
            while (array[j] > pivot) j--;

            // Swap them.
            if (i <= j) {
                swap(array, i, j);
                i++; j--;
            }
        }

        // Recursive call on both partitions.
        if (low < j)
            quickSort(array, low, j, onTheLeft);
        if (i < high)
            quickSort(array, i, high, false);
    }

    /**
     * Normal insertion sort, called on the left part in order to not go out of bounds.
     * @param array being sorted;
     * @param low index;
     * @param high index;
     */
    private static void insertionSort (int[] array, int low, int high) {
        for (int i = low, j = i; i <= high; j = ++i) {
            int ai = array[i];
            while (ai < array[j]) {
                array[j+1] = array[j];
                if (j-- == low) {
                    break;
                }
            }
            array[j+1] = ai;
        }
    }

    /**
     * Pair insertion sort I shamelessly copied from JDK 8.
     * @param array being sorted;
     * @param low index;
     * @param high index;
     */
    private static void pairInsertionSort (int [] array, int low, int high) {
        do {
            if (low >= high) return;
        } while (array[++low] >= array[low - 1]);

        for (int k = low; ++low <= high; k = ++low) {
            int a1 = array[k], a2 = array[low];

            if (a1 < a2) a2 = a1; a1 = array[low];
            while (a1 < array[--k]) array[k + 2] = array[k];
            array[++k + 1] = a1;

            while (a2 < array[--k]) array[k + 1] = array[k];
            array[k + 1] = a2;
        }
        int last = array[high];

        while (last < array[--high]) array[high + 1] = array[high];
        array[high + 1] = last;
    }

    /**
     * Swaps low, middle and high, so that the pivot (middle) is the median.
     * The three elements are sorted, there's no need to check them further.
     * @param array being sorted;
     * @param low index;
     * @param high index;
     */
    private static void medianOfThree (int[] array, int low, int mid, int high) {
        if (array[high] < array[low]) {
            swap(array, high, low);
        }
        if (array[mid] < array[low]) {
            swap(array, mid, low);
        }
        if (array[high] < array[mid]) {
            swap(array, high, mid);
        }
    }

    /**
     * Swap elements by index.
     * @param array being sorted;
     * @param i index;
     * @param j index;
     */
    private static void swap (int[] array, int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

}
