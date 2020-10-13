import java.util.Comparator;

public class bHumedadComparator implements Comparator<Bioma> {
    public int compare(Bioma humedad1, Bioma humedad2){

        if (humedad1.humedad.compareTo(humedad2.humedad) < 0){
            return -1;
        }else if(humedad1.humedad.equals(humedad2.humedad)){
            return 0;
        }else{
            return 1;
        }
    }
}
