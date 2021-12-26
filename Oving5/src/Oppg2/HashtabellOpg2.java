package Oppg2;

public abstract class HashtabellOpg2 {
    Integer []tabell;
    Integer []hashedTabell;
    int antallKollisjoner;
    final static int sizeOfTable = 14000627;
    public HashtabellOpg2(Integer[] tabell) {
        this.tabell = tabell;
        hashedTabell = new Integer[14000627];
        antallKollisjoner = 0;
    }

    public int legginn(Integer k, Integer []ht){
        int m = ht.length;
        int h = divHash(k.intValue(),m);
        boolean collision = false;
        for(int i = 0; i < m; ++i){
            int j = probe(h,i,m);
            if(j < 0){
                j *= (-1);
            }
            if(ht[j] == null){
                ht[j] = k;
                if(collision){
                    //antallKollisjoner++;
                }
                return j;
            }else{
                collision = true;
                antallKollisjoner++;
            }
        }
        return -1; //Fult
    }

    public abstract int probe(int h, int i, int m);

    public static int divHash(int k, int m){
        return k % m;
    }

    public Integer[] getHashedTabell() {
        return hashedTabell;
    }

    public int getAntallKollisjoner() {
        return antallKollisjoner;
    }
}


