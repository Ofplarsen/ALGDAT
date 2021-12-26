package main.kotlin

import java.io.*
import java.util.*
import kotlin.collections.ArrayList


class Graph {
    lateinit var priorityQueue: PriorityQueue<Node>
    var amountOfNodes = 0
    var amountOfEdges = 0
    lateinit var nodes: Array<Node?>
    lateinit var landmarks: ArrayList<Int>
    lateinit var landmarkDistFrom: Array<IntArray>
    lateinit var landmarkDistTo: Array<IntArray>

    @Throws(IOException::class)
    fun checkLandmarks(readerNodes: File, readerEdges: File, readerInterestLoc: File, landmarks: ArrayList<Int>) {
        if(!(File("prePosFrom.txt").exists()) or !(File("prePosTo.txt").exists())){
            prePosLandmarks(readerNodes, readerEdges, landmarks)
        }

        readInterestLoc(readerInterestLoc)
    }

    @Throws(IOException::class)
    fun getGraphFromFile(readerNodes: File, readerEdges: File, readerInterestLoc: File) {

        nodes = getNodesFromFile(readerNodes)!!
        readInterestLoc(readerInterestLoc)
        getEdgesFromFile(readerEdges)

    }

    fun prePosLandmarks(readerNodes: File, readerEdges: File, landmarks: ArrayList<Int>){

        nodes = getNodesFromFile(readerNodes)!!
        getMirroredEdges(readerEdges)
        preposALTLandmark(landmarks, File("prePosTo.txt"))

        nodes = getNodesFromFile(readerNodes)!!
        getEdgesFromFile(readerEdges)
        preposALTLandmark(landmarks, File("prePosFrom.txt"))

    }

    private fun getMirroredEdges(readerFile: File){
        var reader = BufferedReader(FileReader(readerFile))
        var st = StringTokenizer(reader.readLine())

        var K = st.nextToken().toInt()
        amountOfEdges = K

        for (i in 0 until amountOfEdges){
            st = StringTokenizer(reader.readLine())
            val fromNode = st.nextToken().toInt()
            val toNode = st.nextToken().toInt()
            //I hundredels sekunder
            val drivingTime = st.nextToken().toInt()
            val drivingLength = st.nextToken().toInt()
            val speedLimit = st.nextToken().toInt()
            var edge = Edge(nodes[fromNode], nodes[toNode], nodes[toNode]?.edge1, drivingLength, speedLimit, drivingTime)
            nodes[toNode]?.edge1 = edge

        }
        reader.close()
    }

    private fun getNodesFromFile(file: File): Array<Node?>? {
        try {
            var reader = BufferedReader(FileReader(file))


            var st = StringTokenizer(reader.readLine())

            var N = st.nextToken().toInt()
            var node = arrayOfNulls<Node>(N)
            amountOfNodes = N

            for (i in 0 until N) {
                node[i] = Node()
            }

            for (i in 0 until amountOfNodes) {
                st = StringTokenizer(reader.readLine())
                val placement = st.nextToken().toInt()
                val width = st.nextToken().toDouble()
                val length = st.nextToken().toDouble()
                node[i]?.placement = placement
                node[i]?.width = width
                node[i]?.length = length
            }
            reader.close()
            return node
        }catch (e: Exception){
            println("h")
        }
        return null
    }

    private fun getEdgesFromFile(readerFile: File){
        var reader = BufferedReader(FileReader(readerFile))
        var st = StringTokenizer(reader.readLine())

        var K = st.nextToken().toInt()
        amountOfEdges = K

        for (i in 0 until amountOfEdges){
            st = StringTokenizer(reader.readLine())
            val fromNode = st.nextToken().toInt()
            val toNode = st.nextToken().toInt()
            //I hundredels sekunder
            val drivingTime = st.nextToken().toInt()
            val drivingLength = st.nextToken().toInt()
            val speedLimit = st.nextToken().toInt()
            var edge = Edge(nodes[toNode], nodes[fromNode], nodes[fromNode]?.edge1, drivingLength, speedLimit, drivingTime)
            nodes[fromNode]?.edge1 = edge

        }
        reader.close()
    }



    fun initPrev(s: Node) {

        for (i in amountOfNodes-1 downTo 0) {
            nodes[i]!!.d = Previous()
        }
        (s.d as Previous?)!!.dist = 0
    }

