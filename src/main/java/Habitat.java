import java.util.ArrayList;

public class Habitat {
    int id;
    String tipoSuelo; // Tierra, nevado, desertico, etc.
    String vegetacion; // Abundante, sin vegetacion, etc
    String tipoJaula; // Aviario, barrotes, malla, etc
    ArrayList<Animal> animal = new ArrayList<>();
    Bioma bioma;
    ArrayList<Tecnico> tecnico = new ArrayList<>();

    public Habitat(int id, String tipoSuelo, String vegetacion, String tipoJaula) {
        this.id = id;
        this.tipoSuelo = tipoSuelo;
        this.vegetacion = vegetacion;
        this.tipoJaula = tipoJaula;
    }

    @Override
    public String toString() {
        return "Habitat{" +
                "id=" + id +
                ", tipoSuelo='" + tipoSuelo + '\'' +
                ", vegetacion='" + vegetacion + '\'' +
                ", tipoJaula='" + tipoJaula + '\'' +
                '}';
    }
}
