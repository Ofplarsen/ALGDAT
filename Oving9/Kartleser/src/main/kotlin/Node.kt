package main.kotlin
import kotlin.Comparator

class Node {
    var edge1: Edge? = null
    var placement = 0
    var width: Double = 0.0
    var length: Double = 0.0
    var d: Previous? = null
    var found = false
    var code = -1
    var nameOfPlace = ""

    fun nodeFound(){
        found = true
    }

    fun resetFound(){
        found = false
    }

    fun compareTo(other: Node): Int {
        var prev1 = d as Previous
        var prev2 = other.d as Previous

        return prev1.dist-prev2.dist
    }

    override fun toString(): String {
        return "Node(placement=$placement, code=$code, nameOfPlace='$nameOfPlace')"
    }

}