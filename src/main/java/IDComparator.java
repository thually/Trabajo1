import java.util.Comparator;

public class IDComparator implements Comparator<Bioma> {
    public int compare(Bioma id1, Bioma id2){
        if (id1.id < id2.id){
            return -1;
        }else if(id1.id == id2.id){
            return 0;
        }else{
            return 1;
        }
    }
}