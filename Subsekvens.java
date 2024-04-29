
public class Subsekvens {
    public final String subsekvens;
    private int antall;

    public Subsekvens(int antall, String subsekvens){
        this.subsekvens = subsekvens;
        this.antall = antall;
    }

    public int hentAntall(){
        return antall;
    }

    public void endreAntall(){
        antall++;
    }

    public String hentString(){
        return subsekvens;
    }

    public String toString(){
        return "(" + subsekvens + "," + antall + ")";
    }
}