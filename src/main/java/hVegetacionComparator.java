import java.util.Comparator;

public class hVegetacionComparator implements Comparator<Habitat> {
    public int compare(Habitat vegetacion1, Habitat vegetacion2){

        if (vegetacion1.vegetacion.compareTo(vegetacion2.vegetacion) < 0){
            return -1;
        }else if(vegetacion1.vegetacion.equals(vegetacion2.vegetacion)){
            return 0;
        }else{
            return 1;
        }
    }
}
