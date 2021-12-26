import java.util.Arrays;
import java.util.Date;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        /*
        int [] array = getRandom(100);
        
        System.out.println(Arrays.toString(array));
        System.out.println("Sum: " + getSum(array));
        quicksortMedHjelp(array, 0, array.length-1);
        System.out.println(Arrays.toString(array));
        System.out.println("Sorted: " + sorted(array));
        System.out.println("Sum: " + getSum(array));
        
         */
        Random random = new Random();
        int n = 1000;
        tidtaker(n, random);
    }

    public static int getSum(int []t){
        int sum = 0;
        for (int i = 0; i < t.length; i++){
            sum += t[i];
        }
        return sum;
    }

    public static boolean sorted(int []t){
        for (int i = 1; i < t.length; i++){
            if (t[i-1] > t[i]){
                System.out.println(i);
                System.out.println(t[i-1] + ", " + t[i]);
                return false;
            }
        }
        return true;
    }

    public static int[] getRandom(int n, Random random){
        int[] array = new int[n];
        for (int i = 0; i < n; i++){
            array[i] = random.nextInt(100);
        }
        return array;
    }

    public static void bytt(int []t, int i, int j){
        int k = t[j];
        t[j] = t[i];
        t[i] = k;
    }

    private static int median3sort(int []t, int v, int h){
        int m = (v+h)/2;
        if(t[v] > t[m]){
            bytt(t,v,m);
        }
        if(t[m]>t[h]){
            bytt(t,m,h);
            if(t[v]>t[m]){
                bytt(t,v,m);
            }
        }
        return m;
    }

    public static int splitt(int []t, int v, int h){
        int iv, ih;
        int m = median3sort(t,v,h);
        int dv = t[m];
        bytt(t,m,h-1);
        for (iv = v, ih = h-1;;){
            while (t[++iv] < dv);
            while (t[--ih] > dv);
            if(iv >= ih) break;
            bytt(t,iv,ih);
        }
        bytt(t,iv,h-1);
        return iv;
    }

    public static void quicksort(int []t, int v, int h){

        if(h - v > 2){
            int delepos = splitt(t,v,h);
            quicksort(t, v, delepos-1);
            quicksort(t, delepos+1, h);

        }else {
            median3sort(t,v,h);
        }
    }

    public static void quicksortMedHjelp(int []t, int v, int h){
        int delingsverdi = 95;
        if(h - v > delingsverdi){
            int delepos = splitt(t,v,h);
            quicksortMedHjelp(t, v, delepos-1);
            quicksortMedHjelp(t, delepos+1, h);

        }else {
            innsettingssortering(t,v,h);
        }

    }

    public static void innsettingssortering(int []t, int fra, int til){
        for(int j = fra; j < til+1; ++j){
            int bytt = t[j];
            int i = j -1;
            while (i>=fra && t[i] > bytt){
                t[i+1] = t[i];
                --i;
            }
            t[i+1] = bytt;
        }
    }

    public static void tidtaker(int n, Random random){
        Date start = new Date();
        int runder = 0;
        double tid;
        Date slutt = null;
        do {
            int [] array = getRandom(n, random);
            int firstSum = getSum(array);
            quicksortMedHjelp(array,0,array.length-1);
            quicksortMedHjelp(array,0,array.length-1);
            if(getSum(array) != firstSum || !sorted(array)){
                System.out.println(Arrays.toString(array));
                System.out.println(sorted(array));
                System.out.println("Error in code");
                System.out.println("Sums: "+ firstSum +", " +getSum(array));
                break;
            }
            slutt = new Date();
            ++runder;
        } while (slutt.getTime()-start.getTime() < 1000);
        try {
            tid = (double)
                    (slutt.getTime() - start.getTime()) / runder;
            System.out.println("Millisekund pr. runde:" + tid);
        }catch (Exception e){

        };
    }
}
