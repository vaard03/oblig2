import java.util.HashMap;

public class Monitor1{
    private static SubsekvensRegister register;

    public Monitor1(){
        register = new SubsekvensRegister();
    }

    public void settInn(HashMap<String, Subsekvens> subsekvenser){
        register.settInn(subsekvenser);
    }

    public void taUt(){
        register.taUt();
    }

    public int antallHash(){
        return register.antallHash();
    }

    public static HashMap<String, Subsekvens> fil(String filnavn){
        return SubsekvensRegister.fil(filnavn);
    }

    public static HashMap<String, Subsekvens> slaaSammen(HashMap<String, Subsekvens> hash1, HashMap<String, Subsekvens> hash2){
        return slaaSammen(hash1,hash2);
    }

    public HashMap<String, Subsekvens> hent(int indeks){
        return register.hent(indeks);
    }
}