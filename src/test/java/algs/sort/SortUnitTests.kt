package algs.sort

import com.tesladodger.dodgerlib.algs.sort.DualPivotQuickSort
import com.tesladodger.dodgerlib.algs.sort.MergeSort
import com.tesladodger.dodgerlib.algs.sort.MultiThreadSort
import com.tesladodger.dodgerlib.algs.sort.QuickSort

import kotlin.random.Random

fun assertOrder (array: IntArray) {
    for (i in 1 until array.size)
        assert(array[i] >= array[i-1])
}

fun sort (array: IntArray, sorter: (array: IntArray) -> Unit) {
    sorter(array)
}

fun sortUnitTest () {
    /* Dual pivot */
    var array = IntArray(1000) { Random.nextInt(1000)}
    //array.forEach { println(it) }
    sort(array, DualPivotQuickSort::sort)
    assertOrder(array)

    /* Merge */
    array = IntArray(1000) { Random.nextInt(1000) }
    sort(array, MergeSort::sort)
    assertOrder(array)

    /* Multi-threaded */
    array = IntArray(1000) { Random.nextInt(1000) }
    sort(array, MultiThreadSort::sort)
    assertOrder(array)

    /* Quick */
    array = IntArray(1000) { Random.nextInt(1000) }
    sort(array, QuickSort::sort)
    assertOrder(array)
}