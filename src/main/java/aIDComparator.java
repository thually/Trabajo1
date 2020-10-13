import java.util.Comparator;

public class aIDComparator implements Comparator<Animal> {
    public int compare(Animal id1, Animal id2){
        if (id1.id < id2.id){
            return -1;
        }else if(id1.id == id2.id){
            return 0;
        }else{
            return 1;
        }
    }
}
