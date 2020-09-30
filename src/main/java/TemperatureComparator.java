import java.util.Comparator;

public class TemperatureComparator implements Comparator<Bioma> {
    public int compare(Bioma temperatura1, Bioma temperatura2){
        if (temperatura1.temperatura < temperatura2.temperatura){
            return -1;
        }else if(temperatura1.temperatura == temperatura2.temperatura){
            return 0;
        }else{
            return 1;
        }
    }
}