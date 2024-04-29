import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Monitor2 {
    Lock hengeLaas = new ReentrantLock(true);
    SubsekvensRegister register;
    Condition finner2 = hengeLaas.newCondition();

    public Monitor2(){
        register = new SubsekvensRegister();
    }

    public void settInn(HashMap<String, Subsekvens> subsekvenser){
        hengeLaas.lock();
        try{
            register.settInn(subsekvenser);   
        }
        finally{
            hengeLaas.unlock();
        }
    }

    public HashMap<String, Subsekvens> taUt(){
        hengeLaas.lock();
        try{
            HashMap<String, Subsekvens> tattUt = register.taUt();
            finner2.signal();  
            return tattUt;
        }
        finally{
            hengeLaas.unlock();
        }
    }
    public int antallHash(){
        hengeLaas.lock();
        try{
            return register.antallHash(); 
        }
        finally{
            hengeLaas.unlock();
        }
    }

    public HashMap<String, Subsekvens> hent(int indeks) {
        hengeLaas.lock();
        try{
            return register.hent(indeks);
        }
        finally{
            hengeLaas.unlock();
        }
    }

    public static HashMap<String, Subsekvens> fil(String filnavn){
        return SubsekvensRegister.fil(filnavn);
    }

    public static HashMap<String, Subsekvens> slaaSammen(HashMap<String, Subsekvens> hash1, HashMap<String, Subsekvens> hash2){
        return SubsekvensRegister.slaaSammen(hash1,hash2);
    }

    public ArrayList<HashMap<String, Subsekvens>> hentUtTo() throws InterruptedException {
        hengeLaas.lock();
        try{
            while(register.antallHash() < 2){
                finner2.await();
            }
            ArrayList<HashMap<String, Subsekvens>> array = new ArrayList<>();
            array.add(register.taUt());
            array.add(register.taUt());
            
            return array;
        }
        finally{
            hengeLaas.unlock();
        }
    }
}