    /*
    1. Ikke legg noder inn i priokøen før de blir funnet!
        1a. Bruk en boolean og lagre status på om noden er funnet i nodeobjecktet
     */
    /*
        1. Lag prioritetskø hvor det baserer seg på nodenes distanseestimat
        2.
     */
    fun dijkstrasAlgorithm(start: Node?, end: Node?):Int{
        start?.d?.dist = 0
        end?.found = true

        if (start != null) {
            initPrev(start)
        }
        priorityQueue = PriorityQueue(amountOfNodes, Comparator<Node> { a: Node, b: Node -> (a.d?.dist?.minus(b.d?.dist!!)!!) })

        priorityQueue.add(start)

        var count = 0
        while(!priorityQueue.isEmpty()){
            count++
            var n = priorityQueue.poll()
            if(n.found){
                return count
            }

            var e = n.edge1

            while (e != null){
                shorten(n,e)
                e = e.next

            }

        }
        return -1
    }



    private fun preposALTLandmark(landmarks: ArrayList<Int>, outputFile: File){

        var fileWriter = FileWriter(outputFile.absolutePath)
        var prePosData = Array(landmarks.size) {IntArray(amountOfNodes)}
        var stringBuilder = StringBuilder()
        stringBuilder.append(landmarks.size)
        stringBuilder.append(" ")
        stringBuilder.append(amountOfNodes)
        stringBuilder.append("\n")

        for (landmark in landmarks){
            nodes[landmark]?.let { dijkstrasAlgPrePos(it) }
            println(landmark)
            stringBuilder.append(landmark)

            for (i in prePosData[0].indices){
                stringBuilder.append(" ")
                //prePosData[i][landmark.placement] = landmark.d?.dist!!
                stringBuilder.append(nodes[i]?.d?.dist)

            }
            stringBuilder.append("\n")
        }
        /*
        Format:
        Antall landemerker Antall Noder
        Landemerke1Placement Node0Dist, Node1Dist, Node2Dist
        Landemerke2Placement Node0Dist, Node1Dist, Node2Dist
         */
        var bw = BufferedWriter(fileWriter)
        bw.write(stringBuilder.toString())
        bw.close()
    }

    fun readPreposALTFrom(reader: BufferedReader){

        var st = StringTokenizer(reader.readLine())

        var ld = st.nextToken().toInt()
        var n = st.nextToken().toInt()
        landmarkDistFrom = Array(ld){IntArray(n)}

        for(i in landmarkDistFrom.indices){
            st = StringTokenizer(reader.readLine())
            var x = st.nextToken().toInt()
            for (j in landmarkDistFrom[i].indices){
                landmarkDistFrom[i][j] = st.nextToken().toInt()
            }
        }

        //println(landmarkDistFrom[0].contentToString())
    }

    fun readPreposALTTo(reader: BufferedReader){

        var st = StringTokenizer(reader.readLine())

        var ld = st.nextToken().toInt()
        var n = st.nextToken().toInt()
        landmarkDistTo = Array(ld){IntArray(n)}

        for(i in landmarkDistTo.indices){
            st = StringTokenizer(reader.readLine())
            var x = st.nextToken().toInt()
            for (j in landmarkDistTo[i].indices){
                landmarkDistTo[i][j] = st.nextToken().toInt()
            }
        }

        //println(landmarkDistTo[0].contentToString())
    }

    private fun dijkstrasAlgPrePos(start: Node){
        start.d?.dist = 0

        initPrev(start)
        priorityQueue = PriorityQueue(amountOfNodes, Comparator<Node> { a: Node, b: Node -> (a.d?.dist?.minus(b.d?.dist!!)!!) })

        priorityQueue.add(start)

        var count = 0
        while(!priorityQueue.isEmpty()) {
            count++
            var n = priorityQueue.poll()

            var e = n.edge1

            while (e != null) {
                shorten(n, e)
                e = e.next

            }

        }
    }

