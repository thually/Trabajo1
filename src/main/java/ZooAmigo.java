import java.util.ArrayList;

public class ZooAmigo {
    int cedula;
    String nombre;
    String telefono;
    ArrayList<Animal> animal;
    Zoologico zoologico;

    public ZooAmigo(int cedula, String nombre, String telefono) {
        this.cedula = cedula;
        this.nombre = nombre;
        this.telefono = telefono;
    }

    @Override
    public String toString() {
        return "ZooAmigo{" +
                "cedula=" + cedula +
                ", nombre='" + nombre + '\'' +
                ", telefono='" + telefono + '\'' +
                '}';
    }
}
