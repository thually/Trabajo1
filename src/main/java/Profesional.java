import java.util.ArrayList;

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
