package algs.sort;

import com.tesladodger.dodgerlib.algs.sort.*;

import java.util.Random;


public class SortTest {

    private static void verifyOrder (int[] a) {
        System.out.println("Verifying...");
        for (int i = 0; i < a.length - 1; i++) {
            if (a[i] > a[i+1])
                throw new RuntimeException("The list is not sorted.");
        }
    }

    public static void main (String[] args) {

        long t;

        // Make list of ints.
        System.out.println("Making list");
        Random ran = new Random();
        int bound = 70000000;
        int[] unsortedArray = new int[bound];
        int i = 0;
        while (i < bound) {
            int please = ran.nextInt(bound * 10);
            unsortedArray[i] = please;
            i++;
        }

        int[] copy = new int[bound];
        System.arraycopy(unsortedArray, 0, copy, 0, bound);


        // -------------------------------------------------------------------- QuickSort //
        System.out.println("\nQuickSort");
        t = System.currentTimeMillis();
        QuickSort.sort(copy);
        t = System.currentTimeMillis() - t;

        verifyOrder(copy);
        System.out.printf("The list was sorted in %s milliseconds.\n", t);/**/


        // -------------------------------------------------------------------- QuickSort //
        System.out.println("\nQuickSort 2");
        System.arraycopy(unsortedArray, 0, copy, 0, bound);
        t = System.currentTimeMillis();
        QuickSort.sort(copy);
        t = System.currentTimeMillis() - t;

        verifyOrder(copy);
        System.out.printf("The list was sorted in %s milliseconds.\n", t);/**/


        // -------------------------------------------------------------------- Merge //
        System.out.println("\nMergeSort");
        System.arraycopy(unsortedArray, 0, copy, 0, bound);
        t = System.currentTimeMillis();
        MergeSort.sort(copy);
        t = System.currentTimeMillis() - t;

        verifyOrder(copy);
        System.out.printf("The list was sorted in %s milliseconds.\n", t);/**/


        // -------------------------------------------------------------------- Merge //
        System.out.println("\nMergeSort 2");
        System.arraycopy(unsortedArray, 0, copy, 0, bound);
        t = System.currentTimeMillis();
        MergeSort.sort(copy);
        t = System.currentTimeMillis() - t;

        verifyOrder(copy);
        System.out.printf("The list was sorted in %s milliseconds.\n", t);/**/


        // -------------------------------------------------------------------- Dual Pivot //
        System.out.println("\nDual Pivot");
        System.arraycopy(unsortedArray, 0, copy, 0, bound);
        t = System.currentTimeMillis();
        DualPivotQuickSort.sort(copy);
        t = System.currentTimeMillis() - t;

        verifyOrder(copy);
        System.out.printf("The list was sorted in %s milliseconds.\n", t);/**/


        // -------------------------------------------------------------------- Dual Pivot //
        System.out.println("\nDual Pivot 2");
        System.arraycopy(unsortedArray, 0, copy, 0, bound);
        t = System.currentTimeMillis();
        DualPivotQuickSort.sort(copy);
        t = System.currentTimeMillis() - t;

        verifyOrder(copy);
        System.out.printf("The list was sorted in %s milliseconds.\n", t);/**/


        // -------------------------------------------------------------------- MultiThreaded //
        System.out.println("\nMultiThreaded");
        System.arraycopy(unsortedArray, 0, copy, 0, bound);
        t = System.currentTimeMillis();
        MultiThreadSort.sort(copy);
        t = System.currentTimeMillis() - t;

        verifyOrder(copy);
        System.out.printf("The list was sorted in %s milliseconds.\n", t);/**/


        // -------------------------------------------------------------------- MultiThreaded //
        System.out.println("\nMultiThreaded 2");
        System.arraycopy(unsortedArray, 0, copy, 0, bound);
        t = System.currentTimeMillis();
        MultiThreadSort.sort(copy);
        t = System.currentTimeMillis() - t;

        verifyOrder(copy);
        System.out.printf("The list was sorted in %s milliseconds.\n", t);/**/


        // -------------------------------------------------------------------- Heap //
        /*
        System.out.println("\nHeap");
        System.arraycopy(unsortedArray, 0, copy, 0, bound);
        t = System.currentTimeMillis();
        HeapSort.sort(copy);
        t = System.currentTimeMillis() - t;

        verifyOrder(copy);
        System.out.printf("The list was sorted in %s milliseconds.\n", t);


        // -------------------------------------------------------------------- Heap //
        System.out.println("\nHeap 2");
        System.arraycopy(unsortedArray, 0, copy, 0, bound);
        t = System.currentTimeMillis();
        HeapSort.sort(copy);
        t = System.currentTimeMillis() - t;

        verifyOrder(copy);
        System.out.printf("The list was sorted in %s milliseconds.\n", t);
         */
    }

}
