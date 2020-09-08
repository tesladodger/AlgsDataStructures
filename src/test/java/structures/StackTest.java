package structures;

import com.tesladodger.dodgerlib.structures.Stack;


public class StackTest {

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