    /*
     *Finner avstand fra node nr. n, til målet slik:
     * 1. Slå opp avstand fra første landemerke til målet, trekk fra avstand fra første landemerke til n
     *      1a. Hvis tallet blir neg, bruk 0
     * 2. Slå opp avstand fra n til første landemerke, trekk fra avstanden fra målet til landemerket
     * 3. Fortsett denne beregningen, og bruk det største estimatet for nodens avstand til målet
     * 4. Dette må gjøres hver gang ALT oppdager en ny nodel og legger den i køen
     *      4a. Hvis noden får ny avstand må den omprioriteres
     *      4b. Estiamtet endres ikke, sp vi trenger ikke gå igjennom alle landemerker omigjen
     * 5. Node bør ha 3 avstandfelter:
     *      - for avstand til start (endrer seg underveis)
     *      - estimat for avstand til mål (beregnes bare en gang for hver node som legges i kø)
     *      - Sum av de to første som priokøen bruker til å finne beste node
     *          * Kan ha aksessmetode som legger sammen ved behov isteden
     * Prioriteskøen bruker:
     *              -
     */
    fun ALT(start: Node,end: Node): Int{

        initPrev(start)

        priorityQueue = PriorityQueue { a: Node, b: Node ->  a.d!!.getSum()-b.d!!.getSum()}
        setDistanceEstimat(start, end)
        priorityQueue.add(start)
        var count = 0
        while (!priorityQueue.isEmpty()){
            var n = priorityQueue.poll()
            if((n == end) ){
                return count
            }

            count++
            var e = n.edge1

            while (e != null){
                shorten(n, e, end)
                e = e.next
            }
        }
        return -1
    }

    private fun shorten(n: Node, e: Edge, end: Node){
        var nd = n.d as Previous
        var md = e.to?.d as Previous

        if (md.dist > nd.dist + e.drivingTime){

            setDistanceEstimat(e.to!!, end)

            md.dist = nd.dist + e.drivingTime
            md.prev = n

            priorityQueue.add(e.to)

        }
    }

    private fun shorten(n: Node, e: Edge){
        var node = n.d as Previous
        var md = e.to?.d as Previous

        if (md.dist > node.dist + e.drivingTime){

            md.dist = node.dist + e.drivingTime
            md.prev = n
            priorityQueue.add(e.to)
        }
    }

    private fun getDistanceLandmarkGoal(start: Node, end: Node, i : Int): Int{
        var dist = landmarkDistFrom[i][end.placement] - landmarkDistFrom[i][start.placement]
        if(dist < 0){
            return 0
        }

        return dist
    }

    private fun getDistanceNLandmark(start: Node,end: Node, i : Int): Int{
        var dist = landmarkDistTo[i][start.placement] - landmarkDistTo[i][end.placement]
        if(dist < 0){
            return 0
        }
        return dist
    }

    private fun setDistanceEstimat(n: Node, end: Node){
        for(i in landmarks.indices){
            if(n.d?.estimatedDistToEnd!! < getDistanceLandmarkGoal(n, end, i)) {
                n.d?.estimatedDistToEnd = getDistanceLandmarkGoal(n, end, i)
            }
            if(n.d?.estimatedDistToEnd!! < getDistanceNLandmark(n, end, i)) {
                n.d?.estimatedDistToEnd = getDistanceNLandmark(n, end, i)
            }
        }
    }

    fun findLocations(nmbLoc: Int, start: Node, code: Int): Array<Node?> {
        var locations = arrayOfNulls<Node>(nmbLoc)
        start?.d?.dist = 0

        initPrev(start)
        priorityQueue = PriorityQueue(amountOfNodes, Comparator<Node> { a: Node, b: Node -> (a.d?.dist?.minus(b.d?.dist!!)!!) })

        priorityQueue.add(start)

        var count = 0
        while(!priorityQueue.isEmpty()){

            var n = priorityQueue.poll()
            if(n.code == code){
                locations[count] = n
                count++
            }

            if(count == nmbLoc){
                break
            }


            var e = n.edge1

            while (e != null){
                shorten(n,e)
                e = e.next

            }

        }
        return locations
    }

    fun reset() {
        for (n in nodes) {
            n?.d = Previous()
            n?.found = false
        }
    }

    fun readInterestLoc(readerfile: File){
        var reader = BufferedReader(FileReader(readerfile))
        var st = StringTokenizer(reader.readLine())

        var length = st.nextToken().toInt()


        for(i in 0 until length){
            st = StringTokenizer(reader.readLine())
            var nodeNr = st.nextToken().toInt()
            var code = st.nextToken().toInt()
            var nameOfPlace = st.nextToken().toString()
            nodes[nodeNr]?.code = code
            nodes[nodeNr]?.nameOfPlace = nameOfPlace
        }
    }
}