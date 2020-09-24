import java.util.ArrayList;

public class Tecnico {
    int cedula;
    String area; //Tipo de trabajo, Guarda, Aseo, etc
    String horaInicio;
    String horaSalida;
    ArrayList<Habitat> habitat;
    Zoologico zoologico;

    public Tecnico(int cedula, String area, String horaInicio, String horaSalida) {
        this.cedula = cedula;
        this.area = area;
        this.horaInicio = horaInicio;
        this.horaSalida = horaSalida;
    }

    @Override
    public String toString() {
        return "Tecnico{" +
                "cedula=" + cedula +
                ", area='" + area + '\'' +
                ", horaInicio='" + horaInicio + '\'' +
                ", horaSalida='" + horaSalida + '\'' +
                '}';
    }
}
