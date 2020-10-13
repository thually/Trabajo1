import java.util.Comparator;

public class tHoraInicioComparator implements Comparator<Tecnico> {
    public int compare(Tecnico horaInicio1, Tecnico horaInicio2){

        if (horaInicio1.horaInicio.compareTo(horaInicio2.horaInicio) < 0){
            return -1;
        }else if(horaInicio1.horaInicio.equals(horaInicio2.horaInicio)){
            return 0;
        }else{
            return 1;
        }
    }
}
