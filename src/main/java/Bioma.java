import java.util.ArrayList;

public class Bioma {
    int id;
    Double temperatura;
    String humedad; // Se utiliza una descripcion, seco, arido, etc
    String tipo;
    Zoologico zoologico;
    ArrayList<Profesional> profesional = new ArrayList<>();
    ArrayList<Habitat> habitat = new ArrayList<>();

    public Bioma(int id, Double temperatura, String humedad, String tipo) {
        this.id = id;
        this.temperatura = temperatura;
        this.humedad = humedad;
        this.tipo = tipo;
    }

    @Override
    public String toString() {
        return "Bioma{" +
                "id=" + id +
                ", temperatura=" + temperatura +
                ", humedad='" + humedad + '\'' +
                ", tipo='" + tipo + '\'' +
                '}';
    }
}
