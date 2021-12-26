import java.io.BufferedReader
import java.io.FileReader
import java.io.IOException


fun main(args: Array<String>) {

    val brBFS = BufferedReader(FileReader(args[0]))
    val graph1 = Graf()

    try {
        graph1.ny_vgraf(brBFS)
    } catch (e: IOException) {
        e.printStackTrace()
    }

    var sluk : Node? = null
    val start = 0;


    for( n in graph1.node){
        if(n.kant1?.kapasitet == 0){
            sluk = n;
        }

    }


    var maxflyt: Int = 0
    println("Økning : Flytøkende vei")
    try {

        while (true) {
            var flyt:Int = 0
            graph1.bfs(graph1.node[start], sluk)
            var prev:Previous  = sluk?.d!!;
            var print:String = sluk.placement.toString()

            while (true) {
                print += " " + prev.prev.placement

                if (prev.prev.d?.prev?.d == null) {
                    break
                }

                prev = prev.prev.d!!
            }
            prev = sluk.d!!
            flyt = graph1.maxFlow(prev, sluk)
            println(flyt.toString() + "   " + print)
            maxflyt += flyt
        }
    }catch (e: Exception){

        println("Maksimal flyt ble: " + maxflyt.toString())

    }

}