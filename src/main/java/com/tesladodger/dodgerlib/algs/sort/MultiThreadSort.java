package com.tesladodger.dodgerlib.algs.sort;

import java.util.Arrays;
import java.util.concurrent.RecursiveAction;


public class MultiThreadSort extends RecursiveAction {

    private static final int THRESHOLD = 1000;

    private final int[] array;
    private final int lo, hi;

    private MultiThreadSort(int[] array, int lo, int hi) {
        this.array = array; this.lo = lo; this.hi = hi;
    }

    public MultiThreadSort(int[] array) { this(array, 0, array.length); }

    public void sort () {
        compute();
    }

    protected void compute() {
        if (hi - lo < THRESHOLD)
            sortSequentially(lo, hi);
        else {
            int mid = (lo + hi) >>> 1;
            invokeAll(new MultiThreadSort(array, lo, mid),
                    new MultiThreadSort(array, mid, hi));
            merge(lo, mid, hi);
        }
    }

    private void sortSequentially(int lo, int hi) {
        Arrays.sort(array, lo, hi);
    }

    private void merge(int lo, int mid, int hi) {
        int[] buf = Arrays.copyOfRange(array, lo, mid);
        for (int i = 0, j = lo, k = mid; i < buf.length; j++)
            array[j] = (k == hi || buf[i] < array[k]) ?
                    buf[i++] : array[k++];
    }

}
