import java.io.*
import java.util.*


class Filbehandler {
    //Variabler til Lempel Ziv
    var index = 0
    var sluttIndex = 0
    lateinit var dataLZ: ByteArray
    lateinit var bufferLZ: ByteArray
    val referenceMinLength = 6

    //Variabler til Huffman
    var listeMedNoder = ArrayList<BitNode>()
    var listeMedBytes = ArrayList<Byte>()
    var heapHuffman = HeapHuffman()
    var sisteByte: Int = 0
    lateinit var outputHuffman: DataOutputStream

    fun komprimer(inputFile: File, outputFile: File){
        lZKomprimer(inputFile,File("LZKomp.ozip"))
        huffmanKomprimering(File("LZKomp.ozip"), outputFile)
    }

    fun dekomprimer(inputFile: File, outputFile: File){
        huffmanDekomprimering(inputFile, File("huffLZDekompHuff.ozip"))
        lZDekomprimer(File("huffLZDekompHuff.ozip"), outputFile)
    }

    private fun lZKomprimer(inputFile: File, outputFile: File){

        /*
        Sudokode:
        1. Les igjennom fila
        2. Input kopieres til output som vanlig
        3. Hvis en lang nok sekvens kommer omigjen:
            3a. Dropp denne sekvensen, skriv heller en referanse til output
            3b. Format: repeterer X tegn, som vi har sett Y tegn tidligere
[Byte-verdi for antall tegn u komprimert]Problemer, p[byte-verdi for hvor mange hakk tilbake for å finne match, antall tegn kopiert]
[antall ukomprimerte tegn som kommer]. Alltid....
        4. Hjelper hvis sekvensen er lenger enn en slik referanse
        5. Søker bakover i et sirkulært buffer
            5a. Si 65536 tegn bakover. Hvis noen av tegnene er lik
        6. Output kan komprimeres videre med Huffman-koding
         */

        //Lager stream for både input og output
        val input = DataInputStream(FileInputStream(inputFile))
        val output = DataOutputStream(FileOutputStream(outputFile))
        dataLZ = input.readAllBytes()
        input.close()
        bufferLZ = ByteArray(65536)
        index = 0
        sluttIndex = 0
        var sistePlass = 0
        //Looper igjennom alle bytes
        var whileIndex = 0
        while(whileIndex < dataLZ.size){
            //Finner ut om byten fins i bufferen
            var indexBuffer = findInBuffer(dataLZ.get(whileIndex), sluttIndex)

            //Hvis den ikke fins legg den til
            if(indexBuffer == -1){
                leggReferanseIBuffer(dataLZ.get(whileIndex))
                index++
            }else{
                var max = lagOrd(indexBuffer, whileIndex)
                var maxIndex = indexBuffer
                while (indexBuffer != -1){
                    indexBuffer = findInBuffer(dataLZ.get(whileIndex),indexBuffer-1)
                    if(indexBuffer == -1){
                        break
                    }
                    if(lagOrd(indexBuffer, whileIndex) > max){
                        max = lagOrd(indexBuffer, whileIndex)
                        maxIndex = indexBuffer
                    }
                }

                if(max > referenceMinLength){
                    output.writeShort(index -sistePlass)
                    var count = 0
                    var temporaryByteArray = ByteArray(index - sistePlass)

                    for (n in sistePlass until index){
                        try {
                            temporaryByteArray[count++] = dataLZ.get(n)
                        }catch (e: ArrayIndexOutOfBoundsException){
                            println("Fail")
                            System.exit(0)
                        }
                    }
                    //Skriver inn referansen til bufferen
                    output.write(temporaryByteArray)
                    output.writeShort((-(whileIndex-maxIndex)))
                    output.writeShort(max)

                    for (n in 0 until max){
                        leggReferanseIBuffer(dataLZ[whileIndex++])

                        index++
                    }
                    sistePlass = whileIndex
                    whileIndex--
                }else{
                    leggReferanseIBuffer(dataLZ[whileIndex])
                    index++
                }
            }
            whileIndex++
        }

        output.writeShort(index-sistePlass)
        for (i in sistePlass until index){

            output.writeByte(dataLZ.get(i).toInt())
        }
        output.close()
    }


