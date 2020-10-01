import java.util.Comparator;

public class nitComparator implements Comparator<Zoologico> {
    public int compare(Zoologico nit1, Zoologico nit2){

        if (nit1.nit.compareTo(nit2.nit) < 0){
            return -1;
        }else if(nit1.nit.equals(nit2.nit)){
            return 0;
        }else{
            return 1;
        }
    }
}