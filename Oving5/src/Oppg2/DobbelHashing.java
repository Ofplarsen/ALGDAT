package Oppg2;

public class DobbelHashing extends HashtabellOpg2 {
    public DobbelHashing(Integer[] tabell) {
        super(tabell);
        for(int i = 0; i < tabell.length; i++){
            legginn(tabell[i], super.hashedTabell);
        }
    }

    public int legginn(Integer k, Integer []ht){
        int m = ht.length;
        int h1 = divHash(k.intValue(),m);
        int h2= 0;
        boolean collision = false;
        for(int i = 0; i < m; ++i){
            int j = probe(h1,h2,i,m);
            if(j < 0){
                j *= (-1);
            }
            if(ht[j] != null){
                if(collision){

                }else{
                    h2= h2(k.intValue(),m);
                }
                collision = true;
                antallKollisjoner++;
            }
            if(ht[j] == null){
                ht[j] = k;
                return j;
            }
        }
        if(collision){
            antallKollisjoner++;
        }
        return -1; //Fult
    }

    @Override
    public int probe(int h, int i, int m) {
        return 0;
    }

    public int probe(int h1, int h2,  int i, int m) {

        int pos = (h1 + i*h2) % m;
        return pos;
    }

    private int h2(int k, int m){
        return (k % (m-1)) + 1;
    }

}
