import java.util.ArrayList;
import java.util.Comparator;

public class Profesional {
    int cedula;
    String area; //Tipo de profesional, ej: Veterinario, Administración
    String horaInicio;
    String horaSalida;
    ArrayList<Bioma> biomas = new ArrayList<>();
    Zoologico zoologico ;

    public Profesional(int cedula, String area, String horaInicio, String horaSalida) {
        this.cedula = cedula;
        this.area = area;
        this.horaInicio = horaInicio;
        this.horaSalida = horaSalida;
    }

    public static void setZoologicos(Zoologico zoologico, Profesional profesional) {
        profesional.zoologico = zoologico;
        zoologico.profesionales.add(profesional);
    }

    public String getBiomaID(){
        ArrayList<Integer> idBiomas = new ArrayList<>();
        for (Bioma bioma : biomas) {
            idBiomas.add(bioma.id);
        }
        return idBiomas.toString();
    }

    public static void setBiomas(Bioma nuevoBio, Profesional profesional){
        profesional.biomas.add(nuevoBio);
        nuevoBio.profesionales.add(profesional);
    }

    public class cedulaComparator implements Comparator<Profesional> {
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

    public class areaComparator implements Comparator<Profesional> {
        public int compare(Profesional area1, Profesional area2){

            if (area1.area.compareTo(area2.area) < 0){
                return -1;
            }else if(area1.area.equals(area2.area)){
                return 0;
            }else{
                return 1;
            }
        }
    }

    public class horaInicioComparator implements Comparator<Profesional> {
        public int compare(Profesional horaInicio1, Profesional horaInicio2){

            if (horaInicio1.horaInicio.compareTo(horaInicio2.horaInicio) < 0){
                return -1;
            }else if(horaInicio1.horaInicio.equals(horaInicio2.horaInicio)){
                return 0;
            }else{
                return 1;
            }
        }
    }

    public class horaSalidaComparator implements Comparator<Profesional> {
        public int compare(Profesional horaSalida1, Profesional horaSalida2){

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
        return "Profesional{" +
                "cedula=" + cedula +
                ", area='" + area + '\'' +
                ", horaInicio='" + horaInicio + '\'' +
                ", horaSalida='" + horaSalida + '\'' +
                ", ID de biomas asociados= " + getBiomaID() +
                ", ID del zoologico asociado= " + ((zoologico == null) ? "N/A" : zoologico.nit) +
                '}';
    }
}
