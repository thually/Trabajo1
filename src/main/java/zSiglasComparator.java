import java.util.Comparator;

public class zSiglasComparator implements Comparator<Zoologico> {
    public int compare(Zoologico siglas1, Zoologico siglas2){

        if (siglas1.siglas.compareTo(siglas2.siglas) < 0){
            return -1;
        }else if(siglas1.siglas.equals(siglas2.siglas)){
            return 0;
        }else{
            return 1;
        }
    }
}