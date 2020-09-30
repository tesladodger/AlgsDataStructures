package structures;

import com.tesladodger.dodgerlib.structures.LinkedList;

public class LinkedListTest {

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
