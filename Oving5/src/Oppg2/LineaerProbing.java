package Oppg2;

public class LineaerProbing extends HashtabellOpg2 {

    public LineaerProbing(Integer[] tabell) {
        super(tabell);

        for(int i = 0; i < tabell.length; i++){
            legginn(tabell[i], super.hashedTabell);
        }
    }


    @Override
    public int probe(int h, int i, int m) {
        return (h+i) % m;
    }

}
