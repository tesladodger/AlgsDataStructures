package com.tesladodger.dodgerlib.algs.sort;


/**
 * Dual-Pivot Quicksort as described by Vladimir Yaroslavskiy.
 */
public class DualPivotQuickSort {

    /**
     * Suppress constructor.
     */
    private DualPivotQuickSort () {}

    /* Maximum array length below which insertion sort is performed. */
    public static final int INSERTION_SORT_THRESHOLD = 17;

    /**
     * Sorts an array of ints.
     *
     * @param array being sorted;
     */
    public static void sort (int[] array) {
        if (array == null || array.length <= 1) return;
        dualPivotQuickSort(array, 0, array.length - 1, true);
    }

    /**
     * Pretty much a simpler version of the algorithm in the JDK.
     *
     * @param array being sorted;
     * @param low index;
     * @param high index;
     * @param onTheLeft boolean;
     */
    private static void dualPivotQuickSort (int[] array, int low, int high, boolean onTheLeft) {
        if ((high - low) < INSERTION_SORT_THRESHOLD) {
            if (onTheLeft) insertionSort(array, low, high);
            else pairInsertionSort(array, low, high);
            return;
        }

        int length = high - low + 1;

        // This equals about 1/5 of the length.
        // 2^-3 + 2^-4 + 2^-7 + 2^-8 ... = 1/5
        // 1/5 = 0.0011 0011 0011 ...
        int fifth = (length >>> 3) + (length >>> 4) + (length >>> 7);
        // 2^-1 = 1/2  = 0.1
        int mid = (low + high) >>> 1;
        int p1 = mid - fifth;  // Position of the pivot 1;
        int p2 = mid + fifth;  // Position of the pivot 2;

        sort5Elements(array, low, mid - fifth, mid, mid + fifth, high);

        int pivot1 = array[p1];
        int pivot2 = array[p2];

        array[p1] = array[low];
        array[p2] = array[high];

        int L = low;
        int G = high;

        // noinspection StatementWithEmptyBody
        while (array[++L] < pivot1);
        // noinspection StatementWithEmptyBody
        while (array[--G] > pivot2);

        loop:
        for (int K = L - 1; ++K <= G;) {
            int aK = array[K];
            if (aK < pivot1) {
                array[K] = array[L];
                array[L++] = aK;
            } else if (aK > pivot2) {
                // Since we don't know what's on this part, whe should skip the elements greater
                // than the second pivot.
                while (array[G] > pivot2) {
                    if (G-- == K) break loop;
                }

                // Put array[G] in the right partition.
                if (array[G] < pivot1) {
                    array[K] = array[L];
                    array[L++] = array[G];
                } else array[K] = array[G];

                // Put aK in the right place.
                array[G] = aK;
                --G;
            }
        }

        array[low] = array[L - 1]; array[L - 1] = pivot1;
        array[high] = array[G + 1]; array[G + 1] = pivot2;

        dualPivotQuickSort(array, low, L - 2, onTheLeft);
        dualPivotQuickSort(array, L, G, false);
        dualPivotQuickSort(array, G + 2, high, false);
    }

    /**
     * Normal insertion sort, called on the left part in order to not go out of bounds.
     *
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
     *
     * @param array being sorted;
     * @param low index;
     * @param high index;
     */
    private static void pairInsertionSort (int[] array, int low, int high) {
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
     * A very ugly insertion sort for the 5 elements.
     *
     * @param array being sorted;
     * @param left index;
     * @param p1 index of the first pivot;
     * @param mid index;
     * @param p2 index of the second pivot;
     * @param high index;
     */
    private static void sort5Elements (int[] array, int left, int p1, int mid, int p2, int high) {
        if (array[p1] < array[left]) { int t = array[left]; array[left] = array[p1]; array[p1] = t; }

        if (array[mid] < array[p1]) { int t = array[p1]; array[p1] = array[mid]; array[mid] = t;
            if (array[p1] < array[left]) { t = array[left]; array[left] = array[p1]; array[p1] = t; }
        }

        if (array[p2] < array[mid]) { int t = array[mid]; array[mid] = array[p2]; array[p2] = t;
            if (array[mid] < array[p1]) { t = array[p1]; array[p1] = array[mid]; array[mid] = t;
                if (array[p1] < array[left]) { t = array[left]; array[left] = array[p1]; array[p1] = t; }
            }
        }

        if (array[high] < array[p2]) { int t = array[p2]; array[p2] = array[high]; array[high] = t;
            if (array[p2] < array[mid]) { t = array[mid]; array[mid] = array[p2]; array[p2] = t;
                if (array[mid] < array[p1]) { t = array[p1]; array[p1] = array[mid]; array[mid] = t;
                    if (array[p1] < array[left]) { t = array[left]; array[left] = array[p1]; array[p1] = t; }
                }
            }
        }
    }

}
