import org.junit.Test
import org.junit.Assert.*
import java.util.*

class TreeSetTest {
    @Test
    fun removeAdd() {
        for (i in 0 until 100) {
            val r = Random()
            val list = mutableListOf<Int>()
            for (j in  0 until 30) {
                list.add(r.nextInt(100))
            }
            val javaSet = java.util.TreeSet<Int>()
            val treeSet = TreeSet<Int>()
            javaSet.addAll(list)
            treeSet.addAll(list)
            val toRemove = list[r.nextInt(list.size)]
            javaSet.remove(toRemove)
            treeSet.remove(toRemove)
            println("-----$i-----")
            println(javaSet)
            println("[${treeSet.joinToString(separator = ", ")}]")
            assertEquals(javaSet, treeSet)
        }
    }
}