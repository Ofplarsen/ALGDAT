import java.util.Date;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        /*
        System.out.println("powCalc: " + powCalc(3,10));
        System.out.println("powCalcSplit: "+ powCalcSplit(3,10));
        System.out.println("Math.pow: " + Math.pow(3, 10)+"\n");

        System.out.println("powCalc: " + powCalc(5,3));
        System.out.println("powCalcSplit: "+ powCalcSplit(5,3));
        System.out.println("Math.pow: " + Math.pow(5, 3)+"\n");

        System.out.println("powCalc: " + powCalc(3,100));
        System.out.println("powCalcSplit: "+ powCalcSplit(3,100));
        System.out.println("Math.pow: " + Math.pow(3, 100));


         */
        int i = 1;
        int b = 3;

        int n = 100;
        double x = 1.001;
        //tidtaker(x, n);
    }
    //2.1-1
    public static double powCalc(double x, int n){
        if(n==0){
            return 1;
        }
        return x*powCalc(x,n-1);
    }

    //2.2-3
    public static double powCalcSplit(double x, int n){
        if(n==0){
            return 1;
        }
        if(n%2 != 0){
            return x*powCalcSplit(x*x,(n-1)/2);
        }else{
            return powCalcSplit(x*x,n/2);
        }
    }

    public static void tidtaker(double x, int n){
        Date start = new Date();
        int runder = 0;
        double tid;
        Date slutt;
        do {
            powCalcSplit(x,n);
            slutt = new Date();
            ++runder;
        } while (slutt.getTime()-start.getTime() < 1000);
        tid = (double)
                (slutt.getTime()-start.getTime()) / runder;
        System.out.println("Millisekund pr. runde:" + tid);
    }
}
