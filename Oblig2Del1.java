import java.io.File;
import java.util.HashMap;
import java.util.Scanner;

class Oblig2Del1{
    public static void main(String[] args) {
        if(args.length == 0){
            System.out.println("Angi filbane som parameter");
            return;
        }

        String mappebane = args[0];
        SubsekvensRegister subsekvensRegister = new SubsekvensRegister();

        File metadataFil = new File(mappebane + "/metadata.csv");

        try(Scanner scanner = new Scanner(metadataFil)){
            while (scanner.hasNextLine()){
                String filnavn = scanner.nextLine().trim();
                if(!filnavn.isEmpty()){
                    File fil = new File(mappebane + "/" + filnavn);
                    if(fil.exists()){
                        HashMap<String,Subsekvens> hashBeholderen = SubsekvensRegister.fil(fil.getAbsolutePath());
                        subsekvensRegister.settInn(hashBeholderen);
                    }
                }     
            }
        } catch(Exception e) {
            System.out.println("Feil ved lesing: " + e.getMessage());
            return;
        }

        while(subsekvensRegister.antallHash() > 1){
            HashMap<String,Subsekvens> subsekvenser1 = subsekvensRegister.taUt();
            HashMap<String,Subsekvens> subsekvenser2 = subsekvensRegister.taUt();
            HashMap<String,Subsekvens> sammenSlaatt = SubsekvensRegister.slaaSammen(subsekvenser1, subsekvenser2);
            subsekvensRegister.settInn(sammenSlaatt);
        }

        HashMap<String,Subsekvens> resultat = subsekvensRegister.taUt();

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