    private fun lZDekomprimer(inputFile: File, outputFile: File){
        var output = DataOutputStream(FileOutputStream(outputFile))
        var input = DataInputStream(FileInputStream(inputFile))

        index = 0
        sluttIndex = 0
        bufferLZ = ByteArray(65536)
        var start = input.readShort()
        dataLZ = ByteArray(start.toInt())

        input.readFully(dataLZ)
        output.write(dataLZ)

        for(i in 0 until start){
            leggReferanseIBuffer(dataLZ.get(i))
            index++
        }

        while(input.available() > 0){
            var bak = input.readShort()
            var kopi = input.readShort()
            var slutt = sluttIndex
            dataLZ = ByteArray(kopi.toInt())
            var localIndex = 0
            var bakOgSlutt = bak+slutt

            for(n in (bakOgSlutt) until bakOgSlutt+kopi){
                var localByteIndex = getByteFromBuffer(n)
                dataLZ[localIndex++] = (localByteIndex)
                leggReferanseIBuffer(localByteIndex)
                index++
            }

            output.write(dataLZ)
            start = input.readShort()
            dataLZ = ByteArray(start.toInt())
            input.readFully(dataLZ)
            for (n in 0 until start){
                leggReferanseIBuffer(dataLZ.get(n))
                index++
            }
            output.write(dataLZ)
        }
        input.close()
        output.close()
    }


    private fun findInBuffer(b: Byte, position: Int): Int{
        for(i in position downTo 0){
            if(bufferLZ.get(i) == b){
                return i
            }
        }
        return -1
    }

    private fun leggReferanseIBuffer(byte: Byte){
        if(index >= bufferLZ.size){
            sluttIndex = 0
        }

        bufferLZ[sluttIndex] = byte
        sluttIndex = (index+1)%bufferLZ.size
    }

    /**
     * Henter ut riktig byte fra bufferen
     */
    private fun getByteFromBuffer(localIndex: Int): Byte{
        //Hvis indexen er større en bufferstørrelsen
        var i = localIndex%bufferLZ.size
        //Her bruker man modulo til å hente riktig buffer
        if(localIndex >= bufferLZ.size){
            return bufferLZ.get(i)
        }
        //Hvis indexen er negativ, legger man til bufferstørrelsen for å få positiv og riktig index
        else if (localIndex < 0){
            i = i + bufferLZ.size
            //Hvis i er like stor som bufferstørrelsen, så henter man første plass i bufferen
            if(i == bufferLZ.size){
                return bufferLZ.get(0)
            }
            return bufferLZ.get(i)
        }
        //Eller så returnerer man bare byten fra bufferen på gitt index
        return bufferLZ.get(localIndex)
    }


    private fun lagOrd(indexBuffer: Int, indexByte: Int): Int{
        var buffer = getByteFromBuffer(indexBuffer)
        var byte = dataLZ.get(indexByte)
        var wordLength = 0
        var indexBufferModified = indexBuffer
        var indexByteModified = indexByte
        while(buffer == byte && indexByteModified != dataLZ.size-1 ){
            byte = dataLZ[++indexByteModified]
            buffer = getByteFromBuffer(++indexBufferModified)
            wordLength++
        }
        return wordLength
    }

