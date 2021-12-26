import java.util.Arrays;
import java.util.Date;

public class Main {

    public static void main(String[] args) {
        Testdata testdata = new Testdata();
        tidstaker(testdata);
        System.out.println(testdata.getResult());
    }


    public static void tidstaker(Testdata testdata){
        Date start = new Date();
        int runder = 0;
        double tid;
        Date slutt;
        do {
            testdata.oppgave1();
            slutt = new Date();
            ++runder;
        } while (slutt.getTime()-start.getTime() < 1000);
        tid = (double)
                (slutt.getTime()-start.getTime()) / runder;
        System.out.println("Millisekund pr. runde:" + tid);
    }
}

