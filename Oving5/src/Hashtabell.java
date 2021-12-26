public class Hashtabell {

    private EnkelLenke hashTabell;
    private int numberOfCollisions;
    private EnkelLenke[] tall;
    private String collisions;

    public Hashtabell() {
        hashTabell = new EnkelLenke();
        numberOfCollisions = 0;
        tall = new EnkelLenke[151];
        collisions = "";
    }

    public void add(String stringToAdd){
        int stringConverted = convertToInt(stringToAdd);
        if(tall[divHash(stringConverted, tall.length)] == null){
            tall[divHash(stringConverted, tall.length)] = new EnkelLenke();
            tall[divHash(stringConverted, tall.length)].settInnBakerst(stringToAdd,stringConverted);
        }else{
            collisions += tall[divHash(stringConverted, tall.length)].getHale().getKeyAndElement()  + " Kolliderer med: ";
            tall[divHash(stringConverted, tall.length)].settInnBakerst(stringToAdd,stringConverted);
            collisions += tall[divHash(stringConverted, tall.length)].getHale().getKeyAndElement() + "\n";
            numberOfCollisions++;
        }
    }

    public String findAllCollisions(){
        for(int i = 0; i < tall.length; i++){

        }
        return null;
    }

    public int getNumberOfCollisions() {
        return numberOfCollisions;
    }

    public double getNumberOfCollisionsPerPerson(){
        return (double)numberOfCollisions / tall.length;
    }

    private int convertToInt(String stringToConvert){
        //Uses Ascii
        int hashedString = 0;
        for(int i = 0; i < stringToConvert.length(); i++){
            hashedString = (hashedString*13+stringToConvert.charAt(i)) % tall.length;
        }
        return hashedString;
    }

    public static int divHash(int k, int m){
        return k % m;
    }

    private int getNumbElements(){
        int numbOfElements = 0;
        for (int i = 0; i < tall.length; i++){
            if(tall[i] == null){

            }else {
                numbOfElements += tall[i].getAntElementer();
            }
        }
        return numbOfElements;
    }

    public Node findPerson(String name){
        System.out.println(tall[divHash(convertToInt(name), tall.length)]);
        return tall[divHash(convertToInt(name), tall.length)].finnPerson(name);
    }

    public double getLastfaktor(){
        return (double) getNumbElements()/tall.length;
    }

    public EnkelLenke[] getHashTabell() {
        return tall;
    }

    public String getCollisions() {
        return collisions;
    }
}

