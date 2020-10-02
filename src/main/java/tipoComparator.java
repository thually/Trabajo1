import java.util.Comparator;

public class tipoComparator implements Comparator<Bioma> {
    public int compare(Bioma tipo1, Bioma tipo2){

        if (tipo1.tipo.compareTo(tipo2.tipo) < 0){
            return -1;
        }else if(tipo1.tipo.equals(tipo2.tipo)){
            return 0;
        }else{
            return 1;
        }
    }
}
