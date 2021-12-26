import java.util.*

class HeapHuffman {
    var len: Int = 0
    //Fikk tips om å bruke denne køen
    var prioKo: PriorityQueue<BitNode> = PriorityQueue()
    lateinit var node: IntArray
    fun over(i: Int): Int{
        return (i - 1) shr i
    }

    fun venstre(i: Int): Int{
        return (i shl 1) + 1
    }

    fun hoyre(i: Int): Int{
        return (i + 1) shl 1
    }

    fun fiks_heap(i: Int){
        var m = venstre(i)
        if(m<len){
            var h = m+1
            if( (h < len) and (node.get(h) > node.get(m))){
                m = h
            }
            if(node.get(m) > node.get(i)){
                bytt(node, i, m)
                fiks_heap(m)
            }
        }
    }

    fun prio_ned(i: Int, p: Int){
        node[i] -= p
        fiks_heap(i)
    }

    fun prio_opp(i: Int, p: Int){
        var f = over(i)
        node[i] += p
        var counter = i
        while ((counter>0) and (node[counter]>node[f])){
            f = over(counter)
            bytt(node, counter, f)
            counter = f
        }
    }

    fun sett_inn(x: Int){
        var i = len++
        node[i] = x
        prio_opp(i,0)
    }

    fun sett_inn2(node: BitNode){
        prioKo.add(node)
    }

    fun heapSort(){
        lag_heap()
        var l = len
        while (len > 1){
            var x = hent_maks()
            node[len] = x
        }
        len = l
    }

    fun hent_maks(): Int{
        var maks = node.get(0)
        node[0] = node[--len]
        fiks_heap(0)
        return maks
    }

    fun lag_heap(){
        var i = len/2
        while (i-->0){
            fiks_heap(i)
        }
    }

    fun bytt(t: IntArray, i: Int, j: Int){
        var k = t.get(j)
        t[j] = t[i]
        t[i] = k
    }


}