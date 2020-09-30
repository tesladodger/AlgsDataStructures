import com.tesladodger.dodgerlib.structures.Stack
import structures.*

fun stackUnitTest () {
    val stack = Stack<Int>()
    assert(stack.size() == 0)
    stack.push(9)
    assert(stack.size() == 1)

    var x = stack.pop()
    assert(x == 9)

    stack.push(9)
    stack.push(8)
    stack.push(7)
    stack.push(6)
    stack.push(5)
    stack.push(4)
    stack.push(3)
    stack.push(2)
    stack.push(1)

    assert(stack.size() == 9)
    assert(stack.peek() == 1)

    for (i in 1..9) {
        val y = stack.pop()
        assert(y == i)
    }

    assert(stack.size() == 0)

    stack.push(9)
    stack.push(8)
    stack.push(7)
    stack.push(6)

    x = 6
    for (i in stack)
        assert(i == x++)

    stack.clear()
    assert(stack.isEmpty)
}

fun linkedListUnitTest () {

}

fun main () {
    // Structures
    stackUnitTest()
    linkedListUnitTest()

    LinkedListTest.unitTests()
    QueueTest.unitTests()
    BinaryTreeTest.unitTests()
    PairingHeapTest.unitTests()
    HashTableTest.unitTests()

    println("Unit tests passed")
}