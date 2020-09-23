package structures;

import com.tesladodger.dodgerlib.structures.BinaryTree;
import com.tesladodger.dodgerlib.structures.Stack;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class BinaryTreeTest {

    public static void unitTests () {
        BinaryTree<Integer, Integer> tree = new BinaryTree<>();
        assert tree.isEmpty();
        assert tree.size() == 0;

        
    }

    public static void main (String[] args) {

        BinaryTree<Integer, Integer> tree = new BinaryTree<>();

        /* Test deletion */
        // Make list of ints.
        System.out.println("Making list");
        Random ran = new Random();
        List<Integer> list = new ArrayList<>();
        int bound = 1000000;
        int i = 0;
        while (i < 50000) {
            int please = ran.nextInt(bound);
            if (!list.contains(please)) {
                list.add(please);
            }
            i++;
        }

        // Insert list in the tree.
        System.out.println("Inserting");
        for (Integer integer : list) {
            tree.insert(integer, integer);
        }
        int size = tree.size();

        // Shuffle the list.
        System.out.println("Shuffling");
        Stack<Integer> stack = new Stack<>();
        while (!list.isEmpty()) {
            int ri = ran.nextInt(list.size());
            stack.push(list.remove(ri));
        }

        // Delete from the tree in new random order. (stack.pop() is O(1))
        System.out.println("Testing");
        long t = System.currentTimeMillis();
        while (!tree.isEmpty()) {
            tree.remove(stack.pop());
        }
        System.out.println("Size: " + size + " | Deletion Time: " + (System.currentTimeMillis() - t));
        /**/

        /* Test sorting */
        System.out.println("\nInserting randomly.");
        bound = 10000000;
        i = 0;
        t = System.currentTimeMillis();
        while (i < bound) {
            int random = ran.nextInt(bound);
            tree.insert(random,random);
            i++;
        }
        System.out.println("Size: " + bound+ " | Insertion time: " + (System.currentTimeMillis() - t));

        System.out.println("Traversing.");
        t = System.currentTimeMillis();
        List<Integer> sorted = tree.traverse();
        t = (System.currentTimeMillis() - t);

        System.out.println("Size: " + bound+ " | Traverse Time: " + t);

        System.out.println("Testing 'sortedness'");
        for (int j = 0; j < sorted.size() - 1; j++) {
            if (sorted.get(j) > sorted.get(j+1)) {
                throw new RuntimeException("The list isn't sorted.");
            }
        }
        System.out.println("Test passed.");


        tree.clear();

        tree.insert(10, 10);
        tree.insert(9, 9);
        tree.insert(23, 23);
        tree.insert(3, 3);
        tree.insert(8, 8);
        tree.insert(2, 2);
        tree.insert(84, 84);
        tree.insert(43, 43);
        tree.insert(87, 87);
        tree.insert(36, 36);
        tree.insert(49, 49);
        tree.insert(69, 69);
        tree.insert(31, 31);
        tree.insert(90, 90);
        tree.insert(74, 74);
        tree.insert(0, 0);
        tree.insert(10, 10);

        System.out.println("\nTest Iterator");
        for (Integer integer : tree) {
            System.out.println(integer);
        }

        if (tree.containsKey(49)) {
            System.out.println("\n" + tree.remove(49));
        }

        System.out.println("Min: " + tree.findMin() + " | Max: " + tree.findMax());

        System.out.println("Min: " + tree.popMin() + " | Max: " + tree.popMax());

        System.out.println(tree.find(69));

        tree.clear();

        /**/

    }

}
