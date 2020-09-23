package structures;

import com.tesladodger.dodgerlib.structures.LinkedList;

public class LinkedListTest {

    public static void unitTests () {
        LinkedList<Integer> list = new LinkedList<>();
        assert list.isEmpty();
        assert list.size() == 0;

        list.add(5);
        list.addFirst(2);

        assert list.get(0) == 2;
        assert list.get(1) == 5;
        assert list.size() == 2;
        assert !list.isEmpty();

        list.addFirst(9);
        list.addFirst(8);
        list.addFirst(7);
        list.addFirst(6);
        list.addFirst(5);
        list.addFirst(4);
        list.addFirst(3);
        list.addFirst(2);
        list.addFirst(1);
        list.addFirst(0);

        assert list.contains(5);
        assert !list.contains(69);

        list.add(32);
        assert list.get(list.size()-1) == 32;

        int size = list.size();
        list.remove(0);
        assert size == list.size() + 1;
        assert !list.contains(0);

        list.remove(5);
        assert !list.contains(6);

        assert !list.isEmpty();
        list.clear();
        assert list.isEmpty();
    }

    public static void main (String[] args) {

        LinkedList<Integer> list = new LinkedList<>();

        list.addFirst(9);
        list.addFirst(8);
        list.addFirst(7);
        list.addFirst(6);
        list.addFirst(5);
        list.addFirst(4);
        list.addFirst(3);
        list.addFirst(2);
        list.addFirst(1);
        list.addFirst(0);

        list.add(10);

        System.out.println(list.get(5));

        System.out.println(list.remove(5));

        System.out.println(list.get(5));

        // Test the iterator
        System.out.println();
        for (Integer i : list) {
            System.out.println(i);
        }
    }

}
