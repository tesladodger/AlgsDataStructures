package structures;

import com.tesladodger.dodgerlib.structures.RedBlackTree;


public class RedBlackTreeTest {

    public static void main (String[] args) {

        RedBlackTree<Integer, Integer> rbTree = new RedBlackTree<>();

        rbTree.insert(8, 8);
        rbTree.insert(5, 5);
        rbTree.insert(15, 15);
        rbTree.insert(12, 12);
        rbTree.insert(19, 19);
        rbTree.insert(13, 13);
        rbTree.insert(9, 9);
        rbTree.insert(23, 23);

        System.out.println(rbTree.size());

        rbTree.insert(23, 23);

        System.out.println(rbTree.size());

        rbTree.insert(10, 10);

        System.out.println(rbTree.size());

    }

}
