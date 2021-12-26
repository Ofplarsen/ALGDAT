import java.io.File

fun main(args: Array<String>) {

    var input = File(args[0])
    var output = File(args[1])
    var filbehandler = Filbehandler()
    filbehandler.komprimer(input, File("LZOgHuffKomp.ozip"))
    //filbehandler.dekomprimer(File("LZOgHuffKomp.ozip"), output)


}

