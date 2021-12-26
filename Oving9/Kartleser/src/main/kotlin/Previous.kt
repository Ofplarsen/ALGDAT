package main.kotlin

class Previous {
    var prev: Node? = null
    var inf = 1000000000
    var dist = inf
    var estimatedDistToEnd = 0

    fun find_dist(): Int {
        return dist
    }

    fun find_prev(): Node? {
        return prev
    }

    fun getSum():Int{

        return dist + estimatedDistToEnd
    }

}