package structures;

import com.tesladodger.dodgerlib.structures.Queue;


public class QueueTest {

    public static void unitTests () {
        Queue<Integer> queue = new Queue<>();
        assert queue.size() == 0;
        assert queue.isEmpty();

        queue.enqueue(0);
        queue.enqueue(1);
        queue.enqueue(2);
        queue.enqueue(3);
        queue.enqueue(4);
        queue.enqueue(5);
        queue.enqueue(6);
        queue.enqueue(7);
        queue.enqueue(8);
        queue.enqueue(9);

        assert queue.size() == 10;

        int x = 0;
        for (int y : queue) {
            assert y == x;
            x++;
        }

        assert queue.size() == 10;

        x = queue.dequeue();
        assert x == 0;
        assert queue.size() == 9;

        assert !queue.isEmpty();
        queue.clear();
        assert queue.isEmpty();
    }

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
