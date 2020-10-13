import java.util.Comparator;

public class hTipoJaulaComparator implements Comparator<Habitat> {
    public int compare(Habitat tipoJaula1, Habitat tipoJaula2){

        if (tipoJaula1.tipoJaula.compareTo(tipoJaula2.tipoJaula) < 0){
            return -1;
        }else if(tipoJaula1.tipoJaula.equals(tipoJaula2.tipoJaula)){
            return 0;
        }else{
            return 1;
        }
    }
}