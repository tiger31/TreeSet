import org.junit.Test
import org.junit.Assert.*
class Test {
    @Test
    fun testAddRemove() {
        val list = listOf(1,3,4,5,6,7,8,9)
        val original = java.util.TreeSet<Int>()
        val avl = TreeSet<Int>()
        original.addAll(list)
        avl.addAll(list)
        assertEquals(avl, original)
        original.removeAll(listOf(2,6,7))
        avl.removeAll(listOf(2,6,7))
        assertEquals(avl, original)
    }
    @Test
    fun subSet() {
        val list = listOf(1,3,4,5,6,7,8,9)
        val original = java.util.TreeSet<Int>()
        val avl = TreeSet<Int>()
        assertEquals(original.subSet(2,7), avl.subSet(2,7))
        assertEquals(original.tailSet(3), avl.tailSet(3))
        assertEquals(original.headSet(10), original.headSet(10))
        original.addAll(list)
        avl.addAll(list)
        assertEquals(original.subSet(2,7), avl.subSet(2,7))
        assertEquals(original.tailSet(3), avl.tailSet(3))
        assertEquals(original.headSet(10), original.headSet(10))
    }
    @Test
    fun subSubSet() {
        val list = listOf(1,3,4,5,6,7,8,9,11,12,13,14,15,20,27)
        val original = java.util.TreeSet<Int>()
        val avl = TreeSet<Int>()
        original.addAll(list)
        avl.addAll(list)
        val subO = original.subSet(2, 19)
        val subA = original.subSet(2, 19)
        assertEquals(subO, subA)
        assertEquals(subO.subSet(2, 9), subA.subSet(2, 9))
        assertEquals(subO.tailSet(10), subA.tailSet(10))
        assertEquals(subO.headSet(7), subA.headSet(7))
    }
    @Test
    fun subTailSet() {
        val list = listOf(1,3,4,5,6,7,8,9,11,12,13,14,15,20,27)
        val original = java.util.TreeSet<Int>()
        val avl = TreeSet<Int>()
        original.addAll(list)
        avl.addAll(list)
        val subO = original.tailSet(10)
        val subA = original.tailSet(10)
        assertEquals(subO, subA)
        assertEquals(subO.subSet(11, 24), subA.subSet(11, 24))
        assertEquals(subO.tailSet(15), subA.tailSet(15))
        assertEquals(subO.headSet(13), subA.headSet(13))
    }
    @Test
    fun subHeadSet() {
        val list = listOf(1,3,4,5,6,7,8,9,11,12,13,14,15,20,27)
        val original = java.util.TreeSet<Int>()
        val avl = TreeSet<Int>()
        original.addAll(list)
        avl.addAll(list)
        val subO = original.headSet(20)
        val subA = original.headSet(20)
        assertEquals(subO, subA)
        assertEquals(subO.subSet(2, 9), subA.subSet(2, 9))
        assertEquals(subO.tailSet(10), subA.tailSet(10))
        assertEquals(subO.headSet(7), subA.headSet(7))
    }
    @Test
    fun subAddRemove() {
        val list = listOf(1,3,4,5,6,7,8,9,11,12,13,14,15,20,27)
        val original = java.util.TreeSet<Int>()
        val avl = TreeSet<Int>()
        original.addAll(list)
        avl.addAll(list)
        val subO = original.subSet(2,30)
        val subA = avl.subSet(2,30)
        assertEquals(subA, subO)
        val list2 = listOf(2,13,23,24,29)
        subA.addAll(list2)
        subO.addAll(list2)
        assertEquals(subO, subA)
        assertEquals(original, avl)
        subA.removeAll(list2)
        subO.removeAll(list2)
        assertEquals(subO, subA)
        assertEquals(original, avl)
    }
    @Test
    fun retain() {
        val list = listOf(1,3,4,5,6,7,8,9,11,12,13,14,15,20,27)
        val original = java.util.TreeSet<Int>()
        val avl = TreeSet<Int>()
        original.addAll(list)
        avl.addAll(list)
        val list2 = listOf(1,3,7,10,15,17,19,22)
        original.retainAll(list2)
        avl.retainAll(list2)
        assertEquals(original,avl)
    }
    @Test
    fun toArray() {
        val list = listOf(1,3,4,5,6,7,8,9,11,12,13,14,15,20,27)
        val original = java.util.TreeSet<Int>()
        val avl = TreeSet<Int>()
        original.addAll(list)
        avl.addAll(list)
        assertArrayEquals(original.toArray(), avl.toArray())
    }
}