package structures;

import com.tesladodger.dodgerlib.structures.PairingHeap;

import java.util.Random;


public class PairingHeapTest {

    public static void unitTests () {
        PairingHeap<Integer, Integer> heap = new PairingHeap<>();
        assert heap.size() == 0;
        assert heap.isEmpty();

        Random ran = new Random();

        int i = 0;
        while (i++ < 10000) {
            int x = ran.nextInt(10000);
            heap.insert(x, x);
        }

        assert !heap.isEmpty();

        int peek = heap.peek();
        int prev = heap.pop();
        assert peek == prev;

        while (!heap.isEmpty()) {
            int p = heap.peek();
            int x = heap.pop();
            assert p == x;
            assert x >= prev;
            prev = x;
        }

        assert heap.isEmpty();

        i = 0;
        while (i++ < 100) {
            int x = ran.nextInt(100);
            heap.insert(x, x);
        }

        PairingHeap<Integer, Integer> clone = PairingHeap.clone(heap);
        assert clone.size() == heap.size();

        while (heap.size() > 5) {
            int x1 = heap.pop();
            int x2 = clone.pop();
            assert x1 == x2;
            assert heap.size() == clone.size();
        }

        heap.clear();
        clone.clear();

        assert heap.isEmpty();
        assert clone.isEmpty();
    }
}
