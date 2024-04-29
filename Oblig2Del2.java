import java.io.File;
import java.util.HashMap;
import java.util.Scanner;

public class Oblig2Del2 {
    public static void main(String[] args) {
        if(args.length == 0){
            System.out.println("Angi filbane som parameter");
            return;
        }

        String mappebane = args[0];
        Monitor2 monitor2 = new Monitor2();

        File metadataFil = new File(mappebane + "/metadata.csv");

        try(Scanner scanner = new Scanner(metadataFil)){
            while (scanner.hasNextLine()){
                String filnavn = scanner.nextLine();
                File fil = new File(mappebane + "/" + filnavn);
                LeseTraad leseTraad = new LeseTraad(monitor2, fil.getAbsolutePath());
                Thread traad = new Thread(leseTraad);
                traad.start();
            }
        } catch(Exception e) {
            System.out.println("Feil ved lesing" + e.getMessage());
            return;
        }

        try{
            Thread.sleep(1000);
        } catch(InterruptedException e) {
            System.out.println("Uventet feil: " + e.getMessage());
        }

        while(monitor2.antallHash() > 1){
            HashMap<String,Subsekvens> subsekvenser1 = monitor2.taUt();
            HashMap<String,Subsekvens> subsekvenser2 = monitor2.taUt();
            HashMap<String,Subsekvens> sammenSlaatt = SubsekvensRegister.slaaSammen(subsekvenser1, subsekvenser2);
            monitor2.settInn(sammenSlaatt);
        }

        HashMap<String,Subsekvens> resultat = monitor2.taUt();

        Subsekvens mestForekommende = null;
        for(Subsekvens subsekvens: resultat.values()){
            if (mestForekommende == null || subsekvens.hentAntall() > mestForekommende.hentAntall()){
                mestForekommende = subsekvens;
            }
        }
        if (mestForekommende == null){
            System.out.println("Ingen subsekvenser funnet");
        } else {
            System.out.println("Mest forekommende subsekvens: " + mestForekommende);
        }
    }
}

