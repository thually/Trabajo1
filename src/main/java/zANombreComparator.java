import java.util.Comparator;

public class zANombreComparator implements Comparator<ZooAmigo> {
    public int compare(ZooAmigo nombre1, ZooAmigo nombre2){

        if (nombre1.nombre.compareTo(nombre2.nombre) < 0){
            return -1;
        }else if(nombre1.nombre.equals(nombre2.nombre)){
            return 0;
        }else{
            return 1;
        }
    }
}