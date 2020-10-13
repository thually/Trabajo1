import java.util.Comparator;

public class aEspecieComparator implements Comparator<Animal> {
    public int compare(Animal especie1, Animal especie2){

        if (especie1.especie.compareTo(especie2.especie) < 0){
            return -1;
        }else if(especie1.especie.equals(especie2.especie)){
            return 0;
        }else{
            return 1;
        }
    }
}

