package deloppgave1;

public class EnkelLenke {
    private Node hode = null;
    private int antElementer = 0;
    private Node hale = null;
    private Node forrige = null;



    public void settInnBakerst(double verdi){
        //O(1) pga halen
        Node denne = new Node(verdi, null);
        if(hode == null) {
            hode = denne;
        }
        else{
            hale.neste = denne;
        }
        hale = denne;
        hale.neste = hode;
        ++antElementer;

    }

    public Node josephusProblem(int intervall){
        fjern(finnNr(intervall)); //O(m)
        Node fjerne = forrige;
        while (antElementer > 1){
            //O(n)
            fjerne = forrige;
            for (int i = 0; i < intervall; i ++){

                fjerne = fjerne.neste;
                //O(m)
            }
            fjern(fjerne);
        }
        return finnNr(1);
    }

    public Node fjern(Node n){
        Node denne = hode;

        //forrige = null;
        //Looper fram til den gitte noden = forrige
        //Og denne = den neste noden
        while (denne != null && denne != n){
            forrige = denne;
            denne = denne.neste;
            //O(n)
        }
        //Sjekker om noden fins
        if(denne != null){
            //Hvis forrige != null, så vil forrige.neste = denne.neste
            if(n != hode && n != hale){
                forrige.neste = denne.neste;
                //Hvis ikke vil hode = denne.neste
            }else if(n == hale) {
                //Hvis n = hale, sett ny hale til forrige før gamle halen fjernes
                hale = forrige;
                forrige.neste = hode;
            }else
            {
                hode = denne.neste;
                forrige = hale;
                forrige.neste = hode;
            }
            denne.neste = null;
            --antElementer;
            return denne;
        }else {
            return null;
        }
    }


    public Node finnNr(int nr){
        Node denne = hode;

        for (int i = 1; i < nr; ++i) {
            denne = denne.neste;
        }

        return denne;
    }

}
