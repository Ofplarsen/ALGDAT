package deloppgave1;

import deloppgave1.EnkelLenke;

import java.util.Arrays;
import java.util.Date;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        int n = 41;
        int intervall = 3;

        System.out.println("Plass nr: " + josephusProblem(n, intervall).element + ", for n: " + n + ", m: " + intervall);

    }

    public static Node josephusProblem(int antall, int intervall){

        EnkelLenke sirkulaerListe = new EnkelLenke();
        for(int i = 1; i <= antall; i++){
            sirkulaerListe.settInnBakerst(i);
            //O(n)
        }
        return sirkulaerListe.josephusProblem(intervall);
    }

}
