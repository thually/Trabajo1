import java.util.ArrayList;

public class Profesional {
    int cedula;
    String area; //Tipo de profesional, ej: Veterinario, Administración
    String horaInicio;
    String horaSalida;
    ArrayList<Bioma> bioma = new ArrayList<>();
    Zoologico zoologico ;

    public Profesional(int cedula, String area, String horaInicio, String horaSalida) {
        this.cedula = cedula;
        this.area = area;
        this.horaInicio = horaInicio;
        this.horaSalida = horaSalida;
    }

    @Override
    public String toString() {
        return "Profesional{" +
                "cedula=" + cedula +
                ", area='" + area + '\'' +
                ", horaInicio='" + horaInicio + '\'' +
                ", horaSalida='" + horaSalida + '\'' +
                '}';
    }
}
