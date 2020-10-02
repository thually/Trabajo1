import java.util.Comparator;
//Comparador Id del habitat
public class IDHComparator implements Comparator<Habitat> {
    public int compare(Habitat id1, Habitat id2){
        if (id1.id < id2.id){
            return -1;
        }else if(id1.id == id2.id){
            return 0;
        }else{
            return 1;
        }
    }
}