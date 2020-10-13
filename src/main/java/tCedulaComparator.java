import java.util.Comparator;

public class tCedulaComparator implements Comparator<Tecnico> {
    public int compare(Tecnico cedula1, Tecnico cedula2){
        if (cedula1.cedula < cedula2.cedula){
            return -1;
        }else if(cedula1.cedula == cedula2.cedula){
            return 0;
        }else{
            return 1;
        }
    }
}
