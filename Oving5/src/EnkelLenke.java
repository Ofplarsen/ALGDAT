public class EnkelLenke {
        private Node hode = null;
        private int antElementer = 0;
        private Node hale = null;



        public void settInnBakerst(String verdi, int key){
            //O(1) pga halen
            Node denne = new Node(verdi, null, key);
            if(hode == null) {
                hode = denne;
            }
            else{
                hale.neste = denne;
            }
            hale = denne;
            hale.neste = null;
            ++antElementer;

        }


        public Node finnPerson(String name){
            Node denne = hode;

            for (int i = 0; i < antElementer; ++i) {
                if(denne.finnElement().equals(name)){
                    return denne;
                }
                denne = denne.neste;
            }

            return null;
        }

    public int getAntElementer() {
        return antElementer;
    }

    public Node getHale() {
        return hale;
    }

    @Override
    public String toString() {
        return "EnkelLenke{" +
                "hode=" + hode.toString() +
                ", antElementer=" + antElementer +
                "}";
    }

}

