import java.util.Random;

public class Testdata {
    private final int[] kursforandring = {-1,3,-9,2,2,-1,2,-1,-5};
    //private int[] kursforandring;
    private int bestDayToBuy;
    private int bestDayToSell;
    private int bestDaySum;
    public Testdata() {

        /*
        Code used to make an array with n different numbers from -10 to 10
        Random random = new Random();
        kursforandring = new int[100000];

        for(int i = 0; i < 100; i++){
            kursforandring[i] = random.nextInt(10+10) - 10;
        }
        */
        bestDayToBuy = 0;
        bestDayToSell = 0;
        bestDaySum = 0;
    }

    public int[] getKursforandring() {
        return kursforandring;
    }

    public void oppgave1(){
        //Variables that are used to keep track of what day to sell/buy, current sum, and the best days / highest score of those
        int dayToSell = 0;
        int dayToBuy = 0;
        int currentSum = 0;
        //Loops through every day
        for(int n = 0; n < kursforandring.length-1; n++){
            currentSum = 0;
            //Checks every remaining days of that day to check where it is best to sell this day
            for(int i = 0; i+n < kursforandring.length; i++){
                dayToBuy = n;
                dayToSell = i+n; //Makes sure that you can't sell the same day you buy
                currentSum += kursforandring[i+n]; //keeps track of the value of the stock

                //If the value of the stock, minus the start value is higher than the last highest sum
                //Set the new best day to buy/sell to this day, as well as noting the difference
                if(currentSum - kursforandring[dayToBuy] > bestDaySum){
                    this.bestDayToBuy = dayToBuy;
                    this.bestDayToSell = dayToSell;
                    this.bestDaySum = currentSum - kursforandring[dayToBuy];
                }
            }

        }
    }

    public String getResult(){
        return "Largest profit: "+bestDaySum + "\n" + "Day to buy: " +(bestDayToBuy+1)  + "\nDay to sell: " + (bestDayToSell+1);
    }
}
