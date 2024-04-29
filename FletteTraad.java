import java.util.ArrayList;
import java.util.HashMap;

public class FletteTraad implements Runnable{

    private Monitor2 monitor2;
    static int antFletteTraader = 0;
    

    public FletteTraad(Monitor2 monitor2){ 
        this.monitor2 = monitor2;
        antFletteTraader++;

    }
    public int hentID(){
        return antFletteTraader;
    }
    
    public void run(){
        while(monitor2.antallHash() > 1){
            try{
                ArrayList<HashMap <String, Subsekvens>> flettet = monitor2.hentUtTo();
                monitor2.settInn(Monitor2.slaaSammen(flettet.get(0), flettet.get(1)));
        
            } catch (InterruptedException e){
                System.out.println("Feil ved fletting: " + e.getMessage());
            }
        } 
    }
}
