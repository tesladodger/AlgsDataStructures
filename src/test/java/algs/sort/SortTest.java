package algs.sort;

import com.tesladodger.dodgerlib.algs.sort.DualPivotQuickSort;
import com.tesladodger.dodgerlib.algs.sort.MergeSort;
import com.tesladodger.dodgerlib.algs.sort.MultiThreadSort;
import com.tesladodger.dodgerlib.algs.sort.QuickSort;

import java.util.Random;


public class SortTest {

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

        int[] copy1 = new int[bound];
        int[] copy2 = new int[bound];
        int[] copy3 = new int[bound];
        int[] copy4 = new int[bound];
        int[] copy5 = new int[bound];
        int[] copy6 = new int[bound];
        int[] copy7 = new int[bound];
        int[] copy8 = new int[bound];
        System.arraycopy(unsortedArray, 0, copy1, 0, bound);
        System.arraycopy(unsortedArray, 0, copy2, 0, bound);
        System.arraycopy(unsortedArray, 0, copy3, 0, bound);
        System.arraycopy(unsortedArray, 0, copy4, 0, bound);
        System.arraycopy(unsortedArray, 0, copy5, 0, bound);
        System.arraycopy(unsortedArray, 0, copy6, 0, bound);
        System.arraycopy(unsortedArray, 0, copy7, 0, bound);
        System.arraycopy(unsortedArray, 0, copy8, 0, bound);


        // -------------------------------------------------------------------- QuickSort //
        System.out.println("\nQuickSort");
        t = System.currentTimeMillis();
        QuickSort.sort(copy1);
        t = System.currentTimeMillis() - t;

        System.out.println("Verifying");
        for (int j = 0; j < copy1.length - 1; j++) {
            if (copy1[j] > copy1[j+1])
                throw new RuntimeException("The list is not sorted.");
        }
        System.out.printf("The list was sorted in %s milliseconds.\n", t);/**/


        // -------------------------------------------------------------------- QuickSort //
        System.out.println("\nQuickSort 2");
        t = System.currentTimeMillis();
        QuickSort.sort(copy2);
        t = System.currentTimeMillis() - t;

        System.out.println("Verifying");
        for (int j = 0; j < copy2.length - 1; j++) {
            if (copy2[j] > copy2[j+1])
                throw new RuntimeException("The list is not sorted.");
        }
        System.out.printf("The list was sorted in %s milliseconds.\n", t);/**/


        // -------------------------------------------------------------------- Merge //
        System.out.println("\nMergeSort");
        t = System.currentTimeMillis();
        MergeSort.sort(copy3);
        t = System.currentTimeMillis() - t;

        System.out.println("Verifying");
        for (int j = 0; j < copy3.length - 1; j++) {
            if (copy3[j] > (copy3[j+1]))
                throw new RuntimeException("The list is not sorted.");
        }
        System.out.printf("The list was sorted in %s milliseconds.\n", t);/**/


        // -------------------------------------------------------------------- Merge //
        System.out.println("\nMergeSort 2");
        t = System.currentTimeMillis();
        MergeSort.sort(copy4);
        t = System.currentTimeMillis() - t;

        System.out.println("Verifying");
        for (int j = 0; j < copy4.length - 1; j++) {
            if (copy4[j] > (copy4[j+1]))
                throw new RuntimeException("The list is not sorted.");
        }
        System.out.printf("The list was sorted in %s milliseconds.\n", t);/**/


        // -------------------------------------------------------------------- Dual Pivot //
        System.out.println("\nDual Pivot");
        t = System.currentTimeMillis();
        DualPivotQuickSort.sort(copy5);
        t = System.currentTimeMillis() - t;

        System.out.println("Verifying");
        for (int j = 0; j < copy5.length - 1; j++) {
            if (copy5[j] > (copy5[j+1]))
                throw new RuntimeException("The list is not sorted.");
        }
        System.out.printf("The list was sorted in %s milliseconds.\n", t);/**/


        // -------------------------------------------------------------------- Dual Pivot //
        System.out.println("\nDual Pivot 2");
        t = System.currentTimeMillis();
        DualPivotQuickSort.sort(copy6);
        t = System.currentTimeMillis() - t;

        System.out.println("Verifying");
        for (int j = 0; j < copy6.length - 1; j++) {
            if (copy6[j] > (copy6[j+1]))
                throw new RuntimeException("The list is not sorted.");
        }
        System.out.printf("The list was sorted in %s milliseconds.\n", t);/**/


        // -------------------------------------------------------------------- Multithreaded //
        System.out.println("\nMultithreaded");
        t = System.currentTimeMillis();

        MultiThreadSort multiThreadSort = new MultiThreadSort(copy7);
        multiThreadSort.sort();

        t = System.currentTimeMillis() - t;

        System.out.println("Verifying");
        for (int j = 0; j < copy7.length - 1; j++) {
            if (copy7[j] > (copy7[j+1]))
                throw new RuntimeException("The list is not sorted.");
        }
        System.out.printf("The list was sorted in %s milliseconds.\n", t);/**/


        // -------------------------------------------------------------------- Multithreaded //
        System.out.println("\nMultithreaded 2");
        t = System.currentTimeMillis();

        MultiThreadSort multiThreadSort2 = new MultiThreadSort(copy8);
        multiThreadSort2.sort();

        t = System.currentTimeMillis() - t;

        System.out.println("Verifying");
        for (int j = 0; j < copy8.length - 1; j++) {
            if (copy8[j] > (copy8[j+1]))
                throw new RuntimeException("The list is not sorted.");
        }
        System.out.printf("The list was sorted in %s milliseconds.\n", t);/**/
    }

}
