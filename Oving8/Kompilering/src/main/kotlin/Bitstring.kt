class Bitstring (var bits: Long,var length: Int){

    fun mergeBitstrings(bs1: Bitstring, bs2: Bitstring): Bitstring?{
        var merged = Bitstring(0,0)

        merged.length = bs1.length + bs2.length

        if(merged.length <= 64) {
            //Merger bitstrengene sammen til en ny bitstring
            merged.bits = bs2.bits or (bs1.bits shl bs2.length)

            return merged
        }

        return null
    }

}