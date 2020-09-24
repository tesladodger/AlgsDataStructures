package structures;

import com.tesladodger.dodgerlib.structures.PairingHeap;


public class PairingHeapTest {

    public static void unitTests () {
        PairingHeap<Integer, Integer> heap = new PairingHeap<>(PairingHeap.Type.MIN);

        heap.insert(0, 0);
        heap.insert(12, 12);
        heap.insert(6, 6);
        heap.insert(10, 10);
        heap.insert(1, 1);
        heap.insert(69, 69);

        for (int i = 0; i < 6; i++) {
            System.out.println(heap.pop());
        }
        
        System.out.println("");
    }
}
