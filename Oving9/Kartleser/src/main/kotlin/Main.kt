import main.kotlin.Graph
import main.kotlin.Node
import java.io.*
import java.util.*
import kotlin.collections.ArrayList

lateinit var graph: Graph
fun main(args: Array<String>) {
    var brNoder = File(args[0])
    var brKanter = File(args[1])
    var brLoc = File(args[2])
    graph = Graph()
    graph.getGraphFromFile(brNoder, brKanter, brLoc)



    var landmarks: ArrayList<Int> = ArrayList()
    //Nordkapp
    landmarks.add(1236417)
    //Lindesnes
    landmarks.add(3450010)

    graph.landmarks = landmarks

    graph.checkLandmarks(brNoder, brKanter, brLoc, landmarks)

    graph.readPreposALTFrom(BufferedReader(FileReader("prePosFrom.txt")))
    graph.readPreposALTTo(BufferedReader(FileReader("prePosTo.txt")))

    var y = 0
    var i = 0


    //Tampere
    y=136963
    //Trondheim
    i = 6861306


    /*
    //Kårvåg
    y=6368906
    //Gjemnes
    i = 6789998


     */
    /*
    //Oslo
    y = 2518118
    //Stockholm
    i = 6487468

     */
    println("Dijk")
    var count =  graph?.nodes[y]?.let { graph.nodes[i]?.let { it1 -> timerDijk(it, it1) } }
    println("Number of searched nodes: " + count.toString())
    println(toDrivingTime(graph.nodes[i]!!.d!!.dist))
    //writeToFile(i, File("pathToFileTAMPERE_TRONDHEIM_Dijk.txt"))

    println("\nALT")
    count = graph?.nodes[y]?.let { graph.nodes[i]?.let { it1 -> timerALT(it, it1) } }
    println("Number of searched nodes: " + count.toString())

    println(toDrivingTime(graph.nodes[i]!!.d!!.dist))
    //writeToFile(i, File("pathToFileTAMPERE_TRONDHEIM_ALT.txt"))
    println("\nGas station:")

    //Bensinstasjoner Værnes
    for(i in graph.nodes[6590451]?.let { graph.findLocations(10, it,2) }!!){
        if (i != null) {
            print(i.width)
            print(", ")
            print(i.length)
            print("\n")
        }
    }
    println("\nCharging stations")
    //Ladestasjoner Risør
    for(i in graph.nodes[1419364]?.let { graph.findLocations(10, it,4) }!!){
        if (i != null) {
            print(i.width)
            print(", ")
            print(i.length)
            print("\n")
        }
    }

}

fun timerDijk(s: Node, end: Node): Int {
    val start = Date()
    var runder = 0
    val tid: Double
    var slutt: Date? = null
    var count = 0
    do {
        count = graph.dijkstrasAlgorithm(s, end)
        slutt = Date()
        ++runder
    } while (slutt!!.getTime() - start.getTime() < 1000)
    try {
        tid = (slutt.getTime() - start.getTime()).toDouble() / runder
        println("Millisekund pr. runde:$tid")
    } catch (e: Exception) {
    }
    return count
}

fun timerALT(s: Node, end: Node): Int {
    val start = Date()
    var runder = 0
    val tid: Double
    var slutt: Date? = null
    var count = 0
    do {
        count = graph.ALT(s, end)
        slutt = Date()
        ++runder
    } while (slutt!!.getTime() - start.getTime() < 1000)
    try {
        tid = (slutt.getTime() - start.getTime()).toDouble() / runder
        println("Millisekund pr. runde:$tid")
    } catch (e: Exception) {
    }
    return count
}

fun toDrivingTime(i: Int): String{
    var time = i / 100
    var hours = time / 3600
    var minutes = time / 60 - (hours * 60)
    var seconds = time - hours * 3600 - minutes * 60
    return "Estimated driving time: $hours hr $minutes min $seconds sec"
}

fun writeToFile(i: Int, path: File){
    var fileWriter = FileWriter(path.absolutePath)
    var stringBuilder = StringBuilder()
    var start = graph.nodes[i]
    var next = start?.d?.prev
    stringBuilder.append(start?.width)
    stringBuilder.append(", ")
    stringBuilder.append(start?.length)
    while(next != null){
        stringBuilder.append("\n")
        stringBuilder.append(next?.width)
        stringBuilder.append(", ")
        stringBuilder.append(next?.length)
        next = next?.d?.prev
    }
    var bw = BufferedWriter(fileWriter)
    bw.write(stringBuilder.toString())
    bw.close()
}

fun getLengthOfQueue(n: Node): Int{
    var start = n
    var next = start?.d?.prev
    var count = 1
    while(next != null){
        count ++
        next = next?.d?.prev
    }
    return count
}