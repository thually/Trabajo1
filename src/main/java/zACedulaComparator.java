import java.util.Comparator;

public class zACedulaComparator implements Comparator<ZooAmigo> {
    public int compare(ZooAmigo cedula1, ZooAmigo cedula2){
        if (cedula1.cedula < cedula2.cedula){
            return -1;
        }else if(cedula1.cedula == cedula2.cedula){
            return 0;
        }else{
            return 1;
        }
    }
}
