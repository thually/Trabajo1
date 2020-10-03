import java.util.Comparator;

public class tAreaComparator implements Comparator<Tecnico> {
    public int compare(Tecnico area1, Tecnico area2){

        if (area1.area.compareTo(area2.area) < 0){
            return -1;
        }else if(area1.area.equals(area2.area)){
            return 0;
        }else{
            return 1;
        }
    }
}