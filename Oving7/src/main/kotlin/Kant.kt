
abstract class Kant (var til: Node?=null, var neste: VKant?=null){

}

class Node {
    var kant1: VKant? = null
    var d: Previous? = null
    var placement: Int? = -1;

    fun addKant(kantToAdd : VKant){
        if(kant1 == null){
            kant1 = kantToAdd as VKant
        }else {
            var kant: VKant? = kant1
            while (kant?.neste != null) {

                kant = kant?.neste as VKant?;
            }
            kant?.neste = kantToAdd
        }
    }

    override fun toString(): String {
        return "Node(placement=$placement)"
    }

}
