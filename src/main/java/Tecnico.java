import java.util.ArrayList;
import java.util.Comparator;

public class Tecnico {
    int cedula;
    String area; //Tipo de trabajo, Guarda, Aseo, etc
    String horaInicio;
    String horaSalida;
    ArrayList<Habitat> habitats = new ArrayList<>();

    public Tecnico(int cedula, String area, String horaInicio, String horaSalida) {
        this.cedula = cedula;
        this.area = area;
        this.horaInicio = horaInicio;
        this.horaSalida = horaSalida;
    }

    public String getHabitatsId(){
        ArrayList<Integer> idHabitats = new ArrayList<>();
        for (Habitat habitat : habitats) {
            idHabitats.add(habitat.id);
        }
        return idHabitats.toString();
    }

    public static void setHabitats(Habitat nuevoHabitat, Tecnico tecnico){
        nuevoHabitat.tecnicos.add(tecnico);
        tecnico.habitats.add(nuevoHabitat);
    }

    public class cedulaComparator implements Comparator<Tecnico> {
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

    public class areaComparator implements Comparator<Tecnico> {
        public int compare(Tecnico area1, Tecnico area2){

            if (area1.area.compareTo(area2.area) < 0){
                return -1;
            }else if(area1.area.equals(area2.area)){
                return 0;
            }else{
                return 1;
            }
        }
    }

    public class horaInicioComparator implements Comparator<Tecnico> {
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

    public class horaSalidaComparator implements Comparator<Tecnico> {
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


    @Override
    public String toString() {
        return "Tecnico{" +
                "cedula=" + cedula +
                ", area='" + area + '\'' +
                ", horaInicio='" + horaInicio + '\'' +
                ", horaSalida='" + horaSalida + '\'' +
                ", ID Habitats asociados= " + getHabitatsId() +
                '}';
    }
}
