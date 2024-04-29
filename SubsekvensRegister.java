import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.io.File;

public class SubsekvensRegister {
    private ArrayList<HashMap<String, Subsekvens>> subsekvensRegister;
    
    public SubsekvensRegister(){
        subsekvensRegister = new ArrayList<>();
    }
    public void settInn(HashMap<String, Subsekvens> subsekvenser){
        subsekvensRegister.add(subsekvenser);
    }

    public HashMap<String, Subsekvens> taUt(){
        if (subsekvensRegister.size() > 0) {
            return subsekvensRegister.remove(subsekvensRegister.size()-1);
        } else {
            return null;
        }
    }

    public int antallHash(){
        int antall = 0;
        for(HashMap<String, Subsekvens> i : subsekvensRegister) {
            antall++;
        }
        return antall;
    }

    public static HashMap<String, Subsekvens> fil(String filnavn){
        HashMap<String, Subsekvens> hashBeholderen = new HashMap<>();
        String linje;
        try {
            File file = new File(filnavn);
            try(Scanner filScanner = new Scanner(file)){
                while (filScanner.hasNextLine()){
                    linje = filScanner.nextLine();
                    if(linje.length() < 3){
                        System.out.println("Linjen er for kort");
                        System.exit(0);
                    } else{
                    for(int i = 0; i <= linje.length() - 3; i++){
                        String subsekvensen = linje.substring(i, i+3);
                        if(!hashBeholderen.containsKey(subsekvensen)){
                            Subsekvens nySubsekvens = new Subsekvens(1, subsekvensen);
                            hashBeholderen.put(subsekvensen, nySubsekvens); 
                        }
                    } 
                }
            } 
        }
    } catch (java.io.FileNotFoundException e){
            System.out.println("Finner ikke filen '" + filnavn + "'");
            System.exit(0);
        } 
        return hashBeholderen;
    }

    public static HashMap<String, Subsekvens> slaaSammen(HashMap<String, Subsekvens> hash1, HashMap<String, Subsekvens> hash2){
        HashMap<String, Subsekvens> slaattSammen = new HashMap<>();
        
        for(Map.Entry<String,Subsekvens> entry: hash1.entrySet()){
            String nokkel = entry.getKey();
            Subsekvens verdi = entry.getValue();
            slaattSammen.put(nokkel,verdi);
        }
        for(Map.Entry<String,Subsekvens> entry: hash2.entrySet()){
            String nokkel = entry.getKey();
            Subsekvens verdi = entry.getValue();

            if(!slaattSammen.containsKey(nokkel)){
                slaattSammen.put(nokkel,verdi);
            }
            else{
                Subsekvens eksisterendeVerdi = slaattSammen.get(nokkel);
                eksisterendeVerdi.endreAntall();
                slaattSammen.put(nokkel,eksisterendeVerdi);
            }
        }    
        return slaattSammen;
    }

    public HashMap<String, Subsekvens> hent(int indeks){
        return subsekvensRegister.get(indeks);
    }

    public String mestForekommende(Monitor2 monitor2){
     HashMap<String,Subsekvens> resultat = monitor2.taUt();
        Subsekvens mestForekommende = null;
        for(Subsekvens subsekvens: resultat.values()){
            if (mestForekommende == null || subsekvens.hentAntall() > mestForekommende.hentAntall()){
                mestForekommende = subsekvens;
            }
        }
        if (mestForekommende == null){
            return "Ingen subsekvenser funnet: ";
        } else {
            return "" + mestForekommende;
        }
   
    }
}
