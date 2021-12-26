import java.util.*
import kotlin.Comparator

class BitNode(val tegn: Char?, val frekvens: Int): Comparable<BitNode>{

    var venstre: BitNode? = null
    var hoyre: BitNode? = null

    fun isParent(): Boolean{
        return (hoyre == null) and (venstre == null)
    }

    //Del av PriorityQueue
    override fun compareTo(other: BitNode): Int {
        return frekvens - other.frekvens
    }

}