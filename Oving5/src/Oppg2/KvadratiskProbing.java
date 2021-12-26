package Oppg2;

public class KvadratiskProbing extends HashtabellOpg2 {
    public KvadratiskProbing(Integer[] tabell) {
        super(tabell);

        for(int i = 0; i < tabell.length; i++){
            legginn(tabell[i], super.hashedTabell);
        }
    }

    @Override
    public int probe(int h, int i, int m) {
        int k1 = 13;
        int k2 = 17;
        return (h + k1*i + k2*i*i) % m;
    }


}
