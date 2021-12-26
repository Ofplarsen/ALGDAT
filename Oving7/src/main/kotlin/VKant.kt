open class VKant(til: Node?, neste: VKant? = null, var kapasitet:Int = 0, var fra:Node?) : Kant(til, neste) {
    var flyt: Int = 0
    lateinit var motsattKant: VKant

    fun getRestKap(): Int {
        return kapasitet - flyt
    }

    fun increaseFlow(flow : Int){
        flyt += flow
        motsattKant.flyt -= flow
    }



    override fun toString(): String {
        return "VKant(kapasitet=$kapasitet, flyt=$flyt)"
    }

}