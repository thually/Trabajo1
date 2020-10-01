import java.util.Comparator;

public class ciudadComparator implements Comparator<Zoologico> {
    public int compare(Zoologico ciudad1, Zoologico ciudad2){

        if (ciudad1.ciudad.compareTo(ciudad2.ciudad) < 0){
            return -1;
        }else if(ciudad1.ciudad.equals(ciudad2.ciudad)){
            return 0;
        }else{
            return 1;
        }
    }
}
