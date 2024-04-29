import java.util.HashMap;

public class LeseTraad implements Runnable{
    public Monitor2 monitor1;
    public String filnavn;

    public LeseTraad(Monitor2 monitor, String filnavn){
        this.monitor1 = monitor;
        this.filnavn = filnavn;
    }

    public void run(){
        HashMap<String, Subsekvens> hashMap = Monitor2.fil(filnavn);
        monitor1.settInn(hashMap);
    }
} 