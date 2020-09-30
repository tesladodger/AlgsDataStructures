package structures;

import com.tesladodger.dodgerlib.structures.Queue;


public class QueueTest {

    public static void main (String[] args) {

        Queue<Integer> q = new Queue<>();
        q.enqueue(0);
        q.enqueue(1);
        q.enqueue(2);
        q.enqueue(3);
        q.enqueue(4);
        q.enqueue(5);
        q.enqueue(6);
        q.enqueue(7);
        q.enqueue(8);
        q.enqueue(9);

        System.out.println("size: " + q.size());

        while (!q.isEmpty()) {
            System.out.println(q.dequeue());
        }

        q.enqueue(0);
        q.enqueue(1);
        q.enqueue(2);
        q.enqueue(3);
        q.enqueue(4);
        q.enqueue(5);
        q.enqueue(6);
        q.enqueue(7);
        q.enqueue(8);
        q.enqueue(9);

        System.out.println("testing iterator");
        for (Integer i : q) {
            System.out.println(i);
        }

        q.clear();

        System.out.println("\n" + q.size());

    }

}
