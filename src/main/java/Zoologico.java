import java.util.ArrayList;

public class Zoologico {
    int nit;
    String nombre;
    String siglas;
    String ciudad;
    Bioma bioma;
    ArrayList<Profesional> profesional = new ArrayList<>();
    ArrayList<ZooAmigo> zooAmigo = new ArrayList<>();

    public Zoologico(int nit, String nombre, String siglas, String ciudad) {
        this.nit = nit;
        this.nombre = nombre;
        this.siglas = siglas;
        this.ciudad = ciudad;
    }

    @Override
    public String toString() {
        return "Zoologico{" +
                "nit=" + nit +
                ", nombre='" + nombre + '\'' +
                ", siglas='" + siglas + '\'' +
                ", ciudad='" + ciudad + '\'' +
                ", bioma=" + bioma +
                '}';
    }
}
