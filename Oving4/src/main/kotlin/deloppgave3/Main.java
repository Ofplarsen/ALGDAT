package deloppgave3;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) {
        Tre tre = new Tre();

        tre.rot = new TreNode('/', null, null, null);
        //Venstre side
        tre.rot.venstre = new TreNode('*', tre.rot, null,null);
        tre.rot.venstre.venstre = new TreNode(3, tre.rot.venstre, null,null);
        tre.rot.venstre.høyre = new TreNode('+', tre.rot.venstre, null,null);
        tre.rot.venstre.høyre.venstre = new TreNode(2, tre.rot.venstre.høyre,null,null);
        tre.rot.venstre.høyre.høyre = new TreNode(4, tre.rot.venstre.høyre,null,null);
        //Høyre side
        tre.rot.høyre = new TreNode('-', tre.rot, null,null);
        tre.rot.høyre.venstre = new TreNode(7, tre.rot.høyre, null,null);
        tre.rot.høyre.høyre = new TreNode('*', tre.rot.høyre, null,null);
        tre.rot.høyre.høyre.venstre = new TreNode(2, tre.rot.høyre.høyre, null,null);
        tre.rot.høyre.høyre.høyre = new TreNode(2, tre.rot.høyre.høyre, null,null);


        tre.printInorden();
        System.out.println("Regnet uttrykk: " + tre.regnUttrykk());

    }
}