    private fun huffmanKomprimering(inputFile: File, outputFile: File){

        /*
        Psudokode Huffman
        Del 1:
        1. Teller hvor mange ganger hvert tegn i en tekst (og intervall?) forekommer
        2. Lag et binærtre for hvert tegn
            2a. Skal bestå av bare en rot
            2b. Tegnet og frekvensen skal lagres i rota
        3. Legg treet inn i en prioritetskø, hvor den med lavest frekvens kommer først ut av køen
            3a. Trær med lik frekvens spiller ikke rekkefølge rolle
        4. Dette burde implemeneters med en heap (kapittel 7)

        Del 2:
        1. Hent to og to trær ut av heapen
            1a. Trærne blir høyre og venstre subtre i et nytt tre
            1b. Rota skal få frekvensen som er summen av frekvensene til de to subtrærne
            1c. Sett deretter treet inn i prioritetskøen igjen
            1d. Fortsett til vi står igjen med ett stort tre
        2. Deretter setter vi nuller (venstre) og enere (høyre) på greinene

        Del 3:
        1. Skriv ned frekvenstabellen i fila, slik at man kan tydelig se frekvensen av ulike tegn for dekomprimering
         */


        var frekvenser = IntArray(256)
        var bitstrenger = arrayOfNulls<String>(256)
        var bytes: ArrayList<Byte> = ArrayList()
        var input = DataInputStream(FileInputStream(inputFile))
        var output = DataOutputStream(FileOutputStream(outputFile))

        var index = 0
        //Slik som oppgaveteksten anbefalte med 64bit osv
        var byte: Long = 0
        var sisteByte = 0
        for(i in 0 until input.available()){
            frekvenser[input.read()]++
        }


        var hufmannRoot = lagHufmannTreKomprimering(frekvenser)

        //Gjør om til bitstrenger slik at man kan bruke disse senere og gjøre de om til byte
        giGreineneBinaerStrengVerdi(hufmannRoot, "", bitstrenger)
        var input2 = FileInputStream(inputFile)
        //Looper igjennom frekvensene og lagrer dem i fila for dekomprimering seinere
        for(i in frekvenser){
            output.writeInt(i)
        }
        var mPlus1 = 0
        //Lager og legger til alle bytes i en ArrayList
        for (n in 0 until input2.available()){
            index = input2.read()*(-1)
            if(index < 0){
                index *= (-1)
            }


            var bitRepresentertIString = bitstrenger[index]

            for (m in 0 until bitRepresentertIString?.length!!){

                if(bitRepresentertIString.get(m) != '0'){

                    byte = (byte shl 1) or 1

                }else{
                    byte = (byte shl 1)
                }
                mPlus1++
                if(mPlus1.mod(8) == 0){
                    bytes.add(byte.toByte())
                    byte = 0
                }

                sisteByte = mPlus1.mod(8)
            }
        }
        //Hvis det var noen som ikke ble ferdig bygd
        if(sisteByte != 0){
            for (i in sisteByte until 8){
                byte = byte shl 1
            }
        }
        bytes.add(byte.toByte())

        output.writeInt(sisteByte)

        //Skriver bytes i den kompilerte fila
        for (localByte in bytes){
            output.write(localByte.toInt())
        }


        input.close()
        output.close()
    }

    private fun lagHufmannTreKomprimering(frekvenser: IntArray): BitNode{
        heapHuffman = HeapHuffman()
        heapHuffman.len = 256
        for (i in frekvenser.indices){
            if(frekvenser[i] == 0){

            }else{
                var node = BitNode(i.toChar(), frekvenser[i])
                heapHuffman.sett_inn2(node)
            }
        }

        var treRot = BitNode(null, 0)

        while (heapHuffman.prioKo.size > 1){
            //Plukker først ut noden til venstre grein
            var venstreGrein = heapHuffman.prioKo.poll()
            //Deretter vil neste min være høyre grein
            var hoyreGrein = heapHuffman.prioKo.poll()
            //Setter subRot (roten til venstre-og-høyre-greinene) til karakter null, og frekvensen er samlet frekvens for greinene
            var subRot = BitNode(null, venstreGrein.frekvens + hoyreGrein.frekvens)

            //Angir venstre og høyre til subroten
            subRot.hoyre = hoyreGrein
            subRot.venstre = venstreGrein

            //Legger til det nye treet i heapen, slik oppgaven ber om
            heapHuffman.prioKo.add(subRot)
            treRot = subRot
        }

        return treRot
    }

    private fun giGreineneBinaerStrengVerdi(rot: BitNode, byteInString: String, bitstring: Array<String?>) :Boolean{
        if(rot.isParent()){
            try {
                bitstring[rot.tegn?.code!!] = byteInString
            }catch (e: ArrayIndexOutOfBoundsException){
                println(rot.tegn)
                println(rot.frekvens)
            }
            return true
        }

        rot.venstre?.let { giGreineneBinaerStrengVerdi(it, byteInString + "0", bitstring) }
        rot.hoyre?.let { giGreineneBinaerStrengVerdi(it, byteInString + "1", bitstring) }
        return false
    }

