import java.util.Comparator;

public class aAlimentacionComparator implements Comparator<Animal> {
    public int compare(Animal alimentacion1, Animal alimentacion2){

        if (alimentacion1.alimentacion.compareTo(alimentacion2.alimentacion) < 0){
            return -1;
        }else if(alimentacion1.alimentacion.equals(alimentacion2.alimentacion)){
            return 0;
        }else{
            return 1;
        }
    }
}

