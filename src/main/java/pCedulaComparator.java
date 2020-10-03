import java.util.Comparator;

public class pCedulaComparator implements Comparator<Profesional> {
    public int compare(Profesional cedula1, Profesional cedula2){
        if (cedula1.cedula < cedula2.cedula){
            return -1;
        }else if(cedula1.cedula == cedula2.cedula){
            return 0;
        }else{
            return 1;
        }
    }
}