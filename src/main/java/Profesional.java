import java.util.ArrayList;

public class Profesional {
    int cedula;
    String area; //Tipo de profesional, ej: Veterinario, Administración
    String horaInicio;
    String horaSalida;
    ArrayList<Bioma> bioma;
    Zoologico zoologico;

    public Profesional(int cedula, String area) {
        this.cedula = cedula;
        this.area = area;
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
