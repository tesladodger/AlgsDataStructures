package structures;

import com.tesladodger.dodgerlib.structures.Stack;


public class StackTest {

    public static void unitTests () {
        Stack<Integer> stack = new Stack<>();
        assert stack.size() == 0;
        stack.push(9);
        assert stack.size() == 1;

        int x = stack.pop();
        assert x == 9;

        stack.push(9);
        stack.push(8);
        stack.push(7);
        stack.push(6);
        stack.push(5);
        stack.push(4);
        stack.push(3);
        stack.push(2);
        stack.push(1);

        assert stack.size() == 9;

        assert stack.peek() == 1;

        for (int i = 1; i < 10; i++) {
            int y = stack.pop();
            assert y == i;
        }

        assert stack.size() == 0;

        stack.push(9);
        stack.push(8);
        stack.push(7);
        stack.push(6);

        x = 6;
        for (Integer i : stack) {
            assert i == x;
            x++;
        }

        stack.clear();
        assert stack.isEmpty();
    }

    public static void main(String[] args) {

        Stack<Integer> stack = new Stack<>();
        stack.push(9);
        stack.push(8);
        stack.push(7);
        stack.push(6);
        stack.push(5);
        stack.push(4);
        stack.push(3);
        stack.push(2);
        stack.push(1);

        System.out.println("iterator test");
        for (Integer i : stack) {
            System.out.println(i);
        }

        System.out.println(stack.contains(6));
        System.out.println(stack.contains(0));

        System.out.println(stack.peek());

        System.out.println("size: " + stack.size());

        while (!stack.isEmpty()) {
            System.out.println(stack.pop());
        }

        stack.push(9);
        stack.push(8);
        stack.push(7);

        stack.clear();
        System.out.println("size: " + stack.size());

    }

}
