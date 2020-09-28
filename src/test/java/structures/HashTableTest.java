package structures;

import com.tesladodger.dodgerlib.structures.HashTable;

import java.util.Random;


public class HashTableTest {

    public static void unitTests () {
        // Integer table
        HashTable<Integer, Integer> table = new HashTable<>();

        assert table.isEmpty();
        assert table.size() == 0;

        table.put(6, 6);
        table.put(2, 2);
        table.put(13, 13);
        table.put(4, 4);

        assert !table.isEmpty();
        assert table.size() == 4;

        assert table.get(2) == 2;
        table.put(2, 69);
        assert table.get(2) == 69;
        assert table.size() == 4;

        assert table.get(1) == null;

        // hash collision with 2:
        table.put(14, 14);
        table.put(24, 24);
        assert table.size() == 6;

        int x = table.remove(2);
        assert x == 69;
        assert table.size() == 5;

        table.remove(24);
        Integer y = table.remove(24);
        assert y == null;

        table.clear();
        assert table.isEmpty();

        Random ran = new Random();
        int i = 0;
        while (i++ < 10000) {
            int z = ran.nextInt(1000);
            table.put(z, z);
        }

        x = 0;
        for (Integer key : table.keys()) {
            assert key != null;
            assert table.get(key).equals(key);
            x++;
        }
        assert x == table.size();

        x = 0;
        for (Integer value : table.values()) {
            assert value != null;
            x++;
        }
        assert x == table.size();

        table.clear();
    }

    public static void main (String[] args) {

        // Insertion speed.
        HashTable<Integer, Integer> table = new HashTable<>(1000, 1);

        long t = System.nanoTime();
        for (int i = 0; i < 1000; i++) {
            table.put(i, i);
        }
        System.out.println(System.nanoTime() - t);


        // Normal operation.
        HashTable<Integer, String> hashTable = new HashTable<>();

        hashTable.put(11, "your mum");
        hashTable.put(3, "is fat");
        hashTable.put(15, "and ugly");
        hashTable.put(2, "kile's");
        hashTable.put(5, "mom");
        hashTable.put(90, "is");
        hashTable.put(8, "a");
        hashTable.put(69, "bitch");

        System.out.println(hashTable.get(3));

        // First resizing.
        hashTable.put(7, "she's");
        hashTable.put(13, "a big fat bitch");

        // Keys don't change.
        System.out.println(hashTable.get(3));
        System.out.println(hashTable.get(11));

        System.out.println(hashTable.remove(2));
        System.out.println(hashTable.remove(5));
        System.out.println(hashTable.remove(90));
        System.out.println(hashTable.remove(8));
        System.out.println(hashTable.remove(69));


        // Double hashing.
        HashTable<Double, Double> doubleTable = new HashTable<>(6);
        doubleTable.put(0.001, 0.001);
        doubleTable.put(0.0005, 0.0005);
        doubleTable.put(0.2, 0.2);


        /**/
    }

}
