import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Oblig2Del2B {
    public static void main(String[] args) throws InterruptedException, FileNotFoundException {
        if(args.length == 0){
            System.out.println("Angi filbane som parameter: ");
            return;
        }
        Monitor2 monitor2 = new Monitor2();
        ArrayList <Thread> leseTraadListe = new ArrayList<>();
        String mappebane = args[0] + "/";

        File metadataFil = new File(mappebane + "metadata.csv");

        try{
            Scanner scanner = new Scanner(metadataFil);
            while (scanner.hasNextLine()){
                String filnavn = mappebane + scanner.nextLine();

                LeseTraad leseTraad = new LeseTraad(monitor2, filnavn);
                Thread traad = new Thread(leseTraad);
                leseTraadListe.add(traad);
                traad.start();
                }
                for(Thread i : leseTraadListe){
                    i.join();
                }
                scanner.close();
        }catch(FileNotFoundException e){
            return;
        }catch(InterruptedException e) {
            System.out.println("Feil ved lesing: " + e.getMessage());
        }
        
        ArrayList<Thread> fletteTraadListe = new ArrayList<>();
        while(FletteTraad.antFletteTraader < 8){
            FletteTraad fletteTraad = new FletteTraad(monitor2);
            Thread trad = new Thread(fletteTraad);
            fletteTraadListe.add(trad);
            trad.start();
        }
        
        try{
            for (Thread i : fletteTraadListe){
                i.join();
            }
        } catch(InterruptedException e){
            System.out.println("Feil med flettetraad: " + e.getMessage());
        }

        HashMap<String,Subsekvens> resultat = monitor2.taUt();
        Subsekvens mestForekommende = null;
        for(Subsekvens subsekvens: resultat.values()){
            if (mestForekommende == null || subsekvens.hentAntall() > mestForekommende.hentAntall()){
                mestForekommende = subsekvens;
            }
        }
        if (mestForekommende == null){
            System.out.println("Ingen subsekvenser funnet: ");
        } else {
            System.out.println("Mest forekommende subsekvens: " + mestForekommende);
        }
    }
}


