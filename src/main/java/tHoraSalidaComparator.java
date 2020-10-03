import java.util.Comparator;

public class tHoraSalidaComparator implements Comparator<Tecnico> {
    public int compare(Tecnico horaSalida1, Tecnico horaSalida2){

        if (horaSalida1.horaSalida.compareTo(horaSalida2.horaSalida) < 0){
            return -1;
        }else if(horaSalida1.horaSalida.equals(horaSalida2.horaSalida)){
            return 0;
        }else{
            return 1;
        }
    }
}

