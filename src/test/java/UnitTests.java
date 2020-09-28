import structures.BinaryTreeTest;
import structures.HashTableTest;
import structures.LinkedListTest;
import structures.PairingHeapTest;
import structures.QueueTest;
import structures.StackTest;


public class UnitTests {

    public static void main (String[] args) {
        /* Structures */
        StackTest.unitTests();
        LinkedListTest.unitTests();
        QueueTest.unitTests();
        BinaryTreeTest.unitTests();
        PairingHeapTest.unitTests();
        HashTableTest.unitTests();


        System.out.println("Unit tests passed!");
    }
}
