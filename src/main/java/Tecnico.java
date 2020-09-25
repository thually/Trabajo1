import java.util.ArrayList;

public class Tecnico {
    int cedula;
    String area; //Tipo de trabajo, Guarda, Aseo, etc
    String horaInicio;
    String horaSalida;
    ArrayList<Habitat> habitats = new ArrayList<>();
    Zoologico zoologico;

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

    @Override
    public String toString() {
        return "Tecnico{" +
                "cedula=" + cedula +
                ", area='" + area + '\'' +
                ", horaInicio='" + horaInicio + '\'' +
                ", horaSalida='" + horaSalida + '\'' +
                ", ID Habitats asociados= " + getHabitatsId() +
                ", NIT del Zoologico= " + zoologico.nit +
                '}';
    }
}
