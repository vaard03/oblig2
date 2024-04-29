import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Oblig2Del2Hele {
    public static void main(String[] args) throws InterruptedException {
        
        if(args.length == 0){
            System.out.println("Angi filbane som parameter: ");
            return;
        }
        Monitor2 syk = new Monitor2();
        Monitor2 frisk = new Monitor2();

        ArrayList <Thread> leseTraadListe = new ArrayList<>();
        String mappebane = args[0] + "/";

        File metadataFil = new File(mappebane + "metadata.csv");
            
        try{
            Scanner scanner = new Scanner(metadataFil);
            while (scanner.hasNextLine()){
                String[] tekst = scanner.nextLine().split(",");
                String filnavn = mappebane + tekst[0];
                String bool = tekst[1];    

                if(bool.equals("False")){
                    LeseTraad leseTraad = new LeseTraad(frisk, filnavn);
                    Thread traad = new Thread(leseTraad);
                    leseTraadListe.add(traad);
                }
                else if(bool.equals("True")){
                    LeseTraad leseTraad = new LeseTraad(syk, filnavn);
                    Thread traad = new Thread(leseTraad);
                    leseTraadListe.add(traad);
                }
                    
            }
            scanner.close();
        }catch(FileNotFoundException e){
            System.out.println("Fant ikke fil: " + e.getMessage());
            return;    
        }
        for(Thread i : leseTraadListe){
            i.start();
        }
        try{
            for(Thread i : leseTraadListe){
                i.join();
            }
        }catch(InterruptedException e){
            System.out.println("feil ved lesing av fil: " + e.getMessage());
        }
                
        try{
            ArrayList<Thread> fletteTraadListe = new ArrayList<>();
                
            while(FletteTraad.antFletteTraader < 8){
                FletteTraad fletteTraad = new FletteTraad(syk);
                Thread trad = new Thread(fletteTraad);
                fletteTraadListe.add(trad);
                trad.start();
            }
            while(FletteTraad.antFletteTraader < 8+8){
                FletteTraad fletteTraad = new FletteTraad(frisk);
                Thread trad = new Thread(fletteTraad);
                fletteTraadListe.add(trad);
                trad.start();
                
            }
            for (Thread i : fletteTraadListe){
                i.join();
            }
        } catch(InterruptedException e){
            System.out.println("Feil med flettetraad: " + e.getMessage());
        }

        HashMap<String,Subsekvens> friskHash = frisk.hent(0);
        HashMap<String,Subsekvens> sykHash = syk.hent(0);

        Subsekvens dominant = null;
        int ant = 0;
        int teller = 0;  
        
        for (String i : sykHash.keySet()) {
          
            if (friskHash.containsKey(i)) {
                if (sykHash.get(i).hentAntall() >= friskHash.get(i).hentAntall()) {
                    ant = sykHash.get(i).hentAntall() - friskHash.get(i).hentAntall();
                } else {ant = sykHash.get(i).hentAntall() - friskHash.get(i).hentAntall();}
            } else {
                ant = sykHash.get(i).hentAntall();
            }
            if (ant >= 7) {
                dominant = sykHash.get(i);
                System.out.println(dominant.hentString()+ " forekommer " + ant + " flere ganger hos syk enn frisk");
            }
            if (ant > teller) {
                teller = ant;
                dominant = sykHash.get(i);
            }   
        } 
        System.out.println("Dominant gen: " + dominant); 
    }
}

  
