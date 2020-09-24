package structures;

import com.tesladodger.dodgerlib.structures.PairingHeap_old;

import java.util.Random;


public class PairingHeapTest_old {

    public static void main (String[] args) {
        // Test priority insert.

        PairingHeap_old<Integer, String> heapA = new PairingHeap_old<>(PairingHeap_old.Type.MIN);

        heapA.insert(5, "A, 5 first");
        heapA.insert(5, "A, 5 second");
        heapA.insert(8, "A, 8 first");
        heapA.insert(4, "A, 4 first");
        heapA.insert(2, "A, 2 only");
        heapA.insert(1, "A, 1 only");
        heapA.insert(8, "A, 8 second");
        heapA.insert(4, "A, 4 second");
        heapA.insert(34, "A, 34 only");
        heapA.insert(54, "A, 54 only");
        heapA.insert(87, "A, 87 only");
        heapA.insert(83, "A, 83 only");
        heapA.insert(10, "A, 10 only");
        heapA.insert(5, "A, 5 third");
        heapA.insert(8, "A, 8 third");
        heapA.insert(28, "A, 28 only");
        heapA.insert(76, "A, 76 only");

        System.out.println("HeapA: size " + heapA.size() + " | top " +
                heapA.peekKey() + " -> " + heapA.peek());

        PairingHeap_old<Integer, String> cloneA = heapA.cloneHeap();


        // Test merging.
        PairingHeap_old<Integer, String> heapB = new PairingHeap_old<>(PairingHeap_old.Type.MIN);
        heapB.insert(9, "B, 9 first");
        heapB.insert(7, "B, 7 only");
        heapB.insert(0, "B, 0 first");
        heapB.insert(0, "B, 0 second");
        heapB.insert(9, "B, 9 second");
        heapB.insert(8, "B, 8 only");
        heapB.insert(34, "B, 34 only");
        heapB.insert(2, "B, 2 only");
        heapB.insert(21, "B, 21 only");
        heapB.insert(12, "B, 12 only");
        heapB.insert(90, "B, 90 only");


        PairingHeap_old<Integer, String> heapC = PairingHeap_old.merge(cloneA, heapB);

        System.out.println("HeapC: size " + heapC.size() + " | top " +
                heapC.peekKey() + " -> " + heapC.peek());

        heapC.pop();
        System.out.println("C contains 100: " + heapC.containsKey(100));
        System.out.println("C contains 34: " + heapC.containsKey(34));


        // Test popping.
        System.out.println(" ");
        while (!heapC.isEmpty()) {
            System.out.printf("Popped from C: %-15s | size: %-3s \n", heapC.pop(), heapC.size());
        }

        System.out.println(" ");
        while (!heapB.isEmpty()) {
            System.out.printf("Popped from B: %-15s | size: %-3s \n", heapB.pop(), heapB.size());
        }


        heapA.clear();
        cloneA.clear();


        // Speed test.
        Random ran = new Random();
        PairingHeap_old<Integer, Integer> testHeap = new PairingHeap_old<>(PairingHeap_old.Type.MAX);
        int i = 0;
        while (i++ < 100000) {
            int key = ran.nextInt(100000);
            testHeap.insert(key, key);
        }

        System.out.printf("\nTest heap of size %s, starting pop() test.\n", testHeap.size());

        long t = System.currentTimeMillis();
        while (!testHeap.isEmpty()) {
            testHeap.pop();
        }
        System.out.println("Time: " + (System.currentTimeMillis() - t));


        // Correct order test.
        i = 0;
        while (i++ < 100000) {
            int key = ran.nextInt(100000);
            testHeap.insert(key, key);
        }
        System.out.println("Starting order test.");
        int popped = testHeap.pop();
        while (!testHeap.isEmpty()) {
            int newPop = testHeap.pop();
            if (newPop > popped) {
                throw new RuntimeException("Wrong order, test failed.");
            }
            popped = newPop;
        }
        System.out.println("Test passed.");

    }

}
