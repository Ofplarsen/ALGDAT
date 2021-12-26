import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

public class MainOpg1 {

    public static void main(String[] args) {
        Hashtabell hashtabell = new Hashtabell();
        String[] allNames = new String[118];
        try {

            BufferedReader reader = Files.newBufferedReader(Paths.get(args[0]));

            Scanner myReader = new Scanner(reader);
            int i = 0;
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                allNames[i] =  data;
                i++;
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        for(int i = 0; i < allNames.length; i++){
            hashtabell.add(allNames[i]);
        }

        //Antall kollisjoner
        System.out.println(hashtabell.getCollisions());
        System.out.println("Antall kollisjoner: " + hashtabell.getNumberOfCollisions() + "\nKollisjoner per person: " + hashtabell.getNumberOfCollisionsPerPerson() + "\nLastfaktor: " + hashtabell.getLastfaktor());

        //Slår opp på person
        System.out.println("Finn meg: " + hashtabell.findPerson("Olav Finne Præsteng Larsen"));
    }
}
