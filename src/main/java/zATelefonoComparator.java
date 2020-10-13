import java.util.Comparator;

public class zATelefonoComparator implements Comparator<ZooAmigo> {
    public int compare(ZooAmigo telefono1, ZooAmigo telefono2){

        if (telefono1.nombre.compareTo(telefono2.nombre) < 0){
            return -1;
        }else if(telefono1.nombre.equals(telefono2.nombre)){
            return 0;
        }else{
            return 1;
        }
    }
}
