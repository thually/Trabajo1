import java.util.Comparator;

public class pAreaComparator implements Comparator<Profesional> {
    public int compare(Profesional area1, Profesional area2){

        if (area1.area.compareTo(area2.area) < 0){
            return -1;
        }else if(area1.area.equals(area2.area)){
            return 0;
        }else{
            return 1;
        }
    }
}
