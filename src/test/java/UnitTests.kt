import com.tesladodger.dodgerlib.structures.BinaryTree
import com.tesladodger.dodgerlib.structures.LinkedList
import com.tesladodger.dodgerlib.structures.Queue
import com.tesladodger.dodgerlib.structures.Stack
import structures.*
import kotlin.random.Random

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

    for (i in 1 until 10)
        assert(stack.pop() == i)

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
    val queue = Queue<Int>()
    assert(queue.size() == 0)
    assert(queue.isEmpty)

    queue.enqueue(0)
    queue.enqueue(1)
    queue.enqueue(2)
    queue.enqueue(3)
    queue.enqueue(4)
    queue.enqueue(5)
    queue.enqueue(6)
    queue.enqueue(7)
    queue.enqueue(8)
    queue.enqueue(9)

    assert(queue.size() == 10)

    var x = 0
    for (y in queue)
        assert(y == x++)

    assert(queue.dequeue() == 0)
    assert(queue.dequeue() == 1)
    assert(queue.dequeue() == 2)
    assert(queue.size() == 7)

    assert(!queue.isEmpty)
    queue.clear()
    assert(queue.isEmpty)
}

fun binaryTreeUnitTests () {
    val tree = BinaryTree<Int, Int>()
    assert(tree.isEmpty)

    for (i in 0..1000000) {
        val x = Random.nextInt(1000000)
        tree.insert(x, x)
    }

    assert(!tree.isEmpty)

    // Assert order of traversal (and of iterator, which uses traversal)
    var min = tree.findMin()
    var prev = tree.popMin()
    assert(min == prev)
    for (i in tree) {
        assert(i > prev)
        prev = i
    }
    assert(prev == tree.findMax())
    assert(prev == tree.popMax())

    // Test random deletions
    for (i in 0..5000) {
        val size = tree.size()
        val x = Random.nextInt(1000000)
        if (tree.containsKey(x)) {
            tree.remove(x)
            assert(tree.size() == size - 1)
        }
    }

    // Test order after deletions
    min = tree.findMin()
    prev = tree.popMin()
    assert(min == prev)
    for (i in tree) {
        assert(i > prev)
        prev = i
    }
    assert(prev == tree.findMax())
    assert(prev == tree.popMax())

    tree.clear()
    assert(tree.isEmpty)

    tree.insert(5, 5)
    tree.insert(9, 9)
    tree.insert(2, 2)

    assert(tree.popMin() == 2)
    assert(tree.popMin() == 5)
    assert(tree.popMin() == 9)

    assert(tree.isEmpty)
}

fun main () {
    // Structures
    stackUnitTest()
    linkedListUnitTest()
    queueUnitTests()
    binaryTreeUnitTests()

    PairingHeapTest.unitTests()
    HashTableTest.unitTests()

    println("Unit tests passed")
}