    private fun huffmanDekomprimering(inputFile: File, outputFile: File){
        /*
        Psudokode:
        1. Lese inn frekvenstabellen som er lagret i den komprimerte fila
        2. Bygge ut nytt huffmanntre basert på frekvensene lest inn
        3. Lage BitStrings av bytesene i fila og gjør disse om til tegn for å få riktige tegn ved hjelp av treet
            3a. Legg til alle riktige tegn i en ArrayListe med Bytes
        4. Skriv Bytsene til fila
         */
        var bytes: ByteArray
        var length: Int
        var bitstring: Bitstring
        var input = DataInputStream(FileInputStream(inputFile))
        outputHuffman = DataOutputStream(FileOutputStream(outputFile))
        //Oppretter frekvenstabellen som skal leses inn
        var frekvenser= IntArray(256)

        var treRot = lagHufmannTreDekomprimering(frekvenser, input)
        bytes = input.readAllBytes()
        length = bytes.size
        //Implementerer Bitstring for å kunne letter bygge bits
        //Bygger tom bitstring

        bitstring = Bitstring(0,0)

        if(sisteByte > 0){
            length--
        }

        for (i in 0 until length){

            var localBitstring = Bitstring(bitTilLong(bytes.get(i), 8), 8)
            bitstring = bitstring.mergeBitstrings(bitstring, localBitstring)!!
            bitstring = gjorOmBitstringTilChar(treRot, bitstring)
        }



        if(sisteByte > 0){
            var bit = Bitstring(bytes[length].toLong() shr 8 - sisteByte,sisteByte)
            bitstring = bitstring.mergeBitstrings(bitstring, bit)!!
            gjorOmBitstringTilChar(treRot, bitstring)
        }

        for(bytesToWrite in listeMedBytes){
            outputHuffman.write(bytesToWrite.toInt())
        }

        input.close()
        outputHuffman.flush()
        outputHuffman.close()
    }

    private fun lagHufmannTreDekomprimering(frekvenser: IntArray, input: DataInputStream): BitNode {
        var heap = HeapHuffman()
        heap.len = 256
        //Leser inn de første 256 ints og legger i frekvenstabellen
        //Legger til alle frekvensene som tilhører noder (ikke er 0) i en tabell som brukes senere for å
        listeMedNoder = ArrayList<BitNode>()
        for (i in frekvenser.indices){
            frekvenser[i] = input.readInt()
            if(frekvenser[i] != 0){
                listeMedNoder.add(BitNode(i.toChar(),frekvenser[i]))
            }
        }

        sisteByte = input.readInt()
        listeMedBytes = ArrayList()
        heap.prioKo = PriorityQueue<BitNode>(256, Comparator<BitNode> { a: BitNode, b: BitNode -> a.frekvens - b.frekvens })
        heap.prioKo.addAll(listeMedNoder)

        var treRot = BitNode(null, 0)


        while (heap.prioKo.size > 1){
            //Plukker først ut noden til venstre grein
            var venstreGrein = heap.prioKo.poll()
            var hoyreGrein = heap.prioKo.poll()
            //Deretter vil neste min være høyre grein
            //Setter subRot (roten til venstre-og-høyre-greinene) til karakter null, og frekvensen er samlet frekvens for greinene
            var subRot = BitNode(null, venstreGrein.frekvens + hoyreGrein.frekvens)


            subRot.hoyre = hoyreGrein
            subRot.venstre = venstreGrein

            heap.prioKo.add(subRot)
            treRot = subRot
        }
        return treRot
    }

    private fun gjorOmBitstringTilChar(tre: BitNode, bitstring: Bitstring): Bitstring{
        var localTre = tre
        var index = (1 shl bitstring.length -1).toLong()
        var counter = 0
        while (index != 0L){
            localTre = if((bitstring.bits and index) != 0L){

                localTre.hoyre!!
            }else{

                localTre.venstre!!
            }
            counter++
            if(localTre.venstre == null) {
                localTre.tegn?.toByte()?.let { listeMedBytes.add(it) }
                var test = (0).inv().toLong()

                bitstring.bits = bitstring.bits and test
                bitstring.length = bitstring.length - counter

                counter = 0
                localTre = tre
            }
            index = index shr 1
        }

        return bitstring
    }

    private fun bitTilLong(byte: Byte, length: Int): Long{
        var localLong: Long = 0
        var i = (1 shl length - 1).toLong()
        while (i != 0L) {

            if ((byte.toLong() and i) == 0L) {
                localLong = localLong shl 1
            } else{
                localLong = (localLong shl 1) or 1
            }

            i = i shr 1
        }
        return localLong
    }
}

