import com.tesladodger.dodgerlib.structures.LinkedList
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
    val list = LinkedList<Int>()
    assert(list.isEmpty)
    assert(list.size() == 0)

    list.add(5)
    list.addFirst(2)

    assert(list.get(0) == 2)
    assert(list.get(1) == 5)
    assert(list.size() == 2)
    assert(!list.isEmpty)

    list.addFirst(9)
    list.addFirst(8)
    list.addFirst(7)
    list.addFirst(6)
    list.addFirst(5)
    list.addFirst(4)
    list.addFirst(3)
    list.addFirst(2)
    list.addFirst(1)
    list.addFirst(0)

    assert(list.contains(5))
    assert(list.contains(9))
    assert(list.contains(2))
    assert(!list.contains(69))
    assert(!list.contains(-1))
    assert(!list.contains(10))

    list.add(32)
    assert(list.get(list.size()-1) == 32)

    val size = list.size()
    list.remove(0)
    assert(list.size() == size - 1)
    assert(!list.contains(0))

    list.remove(5)
    assert(!list.contains(6))

    assert(!list.isEmpty)
    list.clear()
    assert(list.isEmpty)
}

fun queueUnitTests () {

}

fun main () {
    // Structures
    stackUnitTest()
    linkedListUnitTest()
    queueUnitTests()

    QueueTest.unitTests()
    BinaryTreeTest.unitTests()
    PairingHeapTest.unitTests()
    HashTableTest.unitTests()

    println("Unit tests passed")
}