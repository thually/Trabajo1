import java.util.Comparator;

public class hTipoSueloComparator implements Comparator<Habitat> {
    public int compare(Habitat tipoSuelo1, Habitat tipoSuelo2){

        if (tipoSuelo1.tipoSuelo.compareTo(tipoSuelo2.tipoSuelo) < 0){
            return -1;
        }else if(tipoSuelo1.tipoSuelo.equals(tipoSuelo2.tipoSuelo)){
            return 0;
        }else{
            return 1;
        }
    }
}