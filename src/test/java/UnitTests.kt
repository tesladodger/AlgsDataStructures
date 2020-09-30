import com.tesladodger.dodgerlib.structures.*
import kotlin.random.Random


fun stackUnitTest () {
    val stack = Stack<Int>()
    assert(stack.size() == 0)
    stack.push(9)
    assert(stack.size() == 1)

    val p = stack.pop()
    assert(p == 9)

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

    var x = 6
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

fun queueUnitTest () {
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

    for ((x, i) in queue.withIndex())
        assert(x == i)

    assert(queue.dequeue() == 0)
    assert(queue.dequeue() == 1)
    assert(queue.dequeue() == 2)
    assert(queue.size() == 7)

    assert(!queue.isEmpty)
    queue.clear()
    assert(queue.isEmpty)
}

fun binaryTreeUnitTest () {
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

fun pairingHeapUnitTest () {
    val heap = PairingHeap<Int, Int>()
    assert(heap.isEmpty)

    for (i in 0..10000) {
        val x = Random.nextInt(10000)
        heap.insert(x, x)
    }

    assert(!heap.isEmpty)

    val peek = heap.peek()
    var prev = heap.pop()
    assert(peek == prev)

    while (!heap.isEmpty) {
        val p = heap.peek()
        assert(p == heap.pop())
        assert(p >= prev)
        prev = p
    }

    for (i in 0..100) {
        val x = Random.nextInt(100)
        heap.insert(x, x)
    }

    val clone = PairingHeap.clone(heap)
    assert(clone.size() == heap.size())

    while (heap.size() > 5) {
        assert(heap.pop() == clone.pop())
        assert(heap.size() == clone.size())
    }

    heap.clear()
    clone.clear()
    assert(heap.isEmpty)
    assert(clone.isEmpty)
}

fun hashTableUnitTest () {
    val table = HashTable<Int, Int>()
    assert(table.isEmpty)

    table.put(6, 6)
    table.put(2, 2)
    table.put(13, 13)
    table.put(4, 4)

    assert(table.size() == 4)
    assert(table.get(2) == 2)
    table.put(2, 69)
    assert(table.get(2) == 69)
    assert(table.size() == 4)
    assert(table.get(1) == null)

    // hash collision with 2
    table.put(14, 14)
    table.put(24, 24)
    assert(table.size() == 6)

    assert(table.remove(2) == 69)
    assert(table.size() == 5)

    assert(table.remove(24) == 24)
    assert(table.remove(24) == null)

    table.clear()
    assert(table.isEmpty)

    for (i in 0..10000) {
        val x = Random.nextInt(10000)
        table.put(x, x)
    }

    var x = 0
    for (key in table.keys()) {
        assert(key != null)
        assert(table.get(key) == key)
        x++
    }
    assert(x == table.size())

    x = 0
    for (value in table.values()) {
        assert(value != null)
        x++
    }
    assert(x == table.size())

    table.clear()
}

fun main () {
    // Structures
    stackUnitTest()
    linkedListUnitTest()
    queueUnitTest()
    binaryTreeUnitTest()
    pairingHeapUnitTest()
    hashTableUnitTest()

    println("Unit tests passed")
}