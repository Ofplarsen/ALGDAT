package Oppg2;

import java.util.Date;
import java.util.Random;

public class MainOpg2 {
    public static void main(String[] args) {
        Integer[] tabell = new Integer[14000627];
        Random random = new Random();
        //Lager tabellen
        for(int i = 0;i < tabell.length; i++){
            if(i == 0){
                tabell[i] = random.nextInt(100)+1;
            }else{
                tabell[i] = tabell[i-1]+random.nextInt(100)+1;
            }
        }

        //Shuffler
        for(int i = 0;i < tabell.length-1; i++){
            int randomNumb = random.nextInt(tabell.length);
            int x = tabell[i];
            int y = tabell[randomNumb];

            tabell[i] = y;
            tabell[randomNumb] = x;
        }

        System.out.println(tidtaker(tabell).getAntallKollisjoner() + "\n");



    }

    public static HashtabellOpg2 tidtaker(Integer[] table){
        Date start = new Date();
        int runder = 0;
        double tid;
        Date slutt = null;
        LineaerProbing lineaerProbing = null;
        do {
            lineaerProbing = new LineaerProbing(table);

            slutt = new Date();
            ++runder;
        } while (slutt.getTime()-start.getTime() < 1000);
        try {
            tid = (double)
                    (slutt.getTime() - start.getTime()) / runder;
            System.out.println("Millisekund pr. runde:" + tid);
        }catch (Exception e){

        };
        return lineaerProbing;
    }
}
